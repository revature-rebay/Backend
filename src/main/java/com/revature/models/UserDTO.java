package com.revature.models;

import lombok.Data;

@Data
public
class UserDTO {
    private String userName;
    private String passWord;
    private String email;
    private String firstName;
    private String lastName;
}
