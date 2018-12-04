package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Livro extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="nome",columnDefinition = "varchar(100) Not null")
    private String nome;

    @Column(name = "descricao",columnDefinition = "mediumtext not null")
    private String descricao;

    @Column(name = "caminho",columnDefinition = " mediumtext Not null")
    private String caminho;

    @ManyToOne(targetEntity = Categoria.class,optional = false)
    private Categoria categoria;

    @ManyToOne(targetEntity = Usuario.class,optional = false)
    private Usuario usuario_dono;

    @Column(name = "nota",columnDefinition = "DOUBLE not null")
    private double nota;

    public Livro(String nome, String descricao, String caminho, Categoria categoria, Usuario usuario_dono,int nota) {
        this.nome = nome;
        this.descricao = descricao;
        this.caminho = caminho;
        this.categoria = categoria;
        this.usuario_dono = usuario_dono;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario_dono() {
        return usuario_dono;
    }

    public void setUsuario_dono(Usuario usuario_dono) {
        this.usuario_dono = usuario_dono;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
