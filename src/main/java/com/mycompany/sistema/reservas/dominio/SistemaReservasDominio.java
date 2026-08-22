/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema.reservas.dominio;

import com.mycompany.sistema.reservas.dominio.modelo.Cliente;
import com.mycompany.sistema.reservas.dominio.modelo.Email;
import com.mycompany.sistema.reservas.dominio.modelo.Reserva;
import com.mycompany.sistema.reservas.dominio.modelo.RangoFechas;
import com.mycompany.sistema.reservas.dominio.modelo.ReservaAnemica;
import com.mycompany.sistema.reservas.dominio.modelo.EstadoReserva;
import com.mycompany.sistema.reservas.dominio.modelo.Habitacion;
import com.mycompany.sistema.reservas.dominio.modelo.NumeroHabitacion;
import com.mycompany.sistema.reservas.dominio.modelo.CapacidadMaxima;
import com.mycompany.sistema.reservas.dominio.modelo.EstadoHabitacion;

import java.time.LocalDateTime;

/**
 *
 * @author daferarte
 */
public class SistemaReservasDominio {

    public static void main(String[] args) {
//        ReservaAnemica reserva = new ReservaAnemica();
//        
//        // PROBLEMA 
//        reserva.setFechaInicio(LocalDateTime.now().plusDays(5));
//        reserva.setFechaFin(LocalDateTime.now().plusDays(2)); // ¡Invalido!
//
//        // PROBLEMA 
//        reserva.setEstado("CONFIRMADA");
//        reserva.setEstado("CANCELADA");
//        reserva.setEstado("CONFIRMADA"); // ¡Una reserva cancelada no debería reconfirmarse sin validar!
//        
//        System.out.println("Reserva creada con estado: " + reserva.getEstado());
        
        try {
            
            Email email = new Email("juan.perez@empresa.com");
            Cliente cliente = new Cliente("Juan Pérez", email);
            System.out.println("Cliente creado: " + cliente.getNombre() + " (Activo: " + cliente.isActivo() + ")");
            
            LocalDateTime inicio = LocalDateTime.now().plusDays(1);
            LocalDateTime fin = LocalDateTime.now().plusDays(3);
            RangoFechas periodo = new RangoFechas(inicio, fin);

            // Se crea la habitación porque ahora toda reserva debe tener una habitación asociada.
            NumeroHabitacion numeroHabitacion = new NumeroHabitacion("224");
            CapacidadMaxima capacidadMaxima = new CapacidadMaxima(2);
            Habitacion habitacion = new Habitacion(numeroHabitacion, capacidadMaxima, EstadoHabitacion.DISPONIBLE);

            // La reserva ahora necesita cliente, periodo y habitación.
            Reserva reserva = new Reserva(cliente, periodo, habitacion);
            
            System.out.println("Reserva creada con ID: " + reserva.getId() + " - Estado: " + reserva.getEstado());

            reserva.confirmar();
            System.out.println("Estado tras confirmar: " + reserva.getEstado());

            reserva.cancelar();
            System.out.println("Estado tras cancelar: " + reserva.getEstado());

            reserva.confirmar(); 

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}