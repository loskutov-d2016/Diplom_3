package praktikum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.core.IsEqual.equalTo;

public class ConstructorNavigationTest {
    private static WebDriver driver;
    private final Faker faker = new Faker();
    private String email;
    private String password;
    private String name;
    private String accessToken;
    private UserAction userAction;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        userAction = new UserAction(new ApiClient());
        createUniqueUser();
    }

    public void createUniqueUser() {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().fullName();
        accessToken = userAction.registerNewUser(email, password, name);
    }

    @Test
    @Description("Constructor")
    @Story("Проверка перехода к разделам «Соусы», «Начинки», «Булки»")
    public void testNavigateToConstructorSections() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        Thread.sleep(2000);
        mainPage.openMainPage();

        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        Thread.sleep(1000);

        // соус
        mainPage.clickSaucesSection();
        Thread.sleep(1000);
        String saucesHeader = driver.findElement(By.xpath("//div[contains(@class, 'tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect') " +
                "and .//span[text()='Соусы']]")).getText();
        Assert.assertEquals("Sauces section header is not displayed as expected", "Соусы", saucesHeader);

        // начинки
        mainPage.clickFillingsSection();
        Thread.sleep(1000);
        String fillingsHeader = driver.findElement(By.xpath("//div[contains(@class, 'tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect')" +
                " and .//span[text()='Начинки']]")).getText();
        Assert.assertEquals("Fillings section header is not displayed as expected", "Начинки", fillingsHeader);

        // булки
        mainPage.clickBunsSection();
        Thread.sleep(1000);
        String bunsHeader = driver.findElement(By.xpath("//div[contains(@class, 'tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect') " +
                "and .//span[text()='Булки']]")).getText();
        Assert.assertEquals("Buns section header is not displayed as expected", "Булки", bunsHeader);
    }

    @After
    public void tearDown() {
        // удаление пользователя и закрытие браузера
        if (accessToken != null) {
            Response deleteResponse = new ApiClient().deleteUser(accessToken);
            deleteResponse.then().statusCode(202);
            deleteResponse.then().body("message", equalTo("User successfully removed"));
        }

        DriverManager.quitDriver();
    }
}
