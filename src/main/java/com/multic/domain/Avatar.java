package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Column(name = "cabello")
    private String cabello;

    @Column(name = "ropa")
    private String ropa;

    @Column(name = "gafas")
    private String gafas;

    @ManyToOne
    private Universo universo;

    @OneToOne(mappedBy = "avatar")
    @JsonIgnore
    private Estudiante estudiante;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
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

    public String getCabello() {
        return cabello;
    }

    public Avatar cabello(String cabello) {
        this.cabello = cabello;
        return this;
    }

    public void setCabello(String cabello) {
        this.cabello = cabello;
    }

    public String getRopa() {
        return ropa;
    }

    public Avatar ropa(String ropa) {
        this.ropa = ropa;
        return this;
    }

    public void setRopa(String ropa) {
        this.ropa = ropa;
    }

    public String getGafas() {
        return gafas;
    }

    public Avatar gafas(String gafas) {
        this.gafas = gafas;
        return this;
    }

    public void setGafas(String gafas) {
        this.gafas = gafas;
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
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

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
            ", gafas='" + getGafas() + "'" +
            "}";
    }
}
