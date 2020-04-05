package fr.pala.accounting.dal;

import fr.pala.accounting.model.AccountModel;
import fr.pala.accounting.model.TransactionModel;

import java.util.List;


public interface AccountDAL {

    AccountModel addAccount(String user_id, AccountModel accountModel);

    List<AccountModel> getAllAccountsOfUsers(String user_id);

    AccountModel getAccountOfUser(String user_id, String account_id);

    Float getAmountOfAccount(String user_id,String account_id);

    void updateAccount(String user_id, String account_id, AccountModel account);

    void deleteAccount(String user_id, String account_id);
}
