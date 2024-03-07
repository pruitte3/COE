package coe.datacollection;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;
    private String pin;
    private String firstName;
    private String lastName;
    private String rank;
    private String department;
    private String role;
}
