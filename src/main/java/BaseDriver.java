import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Properties;

public class BaseDriver {
   private static WebDriver driver=null;
    static Properties browserFile;

   public WebDriver baseDriver() throws IOException {
        browserFile =new Properties();
        FileInputStream fis=new FileInputStream("/Users/saranyababu/IdeaProjects/Spree_Framework/src/main/java/Browser_properties");
        browserFile.load(fis);
        String browser= browserFile.getProperty("browser");
        if (browser.equals("chrome"))
        {
            System.setProperty("webdriver.chrome.driver","/Users/saranyababu/Downloads/chromedriver");
            if(driver==null)
                driver=new ChromeDriver();
        }
        return driver;

    }

    public static String screenshot(String tc_name) throws IOException {
        TakesScreenshot screen=(TakesScreenshot) BaseDriver.driver;
        File name=screen.getScreenshotAs(OutputType.FILE);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));
        String format=dtf.format(now);
        format=format.replace("/","_");
        format=format.replace(" ","_");
        format=tc_name+"_"+format;
        System.out.println(format);
        String dest=System.getProperty("user.dir")+"//Screenshots//"+format+".png";
        File destination=new File(dest);
        FileUtils.copyFile(name,new File(dest));
        InputStream is=new FileInputStream(dest);
        byte[] imageBytes= FileUtils.readFileToByteArray(destination);
        String base64= "data:image/png;base64,"+ Base64.getEncoder().encodeToString(imageBytes);
        return base64;
       // return dest;
    }
    public static String attachScreenshot(String filepath)
    {

        System.out.println(filepath);

        String path="<br><img src='"+filepath+"' height='400' width='400'/><br>";

        return path;
    }
}
