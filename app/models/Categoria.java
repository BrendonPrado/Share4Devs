package models;

import com.avaje.ebean.Model;

import javax.naming.Name;
import javax.persistence.*;

@Entity
public class Categoria extends Model {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome",columnDefinition = "varchar(25) Not null")
    private String name_cat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_cat() {
        return name_cat;
    }

    public void setName_cat(String name_cat) {
        this.name_cat = name_cat;
    }
}
