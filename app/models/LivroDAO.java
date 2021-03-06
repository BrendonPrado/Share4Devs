package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

import java.util.List;

public class LivroDAO {
    private  EbeanServer ebeans = Ebean.getDefaultServer();

    public void Insert(Livro l){
        l.save();
    }

    public Livro SelectPorID(String id){
        Livro l = ebeans.find(Livro.class).where().like("id",id).findUnique();
        return l;
    }

    public List<Livro> SelectALL(){
        List<Livro> livros = ebeans.find(Livro.class).findList();
        return livros;
    }

    public List<Livro> SelectPorNomeAprox(String busca){
        List<Livro> livros = ebeans.find(Livro.class).where().icontains("nome",busca).findList();
        return livros;
    }

    public Livro SelectPorNome(String n){
        Livro l = ebeans.find(Livro.class).where().like("nome", n).findUnique();
        return l;
    }
    public List<Livro> SelectPorCateg(String id){
        List<Livro>  l = ebeans.find(Livro.class).where().like("categoria_id", id).findList();
        return l;
    }
    public List<Livro> SelectPorUsuario(String id){
        List<Livro> l = ebeans.find(Livro.class).where().like("usuario_dono_id", id).findList();
        return l;
    }

    public void  UpdateLivro(Livro l_editado){
        l_editado.update();
    }

    public void Delete(Livro l){
        l.delete();
    }

}
