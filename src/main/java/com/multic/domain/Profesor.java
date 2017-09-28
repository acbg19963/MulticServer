package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profesor.
 */
@Entity
@Table(name = "profesor")
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "materia")
    private String materia;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private Set<Actividad> actividades = new HashSet<>();

    @ManyToMany(mappedBy = "profesores")
    @JsonIgnore
    private Set<Curso> cursos = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public Profesor materia(String materia) {
        this.materia = materia;
        return this;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public User getUsuario() {
        return usuario;
    }

    public Profesor usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public Profesor actividades(Set<Actividad> actividads) {
        this.actividades = actividads;
        return this;
    }

    public Profesor addActividades(Actividad actividad) {
        this.actividades.add(actividad);
        actividad.setProfesor(this);
        return this;
    }

    public Profesor removeActividades(Actividad actividad) {
        this.actividades.remove(actividad);
        actividad.setProfesor(null);
        return this;
    }

    public void setActividades(Set<Actividad> actividads) {
        this.actividades = actividads;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public Profesor cursos(Set<Curso> cursos) {
        this.cursos = cursos;
        return this;
    }

    public Profesor addCursos(Curso curso) {
        this.cursos.add(curso);
        curso.getProfesores().add(this);
        return this;
    }

    public Profesor removeCursos(Curso curso) {
        this.cursos.remove(curso);
        curso.getProfesores().remove(this);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
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
        Profesor profesor = (Profesor) o;
        if (profesor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profesor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profesor{" +
            "id=" + getId() +
            ", materia='" + getMateria() + "'" +
            "}";
    }
}
