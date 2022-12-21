package Pages;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import au.com.bytecode.opencsv.CSVReader;
import org.testng.Assert;

public class CompareCSVFiles_Page {

    /*
      This method checks if both CSV files have same number of rows and columns

     */
    public void compareNumberOfRowsAndColumnsInCSVFiles(CSVReader readOldCSV, CSVReader readerNewFile) throws Exception {
        //Read the CSV files
        List<String[]> list1 = readOldCSV.readAll();
        List<String[]> list2 = readerNewFile.readAll();
        //Count number of rows in each file and verify that they are equal in both files
        System.out.println("Total rows in previously generated file is " + list1.size() + " and total number of rows in the downloaded file is " + list1.size());
        Assert.assertEquals(list1.size(), list2.size(), "Sheets have different count of rows");
        //Create Iterator reference
        Iterator<String[]> iterator1 = list1.iterator();
        Iterator<String[]> iterator2 = list2.iterator();
        //Get total number of columns for each row and verify that they are equal for the two files
        while (iterator1.hasNext()) {
            String[] str1 = iterator1.next();
            String[] str2 = iterator2.next();
            int cellCounts1 = str1.length;
            int cellCounts2 = str2.length;
            Assert.assertEquals(cellCounts1, cellCounts2, "Sheets doesn't have the same count of columns");
        }
        System.out.println("files have same number of columns in each row");

    }

    // this method reads CSV file and saves its data in a map
    public List<Map<String, String>> read(String filename) throws Exception {
        CSVReader csvReader = new CSVReader(new FileReader(filename));
        String[] headerArray = csvReader.readNext();
        String[] lineInArray;
        List<Map<String, String>> listOfMaps = new ArrayList<Map<String, String>>();
        //  get the file rows one by one
        while ((lineInArray = csvReader.readNext()) != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            // Read each cell in row(i)
            for (int i = 0; i < lineInArray.length; i++) {
                map.put(headerArray[i], lineInArray[i]);
            }
            // save the data row(i) in the map
            listOfMaps.add(map);
        }
        return listOfMaps;
    }

      /*
        This method verifies that both files have same data
       */
    public void compareDataOfBothCSVFiles(String oldFilePath, String newFilePath) throws Exception {
        {
            List<Map<String, String>> previouslyGeneratedReport = read(oldFilePath);
            List<Map<String, String>> newlyGeneratedReport = read(newFilePath);
            Integer countDifferentRecords = 0;

            for (int i = 0; i < previouslyGeneratedReport.size(); i++) {
                /*
                 Update date format for date columns in both files before comparing the files,
                   as the comparison fails if dates in both files don't have the same format
                */

                SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
                String date_s = previouslyGeneratedReport.get(i).get("Status Change Date");
                String date_s1 = newlyGeneratedReport.get(i).get("Status Change Date");
                String date_s2 = previouslyGeneratedReport.get(i).get("Date Announced");
                String date_s3 = newlyGeneratedReport.get(i).get("Date Announced");
                if (isValidDate(date_s) && isValidDate(date_s1)) {
                    Date date = dt.parse(date_s);
                    previouslyGeneratedReport.get(i).put("Status Change Date", date.toString());
                    SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
                    Date date1 = dt1.parse(date_s1);
                    newlyGeneratedReport.get(i).put("Status Change Date", date1.toString());
                }
                // convert value of "Status Change Date" columns in both file to same date format
                // if condition to check if the string in "Status Change Date" is a valid date before parsing it
                if (isValidDate(date_s2) && isValidDate(date_s3)) {
                    Date date2 = dt.parse(date_s2);
                    previouslyGeneratedReport.get(i).put("Date Announced", date2.toString());
                    SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
                    Date date3 = dt1.parse(date_s3);
                    newlyGeneratedReport.get(i).put("Date Announced", date3.toString());
                }
                // convert value of "Date Announced" columns in both file to same date format
                // if condition to check if the string in "Date Announced" is a valid date before parsing it
                if (!previouslyGeneratedReport.get(i).equals(newlyGeneratedReport.get(i))) {
                    countDifferentRecords++;

                }
            }
            System.out.println("************************************** Number of different records= " + countDifferentRecords + "**************************************");

           Integer expected_Result = 0;
           Integer actual_Result = countDifferentRecords;

            if (expected_Result == actual_Result){
                Assert.assertTrue(actual_Result.equals(expected_Result));
                System.out.println("******************Downloaded report match the older version*************************");}
                else {
                Assert.assertFalse(actual_Result.equals(expected_Result));
                System.out.println("************************Downloaded report doesn't match the older version***********************");
            }

        }
    }


// This method checks if a string is a valid date
 // used in compareDataOfBothCSVFiles2 to prevent exceptions during the execution
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }



}
