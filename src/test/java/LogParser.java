/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-27  19:13:36
 * @Description:
 * @Version: 1.0
 */
import java.io.*;

public class LogParser {
    public static void main(String[] args) {
        String logFilePath = "/Users/yaoheng5/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/5bc6c5f2b9acec507b65a8aa1339cc3d/Message/MessageTemp/ef8d45dc40515259e0bd827f47711c83/File/collector022201(1).log";
        String searchString = "Receive voice MRCP, the msg is : ";
        String callId = "9A93EA05-BCAD-4DF5-ACA6-07ADBEFF6C7A";
        String outputFilePath = "/Users/yaoheng5/Documents/output.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(searchString) && line.contains(callId)) {
                    String json = extractJson(line, searchString);
                    writer.write(json);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractJson(String line, String searchString) {
        int startIndex = line.indexOf(searchString) + searchString.length();
//        int endIndex = line.indexOf("}", startIndex) + 1;
        int endIndex = line.length()-1;
        return line.substring(startIndex, endIndex);
    }
}
