package controllers;


import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import models.*;
import org.h2.store.fs.FileUtils;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

public class LivroController extends Controller {

    @Inject
    private FormFactory formularios;


    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private LivroDAO livroDAO = new LivroDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    public Result CadastroLivro() {
        if (session().keySet().size() == 0) {
            return ok( login.render("Faça o login Primeiro"));
        } else {
            List<Categoria> cat = categoriaDAO.SelectALL();
            return ok(cadastroLivro.render(cat));
        }
    }

    public Result SalvaNovoLivro() throws InterruptedException {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart arquivo = body.getFile("fileUpload");
        DynamicForm f = formularios.form().bindFromRequest();
        String nome = f.get("nome");
        String descricao = f.get("descricao");
        String categoria = f.get("categoria");
        if (arquivo != null) {
            try{
                String fileName = arquivo.getFilename();
                File file = (File) arquivo.getFile();
                FileUtils.move(file.getPath(), "public/livros/" + nome + fileName.substring(fileName.lastIndexOf("."), fileName.length()));
                String email = session("conectado");
                livroDAO.Insert(new Livro(nome, descricao, "public/livros/" + nome + fileName.substring(fileName.lastIndexOf("."), fileName.length()), categoriaDAO.SelectPorNome(categoria), usuarioDAO.SelectPorEmail(email),0));
                return ok("Livro upado com sucesso!");
            }catch (Exception e){
                return ok("Nome já usado, por favor escolha outro nome para seu livro!");
            }

        } else {
            flash("error", "Missing file");
            List<Categoria> cat = categoriaDAO.SelectALL();
            return ok(cadastroLivro.render(cat));
        }
    }

        public Result PaginaIni(){
            if (session().keySet().size() == 0) {
                return ok( login.render("Faça o login Primeiro"));
            } else {
                List<Categoria> cat = categoriaDAO.SelectALL();
                return ok(inicial.render(cat));
            }
        }

        public Result MostraLivros(String id){
            if (session().keySet().size() == 0) {
                return ok( login.render("Faça o login Primeiro"));
            }
            List<Livro> l  = livroDAO.SelectPorCateg(id);
            if(l.size()==0){
                return ok("não há livros cadastrados");
            }
            String email =  session("conectado");
            List<Avaliacao> avs = avaliacaoDAO.SelectPorIDUsuario(usuarioDAO.SelectPorEmail(email).getId());
            for (Livro livro:l) {
                int alarme = 0;
                for (Avaliacao ava : avs) {
                    if(ava.getLivro_avaliado().getId()==livro.getId()) {
                        alarme = 1;
                        break;
                    }
                }
                if(alarme==0){
                    avs.add(new Avaliacao(usuarioDAO.SelectPorEmail(email),livro,0));
                }

            }
            return ok(livros.render(l,avs));
        }

        public Result DownloadLivro(String id){
            if (session().keySet().size() == 0) {
                return ok( login.render("Faça o login Primeiro"));
            }
            Livro l = livroDAO.SelectPorID(id);
            java.io.File file = new java.io.File(l.getCaminho());
            java.nio.file.Path path = file.toPath();
            Source<ByteString, ?> source = FileIO.fromPath(path);
            return ok().sendFile(file,false);
        }

        public Result EditarLivro(String id){
        if (session().keySet().size() == 0) {
            return ok( login.render("Faça o login Primeiro"));
            }
        Livro l = livroDAO.SelectPorID(id);
        List<Categoria> cat = categoriaDAO.SelectALL();
        cat.remove(l.getCategoria());
        return ok(editarLivro.render(l,cat));
        }

        public Result MostrarTodosLivros(){
            if (session().keySet().size() == 0) {
                return ok( login.render("Faça o login Primeiro"));
            }
            List<Livro> l  = livroDAO.SelectALL();
            if(l.size()==0){
                return ok("não há livros cadastrados");
            }
            String email =  session("conectado");
            List<Avaliacao> avs = avaliacaoDAO.SelectPorIDUsuario(usuarioDAO.SelectPorEmail(email).getId());
            for (Livro livro:l) {
                int alarme = 0;
                for (Avaliacao ava : avs) {
                    if(ava.getLivro_avaliado().getId()==livro.getId()) {
                        alarme = 1;
                        break;
                    }
                }
                if(alarme==0){
                    avs.add(new Avaliacao(usuarioDAO.SelectPorEmail(email),livro,0));
                }

            }
            return ok(livros.render(l,avs));
        }

        public Result BuscarLivros(){
            if (session().keySet().size() == 0) {
                return ok( login.render("Faça o login Primeiro"));
            }
            DynamicForm f = formularios.form().bindFromRequest();
            List<Livro> l  = livroDAO.SelectPorNomeAprox(f.get("buscar"));
            if(l.size()==0){
                return ok("não há livros cadastrados");
            }
            String email =  session("conectado");
            List<Avaliacao> avs = avaliacaoDAO.SelectPorIDUsuario(usuarioDAO.SelectPorEmail(email).getId());
            for (Livro livro:l) {
                int alarme = 0;
                for (Avaliacao ava : avs) {
                    if(ava.getLivro_avaliado().getId()==livro.getId()) {
                        alarme = 1;
                        break;
                    }
                }
                if(alarme==0){
                    avs.add(new Avaliacao(usuarioDAO.SelectPorEmail(email),livro,0));
                }

            }
            return ok(livros.render(l,avs));

        }


        public Result UpdateLivro(String id){
        Livro l = livroDAO.SelectPorID(id);
        String email =  session("conectado");
            if (session().keySet().size() == 0 || (!l.getUsuario_dono().getEmail().equals(email))) {
                return ok( login.render("Faça o login Primeiro"));
            }
        DynamicForm f = formularios.form().bindFromRequest();
        l.setNome(f.get("nome"));l.setDescricao(f.get("descricao"));l.setCategoria(categoriaDAO.SelectPorNome(f.get("categoria")));
        l.update();
        Usuario user = usuarioDAO.SelectPorEmail(email);
        List<Livro> livros = livroDAO.SelectPorUsuario(String.valueOf(user.getId()));
        return ok(meusLivros.render(livros));
        }

        public Result RemoveLivro(String id){
            Livro l = livroDAO.SelectPorID(id);
            String email = session("conectado");
            if (session().keySet().size() == 0 || (!l.getUsuario_dono().getEmail().equals(email)) ) {
                return ok( login.render("Faça o login Primeiro"));
            }else {
                new File(l.getCaminho()).delete();
                livroDAO.Delete(l);
                Usuario user = usuarioDAO.SelectPorEmail(email);
                List<Livro> livros = livroDAO.SelectPorUsuario(String.valueOf(user.getId()));
                return ok(meusLivros.render(livros));
            }
        }

    public Double CalcularMedia(List<Avaliacao> avs,Livro l) {
        double soma = 0;
        for (Avaliacao ava :avs) {
            soma+=ava.getNota_avaliacao();
        }
        l.setNota(soma/avs.size());
        livroDAO.UpdateLivro(l);
        return soma/avs.size();
    }
}
