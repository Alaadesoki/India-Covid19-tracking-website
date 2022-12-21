package Step_Definition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.io.*;
import static Configuration.configurationFiles.*;


public class Hooks {

        public static WebDriver driver;
        @Before
        public static void open_browser()
        {
            WebDriverManager.chromedriver() .setup();    //setup chrome driver and open it
            ChromeOptions options=new ChromeOptions();
            HashMap<String,Object> hmap=new HashMap<String,Object>();
            hmap.put("download.default_directory",System.getProperty("user.dir") +"\\src\\main\\resources\\Downloads");
            hmap.put("downlod.prompt_for_download" , false);
            options.setExperimentalOption("prefs" , hmap);
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1024, 768)); // browser resolution is 1024x768px
            driver.manage() .window().maximize(); // to maximize browser's windows
        }

        @After
        public static void quit_browser()
        {
            File file = new File(downloadedFilePath);
            file.delete();
            driver.quit();

        } //close the browser
    }




