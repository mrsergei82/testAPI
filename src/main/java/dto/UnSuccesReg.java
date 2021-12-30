package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnSuccesReg {

    private String error;

    /*public UnSuccesReg(String error) {
        this.error = error;
    }
    public UnSuccesReg(){

    }*/

   /* public String getError() {
        return error;
    }*/
}
