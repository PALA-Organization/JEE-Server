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
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UserDAO.class)
@RunWith(SpringRunner.class)
public class UserDAOTest {

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void addUserTest() {

        ArrayList<AccountModel> accounts = new ArrayList<>();
        UserModel user = new UserModel("", "Test", "test@test.fr", new Date(), new Date(), accounts);
        UserModel userResult = new UserModel("23424524523412", "Test", "test@test.fr", new Date(), new Date(), accounts);

        Mockito.when(mongoTemplate.save(Mockito.any(UserModel.class))).thenReturn(userResult);

        assertThat(userDAO.addUser(user).getUser_id()).isEqualTo("23424524523412");
    }

    @Test
    public void getAllUsersTest() {
        ArrayList<AccountModel> accounts = new ArrayList<>();

        Mockito.when(mongoTemplate.findAll(UserModel.class))
                .then(ignoredInvocation -> Arrays.asList(new UserModel("", "Test", "test@test.fr", new Date(), new Date(), accounts),
                        new UserModel("", "Test", "test@test.fr", new Date(), new Date(), accounts)));

        assertThat(userDAO.getAllUsers()).hasSize(2);
    }

    @Test
    public void getUserByIdTest(){
        String user_id= "34234234234";
        ArrayList<AccountModel> accounts = new ArrayList<>();

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        Mockito.when(mongoTemplate.findOne(query, UserModel.class))
                .then(ignoredInvocation -> new UserModel("34234234234", "Test", "test@test.fr", new Date(), new Date(), accounts));

        assertThat(userDAO.getUserById(user_id).getName()).isEqualTo("Test");
    }



}
