package praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MainPage {
    private final WebDriver driver;

    private final By personalAccountButton = By.xpath("//*[text()='Личный Кабинет']");

    private final By registerButton = By.className("Auth_link__1fOlj");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("открытие страницы")
    public void openMainPage() {
        driver.get("https://stellarburgers.nomoreparties.site/");
    }
    @Step("нажатие кнопки 'PersonalAccountButton'")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }
    @Step("нажатие кнопки 'registerButton'")
    public void registerButton() {
        driver.findElement(registerButton).click();
    }
    @Step("нажатие кнопки 'LoginButton'")
    public void clickLoginButton() {
        driver.findElement(By.xpath("//button[text()='Войти в аккаунт']")).click();
    }
    @Step("нажатие кнопки 'PersonalCabinet'")
    public void clickPersonalCabinetButton() {
        driver.findElement(By.xpath("//*[text()='Личный Кабинет']")).click();
    }
    @Step("нажатие кнопки 'LoginWithRegistration'")
    public void clickLoginWithRegistrationButton() {
        driver.findElement(By.xpath("//*[@class='Auth_link__1fOlj']")).click();//кнопка воити на реге
    }

    @Step("нажатие кнопки 'PasswordRecoveryButton'")
    public void clickPasswordRecoveryButton() {
        driver.findElement(By.xpath("//*[text()='Восстановить пароль']")).click();
    }

    //Персональная информация

    private final By constructorButton = By.xpath("//*[text()='Конструктор']");
    private final By stellarBurgersLogo = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");
    private final By logoutButton = By.xpath("//*[text()='Выход']");
    @Step("нажатие кнопки 'Constructor'")
      public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }
    @Step("нажатие кнопки 'StellarBurgers'")
    public void clickStellarBurgersLogo() {
        driver.findElement(stellarBurgersLogo).click();
    }
    @Step("нажатие кнопки 'Logout'")
    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }
    //конструктор
    private final By bunsSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Булки']]");
    private final By saucesSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Соусы']]");
    private final By fillingsSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Начинки']]");
    @Step("нажатие кнопки 'Buns'")
    public void clickBunsSection() {
        driver.findElement(bunsSection).click();
    }
    @Step("нажатие кнопки 'Sauces'")
    public void clickSaucesSection() {
        driver.findElement(saucesSection).click();
    }
    @Step("нажатие кнопки 'Fillings'")
    public void clickFillingsSection() {
        driver.findElement(fillingsSection).click();
    }

}