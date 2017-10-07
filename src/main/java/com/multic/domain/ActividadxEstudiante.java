package com.multic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActividadxEstudiante.
 */
@Entity
@Table(name = "actividadx_estudiante")
public class ActividadxEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "acerto")
    private Boolean acerto;

    @Column(name = "cantayuda")
    private Integer cantayuda;

    @Column(name = "tiempo")
    private Integer tiempo;

    @ManyToOne
    private Estudiante estudiante;

    @ManyToOne
    private Actividad actividad;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAcerto() {
        return acerto;
    }

    public ActividadxEstudiante acerto(Boolean acerto) {
        this.acerto = acerto;
        return this;
    }

    public void setAcerto(Boolean acerto) {
        this.acerto = acerto;
    }

    public Integer getCantayuda() {
        return cantayuda;
    }

    public ActividadxEstudiante cantayuda(Integer cantayuda) {
        this.cantayuda = cantayuda;
        return this;
    }

    public void setCantayuda(Integer cantayuda) {
        this.cantayuda = cantayuda;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public ActividadxEstudiante tiempo(Integer tiempo) {
        this.tiempo = tiempo;
        return this;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public ActividadxEstudiante estudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public ActividadxEstudiante actividad(Actividad actividad) {
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
        ActividadxEstudiante actividadxEstudiante = (ActividadxEstudiante) o;
        if (actividadxEstudiante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actividadxEstudiante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActividadxEstudiante{" +
            "id=" + getId() +
            ", acerto='" + isAcerto() + "'" +
            ", cantayuda='" + getCantayuda() + "'" +
            ", tiempo='" + getTiempo() + "'" +
            "}";
    }
}
