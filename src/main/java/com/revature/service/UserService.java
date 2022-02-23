package com.revature.service;

import com.revature.models.User;
import com.revature.repo.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(int id){
        return null;
    }

    public boolean saveUser(User user){

        return false;
    }


    public User validateAccount(String userName, String passWord) {

        return null;
    }


}
