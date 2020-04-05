package fr.pala.accounting.dal;

import fr.pala.accounting.model.TransactionModel;

import java.util.List;

public interface TransactionDAL {

    List<TransactionModel> getAllTransactionsOfAccount(String user_id, String account_id);

    TransactionModel getTransaction(String transaction_id);

    TransactionModel addTransaction(String user_id, String account_id, TransactionModel transactionModel);

    void updateTransaction(String user_id, String account_id, TransactionModel transactionModel);


    void deleteTransaction(String user_id, String account_id, TransactionModel transaction);
}
