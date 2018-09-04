package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Livro extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="nome",columnDefinition = "varchar(25) Not null")
    private String nome;

    @Column(name = "descricao",columnDefinition = "mediumtext not null")
    private String descricao;

    @Column(name = "caminho",columnDefinition = " mediumtext Not null")
    private String caminho;

    @ManyToOne(targetEntity = Categoria.class,optional = false)
    private Categoria categoria;

    @ManyToOne(targetEntity = Usuario.class,optional = false)
    private Usuario usuario_dono;

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
}
