package controllers;

import models.Usuario;
import models.UsuarioDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.formularioNovo;
import views.html.landing;
import views.html.login;

import javax.inject.Inject;

public class UsuarioController extends Controller {

    @Inject
    private FormFactory formularios;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Result salvaNovoUsuario() {
        Form<Usuario> formulario = formularios.form(Usuario.class).bindFromRequest();
        Usuario user = formulario.get();
        usuarioDAO.Insert(user);
        return redirect(routes.UsuarioController.login());
    }

    public Result formularioNovoUsuario() {
        return ok(formularioNovo.render("Cadastrar produto"));
    }

    public Result logar() {
        DynamicForm form = formularios.form().bindFromRequest();
        Usuario user = usuarioDAO.SelectPorEmail(form.get("email"));
        if (user!=null && user.login(form.get("pass"))){
            session().clear();
            session().put(form.get("email"), form.get("pass"));
            return ok(landing.render());
        } else {
            return ok(("Senha ou email in"));
        }
    }

    public Result login() {
        return ok(login.render(""));
    }




}
