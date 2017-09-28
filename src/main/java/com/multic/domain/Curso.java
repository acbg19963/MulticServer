package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany
    @JoinTable(name = "curso_profesores",
               joinColumns = @JoinColumn(name="cursos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="profesores_id", referencedColumnName="id"))
    private Set<Profesor> profesores = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private Set<Estudiante> estudiantes = new HashSet<>();

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

    public Curso nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Profesor> getProfesores() {
        return profesores;
    }

    public Curso profesores(Set<Profesor> profesors) {
        this.profesores = profesors;
        return this;
    }

    public Curso addProfesores(Profesor profesor) {
        this.profesores.add(profesor);
        profesor.getCursos().add(this);
        return this;
    }

    public Curso removeProfesores(Profesor profesor) {
        this.profesores.remove(profesor);
        profesor.getCursos().remove(this);
        return this;
    }

    public void setProfesores(Set<Profesor> profesors) {
        this.profesores = profesors;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public Curso estudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        return this;
    }

    public Curso addEstudiantes(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.setCurso(this);
        return this;
    }

    public Curso removeEstudiantes(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.setCurso(null);
        return this;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
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
        Curso curso = (Curso) o;
        if (curso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
