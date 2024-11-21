package pojo;

//pojo: plain old java object
//can not extent/implement anything - pojo class can not extent any class
// we need to create private data fields(variables)
// to access those private fields we need to create public getter/setter
//so basically pojo class always follows the encapsulation
//pojo class should have one public const

//Problems of pojo class
// If we have a lot of variable then we need to create a lot of getters() and setters() and constructors
// soluation  to that problem is - lombok
// lombok will provide annotations
// Lombok says that just provide me the blueprint /how many variables do we need in that class
// other things like getters()/setters()/constructors()(argument constructor/without argument constructors) will be taken care by lombok annotation
//lombok is only available in java and also used by API designer / in spring boot
public class Credentials {

    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //getter and setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
