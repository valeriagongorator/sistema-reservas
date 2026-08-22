/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author daferarte
 */
public record RangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {

    public RangoFechas {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                "La fecha de fin no puede ser anterior a la fecha de inicio"
            );
        }
    }

    public boolean incluye(LocalDateTime fecha) {
        return !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
    }

    public long duracionEnDias() {
        return Duration.between(fechaInicio, fechaFin).toDays();
    }
}