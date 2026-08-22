/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

/**
 *
 * @author valeriaestefaniagongoratorres
 */
public record CapacidadMaxima(int capacidad) {

    public CapacidadMaxima {
        if (capacidad < 1) {
            throw new IllegalArgumentException("La capacidad máxima debe ser de al menos 1 persona");
        }
    }
}

