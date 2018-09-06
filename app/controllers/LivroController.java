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


    public Result CadastroLivro() {
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        } else {
            List<Categoria> cat = categoriaDAO.SelectALL();
            return ok(cadastroLivro.render(cat));
        }
    }

    public Result SalvaNovoLivro() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart arquivo = body.getFile("fileUpload");
        DynamicForm f = formularios.form().bindFromRequest();
        String nome = f.get("nome");
        String descricao = f.get("descricao");
        String categoria = f.get("categoria");
        if (arquivo != null) {
            String fileName = arquivo.getFilename();
            File file = (File) arquivo.getFile();
            FileUtils.move(file.getAbsolutePath(), "public/livros/" + nome + fileName.substring(fileName.lastIndexOf("."), fileName.length()));
            String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
            livroDAO.Insert(new Livro(nome, descricao, "public/livros/" + nome + fileName.substring(fileName.lastIndexOf("."), fileName.length()), categoriaDAO.SelectPorNome(categoria), usuarioDAO.SelectPorEmail(email)));
            return ok("Arquivo upado!!");
        } else {
            flash("error", "Missing file");
            return redirect(routes.UsuarioController.login());
        }
    }

        public Result PaginaIni(){
            if (session().keySet().size() == 0) {
                return ok("Faça o login primeiro");
            } else {
                List<Categoria> cat = categoriaDAO.SelectALL();
                return ok(inicial.render(cat));
            }
        }

        public Result MostraLivros(String id){
            if (session().keySet().size() == 0) {
                return ok("Faça o login primeiro");
            }
            List<Livro> l  = livroDAO.SelectPorCateg(id);
            if(l.size()==0){
                return ok("não há livros cadastrados");
            }
            return ok(livros.render(l));
        }

        public Result DownloadLivro(String id){
            if (session().keySet().size() == 0) {
                return ok("Faça o login primeiro");
            }
            Livro l = livroDAO.SelectPorID(id);
            java.io.File file = new java.io.File(l.getCaminho());
            java.nio.file.Path path = file.toPath();
            Source<ByteString, ?> source = FileIO.fromPath(path);
            return ok().sendFile(file,false);
        }

        public Result EditarLivro(String id){
        if (session().keySet().size() == 0) {
                return ok("Faça o login primeiro");
            }
        Livro l = livroDAO.SelectPorID(id);
        List<Categoria> cat = categoriaDAO.SelectALL();
        cat.remove(l.getCategoria());
        return ok(editarLivro.render(l,cat));
        }

        public Result UpdateLivro(String id){
        Livro l = livroDAO.SelectPorID(id);
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
            if (session().keySet().size() == 0 && l.getUsuario_dono().getEmail() == email) {
                return ok("Faça o login primeiro");
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
            String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
            if (session().keySet().size() == 0 && l.getUsuario_dono().getEmail() == email) {
                return ok("Faça o login primeiro");
            }
            new File(l.getCaminho()).delete();
            livroDAO.Delete(l);
            Usuario user = usuarioDAO.SelectPorEmail(email);
            List<Livro> livros = livroDAO.SelectPorUsuario(String.valueOf(user.getId()));
            return ok(meusLivros.render(livros));
        }
}
