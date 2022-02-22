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
        //return userDAO.findById(id).orElse(null);
        return null;
    }

    public boolean saveUser(User user){

//        try{
//            userDAO.save(user);
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;

        return false;
    }


    public User validateAccount(String userName, String passWord) {

        // Checks if any of the fields are empty
//        if (userName.isEmpty() || passWord.isEmpty()){
//            return null;
//        }
//
//        // Checks if account exists
//        User u = userDAO.findByUsername(userName);
//
//        // User doesn't exist
//        if (u == null) {
//            return null;
//        }
//        // Checks if passwords match
//        if (!u.getPassWord().equals(passWord)){
//            return null;
//        }
//        return u;
        return null;
    }


}
