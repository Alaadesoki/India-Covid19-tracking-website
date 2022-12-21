package Configuration;

public class configurationFiles {
    //URL
    public static String URL ="https://data.covid19india.org/";
    // Paths
    public static String downloadedFilePath= System.getProperty("user.dir") +"\\src\\main\\resources\\Downloads\\raw_data1.csv";
    public static String negativeDataFilePath= System.getProperty("user.dir") +"\\src\\main\\resources\\Previously_Generated_Files\\raw_data1_Negative.csv";
    public static String positiveDataFilePath= System.getProperty("user.dir") +"\\src\\main\\resources\\Previously_Generated_Files\\raw_data1_Positive.csv";
}
