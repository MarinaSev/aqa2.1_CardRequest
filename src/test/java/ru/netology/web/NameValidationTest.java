package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameValidationTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void driverSetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79991112233");
        driver.findElement(By.className("checkbox__box")).click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldInputNameInEnglish() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ivanov Ivan");
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.className("input_invalid"));
        String actualText = element.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        String expexted = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expexted, actualText);

        }

    @Test
    public void shouldInputNameInChineese() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("灿木");
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.cssSelector("[data-test-id='name']"));
        String actualText = element.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        String expexted = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldInputNameInArabian() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("محمود الحسين");
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.cssSelector("[data-test-id='name']"));
        String actualText = element.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        String expexted = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldInputNameInNumbers() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("+79993332211");
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.cssSelector("[data-test-id='name']"));
        String actualText = element.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        String expexted = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldInputNameInLettersAndNumbers() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Алиса13 Петрова");
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.cssSelector("[data-test-id='name']"));
        String actualText = element.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        String expexted = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldSendEmptyNameField() {
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.cssSelector("[data-test-id='name']"));
        String actualText = element.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        String expexted = "Поле обязательно для заполнения";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldInputNameWithHyphen() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Анна-Ева Петрова");
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expexted = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldInputSurnameWithHyphen() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Анна Петрова-Водкина");
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expexted = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expexted, actualText);
    }
}
