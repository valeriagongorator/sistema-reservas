/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

/**
 *
 * @author daferarte
 */

import java.util.UUID;

public class Cliente {
    private final UUID id;             // Identidad inmutable
    private final String nombre;       // Inmutable
    private Email email;               // Value Object
    private boolean activo;
    private int penalizaciones;
    
    public Cliente(String nombre, Email email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (email == null) {
            throw new IllegalArgumentException("El email del cliente es obligatorio");
        }
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.email = email;
        this.activo = true;
        this.penalizaciones = 0;
    }
    
    public void registrarPenalizacion() {
        this.penalizaciones++;
        // Regla: Si acumula 3 o más penalizaciones, se suspende automáticamente
        if (this.penalizaciones >= 3) {
            this.activo = false;
        }
    }
    
    public void reactivar() {
        this.activo = true;
        this.penalizaciones = 0;
    }
    
    public void actualizarEmail(Email nuevoEmail) {
        if (nuevoEmail == null) {
            throw new IllegalArgumentException("El nuevo email no puede ser nulo");
        }
        this.email = nuevoEmail;
    }

    public boolean puedeRealizarReservas() {
        return this.activo;
    }
    
    // Getters de lectura (Sin setters públicos)
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public Email getEmail() { return email; }
    public boolean isActivo() { return activo; }
    public int getPenalizaciones() { return penalizaciones; }
    public String nombre() {return nombre; }
}
