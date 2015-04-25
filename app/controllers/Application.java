package controllers;

import models.*;
import org.mindrot.jbcrypt.BCrypt;
import play.data.DynamicForm;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import org.mindrot.jbcrypt.BCrypt;
import play.data.DynamicForm;

import java.util.Date;
import java.util.List;

import views.html.*;

public class Application extends Controller {

    public static Result toErrorPage() {
        response().setHeader("Cache-Control","no-cache");
        return ok(error.render(""));
    }

    public static Result toLoginPage() {
        List<User> userList = User.findAll();
        int count = 0;
        for (int i = 0 ; i < userList.size() ; i++) {
            if (userList.get(i).getType() == User.ADMIN) {
                count++;
            }
        }
        if (count == 0) {
            defaultSetting();
        }

        if (session().get("username") != null) {
            routes.ProjectListController.toProjectListPage();
        }

        response().setHeader("Cache-Control","no-cache");
        return ok(login.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result toTestPage() {
        response().setHeader("Cache-Control","no-cache");
        return ok(test.render(User.findByUsername(request().username())));
    }

    public static Result toClockPage() {
        response().setHeader("Cache-Control","no-cache");
        return ok(testclock.render());
    }

    public static Result authenticate() {
        DynamicForm dy = new DynamicForm().bindFromRequest();
        String username = dy.get("username");
        String password = dy.get("password");

        User user = User.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            session().clear();
            session("username", username);
            return redirect(routes.ProjectListController.toProjectListPage());
        }
        else {
            response().setHeader("Cache-Control","no-cache");
            return badRequest(login.render());
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.toLoginPage());
    }

    public static void defaultSetting() {
        User.create("admin99", "admin", "Auto", "Created", 99);

        User.create("test01", "test", "test", "test", 1);
        User.create("test02", "test", "test", "test", 1);
        User.create("test03", "test", "test", "test", 1);
        User.create("test04", "test", "test", "test", 1);
        User.create("test05", "test", "test", "test", 1);
        User.create("test06", "test", "test", "test", 1);

        Team team1 = Team.create("team01");
        Project project1 = Project.create("project01", "1");
        team1.setProject(project1);
        team1.update();
        Team team2 = Team.create("team02");
        Project project2 = Project.create("project02", "2");
        team2.setProject(project2);
        team2.update();
        Team team3 = Team.create("team03");
        Project project3 = Project.create("project03", "3");
        team3.setProject(project3);
        team3.update();
        Team team4 = Team.create("team04");
        Project project4 = Project.create("project04", "4");
        team4.setProject(project4);
        team4.update();
        Team team5 = Team.create("team05");
        Project project5 = Project.create("project05", "5");
        team5.setProject(project5);
        team5.update();
        Team team6 = Team.create("team06");
        Project project6 = Project.create("project06", "6");
        team6.setProject(project6);
        team6.update();

        RateCategory.create("A");
        RateCategory.create("B");
        RateCategory.create("C");
        RateCategory.create("D");
        RateCategory.create("E");

        List<User> users = User.findAll();
        List<Project> projects = Project.findAll();
        List<RateCategory> rateCategories = RateCategory.findAll();

        for (User user: users) {
            for (Project project: projects) {
                for (RateCategory rateCategory: rateCategories) {
                    Rate.create(user, project, rateCategory, (int)(Math.random()*6 - 1));
                }
            }
        }

//        //Division1
//        //FATCAT
//        User.create("b5610546257", "natchanon.ch", "Natchanon", "Charoensuk", 1);
//        User.create("b5610546222", "chonnipa.k", "Chonnipa", "Kitisiriprasert", 1);
//        User.create("b5610546699", "kittipat.pro", "Kittipat", "Promdirek", 1);
//        User.create("b5610545684", "nichamon.h", "Nichamon", "Hanidhikul", 1);
//        User.create("b5610546702", "jiratchaya.i", "Jiratchaya", "Intaragumhaeng", 1);
//        Team.create("Fatcat","2,3,4,5,6");
//        Project.create("Fatcat", "MEOW MEOW MEOW MEOW MEOW MEOW MEOW MEOW MEOW MEOW MEOW", 1);
//        //Saint4
//        User.create("b5610545765","muninthorn.t","Muninthorn","Thongnuch",1);
//        User.create("b5610545781","runyasak.c","Runyasak","Chaengnaimuang",1);
//        User.create("b5610545706","nara.s","Nara","Surawit",1);
//        User.create("b5610546788","vasupol.c","Vasupol","Chatmethakul",1);
//        User.create("b5610545803","wuttipong.kh","Wuttipong","Khemphetjetsada",1);
//        Team.create("Saint4","7,8,9,10,11");
//        Project.create("Saint4", "The eXceed Vote application focuses on boiling down the voting process to extract only its core function--to vote. This application requires no manual, it's self explanatory, and make the voting experience 100 times faster.", 2);
//        //Manat
//        User.create("b5610546231","chinnaporn.s","Chinnaporn","Soonue",1);
//        User.create("b5610545811","sorrawit.c","Sorrawit","Chancherngkit",1);
//        User.create("b5610546290","worapon.o","Worapon","Olanwanitchakul",1);
//        User.create("b5610545013","niti.pe","Niti","Petcharatmora",1);
//        User.create("b5610546800","supason.k","Supasn","Kotanut",1);
//        Team.create("Manat","12,13,14,15,16");
//        Project.create("Manat", "Manat swim into the sky", 3);
//        //2Big2Slim
//        User.create("b5610545722","punpikorn.r","Punpikorn","Rattanawirojkul",1);
//        User.create("b5610545668","nathakorn.s","Nathakorn","Sukumsirichart",1);
//        User.create("b5610545731","piyaphat.t","Piyapat","Tulakoop",1);
//        User.create("b5610546711","nabhat.y","Nabhat","Yuktadatta",1);
//        User.create("b5610545676","nut.ka","Nut","Kaewnak",1);
//        Team.create("2Big2Slim","17,18,19,20,21");
//        Project.create("2Big2Slim", "We have 2 big guys and 2 slim guys in this group", 4);
//
//        //Division2
//        //GG
//        User.create("b5610545749","pongsachon.p","Pongsachon","Pornsriniyorm",1);
//        User.create("b5610545757","manatsawin.h","Manatsawin","Hanmongkolchai",1);
//        User.create("b5610546770", "varis.k", "Varis","Kritpolchai",1);
//        Team.create("GG","22,23,24");
//
//        //JDED
//        User.create("b5410545044","warrunyou.r","Waranyu","Rerkdee",1);
//        User.create("b5410545052","supayut.r","Supayut","Raksuk",1);
//        User.create("b5410546334", "wasin.ha","Wasin","Hawaree",1);
//        User.create("b5410546393", "akkarawit.p","Akkarawit","Piyawin",1);
//        User.create("b5410547594","nachanok.su","Nachanok","Suktarachan",1);
//        Team.create("JDED","25,26,27,28,29");
//
//        //Malee
//        User.create("b5610545048","tanatorn.a","Tanatorn","Assawaamnuey",1);
//        User.create("b5610545714","patawat.w","Patawat","Watakul",1);
//        User.create("b5610546745","thanyaboon.t","Thanyaboon","Tovorapan",1);
//        User.create("b5610546761","mintra.t","Mintra","Thirasirisin",1);
//        Team.create("Malee","30,31,32,33");
//
//        //TheFrank
//        User.create("b5610545692","thanachote.v","Thanachote","Visetsuthimout",1);
//        User.create("b5610546281","perawith.j","Perawith","Jarunithi",1);
//        User.create("b5610546681","kittinan.n","Kittinan","Napapongsa",1);
//        User.create("b5610546729","thanaphon.k","Thanaphon","Ketsin",1);
//        User.create("b5610546753","nathas.y","Nathas","Yingsukamol",1);
//        Team.create("TheFrank","34,35,36,37,38");
//
//        //Staff
//        User.create("b5510546166","sarun.wo","Sarun","",3);
//        User.create("b5410545036","thai.p","Thai","",3);
//        User.create("fengjeb","james.b","Jim","",3);
//        User.create("geedev","keeratipong.u","Keeratipong","",3);
//
//        RateCategory.create("Ease of use");
//        RateCategory.create("Reliability/Stability");
//        RateCategory.create("Completeness");
//        RateCategory.create("Security");
//        RateCategory.create("Quality of UI");

    }
}