package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registr {
    private String email;
    private String password;

    /*public Registr(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Registr(){

    }*/

    //public String getEmail() {
    //    return email;
    //}

    //public String getPassword() {
       // return password;
   // }
}
