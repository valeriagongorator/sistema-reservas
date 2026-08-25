# Sistema de Reservas

Este proyecto es un sistema de reservas de hotel en Java. Está organizado en paquetes, cada uno con una responsabilidad distinta, para que el código sea más fácil de mantener y de extender. Aquí explico qué hace cada parte y por qué unas dependen de otras.

---
# Diagrama de Clases
<img width="3344" height="1381" alt="Sistema reserva" src="https://github.com/user-attachments/assets/40f3391f-637e-46db-bf8c-797a4f8a38ec" />

---

## `dominio.modelo` — Las entidades del negocio

Este paquete tiene las clases que representan los conceptos principales: clientes, reservas, habitaciones y fechas.

- **`Cliente`**: guarda el nombre, el email y si el cliente está activo. Tiene la lógica de penalizaciones: si acumula 3, se suspende solo.
- **`Reserva`**: es la clase principal. Usa a `Cliente`, `RangoFechas` y `Habitacion` porque una reserva no tiene sentido sin esos tres datos — necesita saber quién reserva, cuándo y en qué habitación. También controla su propio estado (pendiente, confirmada, cancelada) y no permite, por ejemplo, confirmar una reserva ya cancelada.
- **`Habitacion`**: tiene número, capacidad máxima y estado. Usa `NumeroHabitacion` y `CapacidadMaxima` en vez de un simple `String` o `int` para poder validar esos datos en un solo lugar y no repetir esa validación en cada clase que los use.
- **`RangoFechas`, `Email`, `NumeroHabitacion`, `CapacidadMaxima`**: son *records* (objetos de valor). Cada uno valida sus propios datos al crearse — por ejemplo `Email` revisa el formato, `RangoFechas` revisa que la fecha de fin no sea antes que la de inicio. Se usan dentro de `Cliente`, `Reserva` y `Habitacion` justamente para no tener que repetir esas validaciones ahí.
- **`EstadoReserva` / `EstadoHabitacion`**: enums para los estados posibles, en vez de usar texto libre.
- **`ReservaAnemica`**: esta clase es un ejemplo de lo contrario a `Reserva` — solo tiene getters y setters, sin ninguna validación ni lógica propia. Está para comparar el antes y el después.

---

## `politicas` — Cálculo de descuentos

Contiene la interfaz `PoliticaDescuento`, que define un solo método: `aplicarDescuento`. Cada tipo de descuento la implementa:

- `DescuentoVIP` (20%)
- `DescuentoCorporativo` (30%)
- `DescuentoTemporadaBaja` (15%)
- `DescuentoEstadiaLarga` (25%, solo si la estadía dura más de 7 días — por eso esta clase importa `RangoFechas`, la necesita para calcular la duración)
- `DescuentoRegular` (sin descuento)

Se usa una interfaz en vez de meter todos los cálculos en una sola clase con `if/else` para que agregar un descuento nuevo no implique modificar código que ya existe, solo se crea una clase más.

---

## `notificacion` — Envío de mensajes al cliente

Misma lógica que el paquete anterior: una interfaz `NotificadorService` con el método `notificar(cliente, mensaje)`, e implementaciones distintas:

- `EmailNotificadorService`
- `SmsNotificadorService`
- `NotificacionWhatsApp`
- `NotificacionPush`

Todas importan `Cliente` porque necesitan sus datos (email, nombre) para armar el mensaje. Al depender de la interfaz y no de una implementación específica, el resto del sistema puede cambiar el canal de notificación sin tocar nada más.

---

## `repositorio` — Guardar la reserva

Interfaz `ReservaRepository` con el método `guardar(reserva)`. Tiene dos implementaciones:

- `ReservaMemoriaRepository`: guarda las reservas en una lista en memoria.
- `ReservaArchivoRepository`: las guarda en el archivo `reservas.txt`.

Ambas importan `Reserva` porque es lo que reciben para guardar. La razón de usar una interfaz aquí es que el servicio que guarda reservas no necesita saber si se están guardando en memoria, en un archivo o después en una base de datos — solo necesita el método `guardar`.

---

## `servicio` — Coordina todo lo anterior

- **`ConfirmacionReservaService`**: recibe en su constructor un `ReservaRepository` y un `NotificadorService` (se le pasan ya construidos, en vez de crearlos él mismo). También importa `PoliticaDescuento` y `Reserva` porque su método `procesar()` hace estas cuatro cosas en orden: confirma la reserva, calcula el precio con la política que le pasen, la guarda usando el repositorio, y notifica al cliente usando el notificador. Es el punto donde se juntan los cuatro paquetes anteriores.
- **`ProcesadorReservasService`**: hace básicamente lo mismo, pero sin usar interfaces — calcula el precio con `if/else` según el tipo de cliente, escribe directo a un archivo con `FileWriter`, y decide el canal de notificación también con `if/else`. Está para mostrar la diferencia frente a `ConfirmacionReservaService`: si mañana cambia la lógica de descuentos o el canal de notificación, aquí hay que editar esta misma clase; en el otro servicio, no.

---

## Por qué está organizado así

La idea de separar en paquetes con interfaces es que cada parte del sistema (descuentos, notificaciones, guardado) pueda cambiar sin afectar a las demás. `ConfirmacionReservaService` no importa las clases concretas (`DescuentoVIP`, `EmailNotificadorService`, `ReservaArchivoRepository`), importa las interfaces (`PoliticaDescuento`, `NotificadorService`, `ReservaRepository`) y recibe la implementación que necesite por parámetro o por constructor. Eso permite, por ejemplo, cambiar de guardar en archivo a guardar en memoria sin tocar el servicio.

---

## Diagrama UML

El diagrama de clases completo, organizado por paquetes y con todas las relaciones (composición, agregación e implementación), está en `diagrama-uml.puml`, listo para pegar en draw.io o en el editor online de PlantUML.

---

## Cómo correrlo

Es un proyecto Maven:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.sistema.reservas.dominio.SistemaReservasDominio"
```

También está `mainsemana2`, que arma el flujo completo (cliente, habitación, reserva, descuento y notificación) para ver cómo se conectan todas las piezas en un solo caso.
