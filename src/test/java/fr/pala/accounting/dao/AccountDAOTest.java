package fr.pala.accounting.dao;
import fr.pala.accounting.model.AccountModel;
import fr.pala.accounting.model.UserModel;
import org.junit.jupiter.api.Test;
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

@SpringBootTest(classes = { TransactionDAO.class, AccountDAO.class, UserDAO.class })
@RunWith(SpringRunner.class)
public class AccountDAOTest {

    @MockBean
    private MongoTemplate mongoTemplate;
    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void addAccountTest(){
        //parameters of addAccount
        String user_id = "234";
        AccountModel account = new AccountModel(null, 23.30, new ArrayList<>());

        //mock of the user get
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("234", "Test", "test@test.fr", new Date(), new Date(), new ArrayList<AccountModel>()));

        //mock of the account save
        AccountModel accountResult = new AccountModel("34234234", 23.30, new ArrayList<>());
        Mockito.when(mongoTemplate.save(Mockito.any(AccountModel.class))).thenReturn(accountResult);

        assertThat(accountDAO.addAccount(user_id, account).getAmount()).isEqualTo(23.30);
    }

    @Test
    public void getAllAccountsOfUsersTest() {
        //parameter of getAllAccountsOfUsers
        String user_id = "12";

        //Mock two accounts
        AccountModel account = new AccountModel("", 234.55, new ArrayList<String>());
        AccountModel account2 = new AccountModel("", 234.55,  new ArrayList<String>());

        //mock the user get
        ArrayList<AccountModel> accounts = new ArrayList<>();
        accounts.add(account);
        accounts.add(account2);
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(accountDAO.getAllAccountsOfUsers(user_id)).hasSize(2);
    }

    @Test
    public void getAccountOfUserTest() {
        //Parameters of getAccountOfUser
        String user_id = "12";
        String account_id = "13";

        //Mock an account get
        AccountModel account = new AccountModel(account_id, 234.55, new ArrayList<String>());
        Query query = new Query();
        query.addCriteria(Criteria.where("account_id").is(account_id));
        Mockito.when(mongoTemplate.findOne(query, AccountModel.class))
                .then(ignoredInvocation -> account);

        //mock the user
        ArrayList<AccountModel> accounts = new ArrayList<>();
        accounts.add(account);
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query1, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(accountDAO.getAccountOfUser(user_id, account_id).getAccount_id()).isEqualTo(account_id);
    }

    @Test
    public void getAmountOfAccountTest() {
        //Parameters of getAmountOfAccount
        String user_id = "12";
        String account_id = "13";

        //Mock an account get
        AccountModel account = new AccountModel(account_id, 234.55, new ArrayList<String>());
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("account_id").is(account_id));
        Mockito.when(mongoTemplate.findOne(query1, AccountModel.class))
                .then(ignoredInvocation -> account);

        //mock the user get
        ArrayList<AccountModel> accounts = new ArrayList<>();
        accounts.add(account);
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("32352453234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(accountDAO.getAmountOfAccount(user_id, account_id)).isEqualTo(234.55);
    }
}
