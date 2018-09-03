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


}
