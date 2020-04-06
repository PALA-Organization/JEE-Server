package fr.pala.accounting.dal;

import fr.pala.accounting.model.UserModel;

import java.util.List;

public interface UserDAL {

    UserModel addUser(UserModel user);

    List<UserModel> getAllUsers();

    UserModel getUserById(String user_id);

    void updateUser(UserModel user);

}
