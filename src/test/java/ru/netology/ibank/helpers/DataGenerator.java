package ru.netology.ibank;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.ibank.dto.RegistrationDto;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Gson gson = new Gson();
    private static final Random random = new Random();

    private DataGenerator() {
    }

    public static void registerUser(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(gson.toJson(user))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        return "user_" + random.nextInt(10000);
    }

    public static String getRandomPassword() {
        return "pass_" + random.nextInt(10000);
    }

    public static RegistrationDto generateUser(String status) {
        return new RegistrationDto(
                getRandomLogin(),
                getRandomPassword(),
                status
        );
    }

    public static RegistrationDto generateActiveUser() {
        return generateUser("active");
    }

    public static RegistrationDto generateBlockedUser() {
        return generateUser("blocked");
    }
}