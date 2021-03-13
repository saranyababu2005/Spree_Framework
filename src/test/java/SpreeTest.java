import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Listeners(Listeners_Spree.class)
public class SpreeTest extends BaseDriver {
    WebDriver driver;

    HomePage homeSpree;
    LoginPage loginSpree;
    CartPage productCart;
    CheckoutPage checkOutDetails;

    @BeforeClass
    public void driverCreation() throws IOException {
        BaseDriver driver_object=new BaseDriver();
        driver = driver_object.baseDriver();
        driver.manage().timeouts().implicitlyWait(12000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(7000,TimeUnit.MILLISECONDS);
        homeSpree =new HomePage(driver);
        loginSpree =new LoginPage(driver);
        productCart =new CartPage(driver);
        checkOutDetails =new CheckoutPage(driver);
    }
    @AfterClass
    public void quitBrowser()
    {
        driver.quit();
    }

    @Test(dataProvider="credentials",priority = 0)
    public void loginSpree(String username, String password, String status){

        driver.get(BaseDriver.browserFile.getProperty("qa_url"));

        homeSpree.loginLink().click();
        loginSpree.enterEmailId(username);
        loginSpree.enterPwd(password);
        loginSpree.signin().click();

        if(homeSpree.validateAlertMsg().getText().equals("Logged in successfully")) {
            Assert.assertTrue(true);
            System.out.println("true");
        }
        else
            {
                Assert.assertTrue(false);
                System.out.println("false");
            }
        //  LoginTest.logout_spree(home_spree);
    }

    @Test(priority = 6)
    public void logoutSpree()
    {
        homeSpree.logoutLink().click();
        Assert.assertEquals(homeSpree.validateAlertMsg().getText(),"Signed out successfully.");
    }

    @DataProvider
    public Object[][] credentials()
    {
        Object[][] input=new Object[1][3];

        input[0][0]="saranyababu2005@gmail.com";
        input[0][1]="spree1234";
        input[0][2]="valid";

        /*input[1][0]="saranyabab2005@gmail.com";
        input[1][1]="spree1234";
        input[1][2]="valid";

        input[2][0]="saranyababu2005@gmail.com";
        input[2][1]="spree123";
        input[2][2]="valid";*/

        return input;
    }

    @Test(dataProvider = "searchTestData",priority = 1)
    public void verifySearch(String product)
    {
        List<String> searchItemResults;
        Boolean flag=null;

        homeSpree.search(product);
        homeSpree.btnSearch().click();
        searchItemResults = homeSpree.getSearchResults();
        flag= HomePage.validateSearch(searchItemResults,product);

         Assert.assertTrue(flag,"Search Results are related to "+product);
    }

    @DataProvider
    public Object[][] searchTestData()
    {
        Object[][] data=new Object[2][1];

        data[0][0]="Ruby";
        data[1][0]="Apache";

        return data;
    }

    @Test(dataProvider = "dataPriceRange",priority = 2)
    public void verifyDisplayedProducts(String category,String pricerange)
    {
        List<Double> product_price;
        Boolean  matchedCriteria = null;

        homeSpree.selectCategory(category);
        homeSpree.selectPriceRange(pricerange);
        homeSpree.filterSearch().click();
        product_price= homeSpree.getPriceRanges();
        matchedCriteria= HomePage.validateProducts(pricerange,product_price);

        Assert.assertTrue(matchedCriteria,"Products are displayed in the given criteria");
    }
    @DataProvider
    public Object[][] dataPriceRange()
    {
        Object[][] data=new Object[3][2];

        data[0][0]="Bags";
        data[0][1]="$15.00 - $18.00";

        data[1][0]="Mugs";
        data[1][1]="$10.00 - $15.00";

        data[2][0]="Bags";
        data[2][1]="$20.00 or over";

        return data;
    }
    @Test(priority = 4,dependsOnMethods = {"loginSpree"})
    public void validateOrderAmount() throws InterruptedException {

        String product="Ruby on Rails Mug";
        String price, totalOrder;

        homeSpree.selectDept("All departments");
        homeSpree.search(product);
        homeSpree.btnSearch().click();
        homeSpree.addToCart("Ruby on Rails Mug");

        price= productCart.getPrice();
        productCart.addToCart();
        productCart.checkOut();

        totalOrder = checkOutDetails.getOrderTotal();
        Assert.assertEquals(price, totalOrder);
       // product_cart.emptyCart();
    }
    @Test(priority = 5,dependsOnMethods = {"loginSpree"})
    public void verifyCartIsEmpty() throws InterruptedException {
        String product="Ruby on Rails Mug";
        int quantity=3;
        String message;

        homeSpree.selectDept("All departments");
        homeSpree.search(product);
        homeSpree.btnSearch().click();
        homeSpree.addToCart("Ruby on Rails Mug");

        productCart.addQuantity(quantity);
        productCart.addToCart();
        productCart.emptyCart();
        message= productCart.getMessage();
        Assert.assertEquals(message,"Your cart is empty");
    }


}
