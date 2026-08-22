/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

import java.util.UUID;

/**
 *
 * @author daferarte
 */
public class Reserva {
    private final UUID id;            // Inmutable 
    private final Cliente cliente;    // Asociación directa con la entidad Cliente
    private RangoFechas periodo;      // Value Object
    private EstadoReserva estado;
    private final Habitacion habitacion; 
    
    public Reserva(Cliente cliente, RangoFechas periodo, Habitacion habitacion) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (!cliente.puedeRealizarReservas()) {
            throw new IllegalStateException("El cliente '" + cliente.getNombre() + "' no está habilitado para realizar reservas");
        }
        if (periodo == null) {
            throw new IllegalArgumentException("El periodo de la reserva es obligatorio");
        }
        
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitacion es obligatoria");
        }

        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.periodo = periodo;
        this.estado = EstadoReserva.PENDIENTE;
        this.habitacion = habitacion;
    }
    
    public void confirmar() {
        if (this.estado == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("No se puede confirmar una reserva que ha sido cancelada");
        }
        this.estado = EstadoReserva.CONFIRMADA;
    }
    
    public void cancelar() {
        if (this.estado == EstadoReserva.CONFIRMADA) {
            // reglas cancelar
        }
        this.estado = EstadoReserva.CANCELADA;
    }
    
    public UUID getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public RangoFechas getPeriodo() { return periodo; }
    public EstadoReserva getEstado() { return estado; }
    public Habitacion getHabitacion() {return habitacion; }
    
    }

