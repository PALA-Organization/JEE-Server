package fr.pala.accounting.dal;

import fr.pala.accounting.model.AccountModel;
import fr.pala.accounting.model.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionDALImpl implements TransactionDAL {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AccountDALImpl accountDALImpl;


    @Override
    public List<TransactionModel> getAllTransactionsOfAccount(String user_id, String account_id) {
        List<TransactionModel> transactionResults = new ArrayList<TransactionModel>();
        AccountModel accountModel = accountDALImpl.getAccountOfUser(user_id, account_id);

        if(accountModel == null){
            return transactionResults;
        }

        ArrayList<String> transactions_ids = accountModel.getTransactions_ids();

        for (int i = 0; i < transactions_ids.size() ; i++) {
            Query query = new Query();
            query.addCriteria(Criteria.where("transaction_id").is(transactions_ids.get(i)));
            transactionResults.add(mongoTemplate.findOne(query, TransactionModel.class));
        }

        return transactionResults;
    }

    @Override
    public TransactionModel getTransaction(String transaction_id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transaction_id").is(transaction_id));
        return mongoTemplate.findOne(query, TransactionModel.class);
    }

    @Override
    public TransactionModel addTransaction(String user_id, String account_id, TransactionModel transactionModel) {
        TransactionModel transactionResult = mongoTemplate.save(transactionModel);

        AccountModel account = accountDALImpl.getAccountOfUser(user_id, account_id);
        ArrayList<String> transactions_ids = account.getTransactions_ids();
        transactions_ids.add(transactionResult.getTransaction_id());
        account.setTransactions_ids(transactions_ids);


        accountDALImpl.updateAccount(user_id, account_id, account);

        return transactionResult;
    }

    @Override
    public void updateTransaction(String user_id, String account_id, TransactionModel transactionModel) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transaction_id").is(transactionModel.getTransaction_id()));
        Update update = new Update();
        update.set("type", transactionModel.getType());
        update.set("shop_name", transactionModel.getShop_name());
        update.set("shop_address", transactionModel.getShop_address());
        update.set("date", transactionModel.getDate());
        update.set("amount", transactionModel.getAmount());
        update.set("description", transactionModel.getDescription());

        mongoTemplate.findAndModify(query, update, TransactionModel.class);

    }

    @Override
    public void deleteTransaction(String user_id, String account_id, TransactionModel transaction) {

        mongoTemplate.remove(transaction);

        AccountModel account = accountDALImpl.getAccountOfUser(user_id, account_id);
        ArrayList<String> transactions_ids = account.getTransactions_ids();

        for (int i = 0; i < transactions_ids.size(); i++) {
            if(transactions_ids.get(i) == transaction.getTransaction_id()){
                transactions_ids.remove(i);
            }
        }
        account.setTransactions_ids(transactions_ids);


        accountDALImpl.updateAccount(user_id, account_id, account);


    }
}
