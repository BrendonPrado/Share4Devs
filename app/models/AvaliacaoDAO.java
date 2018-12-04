package models;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

import java.util.List;

public class AvaliacaoDAO {
    private EbeanServer ebeans = Ebean.getDefaultServer();

    public boolean InsertAvaliacao(Avaliacao avaliacao){
        avaliacao.save();
        return true;
    }
    public Avaliacao SelectPorIdLivroIdUsuario(Avaliacao avaliacao){
        Avaliacao avaliacao1 = ebeans.find(Avaliacao.class).where().eq("usuario_avaliador_id",avaliacao.getUsuario_avaliador().getId()).eq("livro_avaliado_id",avaliacao.getLivro_avaliado().getId()).findUnique();
        return avaliacao1;
    }


    public boolean UpdateAvaliacao(Avaliacao avaliacao) {
        Avaliacao avaliacao1 = ebeans.find(Avaliacao.class).where().eq("usuario_avaliador_id",avaliacao.getUsuario_avaliador().getId()).eq("livro_avaliado_id",avaliacao.getLivro_avaliado().getId()).findUnique();
        avaliacao1.setNota_avaliacao(avaliacao.getNota_avaliacao());
        avaliacao1.update();
        return true;
    }

    public List<Avaliacao> SelectPorIDUsuario(int id){
        List<Avaliacao> avaliacaos = ebeans.find(Avaliacao.class).where().eq("usuario_avaliador_id",id).findList();
        return avaliacaos;
    }

    public List<Avaliacao> SelectIdLivro(int id) {
        List<Avaliacao> avaliacaos = ebeans.find(Avaliacao.class).where().eq("livro_avaliado_id",id).findList();
        return avaliacaos;
    }
}