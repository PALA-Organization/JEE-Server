package fr.pala.accounting.dal;

import fr.pala.accounting.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDALImpl implements UserDAL {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserModel addUser(UserModel user) {
        mongoTemplate.save(user);
        return user;
    }

    @Override
    public List<UserModel> getAllUsers() {
        return mongoTemplate.findAll(UserModel.class);
    }

    @Override
    public UserModel getUserById(String user_id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        return mongoTemplate.findOne(query, UserModel.class);
    }

    @Override
    public UserModel updateUser(UserModel user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUser_id()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("email", user.getEmail());
        update.set("last_connection", user.getLast_connection());
        mongoTemplate.findAndModify(query, update, UserModel.class);

        return user;
    }
}
