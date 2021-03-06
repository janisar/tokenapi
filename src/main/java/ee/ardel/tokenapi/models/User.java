package ee.ardel.tokenapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@Builder
public class User {

    @Indexed
    private String id;
    @Indexed
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}
