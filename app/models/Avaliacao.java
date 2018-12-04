package models;


import com.avaje.ebean.Model;

import javax.persistence.*;
import java.lang.annotation.Target;

@Entity
public class Avaliacao extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuario_avaliador;

    @ManyToOne(targetEntity = Livro.class)
    private Livro livro_avaliado;

    @Column(name = "nota", columnDefinition = "integer not null")
    private int nota_avaliacao;

    public Avaliacao(Usuario usuario_avaliador, Livro livro_avaliado, int nota_avaliacao) {
        this.usuario_avaliador = usuario_avaliador;
        this.livro_avaliado = livro_avaliado;
        this.nota_avaliacao = nota_avaliacao;
    }

    public Usuario getUsuario_avaliador() {
        return usuario_avaliador;
    }

    public void setUsuario_avaliador(Usuario usuario_avaliador) {
        this.usuario_avaliador = usuario_avaliador;
    }

    public Livro getLivro_avaliado() {
        return livro_avaliado;
    }

    public void setLivro_avaliado(Livro livro_avaliado) {
        this.livro_avaliado = livro_avaliado;
    }

    public int getNota_avaliacao() {
        return nota_avaliacao;
    }

    public void setNota_avaliacao(int nota_avaliacao) {
        this.nota_avaliacao = nota_avaliacao;
    }
}