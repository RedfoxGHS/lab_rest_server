package br.inatel.labs.labrest.server.entities;


public class Curso implements Comparable<Curso> {

    public static int numeroDeCursosDeletados = 0;

    private Long id;

    private String descricao;

    private Integer cargaHoraria;

    public Curso() {
    }

    public Curso(Long id, String descricao, Integer cargaHoraria) {
        this.id = id;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Curso curso = (Curso) o;

        return id.equals(curso.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Curso o) {
        return this.getId().compareTo(o.getId());
    }
}
