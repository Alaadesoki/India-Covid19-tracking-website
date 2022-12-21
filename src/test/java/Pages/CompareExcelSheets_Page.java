package Pages;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Assert;

    public class CompareExcelSheets_Page {

        /*
        This method compares if both Excel files have same number of sheets
        */
        public void compareNumberOfSheetsInBothFiles(Workbook workbook1, Workbook workbook2) {
            System.out.println("Checking if both excel files have same number of sheets");
            // Get number of sheets in the first file
            int sheetsInWorkbook1 = workbook1.getNumberOfSheets();
            // Get number of sheets in the second file
            int sheetsInWorkbook2 = workbook2.getNumberOfSheets();
            // Compare if both Excel files have same number of sheets
            Assert.assertEquals(sheetsInWorkbook1, sheetsInWorkbook2,
                    "Excel work books have different number of sheets. \n "
                            + "Sheets in work book 1 : " + sheetsInWorkbook1 + "\n "
                            + "Number of sheets in work book 2 : " + sheetsInWorkbook2);
            //  logging the results of compare number of sheets
            System.out.println("Sheets in first work book : " + sheetsInWorkbook1);
            System.out.println("Sheets in second work book : " + sheetsInWorkbook2);
            System.out.println("Both excel files have same number of sheets");
        }

        // This method compares if both Excel files have same number of rows and columns
        public void verifySheetsInExcelFilesHaveSameNumberOfRowsAndColumns(Workbook workbook1, Workbook workbook2) {
            System.out.println("Checking if both excel files have same number of rows and columns in all sheets");
            int sheetCounts = workbook1.getNumberOfSheets();

            for (int i = 0; i < sheetCounts; i++) {
                Sheet s1 = workbook1.getSheetAt(i);
                Sheet s2 = workbook2.getSheetAt(i);
                int rowsInSheet1 = s1.getPhysicalNumberOfRows();
                int rowsInSheet2 = s2.getPhysicalNumberOfRows();
                Assert.assertEquals(rowsInSheet1, rowsInSheet2, "Sheets have different count of rows");

                Iterator<Row> rowInSheet1 = s1.rowIterator();
                Iterator<Row> rowInSheet2 = s2.rowIterator();
                while (rowInSheet1.hasNext()) {
                    int cellCounts1 = rowInSheet1.next().getPhysicalNumberOfCells();
                    int cellCounts2 = rowInSheet2.next().getPhysicalNumberOfCells();
                    Assert.assertEquals(cellCounts1, cellCounts2, "Sheets have different count of columns");
                }
            }
            System.out.println("Sheets of both files have same number of rows and columns");

        }

        public void compareDataOfBothExcelFiles(Workbook workbook1, Workbook workbook2) {
            System.out.println("Checking if both excel files books have same data");
            int sheetCounts = workbook1.getNumberOfSheets();
            // Iterating through sheet by sheet
            for (int i = 0; i < sheetCounts; i++) {
                // Get sheet at same index of both work books
                Sheet s1 = workbook1.getSheetAt(i);
                Sheet s2 = workbook2.getSheetAt(i);
                // Iterating through each row
                int rowCounts = s1.getPhysicalNumberOfRows();
                for (int j = 0; j < rowCounts; j++) {
                    // Iterating through each cell
                    int cellCounts = s1.getRow(j).getPhysicalNumberOfCells();
                    for (int k = 0; k < cellCounts; k++) {
                        // Getting individual cell
                        Cell c1 = s1.getRow(j).getCell(k);
                        Cell c2 = s2.getRow(j).getCell(k);
                        // Since cell have types and need to use different methods
                        if (c1.getCellType().equals(c2.getCellType())) {
                            if (c1.getCellType() == CellType.STRING) {
                                String v1 = c1.getStringCellValue();
                                String v2 = c2.getStringCellValue();
                                Assert.assertEquals(v1, v2, "Cell values are different.");
                                System.out.println("Its matched : " + v1 + " === " + v2);
                            }
                           else if (c1.getCellType() == CellType.NUMERIC) {
                                // If cell type is numeric, we need to check if data is of Date type
                                if (DateUtil.isCellDateFormatted(c1) | DateUtil.isCellDateFormatted(c2)) {
                                    // Need to use DataFormatter to get data in given style otherwise it will come as time stamp
                                    DataFormatter df = new DataFormatter();
                                    String v1 = df.formatCellValue(c1);
                                    String v2 = df.formatCellValue(c2);
                                    Assert.assertEquals(v1, v2, "Cell values are different.");
                                    System.out.println("Its matched : " + v1 + " === " + v2);
                                } else {
                                    double v1 = c1.getNumericCellValue();
                                    double v2 = c2.getNumericCellValue();
                                    Assert.assertEquals(v1, v2, "Cell values are different.");
                                    System.out.println("Its matched : " + v1 + " === " + v2);
                                }
                            }
                          else  if (c1.getCellType() == CellType.BOOLEAN) {
                                boolean v1 = c1.getBooleanCellValue();
                                boolean v2 = c2.getBooleanCellValue();
                                Assert.assertEquals(v1, v2, "Cell values are different.");
                                System.out.println("Its matched : " + v1 + " === " + v2);
                            }
                        }
                        else {
                            // If cell types are not same, exit comparison
                            Assert.fail("Non matching cell type.");
                        }
                    }
                }
            }
            System.out.println("Both work books have same data.");
        }
    }
