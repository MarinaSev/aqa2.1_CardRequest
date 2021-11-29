package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneValidationTest {
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
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.className("checkbox__box")).click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSend12Numbers() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+799911122333");
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__top")).getText();
        String expexted = "Мобильный телефон";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldSend10Numbers() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7999111223");
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__top")).getText();
        String expexted = "Мобильный телефон";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldSendWithoutPlus() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("89991112233");
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__top")).getText();
        String expexted = "Мобильный телефон";
        assertEquals(expexted, actualText);
    }

    @Test
    public void shouldSendEmptyPhoneField() {
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__top")).getText();
        String expexted = "Мобильный телефон";
        assertEquals(expexted, actualText);
    }
}
