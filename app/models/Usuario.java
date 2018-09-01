package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Usuario extends Model implements logar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "nome",
            columnDefinition = "NOT NULL")
    private String nome;
    @Column(name = "email",
            columnDefinition = "NOT NULL")
    private String email;
    @Column(name = "senha",
            columnDefinition = "NOT NULL")
    private String senha;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    @Override
    public boolean login(String pass) {
        if (pass.equals(this.getSenha())) {
            return true;
        } else {
            return false;
        }

    }
}            