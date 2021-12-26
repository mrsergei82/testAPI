import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RecTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void chekAvatarAndIdTest(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationOk200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract()
                .body()
                .jsonPath()
                .getList("data",UserData.class);
        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
    }
    @Test
    public void regTest(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationOk200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        Registr user = new Registr("eve.holt@reqres.in","pistol");

        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        Assert.assertEquals(id,successReg.getId());
        Assert.assertEquals(token,successReg.getToken());
    }
    @Test
    public void noRegTest(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationErr400());
        String error = "Missing password";
        Registr user = new Registr("sydney@fife","");
        UnSuccesReg unSuccesReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .statusCode(400)
                .extract().as(UnSuccesReg.class);
        Assert.assertEquals(error,unSuccesReg.getError());

    }
}
