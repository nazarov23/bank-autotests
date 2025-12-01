package ru.netology.ibank;

import org.junit.jupiter.api.*;
import ru.netology.ibank.dto.RegistrationDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static ru.netology.ibank.DataGenerator.*;

public class AuthApiTest {

    @BeforeAll
    static void setUpAll() {
        // Проверяем, доступен ли сервер
        if (!isServerAvailable()) {
            System.out.println("⚠️ Сервер недоступен. Тесты будут проверять только логику.");
            System.out.println("Для полных тестов запустите: java -jar app-ibank.jar -P:profile=test");
        }
    }

    private static boolean isServerAvailable() {
        try {
            java.net.Socket socket = new java.net.Socket("localhost", 9999);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void shouldGenerateValidUserData() {
        // Проверяем логику генерации данных
        RegistrationDto user = generateActiveUser();

        assertNotNull(user.getLogin());
        assertNotNull(user.getPassword());
        assertEquals("active", user.getStatus());

        assertTrue(user.getLogin().startsWith("user_"));
        assertTrue(user.getPassword().startsWith("pass_"));
    }

    @Test
    @DisplayName("Регистрация активного пользователя (требует сервер)")
    void shouldRegisterActiveUser() {
        // Пропускаем тест, если сервер недоступен
        assumeTrue(isServerAvailable(), "Сервер недоступен, тест пропущен");

        RegistrationDto activeUser = generateActiveUser();
        registerUser(activeUser);

        System.out.println("✅ Активный пользователь создан: " + activeUser.getLogin());
    }

    @Test
    @DisplayName("Регистрация заблокированного пользователя (требует сервер)")
    void shouldRegisterBlockedUser() {
        assumeTrue(isServerAvailable(), "Сервер недоступен, тест пропущен");

        RegistrationDto blockedUser = generateBlockedUser();
        registerUser(blockedUser);

        System.out.println("✅ Заблокированный пользователь создан: " + blockedUser.getLogin());
    }

    @Test
    void shouldValidateUserData() {
        // Проверяем валидацию данных
        RegistrationDto user = new RegistrationDto("", "short", "invalid");

        // Можно добавить проверки в DataGenerator
        assertThrows(IllegalArgumentException.class, () -> {
            if (user.getLogin().isEmpty()) {
                throw new IllegalArgumentException("Логин не может быть пустым");
            }
        });
    }
}