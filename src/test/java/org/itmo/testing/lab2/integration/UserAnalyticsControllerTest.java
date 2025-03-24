package org.itmo.testing.lab2.integration;

import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.itmo.testing.lab2.controller.UserAnalyticsController;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAnalyticsControllerTest {
    private Javalin app;
    private int port = 7000;

    @BeforeAll
    void setUp() {
        app = UserAnalyticsController.createApp();
        app.start(port);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @AfterAll
    void tearDown() {
        app.stop();
    }

    // POST /register
    @Test
    @Order(1)
    @DisplayName("Тест регистрации без userName")
    void testRegisterWithoutUserName() {
        given()
                .queryParam("userId", "user123")
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .body(equalTo("Missing parameters"));
    }

    @Test
    @Order(2)
    @DisplayName("Тест регистрации без userId")
    void testRegisterWithoutUserId() {
        given()
                .queryParam("userName", "Name")
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .body(equalTo("Missing parameters"));
    }

    // POST /recordSession
    @Test
    @Order(3)
    @DisplayName("Тест записи сессии без userId")
    void testRecordSessionWithoutUserId() {
        LocalDateTime now = LocalDateTime.now();
        given()
                .queryParam("loginTime", now.minusHours(1).toString())
                .queryParam("logoutTime", now.toString())
                .when()
                .post("/recordSession")
                .then()
                .statusCode(400)
                .body(equalTo("Missing parameters"));
    }

    @Test
    @Order(4)
    @DisplayName("Тест записи сессии без loginTime")
    void testRecordSessionWithoutLoginTime() {
        LocalDateTime now = LocalDateTime.now();
        given()
                .queryParam("userId", "user123")
                .queryParam("logoutTime", now.toString())
                .when()
                .post("/recordSession")
                .then()
                .statusCode(400)
                .body(equalTo("Missing parameters"));
    }

    // POST /recordSession
    @Test
    @Order(5)
    @DisplayName("Тест записи сессии с некорректным форматом даты")
    void testRecordSessionWithInvalidDateFormat() {
        given()
                .queryParam("userId", "user123")
                .queryParam("loginTime", "invalid-date")
                .queryParam("logoutTime", "invalid-date")
                .when()
                .post("/recordSession")
                .then()
                .statusCode(400)
                .body(containsString("Invalid data:"));
    }

    // GET /totalActivity
    @Test
    @Order(6)
    @DisplayName("Тест получения общего времени активности без userId")
    void testGetTotalActivityWithoutUserId() {
        given()
                .when()
                .get("/totalActivity")
                .then()
                .statusCode(400)
                .body(equalTo("Missing userId"));
    }

    // GET /inactiveUsers
    @Test
    @Order(7)
    @DisplayName("Тест получения неактивных пользователей без days")
    void testGetInactiveUsersWithoutDays() {
        given()
                .when()
                .get("/inactiveUsers")
                .then()
                .statusCode(400)
                .body(equalTo("Missing days parameter"));
    }

    // GET /monthlyActivity
    @Test
    @Order(9)
    @DisplayName("Тест получения метрик активности без userId")
    void testGetMonthlyActivityWithoutUserId() {
        given()
                .queryParam("month", "2023-10")
                .when()
                .get("/monthlyActivity")
                .then()
                .statusCode(400)
                .body(equalTo("Missing parameters"));
    }

    @Test
    @Order(10)
    @DisplayName("Тест получения метрик активности без month")
    void testGetMonthlyActivityWithoutMonth() {
        given()
                .queryParam("userId", "user1")
                .when()
                .get("/monthlyActivity")
                .then()
                .statusCode(400)
                .body(equalTo("Missing parameters"));
    }

    @Test
    @Order(11)
    @DisplayName("Тест получения метрик активности с несуществующим userId")
    void testGetMonthlyActivityWithNonExistentUserId() {
        given()
                .queryParam("userId", "nonexistentuser")
                .queryParam("month", "2023-10")
                .when()
                .get("/monthlyActivity")
                .then()
                .statusCode(400)
                .body(containsString("No sessions found for user"));
    }
}