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

    public boolean saveUser(User user){
            if(user!=null){
                user.setRoleId(2);//sets newly registered user to user/customer role
                user.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
                userDAO.save(user);
                return true;
            }

        return false;
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
