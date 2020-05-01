package fr.pala.accounting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "Account")
@Getter
@Setter
public class AccountModel {

    @Id
    private String account_id;
    private Double amount;
    private ArrayList<String> transactions_ids;

    public AccountModel(String account_id, Double amount, ArrayList<String> transactions_ids){
        this.account_id = account_id;
        this.amount = amount;
        this.transactions_ids = transactions_ids;
    }
}
