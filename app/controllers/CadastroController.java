package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.Usuario;
import org.h2.store.fs.FileUtils;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.cadastroLivro;
import views.html.formularioNovo;
import views.html.landing;
import views.html.login;

import javax.inject.Inject;
import java.io.File;

public class CadastroController extends Controller {

    @Inject
    private FormFactory formularios;

    public Result salvaNovoProduto() {
        Form<Usuario> formulario = formularios.form(Usuario.class).bindFromRequest();
        Usuario user = formulario.get();
        user.save();
        return redirect(routes.CadastroController.formularioDeNovoProduto());
    }

    public Result formularioDeNovoProduto() {
        return ok(formularioNovo.render("Cadastrar produto"));
    }

    public Result logar() {
        EbeanServer ebeans = Ebean.getDefaultServer();
        DynamicForm form = formularios.form().bindFromRequest();
        Usuario user = ebeans.find(Usuario.class).where().like("email", form.get("email")).findUnique();
        if (user.login(form.get("pass"))) {
            session().clear();
            session().put(form.get("email"), form.get("pass"));
            return ok(landing.render());
        } else {
            return ok(login.render("Senha ou email in"));
        }
    }

    public Result login() {
        return ok(login.render(""));
    }

    public Result CadastroLivro() {
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        } else {
            return ok(cadastroLivro.render());
        }
    }

    public Result SalvaNovoLivro() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart arquivo = body.getFile("fileUpload");
        if (arquivo != null) {
            String fileName = arquivo.getFilename();
            String contentType = arquivo.getContentType();
            File file = (File) arquivo.getFile();
            FileUtils.move(file.getAbsolutePath(), "public/livros/livro.pdf");
            return ok("File uploaded" );

        } else {
            flash("error", "Missing file");
            return redirect(routes.CadastroController.login());
        }
    }
}
