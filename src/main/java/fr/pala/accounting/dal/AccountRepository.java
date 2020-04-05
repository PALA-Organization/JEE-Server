package fr.pala.accounting.dal;

import fr.pala.accounting.model.AccountModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<AccountModel, String> {
}
