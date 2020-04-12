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
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { TransactionDALImpl.class, AccountDALImpl.class, UserDALImpl.class })
@RunWith(SpringRunner.class)
public class AccountDALImplTest {

    @MockBean
    private MongoTemplate mongoTemplate;
    @Autowired
    private AccountDALImpl accountDALImpl;

    @Test
    public void addAccountTest(){
        //String user_id, AccountModel accountModel
        String user_id = "234";
        AccountModel account = new AccountModel("34234234", 23.30, new ArrayList<>());
        //mock un user
        ArrayList<AccountModel> accounts = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        AccountModel accountResult = new AccountModel("34234234", 23.30, new ArrayList<>());
        Mockito.when(mongoTemplate.save(Mockito.any(AccountModel.class))).thenReturn(accountResult);

        assertThat(accountDALImpl.addAccount(user_id, account).getAccount_id()).isEqualTo("34234234");
    }

    @Test
    public void getAllAccountsOfUsersTest() {
        //user_id
        //String user_id, String account_id
        String user_id = "12";


        //Mock a account
        ArrayList<String> transactionsIds = new ArrayList<String>();
        transactionsIds.add("235");
        transactionsIds.add("444");
        AccountModel account = new AccountModel("", 234.55, transactionsIds);
        AccountModel account2 = new AccountModel("", 234.55, transactionsIds);

        //mock the user
        ArrayList<AccountModel> accounts = new ArrayList<>();
        accounts.add(account);
        accounts.add(account2);
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(accountDALImpl.getAllAccountsOfUsers(user_id)).hasSize(2);
    }

    @Test
    public void getAccountOfUserTest() {
        //String user_id, String account_id
        String user_id = "12";

        String account_id = "13";

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

        assertThat(accountDALImpl.getAccountOfUser(user_id, account_id).getAccount_id()).isEqualTo(account_id);
    }

    @Test
    public void getAmountOfAccountTest() {
        String user_id = "12";

        String account_id = "13";

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

        assertThat(accountDALImpl.getAmountOfAccount(user_id, account_id)).isEqualTo(234.55);
    }

}
