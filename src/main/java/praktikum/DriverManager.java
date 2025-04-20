package praktikum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser", "chrome");
            if (browser.equals("firefox")) {
                driver = new FirefoxDriver();
            } else if (browser.equals("yandex")) {
                // Укажите путь к драйверу Яндекс Браузера
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\Администратор\\Downloads\\yandexdriver.exe"); // Путь к драйверу
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:\\Users\\Администратор\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe"); // Путь к исполняемому файлу Яндекс Браузера
                driver = new ChromeDriver(options);
            } else {
                driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
