package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
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
        if(usuarioDAO.SelectPorEmail(user.getEmail())!=null){
            return ok(formularioNovo.render("Esse usuário já foi cadastrado!"));
        }
        usuarioDAO.Insert(user);
        return ok(login.render(""));
    }

   

    public Result Landing(){
        if (session().keySet().size() == 0) {
            return ok( login.render("Faça o login Primeiro"));
        }else {
            Usuario user = usuarioDAO.SelectPorEmail(session("conectado"));
            return ok(landing.render(user.getNome()));
        }
    }

    public Result logar() {
        DynamicForm form = formularios.form().bindFromRequest();
        Usuario user = usuarioDAO.SelectPorEmail(form.get("email"));
        if (user!=null && user.login(form.get("pass"))){
            session().clear();
            session("conectado",form.get("email"));
            return ok(landing.render(user.getNome()));
        } else {
            return ok(login.render("Email ou senha incorretos"));
        }
    }

   

    public Result MeusLivros(){
        if (session().keySet().size() == 0) {
            return ok( login.render("Faça o login Primeiro"));
        }
        String email = session("conectado");
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
            return ok( login.render("Faça o login Primeiro"));
        }
        String email = session("conectado");
        Usuario user = usuarioDAO.SelectPorEmail(email);
        return ok(gerenciarCadastro.render(user,""));
    }

    public Result EditCadastro(){
        if (session().keySet().size() == 0) {
            return ok( login.render("Faça o login Primeiro"));
        }
        String email = session("conectado");
        Usuario user = usuarioDAO.SelectPorEmail(email);
        return ok(editarCadastro.render(user,""));
    }

    public Result UpdateCadastro(){
        String email = session("conectado");
        Usuario user = usuarioDAO.SelectPorEmail(email);
        DynamicForm f = formularios.form().bindFromRequest();
        if((!user.getEmail().equals(f.get("email"))) && usuarioDAO.SelectPorEmail(f.get("email"))!=null){
            return ok(editarCadastro.render(user,"Email já utilizado"));
        }
        session().clear();
        session("conectado",f.get("email"));
        user.setNome(f.get("nome"));
        user.setEmail(f.get("email"));
        usuarioDAO.UpdateUsuario(user);
        return ok(gerenciarCadastro.render(user,"Alteração realizada"));
    }

    public  Result EditSenha(){
        if (session().keySet().size() == 0) {
            return ok( login.render("Faça o login Primeiro"));
        }
        return ok(confirmarSenha.render(""));
    }

    public Result ConfirmarSenhaAntiga(){
        String email = session("conectado");
        Usuario user = usuarioDAO.SelectPorEmail(email);
        DynamicForm f = formularios.form().bindFromRequest();
        String senha_tentativa = f.get("senha");
        if(senha_tentativa.equals(user.getSenha())){
            return ok( login.render("Faça o login Primeiro"));
        }else {
            return ok(confirmarSenha.render("Senha incorreta! Tente novamente"));
        }
    }
    
    public Result RedefinirSenha(){
        DynamicForm f = formularios.form().bindFromRequest();
        if(f.get("senha").equals(f.get("conf_senha"))){
            String email = session("conectado");
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
            return ok( login.render("Faça o login Primeiro"));
        }
        String email = session("conectado");
        Usuario user = usuarioDAO.SelectPorEmail(email);
        usuarioDAO.Delete(user);
        session().clear();
        return ok(login.render("Sua Conta foi Excluida!Esperamos que volte!"));
    }
}
