package UpdateUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // for generating getter() and setter()
@AllArgsConstructor // Create a constructor with all the class variables
@NoArgsConstructor  // To add default constructor
@Builder // I want to use a builder pattern // is to apply the builder pattern
public class UserLombok {
    private String name;
    private String email;
    private String gender;
    private String status;
}
