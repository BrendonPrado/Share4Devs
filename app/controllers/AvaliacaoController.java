package controllers;


import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;


public class AvaliacaoController extends Controller {
    @Inject
    private FormFactory ajax;
    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private UsuarioDAO usuarioDAO =  new UsuarioDAO();
    private LivroDAO livroDAO = new LivroDAO();
    private LivroController livroController =  new LivroController();


    public Result AvaliarLivro(){

        DynamicForm req = ajax.form().bindFromRequest();
        String nota = req.get("nota");
        String id = req.get("id");
        String email = session("conectado");

        Avaliacao avaliacao = new Avaliacao(usuarioDAO.SelectPorEmail(email),livroDAO.SelectPorID(id),Integer.parseInt(nota));
        try {
            if (avaliacaoDAO.SelectPorIdLivroIdUsuario(avaliacao)!=null){
                avaliacaoDAO.UpdateAvaliacao(avaliacao);
                Livro livro= livroDAO.SelectPorID(id);
                List<Avaliacao> avs = avaliacaoDAO.SelectIdLivro(Integer.parseInt(id));
                double res = livroController.CalcularMedia(avs,livro);
                return ok(String.valueOf(res));
            }else {
                avaliacaoDAO.InsertAvaliacao(avaliacao);
                Livro livro= livroDAO.SelectPorID(id);
                List<Avaliacao> avs = avaliacaoDAO.SelectIdLivro(Integer.parseInt(id));
                double res = livroController.CalcularMedia(avs,livro);
                return ok(String.valueOf(res));
            }
        }catch (Exception e){
            return ok(String.valueOf(e));

        }

    }
}
