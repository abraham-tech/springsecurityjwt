package miu.edu.cse.securitydemo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import miu.edu.cse.securitydemo.user.Role;

@AllArgsConstructor
@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
