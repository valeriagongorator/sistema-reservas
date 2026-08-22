package com.mycompany.sistema.reservas.dominio;

import com.mycompany.sistema.reservas.dominio.modelo.Cliente;
import com.mycompany.sistema.reservas.dominio.modelo.Email;
import com.mycompany.sistema.reservas.dominio.modelo.RangoFechas;
import com.mycompany.sistema.reservas.dominio.modelo.Reserva;
import com.mycompany.sistema.reservas.dominio.modelo.Habitacion;
import com.mycompany.sistema.reservas.dominio.modelo.NumeroHabitacion;
import com.mycompany.sistema.reservas.dominio.modelo.CapacidadMaxima;
import com.mycompany.sistema.reservas.dominio.modelo.EstadoHabitacion;
import java.time.LocalDateTime;
import notificacion.EmailNotificadorService;
import notificacion.NotificadorService;
import notificacion.NotificacionWhatsApp;
import notificacion.NotificacionPush;
import politicas.DescuentoCorporativo;
import politicas.DescuentoEstadiaLarga;
import politicas.DescuentoTemporadaBaja;
import politicas.PoliticaDescuento;
import repositorio.ReservaArchivoRepository;
import repositorio.ReservaRepository;
import servicio.ConfirmacionReservaService;

/**
 *
 * @author daferarte
 */
public class mainsemana2 {

    public static void main(String[] args) {

        // 1. Instanciar Entidades y Value Objects de Semana 1
        Cliente cliente = new Cliente("Beatriz Morales", new Email("beatriz@empresa.com"));

        NumeroHabitacion numeroHabitacion = new NumeroHabitacion("224");

        CapacidadMaxima capacidadMaxima = new CapacidadMaxima(2);

        Habitacion habitacion = new Habitacion(numeroHabitacion, capacidadMaxima, EstadoHabitacion.DISPONIBLE);

        RangoFechas periodo = new RangoFechas(
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(9)
        );

        Reserva reserva = new Reserva(cliente, periodo, habitacion);

        // 2. Configurar la infraestructura deseada
        ReservaRepository repositorio = new ReservaArchivoRepository();

        NotificadorService notificador = new EmailNotificadorService();

        // Nuevo canal de notificación por WhatsApp
        NotificadorService whatsapp = new NotificacionWhatsApp();

        // Nuevo canal de notificación Push / App
        NotificadorService push = new NotificacionPush();

        // 3. Crear el servicio usando Push / App como canal de notificación
        ConfirmacionReservaService servicio = new ConfirmacionReservaService(repositorio, push);

        // 4. Crear las políticas de descuento
        PoliticaDescuento descuentoCorporativo = new DescuentoCorporativo();

        PoliticaDescuento descuentoEstadiaLarga = new DescuentoEstadiaLarga(periodo);

        PoliticaDescuento descuentoTemporadaBaja = new DescuentoTemporadaBaja();

        // 5. Elegir la política que se desea aplicar
        // En este caso se aplica el descuento de temporada baja del 15%.
        double precioFinal = servicio.procesar(reserva, descuentoTemporadaBaja, 3000);

        System.out.println("Proceso finalizado. Total pagado: $" + precioFinal);
    }
}