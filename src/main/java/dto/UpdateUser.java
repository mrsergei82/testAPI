package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    private String name;
    private String job;

   /* public UpdateUser(String name, String job) {
        this.name = name;
        this.job = job;
    }
    public UpdateUser(){}*/

    /*public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }*/
}
