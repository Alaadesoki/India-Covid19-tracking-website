package Step_Definition;

import Pages.CompareCSVFiles_Page;
import Pages.CompareExcelSheets_Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileReader;
import java.time.Duration;


import au.com.bytecode.opencsv.CSVReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Configuration.configurationFiles.*;

public class RowDataStepDef {


    @Given("India COVID API website is open")
    public void covidIndiaAPIWebsiteIsOpen() {

      //Navigate to covid19india website
        Hooks.driver.navigate().to(URL);
        Hooks.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @When("I download raw_data1 file")
    public void downloadFile () throws InterruptedException {

      //download the report
        By raw_data1 = By.partialLinkText("raw_data1");
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(raw_data1));
        Hooks.driver.findElement(raw_data1).click();
        Thread.sleep(5000);
    }

    @Then("I find the CSV of the report previously generated is similar to the newly generated CSV report")
    public void compareCsvFiles()  throws Exception{

      //compare between 2 CSV files
        CSVReader readOldCSV = new CSVReader(new FileReader(positiveDataFilePath));
        CSVReader readNewFile = new CSVReader(new FileReader(downloadedFilePath));
        CompareCSVFiles_Page compareCSV = new CompareCSVFiles_Page();
        compareCSV.compareNumberOfRowsAndColumnsInCSVFiles(readOldCSV,readNewFile);
        compareCSV.compareDataOfBothCSVFiles(downloadedFilePath,positiveDataFilePath);
    }

    @Then("I find the csv {string} report is the same as the newly generated report")
    //function to run the same test case with both negative and positive files
    public void iFindTheReportInCSVIsTheSameAsTheNewlyGeneratedReport(String arg0)throws Exception {
        String PreviouslyGeneratedFilePath = "";
        switch (arg0) {
            case "Positive scenario file":
                PreviouslyGeneratedFilePath= positiveDataFilePath;
                break;
            case "Negative scenario file":
                PreviouslyGeneratedFilePath= negativeDataFilePath;
                break;}

        //compare between 2 CSV files
        CSVReader readOldCSV = new CSVReader(new FileReader(PreviouslyGeneratedFilePath));
        CSVReader readNewFile = new CSVReader(new FileReader(downloadedFilePath));
        CompareCSVFiles_Page compareCSV = new CompareCSVFiles_Page();
        compareCSV.compareNumberOfRowsAndColumnsInCSVFiles(readOldCSV,readNewFile);
        compareCSV.compareDataOfBothCSVFiles(downloadedFilePath,PreviouslyGeneratedFilePath);

    }

    @Then("I find the xsl {string} report previously generated is similar to the newly generated xsl report")
    public void compareXslFiles(String arg0)  throws Exception{
            String PreviouslyGeneratedFilePath = "";
            switch (arg0) {
                case "Positive scenario file":
                    PreviouslyGeneratedFilePath= positiveDataFilePath;
                    break;
                case "Negative scenario file":
                    PreviouslyGeneratedFilePath= negativeDataFilePath;
                    break;}

        // compare between 2 Excel sheets files
        Workbook wb1 = WorkbookFactory.create(new File(downloadedFilePath));
        Workbook wb2 = WorkbookFactory.create(new File(PreviouslyGeneratedFilePath));
        CompareExcelSheets_Page compareSheets = new CompareExcelSheets_Page();
        compareSheets.compareNumberOfSheetsInBothFiles(wb1, wb2);
        compareSheets.verifySheetsInExcelFilesHaveSameNumberOfRowsAndColumns(wb1,wb2);
        compareSheets.compareDataOfBothExcelFiles(wb1,wb2);
    }


}








