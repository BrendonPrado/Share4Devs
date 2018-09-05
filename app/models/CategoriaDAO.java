package models;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

import java.util.List;

public class CategoriaDAO {
    public void Insert(Categoria categoria){
        categoria.save();
    }
    public Categoria SelectPorID(int id){
        EbeanServer ebeans = Ebean.getDefaultServer();
        Categoria categoria = ebeans.find(Categoria.class).where().like("id", String.valueOf(id)).findUnique();
        return categoria;
    }

    public Categoria SelectPorNome(String nome){
        EbeanServer ebeans = Ebean.getDefaultServer();
        Categoria categoria = ebeans.find(Categoria.class).where().like("nome", nome).findUnique();
        return categoria;
    }
    public List<Categoria> SelectALL(){
        EbeanServer ebeans = Ebean.getDefaultServer();
        List<Categoria> cats = ebeans.find(Categoria.class).findList();
        return cats;
    }

    public void  UpdateCategoria(Categoria usuario){
        usuario.update();
    }

    public void Delete(Categoria usuario){
        usuario.delete();
    }

}
