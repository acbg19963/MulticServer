package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Planeta.
 */
@Entity
@Table(name = "planeta")
public class Planeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "progreso")
    private Integer progreso;

    @ManyToOne
    private Universo universo;

    @OneToMany(mappedBy = "planeta")
    @JsonIgnore
    private Set<Actividad> actividades = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Planeta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Planeta descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public Planeta progreso(Integer progreso) {
        this.progreso = progreso;
        return this;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public Universo getUniverso() {
        return universo;
    }

    public Planeta universo(Universo universo) {
        this.universo = universo;
        return this;
    }

    public void setUniverso(Universo universo) {
        this.universo = universo;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public Planeta actividades(Set<Actividad> actividads) {
        this.actividades = actividads;
        return this;
    }

    public Planeta addActividades(Actividad actividad) {
        this.actividades.add(actividad);
        actividad.setPlaneta(this);
        return this;
    }

    public Planeta removeActividades(Actividad actividad) {
        this.actividades.remove(actividad);
        actividad.setPlaneta(null);
        return this;
    }

    public void setActividades(Set<Actividad> actividads) {
        this.actividades = actividads;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Planeta planeta = (Planeta) o;
        if (planeta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planeta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Planeta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", progreso='" + getProgreso() + "'" +
            "}";
    }
}
