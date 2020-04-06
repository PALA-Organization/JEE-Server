package fr.pala.accounting.dal;


import fr.pala.accounting.server.ServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountDALImplTest {

    @MockBean
    private MongoTemplate mongoTemplate;

    ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class);
    private AccountDALImpl accountDALImpl = context.getBean(AccountDALImpl.class);

    @Test
    public void getAllAccountsOfUsersTest() {
       /* String user_id =

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").regex("user_id"));
        ArrayList<String> transactions_ids = new ArrayList<String>();
        transactions_ids.add("5676585787");
        Mockito.when(mongoTemplate.find(query, AccountModel.class))
                .then(ignoredInvocation -> Arrays.asList(new AccountModel("2398293819238", 12.0, transactions_ids)));


        accountDALImpl.getAllAccountsOfUsers()

        // verification du résultat
        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).containsExactly(new Product("ID", "CODE", "LABEL", "DESCRIPTION", 1234D));*/
    }


}
