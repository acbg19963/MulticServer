/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.multic.service.dto;

import com.multic.domain.Actividad;

/**
 *
 * @author andre
 */
public class ProgresoEstudiante { 
    
    private Actividad actividad;
    private Integer ayudas;
    private Integer tiempo;
    private boolean terminado = false;

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }
    
    
    
    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Integer getAyudas() {
        return ayudas;
    }

    public void setAyudas(Integer ayudas) {
        this.ayudas = ayudas;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }
    
    
    
}
