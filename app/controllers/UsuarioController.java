package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;
import java.util.List;

public class UsuarioController extends Controller {

    @Inject
    private FormFactory formularios;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LivroDAO livroDAO = new LivroDAO();

    public Result salvaNovoUsuario() {
        Form<Usuario> formulario = formularios.form(Usuario.class).bindFromRequest();
        Usuario user = formulario.get();
        usuarioDAO.Insert(user);
        return redirect(routes.UsuarioController.login());
    }

    public Result formularioNovoUsuario() {
        return ok(formularioNovo.render("Cadastrar produto"));
    }

    public Result Landing(){
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        }else {
            return ok(landing.render());
        }
    }

    public Result logar() {
        DynamicForm form = formularios.form().bindFromRequest();
        Usuario user = usuarioDAO.SelectPorEmail(form.get("email"));
        if (user!=null && user.login(form.get("pass"))){
            session().clear();
            session().put(form.get("email"), form.get("pass"));
            return ok(landing.render());
        } else {
            return ok(login.render("Email ou senha incorretos"));
        }
    }

    public Result login() {
        return ok(login.render(""));
    }

    public Result MeusLivros(){
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        }
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
        Usuario user = usuarioDAO.SelectPorEmail(email);
        List<Livro> l = livroDAO.SelectPorUsuario(String.valueOf(user.getId()));
        return  ok(meusLivros.render(l));
    }

    public Result Logout(){
        session().clear();
        return  ok(login.render(""));
    }

    public Result GerenciarCadastro(){
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        }
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
        Usuario user = usuarioDAO.SelectPorEmail(email);
        return ok(gerenciarCadastro.render(user,""));
    }

    public Result EditCadastro(){
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        }
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
        Usuario user = usuarioDAO.SelectPorEmail(email);
        return ok(editarCadastro.render(user));
    }

    public Result UpdateCadastro(){
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
        Usuario user = usuarioDAO.SelectPorEmail(email);
        DynamicForm f = formularios.form().bindFromRequest();
        user.setNome(f.get("nome"));
        user.setEmail(f.get("email"));
        usuarioDAO.UpdateUsuario(user);
        return ok(gerenciarCadastro.render(user,"Alteração realizada"));
    }

    public  Result EditSenha(){
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        }
        return ok(confirmarSenha.render(""));
    }

    public Result ConfirmarSenhaAntiga(){
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
        Usuario user = usuarioDAO.SelectPorEmail(email);
        DynamicForm f = formularios.form().bindFromRequest();
        String senha_tentativa = f.get("senha");
        if(senha_tentativa.equals(user.getSenha())){
            return ok(redefinirSenha.render(""));
        }else {
            return ok(confirmarSenha.render("Senha incorreta! Tente novamente"));
        }
    }

    public Result RedefinirSenha(){
        DynamicForm f = formularios.form().bindFromRequest();
        if(f.get("senha").equals(f.get("conf_senha"))){
            String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
            Usuario user = usuarioDAO.SelectPorEmail(email);
            user.setSenha(f.get("senha"));
            usuarioDAO.UpdateUsuario(user);
            return ok(gerenciarCadastro.render(user,"Alteração realizada"));
        }else {
            return ok(redefinirSenha.render("As senhas não conferem "));
        }
    }
    public Result RemoverCadastro(){
        if (session().keySet().size() == 0) {
            return ok("Faça o login primeiro");
        }
        String email = session().keySet().toString().substring(1, session().keySet().toString().length() - 1);
        Usuario user = usuarioDAO.SelectPorEmail(email);
        usuarioDAO.Delete(user);
        session().clear();
        return ok(login.render("Sua Conta foi Excluida!Esperamos que volte!"));
    }
}
