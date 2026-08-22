/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

/**
 *
 * @author valeriaestefaniagongoratorres
 */
public class Habitacion {
    
    private final NumeroHabitacion numeroHabitacion;
    private final CapacidadMaxima capacidadMaxima;
    private final EstadoHabitacion estadoHabitacion;
    
    
    public Habitacion(NumeroHabitacion numeroHabitacion, CapacidadMaxima capacidadMaxima, EstadoHabitacion estadoHabitacion) {
        
        if (numeroHabitacion == null) {
            throw new IllegalArgumentException("El número de habitacion es obligatorio");
     
        } 
        
        if (capacidadMaxima == null) {
            throw new IllegalArgumentException("La capacidad maxima es bligatoria");
            
        }
        
        if (estadoHabitacion == null) {
            throw new IllegalArgumentException("El estado de la habitacion es obligatorio");
        }
        
        this.numeroHabitacion = numeroHabitacion;
        this.capacidadMaxima = capacidadMaxima;
        this.estadoHabitacion = estadoHabitacion;
    }
    
    public NumeroHabitacion getNumeroHabitacion() {return numeroHabitacion; }
    public CapacidadMaxima getCapacidadMaxima() {return capacidadMaxima; }
    public EstadoHabitacion getEstadoHabitacion() {return estadoHabitacion; }
   
    
}
    

