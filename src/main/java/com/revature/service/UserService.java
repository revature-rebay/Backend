package com.revature.service;

import com.revature.models.User;
import com.revature.repo.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

//    @Autowired
//    public UserService(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }

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
