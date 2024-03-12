/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-27  19:13:36
 * @Description:日志过滤器
 * @Version: 1.0
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class LogCollectorParser2 {
    public static void main(String[] args) throws Exception {
        //源文件
        String logFilePath = "/Users/yaoheng5/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/5bc6c5f2b9acec507b65a8aa1339cc3d/Message/MessageTemp/ef8d45dc40515259e0bd827f47711c83/File/collector022801.log";
        //目标文件
        String outputFilePath = "/Users/yaoheng5/Documents/output.txt";
        //日志标识
        String searchString = "Receive voice MRCP, the msg is : ";
        String callId = "568C9115-161F-4DAE-890B-55FFDA6EA17B-17B";

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            String previousCallId = "";
            while ((line = br.readLine()) != null) {
                if (line.contains(searchString)) {
                    if (line.contains(searchString) && line.contains(callId)) {
                        String currentCallId = extractCallId(line);
                        if (!currentCallId.equals(previousCallId)) {
                            writer.newLine(); // Add a new line for different callid
                            previousCallId = currentCallId;
                        }
                        String json = extractJson(line, searchString);

                        writer.write(json);
                        writer.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractCallId(String line) {
        // 寻找包含"callId":"的子字符串的起始位置
//        int startIndex = line.indexOf("\"callId\":\"");
        int startIndex = line.indexOf("\"callId\":\"");
        // 在起始位置之后寻找逗号的位置，作为子字符串的结束位置
        int endIndex = line.indexOf(",", startIndex);
        // 返回从起始位置到结束位置之间的子字符串
        return line.substring(startIndex, endIndex);
    }

    private static String extractJson(String line, String searchString) {
        int startIndex = line.indexOf(searchString) + searchString.length();
        int endIndex = line.length();
        return line.substring(startIndex, endIndex);
    }
}

