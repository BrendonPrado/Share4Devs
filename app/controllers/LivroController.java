package controllers;


import org.h2.store.fs.FileUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.cadastroLivro;

import java.io.File;

public class LivroController extends Controller {

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
            return redirect(routes.UsuarioController.login());
        }
    }

}
