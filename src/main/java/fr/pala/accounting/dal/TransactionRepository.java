package fr.pala.accounting.dal;

import fr.pala.accounting.model.TransactionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionModel, String> {
}
