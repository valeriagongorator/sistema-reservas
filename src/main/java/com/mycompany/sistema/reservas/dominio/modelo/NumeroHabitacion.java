/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

/**
 *
 * @author valeriaestefaniagongoratorres
 */
public record NumeroHabitacion(String numHabitacion) {

    public NumeroHabitacion {
        if (numHabitacion == null || numHabitacion.isBlank()) {
            throw new IllegalArgumentException("El número de habitación es obligatorio");
        }
    }
}