import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private By linkLogin = By.cssSelector("a.nav-link");
    private By validateMsg = By.cssSelector("div.alert");
    private By linkLogout = By.xpath("//a[@href='/logout']");
    private By search = By.xpath("//input[@type='search']");
    private By btnSearch = By.cssSelector("input[type='submit']");
    private By searchResult = By.xpath("//img[@itemprop='image']");
    private By categoryBags = By.linkText("Bags");
    private By categoryMugs = By.linkText("Mugs");
    private By categoryClothing = By.linkText("Clothing");
    private By priceLabel = By.xpath("//label[@class='nowrap']");
    private By priceCheckBox = By.xpath("//input[@type='checkbox']");
    private By searchFilter = By.xpath("//input[@class='btn btn-primary']");
    private By categoryTitle = By.cssSelector("h1.taxon-title");
    private By priceRange = By.cssSelector("span[itemprop='price']");
    private By addProduct = By.xpath("//span[@itemprop='name']");
    private By dept = By.cssSelector("select#taxon");


    public WebElement loginLink() {
        return driver.findElement(linkLogin);
    }

    public WebElement validateAlertMsg() {
        return driver.findElement(validateMsg);
    }

    public WebElement logoutLink() {
        return driver.findElement(linkLogout);
    }

    public void search(String text) {
        driver.findElement(search).clear();
        driver.findElement(search).sendKeys(text);
    }

    public WebElement btnSearch() {
        return driver.findElement(btnSearch);

    }

    public List<String> getSearchResults() {
        List<WebElement> results = driver.findElements(searchResult);
        List<String> searchText = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            searchText.add(results.get(i).getAttribute("alt"));
        }
        return searchText;
    }

    public void selectCategory(String category) {
        if (category.equals("Bags"))
            driver.findElement(categoryBags).click();
        else if (category.equals("Mugs"))
            driver.findElement(categoryMugs).click();
        else if (category.equals("Clothing"))
            driver.findElement(categoryClothing).click();
    }

    public void selectPriceRange(String priceRange) {
        List<WebElement> priceLabels = driver.findElements(priceLabel);
        List<WebElement> priceCheckBoxes = driver.findElements(priceCheckBox);
        for (int i = 0; i < priceLabels.size(); i++) {
            if (priceRange.equals(priceLabels.get(i).getText()))
                priceCheckBoxes.get(i).click();
        }
    }

    public WebElement filterSearch() {
        return driver.findElement(searchFilter);
    }

    public WebElement getTitle() {
        return driver.findElement(categoryTitle);
    }

    public List<Double> getPriceRanges() {
        List<WebElement> priceRanges = driver.findElements(priceRange);
        List<Double> productPrice = new ArrayList<>();
        for (int i = 0; i < priceRanges.size(); i++)
            productPrice.add(Double.parseDouble((priceRanges.get(i).getText().replace("$", ""))));
        return productPrice;
    }

    public void addToCart(String product) {
        List<WebElement> products = driver.findElements(addProduct);
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getAttribute("title").equals(product)) {
                products.get(i).click();
                break;
            }

        }
    }

    public void selectDept(String department) {
        Select departments = new Select(driver.findElement(dept));
        departments.selectByVisibleText(department);
    }

    public static boolean validateProducts(String priceRange, List<Double> productPrice) {
        double lowerprice = 0, upperprice = 0, flag = 0;
        Boolean matchedCriteria = null;
        //List<Double> product_price;
        if (priceRange.indexOf("Under") >= 0) {
            String[] price = priceRange.split("$");
            upperprice = Double.parseDouble((price[1].replace("$", "")));
            lowerprice = 0.0;
            flag = 1;
        } else if (priceRange.indexOf("over") >= 0) {
            String[] price = priceRange.split("or");
            lowerprice = Double.parseDouble((price[0].replace("$", "")));
            upperprice = 0;
            flag = 2;
        } else if (priceRange.indexOf("-") >= 0) {
            String[] price = priceRange.split("-");
            lowerprice = Double.parseDouble((price[0].replace("$", "")));
            upperprice = Double.parseDouble((price[1].replace("$", "")));
            flag = 3;
        }


        if (flag == 1) {
            for (int i = 0; i < productPrice.size(); i++) {
                if (productPrice.get(i) < upperprice)
                    matchedCriteria=true;
                    //Assert.assertTrue(product_price.get(i) < upperprice, "Products displayed are in given range");
                else
                    matchedCriteria=false;
                    //Assert.assertTrue(product_price.get(i) < upperprice, "Products displayed are not in given range");
            }
        } else if (flag == 2) {
            for (int i = 0; i < productPrice.size(); i++) {
                if (productPrice.get(i) > lowerprice)
                    matchedCriteria=true;
                    //Assert.assertTrue(product_price.get(i) > lowerprice, "Products displayed are in given range");
                else
                    matchedCriteria=false;
                    //Assert.assertTrue(product_price.get(i) > lowerprice, "Products displayed are not in given range");
            }
        } else if (flag == 3) {
            System.out.println(productPrice);
            for (int i = 0; i < productPrice.size(); i++) {
                if ((productPrice.get(i) > lowerprice) && (productPrice.get(i) < upperprice))
                    matchedCriteria=true;
                    //Assert.assertTrue((product_price.get(i) > lowerprice) && (product_price.get(i) < upperprice), "Products displayed are in given range");
                else
                    matchedCriteria=false;
                    //Assert.assertTrue((product_price.get(i) > lowerprice) && (product_price.get(i) < upperprice), "Products displayed are not in given range");
            }

        }
        return  matchedCriteria;
    }

    public static Boolean validateSearch(List<String> searchItemResults,String product)
    {
        Boolean flag=null;
        for(int i=0;i<searchItemResults.size();i++)
        {
            //System.out.println(search_item_results.get(i));
            if(searchItemResults.get(i).indexOf(product)>=0)
                flag=true;
            else {
                flag = false;                ;
                break;
            }
        }
        return flag;
    }
}
