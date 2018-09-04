package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

public class LivroDAO {
    public void Insert(Livro l){
        l.save();
    }
    public Livro SelectPorNome(String n){
        EbeanServer ebeans = Ebean.getDefaultServer();
        Livro l = ebeans.find(Livro.class).where().like("nome", n).findUnique();
        return l;
    }
    public Livro SelectPorCateg(int id){
        EbeanServer ebeans =Ebean.getDefaultServer();
        Livro l = ebeans.find(Livro.class).where().like("categoria_id", String.valueOf(id)).findUnique();
        return l;
    }
    public Livro SelectPorUsuario(int id){
        EbeanServer ebeans = Ebean.getDefaultServer();
        Livro l = ebeans.find(Livro.class).where().like("usuario_dono_id", String.valueOf(id)).findUnique();
        return l;
    }

    public void  UpdateLivro(Livro l_editado){
        l_editado.update();
    }

    public void Delete(Livro l){
        l.delete();
    }

}
