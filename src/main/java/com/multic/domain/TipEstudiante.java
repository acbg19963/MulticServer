package com.multic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipEstudiante.
 */
@Entity
@Table(name = "tip_estudiante")
public class TipEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tip", nullable = false)
    private String tip;

    @ManyToOne
    private Estudiante estudiante;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public TipEstudiante tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public TipEstudiante estudiante(Estudiante estudiante) {
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
        TipEstudiante tipEstudiante = (TipEstudiante) o;
        if (tipEstudiante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipEstudiante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipEstudiante{" +
            "id=" + getId() +
            ", tip='" + getTip() + "'" +
            "}";
    }
}
