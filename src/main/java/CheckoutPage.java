import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver)
    {
        this.driver=driver;
    }

    private By orderTotal=By.cssSelector("span#summary-order-total");

    public String getOrderTotal(){
        return driver.findElement(orderTotal).getText();
    }
}
