import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By title=By.cssSelector("h1.product-title");
    private By price=By.xpath("//span[@itemprop='price']");
    private By addProduct =By.cssSelector("button#add-to-cart-button");
    private By checkout=By.cssSelector("button#checkout-link");
    private By cart=By.xpath("//a[@href='/cart']");
    private By emptyCart=By.xpath("//input[@value='Empty Cart']");
    private By quanityText = By.cssSelector("input#quantity");
    private By emptyMsg =By.cssSelector("div.alert.alert-info");

    public String getTitle()
    {
        String text=driver.findElement(title).getText();
        return text;
    }
    public String getPrice()
    {
        String price_value=driver.findElement(price).getText();
        return price_value;
    }

    public void addToCart()
    {
        driver.findElement(addProduct).click();
    }

    public void checkOut()
    {
        driver.findElement(checkout).click();
    }

    public void emptyCart() throws InterruptedException {
        driver.findElement(cart).click();
        //Thread.sleep(3000);
        driver.findElement(emptyCart).click();
    }

    public void addQuantity(int quantity)
    {
       driver.findElement(quanityText).clear();
        driver.findElement(quanityText).sendKeys(String.valueOf(quantity));
    }

    public String getMessage()
    {
        return driver.findElement(emptyMsg).getText();
    }

}
