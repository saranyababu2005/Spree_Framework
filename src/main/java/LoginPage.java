import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver)
    {
        this.driver=driver;
    }
    private By email=By.cssSelector("input#spree_user_email");
    private By password=By.cssSelector("input#spree_user_password");
    private By btnLogin =By.xpath("//input[@name='commit']");
    private By error=By. cssSelector("div.alert");


    public void enterEmailId(String mailId)
    {
        driver.findElement(email).sendKeys(mailId);
    }

    public void enterPwd(String pwd)
    {
        driver.findElement(password).sendKeys(pwd);
    }

    public WebElement signin()
    {
        return driver.findElement(btnLogin);
    }

    public WebElement errorMsg(){
        return driver.findElement(error);
    }
    }



