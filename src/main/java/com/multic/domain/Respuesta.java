package com.multic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Respuesta.
 */
@Entity
@Table(name = "respuesta")
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enunciado")
    private String enunciado;

    @Column(name = "acertado")
    private Boolean acertado;

    @ManyToOne
    private Actividad actividad;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public Respuesta enunciado(String enunciado) {
        this.enunciado = enunciado;
        return this;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Boolean isAcertado() {
        return acertado;
    }

    public Respuesta acertado(Boolean acertado) {
        this.acertado = acertado;
        return this;
    }

    public void setAcertado(Boolean acertado) {
        this.acertado = acertado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public Respuesta actividad(Actividad actividad) {
        this.actividad = actividad;
        return this;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
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
        Respuesta respuesta = (Respuesta) o;
        if (respuesta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), respuesta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Respuesta{" +
            "id=" + getId() +
            ", enunciado='" + getEnunciado() + "'" +
            ", acertado='" + isAcertado() + "'" +
            "}";
    }
}
