package com.revature.utils;

import com.revature.models.User;
import org.springframework.http.ResponseCookie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Cookies {

    //name value for the cookie
    static final String COOKIENAME = "rebay_User";

    //private constructor since util shouldn't be instantiated
    private Cookies() {
        throw new IllegalStateException("Utility class");
    }

    // Converts User Into a cookie object
    public static ResponseCookie buildResponseCookie(User user){

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();

            return ResponseCookie.from(COOKIENAME,
                            Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()))
                    .maxAge(7 * 24 * 60 * 60L)
                    .build();
        //if conversion fails, return an empty cookie
        }catch (Exception e){
            e.printStackTrace();
            return ResponseCookie.from(COOKIENAME,"").maxAge(0).build();
        }
    }

    //returns user object derived from cookie value
    public static User isCookieValid(String cookie){
        if (cookie.isEmpty()) {
            return null;
        }
        byte[] data = Base64.getDecoder().decode(cookie);

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            User obj = (User) objectInputStream.readObject();
            objectInputStream.close();
            if (obj != null) {
                return obj;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public static ResponseCookie nullResponseCookie(){
        // Returns Empty Cookie
        return ResponseCookie.from(COOKIENAME,"").maxAge(0).build();
    }
}