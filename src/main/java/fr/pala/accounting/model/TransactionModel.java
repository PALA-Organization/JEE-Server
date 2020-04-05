package fr.pala.accounting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
public class TransactionModel {

    @Id
    private String transaction_id;
    private String type;
    private String shop_name;
    private String shop_address;
    private Date date;
    private Float amount;
    private String description;

}
