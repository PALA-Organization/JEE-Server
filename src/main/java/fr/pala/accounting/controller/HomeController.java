package fr.pala.accounting.controller;

import fr.pala.accounting.dal.UserDALImpl;
import fr.pala.accounting.model.AccountModel;
import fr.pala.accounting.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class HomeController implements ErrorController {

    @Autowired
    UserDALImpl userDAL;

    private static final String PATH = "/error";

    @GetMapping("/")
    public String writeHello() {
        return "Welcome home";
    }

    @RequestMapping(value = PATH)
    public String error() {
        return "Error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping("/testroute")
    public String addUser() {
        ArrayList<AccountModel> accounts = new ArrayList<>();

        UserModel user = new UserModel(null, "Test", "test@test.fr", new Date(),
                new Date(), accounts);

        userDAL.addUser(user);
        return "Test user created";
    }

}
