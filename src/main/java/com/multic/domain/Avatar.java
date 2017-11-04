package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.multic.domain.enumeration.COLOR;

/**
 * A Avatar.
 */
@Entity
@Table(name = "avatar")
public class Avatar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monedas")
    private Integer monedas;

    @Column(name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "cabello")
    private COLOR cabello;

    @Enumerated(EnumType.STRING)
    @Column(name = "ropa")
    private COLOR ropa;

    @ManyToOne
    private Universo universo;

    @OneToOne(mappedBy = "avatar")
    @JsonIgnore
    private Estudiante estudiante;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonedas() {
        return monedas;
    }

    public Avatar monedas(Integer monedas) {
        this.monedas = monedas;
        return this;
    }

    public void setMonedas(Integer monedas) {
        this.monedas = monedas;
    }

    public String getNombre() {
        return nombre;
    }

    public Avatar nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public COLOR getCabello() {
        return cabello;
    }

    public Avatar cabello(COLOR cabello) {
        this.cabello = cabello;
        return this;
    }

    public void setCabello(COLOR cabello) {
        this.cabello = cabello;
    }

    public COLOR getRopa() {
        return ropa;
    }

    public Avatar ropa(COLOR ropa) {
        this.ropa = ropa;
        return this;
    }

    public void setRopa(COLOR ropa) {
        this.ropa = ropa;
    }

    public Universo getUniverso() {
        return universo;
    }

    public Avatar universo(Universo universo) {
        this.universo = universo;
        return this;
    }

    public void setUniverso(Universo universo) {
        this.universo = universo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Avatar estudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Avatar avatar = (Avatar) o;
        if (avatar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avatar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Avatar{" +
            "id=" + getId() +
            ", monedas='" + getMonedas() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", cabello='" + getCabello() + "'" +
            ", ropa='" + getRopa() + "'" +
            "}";
    }
}
