package fr.pala.accounting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "User")
@Getter
@Setter
public class UserModel {

    @Id
    private String user_id;
    private String name;
    private String email;
    private Date created;
    private Date last_connection;
    private ArrayList<AccountModel> accounts;

    public UserModel(String user_id, String name, String email, Date created, Date last_connection, ArrayList<AccountModel> accounts){
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.created = created;
        this.last_connection = last_connection;
        this.accounts = accounts;
    }
}
