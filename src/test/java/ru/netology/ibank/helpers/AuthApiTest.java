package ru.netology.ibank;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.dto.RegistrationDto;

import static io.restassured.RestAssured.given;
import static ru.netology.ibank.DataGenerator.*;

public class AuthApiTest {

    @BeforeAll
    static void setUpAll() {
        // Общая настройка для всех тестов
        // Можно оставить пустым, так как спецификация уже в DataGenerator
    }

    @Test
    void shouldRegisterActiveUser() {
        // Генерируем активного пользователя
        RegistrationDto activeUser = generateActiveUser();

        // Регистрируем его
        registerUser(activeUser);

        System.out.println("Зарегистрирован активный пользователь: " + activeUser.getLogin());
    }

    @Test
    void shouldRegisterBlockedUser() {
        // Генерируем заблокированного пользователя
        RegistrationDto blockedUser = generateBlockedUser();

        // Регистрируем его
        registerUser(blockedUser);

        System.out.println("Зарегистрирован заблокированный пользователь: " + blockedUser.getLogin());
    }

    @Test
    void shouldOverwriteUserWithSameLogin() {
        // Создаем первого пользователя
        RegistrationDto user1 = new RegistrationDto(
                "same_login_user",
                "password1",
                "active"
        );

        // Создаем второго пользователя с тем же логином
        RegistrationDto user2 = new RegistrationDto(
                "same_login_user",  // Тот же логин!
                "password2",        // Другой пароль
                "blocked"           // Другой статус
        );

        // Первая регистрация
        registerUser(user1);
        System.out.println("Первый пользователь создан: " + user1.getLogin());

        // Перезапись пользователя (должна работать согласно документации)
        registerUser(user2);
        System.out.println("Пользователь перезаписан: " + user2.getLogin());

        // Здесь можно добавить проверку, что данные изменились
        // Например, сделать запрос на проверку пользователя
    }

    @Test
    void shouldRegisterMultipleRandomUsers() {
        // Пример регистрации нескольких случайных пользователей
        for (int i = 0; i < 3; i++) {
            RegistrationDto user = generateActiveUser();
            registerUser(user);
            System.out.println("Зарегистрирован пользователь #" + (i+1) + ": " + user.getLogin());
        }
    }
}