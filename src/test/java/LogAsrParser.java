/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-27  19:13:36
 * @Description:日志过滤器
 * @Version: 1.0
 */

import java.io.*;

public class LogAsrParser {
    public static void main(String[] args) {
        //源文件
        String logFilePath = "/Users/yaoheng5/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/5bc6c5f2b9acec507b65a8aa1339cc3d/Message/MessageTemp/ef8d45dc40515259e0bd827f47711c83/File/amrealqa022803.log";
        //目标文件
        String outputFilePath = "/Users/yaoheng5/Documents/output.txt";
        //日志标识
//        String searchString = "Received request";
        String searchString = "Send response";

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            String previousCallId = "";
            while ((line = br.readLine()) != null) {
                if (line.contains(searchString)) {
//                    String currentCallId = extractCallId(line);
                    String currentCallId = extractCallIdResponse(line);
                    if (!currentCallId.equals(previousCallId)) {
                        writer.newLine(); // Add a new line for different callid
                        previousCallId = currentCallId;
                    }
                    String json = extractJson(line, searchString);
                    writer.write(json);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String extractCallId(String line) {
        // 寻找包含"callId":"的子字符串的起始位置
        int startIndex = line.indexOf("reqid=");
        // 在起始位置之后寻找逗号的位置，作为子字符串的结束位置
        int endIndex = line.indexOf(" : ", startIndex);
        // 返回从起始位置到结束位置之间的子字符串
        return line.substring(startIndex, endIndex);
    }

    private static String extractCallIdResponse(String line) {
        // 寻找包含"callId":"的子字符串的起始位置
        int startIndex = line.indexOf("reqid: ");
        // 在起始位置之后寻找逗号的位置，作为子字符串的结束位置
        int endIndex = line.indexOf(" appid", startIndex);
        // 返回从起始位置到结束位置之间的子字符串
        return line.substring(startIndex, endIndex);
    }

    private static String extractJson(String line, String searchString) {
        int startIndex = line.indexOf(searchString) + searchString.length();
        int endIndex = line.length();
        return line.substring(startIndex, endIndex);
    }
}
