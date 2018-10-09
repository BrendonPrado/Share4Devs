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

public class SiteController extends Controller {
     public Result formularioNovoUsuario() {
        return ok(formularioNovo.render(""));
    }
     public Result login() {
        return ok(login.render(""));
    }
    public Result Home(){
        return ok(home.render());
    }

}