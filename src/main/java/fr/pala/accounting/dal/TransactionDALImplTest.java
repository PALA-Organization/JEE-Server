package fr.pala.accounting.dal;

import fr.pala.accounting.model.AccountModel;
import fr.pala.accounting.model.TransactionModel;
import fr.pala.accounting.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { TransactionDALImpl.class, AccountDALImpl.class, UserDALImpl.class })
// Note : This annotation loads the classes and creates a "fake" context.
// We COULD use the original ServerApplication context but there is a mongo error as we disabled mongo
@RunWith(SpringRunner.class)
public class TransactionDALImplTest {

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private TransactionDALImpl transactionDALImpl;

    @Test
    public void getAllTransactionsOfNoAccountTest() {

        String user_id = "32352453234";
        String account_id = "12";

        ArrayList<AccountModel> accounts = new ArrayList<>();

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(transactionDALImpl.getAllTransactionsOfAccount(user_id, account_id)).hasSize(0);
    }

    @Test
    public void getAllTransactionsOfAccountTest() {

        String user_id = "32352453234";
        String account_id = "12";

        ArrayList<AccountModel> accounts = new ArrayList<>();
        ArrayList<String> transactionsIds = new ArrayList<String>();
        transactionsIds.add("235");
        transactionsIds.add("444");
        AccountModel account = new AccountModel("12", 234.55, transactionsIds);
        accounts.add(account);

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(transactionDALImpl.getAllTransactionsOfAccount(user_id, account_id)).hasSize(2);
    }

    @Test
    public void getTransactionTest(){
        String transactionId = "223435345345";

        TransactionModel transaction = new TransactionModel("223435345345", "Test", "Auchan",
                "Test", new Date(), 33.70, "Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("transaction_id").is(transactionId));
        Mockito.when(mongoTemplate.findOne(query, TransactionModel.class))
                .then(ignoredInvocation -> transaction);

        assertThat(transactionDALImpl.getTransaction(transactionId)).isEqualTo(transaction);
    }


    @Test
    public void addTransactionTest() {

        //input parameters
        String user_id = "12";
        String account_id = "3234234";
        TransactionModel transaction = new TransactionModel("223435345345", "Test", "Auchan",
                "Test", new Date(), 33.70, "Test");


        //Mock a account
        ArrayList<String> transactionsIds = new ArrayList<String>();
        transactionsIds.add("235");
        transactionsIds.add("444");
        AccountModel account = new AccountModel(account_id, 234.55, transactionsIds);
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("account_id").is(account_id));
        Mockito.when(mongoTemplate.findOne(query1, AccountModel.class))
                .then(ignoredInvocation -> account);

        //mock the user
        ArrayList<AccountModel> accounts = new ArrayList<>();
        accounts.add(account);
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));



        Mockito.when(mongoTemplate.save(Mockito.any(TransactionModel.class))).thenReturn(transaction);

        assertThat(transactionDALImpl.addTransaction(user_id, account_id, transaction).getTransaction_id()).isEqualTo("223435345345");
    }
}
