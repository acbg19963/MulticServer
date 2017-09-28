package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Universo.
 */
@Entity
@Table(name = "universo")
public class Universo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "universo")
    @JsonIgnore
    private Set<Avatar> avatares = new HashSet<>();

    @OneToMany(mappedBy = "universo")
    @JsonIgnore
    private Set<Planeta> planetas = new HashSet<>();

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

    public Universo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Universo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Avatar> getAvatares() {
        return avatares;
    }

    public Universo avatares(Set<Avatar> avatars) {
        this.avatares = avatars;
        return this;
    }

    public Universo addAvatares(Avatar avatar) {
        this.avatares.add(avatar);
        avatar.setUniverso(this);
        return this;
    }

    public Universo removeAvatares(Avatar avatar) {
        this.avatares.remove(avatar);
        avatar.setUniverso(null);
        return this;
    }

    public void setAvatares(Set<Avatar> avatars) {
        this.avatares = avatars;
    }

    public Set<Planeta> getPlanetas() {
        return planetas;
    }

    public Universo planetas(Set<Planeta> planetas) {
        this.planetas = planetas;
        return this;
    }

    public Universo addPlanetas(Planeta planeta) {
        this.planetas.add(planeta);
        planeta.setUniverso(this);
        return this;
    }

    public Universo removePlanetas(Planeta planeta) {
        this.planetas.remove(planeta);
        planeta.setUniverso(null);
        return this;
    }

    public void setPlanetas(Set<Planeta> planetas) {
        this.planetas = planetas;
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
        Universo universo = (Universo) o;
        if (universo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), universo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Universo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
