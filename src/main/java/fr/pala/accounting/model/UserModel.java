package fr.pala.accounting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document
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
}
