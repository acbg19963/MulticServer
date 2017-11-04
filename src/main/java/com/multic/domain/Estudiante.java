package com.multic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.multic.domain.enumeration.Escenario;

/**
 * A Estudiante.
 */
@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNac;

    @Column(name = "colegio")
    private String colegio;

    @Enumerated(EnumType.STRING)
    @Column(name = "escenario")
    private Escenario escenario;

    @Column(name = "genero")
    private String genero;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToOne
    @JoinColumn(unique = true)
    private Avatar avatar;

    @ManyToOne
    private Curso curso;

    @OneToMany(mappedBy = "estudiante")
    @JsonIgnore
    private Set<TipEstudiante> tips = new HashSet<>();

    @OneToMany(mappedBy = "estudiante")
    @JsonIgnore
    private Set<ActividadxEstudiante> actividadesxEstus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public Estudiante fechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
        return this;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getColegio() {
        return colegio;
    }

    public Estudiante colegio(String colegio) {
        this.colegio = colegio;
        return this;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public Escenario getEscenario() {
        return escenario;
    }

    public Estudiante escenario(Escenario escenario) {
        this.escenario = escenario;
        return this;
    }

    public void setEscenario(Escenario escenario) {
        this.escenario = escenario;
    }

    public String getGenero() {
        return genero;
    }

    public Estudiante genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public User getUsuario() {
        return usuario;
    }

    public Estudiante usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public Estudiante avatar(Avatar avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Curso getCurso() {
        return curso;
    }

    public Estudiante curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<TipEstudiante> getTips() {
        return tips;
    }

    public Estudiante tips(Set<TipEstudiante> tipEstudiantes) {
        this.tips = tipEstudiantes;
        return this;
    }

    public Estudiante addTips(TipEstudiante tipEstudiante) {
        this.tips.add(tipEstudiante);
        tipEstudiante.setEstudiante(this);
        return this;
    }

    public Estudiante removeTips(TipEstudiante tipEstudiante) {
        this.tips.remove(tipEstudiante);
        tipEstudiante.setEstudiante(null);
        return this;
    }

    public void setTips(Set<TipEstudiante> tipEstudiantes) {
        this.tips = tipEstudiantes;
    }

    public Set<ActividadxEstudiante> getActividadesxEstus() {
        return actividadesxEstus;
    }

    public Estudiante actividadesxEstus(Set<ActividadxEstudiante> actividadxEstudiantes) {
        this.actividadesxEstus = actividadxEstudiantes;
        return this;
    }

    public Estudiante addActividadesxEstu(ActividadxEstudiante actividadxEstudiante) {
        this.actividadesxEstus.add(actividadxEstudiante);
        actividadxEstudiante.setEstudiante(this);
        return this;
    }

    public Estudiante removeActividadesxEstu(ActividadxEstudiante actividadxEstudiante) {
        this.actividadesxEstus.remove(actividadxEstudiante);
        actividadxEstudiante.setEstudiante(null);
        return this;
    }

    public void setActividadesxEstus(Set<ActividadxEstudiante> actividadxEstudiantes) {
        this.actividadesxEstus = actividadxEstudiantes;
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
        Estudiante estudiante = (Estudiante) o;
        if (estudiante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estudiante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estudiante{" +
            "id=" + getId() +
            ", fechaNac='" + getFechaNac() + "'" +
            ", colegio='" + getColegio() + "'" +
            ", escenario='" + getEscenario() + "'" +
            ", genero='" + getGenero() + "'" +
            "}";
    }
}
