package praktikum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationTests {
    private static WebDriver driver;
    private final Faker faker = new Faker();

    @Before
    public void setUpClass() {
        driver = DriverManager.getDriver();
    }

    @Test
    @Description("Registration")
    @Story("Успешная регистрация.")
    public void testSuccessfulRegistration() throws InterruptedException {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = "validPassword";

        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickPersonalAccountButton();
        mainPage.registerButton();
        Thread.sleep(1000);

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.fillRegistrationForm(name, email, password);
        Thread.sleep(1000);

        // Проверка наличия текста "Вход"
        String loginText = driver.findElement(By.xpath("//h2[text()='Вход']")).getText();
        Assert.assertEquals("Login text is not displayed as expected", "Вход", loginText);
    }

    @Test
    @Description("Registration")
    @Story("Ошибка некорректного пароля.")
    public void testRegistrationWithInvalidPassword() throws InterruptedException {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = "short"; // Некорректный пароль

        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickPersonalAccountButton();
        mainPage.registerButton();
        Thread.sleep(1000);

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.fillRegistrationForm(name, email, password);
        Thread.sleep(1000);

        // Проверка отображения ошибки
        String errorMessage = registrationPage.getErrorMessage();
        Assert.assertEquals("Error message is not as expected", "Некорректный пароль", errorMessage);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
