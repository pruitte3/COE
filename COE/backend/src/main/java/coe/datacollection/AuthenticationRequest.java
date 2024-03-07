package coe.datacollection;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;

    // Default constructor
    public AuthenticationRequest() {
    }

    // Constructor with parameters
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

