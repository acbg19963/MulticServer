package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.multic.domain.enumeration.DIFICULTAD;

/**
 * A Actividad.
 */
@Entity
@Table(name = "actividad")
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enunciado")
    private String enunciado;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad")
    private DIFICULTAD dificultad;

    @Column(name = "es_quiz")
    private Boolean esQuiz;

    @ManyToOne
    private Planeta planeta;

    @ManyToOne
    private Profesor profesor;

    @OneToMany(mappedBy = "actividad")
    @JsonIgnore
    private Set<Respuesta> respuestas = new HashSet<>();

    @OneToMany(mappedBy = "actividad")
    @JsonIgnore
    private Set<Ayuda> ayudas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public Actividad enunciado(String enunciado) {
        this.enunciado = enunciado;
        return this;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public DIFICULTAD getDificultad() {
        return dificultad;
    }

    public Actividad dificultad(DIFICULTAD dificultad) {
        this.dificultad = dificultad;
        return this;
    }

    public void setDificultad(DIFICULTAD dificultad) {
        this.dificultad = dificultad;
    }

    public Boolean isEsQuiz() {
        return esQuiz;
    }

    public Actividad esQuiz(Boolean esQuiz) {
        this.esQuiz = esQuiz;
        return this;
    }

    public void setEsQuiz(Boolean esQuiz) {
        this.esQuiz = esQuiz;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public Actividad planeta(Planeta planeta) {
        this.planeta = planeta;
        return this;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public Actividad profesor(Profesor profesor) {
        this.profesor = profesor;
        return this;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Set<Respuesta> getRespuestas() {
        return respuestas;
    }

    public Actividad respuestas(Set<Respuesta> respuestas) {
        this.respuestas = respuestas;
        return this;
    }

    public Actividad addRespuestas(Respuesta respuesta) {
        this.respuestas.add(respuesta);
        respuesta.setActividad(this);
        return this;
    }

    public Actividad removeRespuestas(Respuesta respuesta) {
        this.respuestas.remove(respuesta);
        respuesta.setActividad(null);
        return this;
    }

    public void setRespuestas(Set<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public Set<Ayuda> getAyudas() {
        return ayudas;
    }

    public Actividad ayudas(Set<Ayuda> ayudas) {
        this.ayudas = ayudas;
        return this;
    }

    public Actividad addAyudas(Ayuda ayuda) {
        this.ayudas.add(ayuda);
        ayuda.setActividad(this);
        return this;
    }

    public Actividad removeAyudas(Ayuda ayuda) {
        this.ayudas.remove(ayuda);
        ayuda.setActividad(null);
        return this;
    }

    public void setAyudas(Set<Ayuda> ayudas) {
        this.ayudas = ayudas;
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
        Actividad actividad = (Actividad) o;
        if (actividad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actividad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Actividad{" +
            "id=" + getId() +
            ", enunciado='" + getEnunciado() + "'" +
            ", dificultad='" + getDificultad() + "'" +
            ", esQuiz='" + isEsQuiz() + "'" +
            "}";
    }
}
