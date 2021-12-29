import io.qameta.allure.Epic;
import org.example.*;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RecTest {
    private final static String URL = "https://reqres.in/";

    @Test
    @Epic("Status200")
    @DisplayName("Проверяем ID на аватаре")
    public void chekAvatarAndIdTest(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationOk200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("data",UserData.class);
        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
    }
    @Test
    @Epic("Status200")
    @DisplayName("Проверяем регистрацию пользователя")
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
                .statusCode(200)
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        Assert.assertEquals(id,successReg.getId());
        Assert.assertEquals(token,successReg.getToken());
    }
    @Test
    @Epic("Status400")
    @DisplayName("Неудачная регистрация")
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
    @Test
    @Epic("Status200")
    @DisplayName("Сортировка по годам")
    public void sortYearTest(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationOk200());
        List<ColorData> colorData = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract()
                .body()
                .jsonPath()
                .getList("data",ColorData.class);
        List<Integer> years = colorData.stream().map(ColorData::getYear).collect(Collectors.toList());
        List<Integer> sortYears = years.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(sortYears,years);
    }
    @Test
    @Epic("AnotherStatus")
    @DisplayName("Удаление пользователя")
    public void deleteTest(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationStat(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all()
                .statusCode(204);

    }
    @Test
    @Epic("Status200")
    @DisplayName("Изменение пользователя")
    public void updateUser(){
        Specifications.instSpecific(Specifications.requestSpecification(URL),Specifications.responseSpecificationOk200());
        UpdateUser updateUser = new UpdateUser("morpheus","zion resident");
        UpdateUserData updateUserData = given()
                .body(updateUser)
                .when()
                .put("api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().as(UpdateUserData.class);
        Assert.assertNotNull(updateUserData.getName());
        Assert.assertNotNull(updateUserData.getJob());
        Assert.assertNotNull(updateUserData.getUpdatedAt());
        Assert.assertEquals("morpheus",updateUserData.getName());
        Assert.assertEquals("zion resident",updateUserData.getJob());

    }
}
