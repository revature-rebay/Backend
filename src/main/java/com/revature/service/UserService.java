package com.revature.service;

import com.revature.models.User;
import com.revature.repo.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class UserService {

    //DAO object, required field
    private final UserDAO userDAO;

    //returns user associated with a certain id or null if no one with that id exists
    public User getUser(int id){
        return userDAO.findById(id).orElse(null);
    }

    //insert or update a user's info, return new user info
    public User saveUser(User user){
        //if user doesn't exist yet, encrypt the password
        if(getUser(user.getId())==null){
            user.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        }

        //update/insert, fails if it violates unique constraint on username and email
        try{
            return userDAO.save(user);
        }catch (Exception e){
            return null;
        }
    }


    public User validateAccount(String userName, String passWord) {

        // Checks if any of the fields are empty
        if (userName.isEmpty() || passWord.isEmpty()){
            return null;
        }
//
//        // Checks if account exists
        User u = userDAO.findByUserName(userName);
//        // User doesn't exist

        if (u == null) {

            return null;
        }

        if (BCrypt.checkpw(passWord, u.getPassWord())){

            return u;
        }
        return null;

    }


}
