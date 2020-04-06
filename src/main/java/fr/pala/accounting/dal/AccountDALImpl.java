package fr.pala.accounting.dal;

import fr.pala.accounting.model.AccountModel;
import fr.pala.accounting.model.UserModel;
import fr.pala.accounting.server.ServerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDALImpl implements AccountDAL {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserDALImpl userDALImpl;

    @Autowired
    private AccountDALImpl() {
    }


    @Override
    public AccountModel addAccount(String user_id, AccountModel accountModel) {
        UserModel user = userDALImpl.getUserById(user_id);
        List<AccountModel> accounts = user.getAccounts();
        accounts.add(accountModel);

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUser_id()));
        Update update = new Update();
        update.set("accounts", accounts);
        mongoTemplate.findAndModify(query, update, UserModel.class);

        return accountModel;
    }

    @Override
    public List<AccountModel> getAllAccountsOfUsers(String user_id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));

        UserModel user = mongoTemplate.findOne(query, UserModel.class);

        return user.getAccounts();
    }


    @Override
    public AccountModel getAccountOfUser(String user_id, String account_id) {
        List<AccountModel> accounts = getAllAccountsOfUsers(user_id);

        AccountModel accountResult = null;
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getAccount_id() == account_id){
                accountResult = accounts.get(i);
                break;
            }
        }

        return accountResult;
    }

    @Override
    public Double getAmountOfAccount(String user_id, String account_id) {
        AccountModel account = getAccountOfUser(user_id, account_id);
        return account.getAmount();
    }

    @Override
    public void updateAccount(String user_id, String account_id, AccountModel account) {
        UserModel user = userDALImpl.getUserById(user_id);
        List<AccountModel> accounts = user.getAccounts();

        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getAccount_id() == account_id){
                accounts.get(i).setAmount(account.getAmount());
                accounts.get(i).setTransactions_ids(account.getTransactions_ids());
                break;
            }
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUser_id()));
        Update update = new Update();
        update.set("accounts", accounts);
        mongoTemplate.findAndModify(query, update, UserModel.class);
    }

    @Override
    public void deleteAccount(String user_id, String account_id) {

        UserModel user = userDALImpl.getUserById(user_id);
        List<AccountModel> accounts = user.getAccounts();

        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getAccount_id() == account_id){
                accounts.remove(i);
                break;
            }
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUser_id()));
        Update update = new Update();
        update.set("accounts", accounts);
        mongoTemplate.findAndModify(query, update, UserModel.class);

    }
}
