package captainadhoc.captainadhocbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
