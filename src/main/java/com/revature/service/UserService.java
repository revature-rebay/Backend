package com.revature.service;

import com.revature.models.User;
import com.revature.repo.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;


    public User getUser(int id){
        return userDAO.findById(id).orElse(null);

    }

    public boolean saveUser(User user){

        try{
            String pw_hash = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt());
            user.setPassWord(pw_hash);
            System.out.println(pw_hash);
            userDAO.save(user);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;

        }


    public User validateAccount(String userName, String passWord) {

        // Checks if any of the fields are empty
        if (userName.isEmpty() || passWord.isEmpty()){
            return null;
        }
//
//        // Checks if account exists
        User u = userDAO.findByUserName(userName);
        System.out.println(u.getPassWord());
//        // User doesn't exist
        if (u == null) {

            return null;
        }

        if (BCrypt.checkpw(passWord, u.getPassWord())){

            return u;
        }
        return u;

    }


}
