package fr.pala.accounting.dal;

import fr.pala.accounting.model.AccountModel;
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
import java.util.Arrays;
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
    public void getAllTransactionsOfAccountTest() {

        String user_id = "32352453234";
        String account_id = "12";

        ArrayList<AccountModel> accounts = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> Arrays.asList(new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts)));

        assertThat(transactionDALImpl.getAllTransactionsOfAccount(user_id, account_id)).hasSize(0);
    }

    /*

    TransactionModel getTransaction(String transaction_id);

    TransactionModel addTransaction(String user_id, String account_id, TransactionModel transactionModel);

    void updateTransaction(String user_id, String account_id, TransactionModel transactionModel);


    void deleteTransaction(String user_id, String account_id, TransactionModel transaction);*/
}
