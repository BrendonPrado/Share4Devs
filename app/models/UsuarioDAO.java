package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

public class UsuarioDAO {
    public void Insert(Usuario usuario){
        usuario.save();
    }
    public Usuario SelectPorID(int id){
        EbeanServer ebeans = Ebean.getDefaultServer();
        Usuario usuario = ebeans.find(Usuario.class).where().like("nome", String.valueOf(id)).findUnique();
        return usuario;
    }

    public Usuario SelectPorEmail(String email){
        EbeanServer ebeans = Ebean.getDefaultServer();
        Usuario usuario = ebeans.find(Usuario.class).where().like("email", email).findUnique();
        return usuario;
    }

    public void  UpdateUsuario(Usuario usuario){
        usuario.update();
    }

    public void Delete(Usuario usuario){
        usuario.delete();
    }

}
