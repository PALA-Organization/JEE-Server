package fr.pala.accounting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Getter
@Setter
public class AccountModel {

    @Id
    private String account_id;
    private Float amount;
    private ArrayList<String> transactions_ids;

}
