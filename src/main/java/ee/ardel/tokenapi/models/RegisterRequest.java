package ee.ardel.tokenapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
