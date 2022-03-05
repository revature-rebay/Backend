package com.revature.service;

import com.revature.models.User;
import com.revature.repo.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    //DAO object, required field
    private final UserDAO userDAO;

    //returns user associated with a certain id or null if no one with that id exists
    public User getUser(int id){
        return userDAO.findById(id).orElse(null);
    }

    //insert or update a user's info, return new user info or null if info violates constraints
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

    //find user with matching username, and verify password, returning whole user object
    public User validateAccount(String userName, String passWord) {
        //get user from db, if they exist
        User user = userDAO.findByUserName(userName);
        //if user exists and password is correct, return user, otherwise return null
        return (user!=null && BCrypt.checkpw(passWord, user.getPassWord())) ? user:null;
    }
}
