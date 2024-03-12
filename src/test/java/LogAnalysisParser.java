/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-27  19:13:36
 * @Description:日志过滤器
 * @Version: 1.0
 */

import java.io.*;

public class LogAnalysisParser {
    public static void main(String[] args) {
        //源文件
        String logFilePath = "/Users/yaoheng5/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/5bc6c5f2b9acec507b65a8aa1339cc3d/Message/MessageTemp/908f21be6162f5758d52d9425eb59568/File/analysis022903";
        //目标文件
        String outputFilePath = "/Users/yaoheng5/Documents/output.txt";
        //日志标识
//        String searchString = "Received request";
        String searchString = "收到客服语音消息：message=";

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            String previousCallId = "CF6FFF03-F45D-46E7-9D1F-F458C869B538-179";
            while ((line = br.readLine()) != null) {
                if (line.contains(searchString)) {
//                    String currentCallId = extractCallId(line);
                    String currentCallId = extractCallIdResponse(line);
                    if (!currentCallId.equals(previousCallId)) {
                        writer.newLine(); // Add a new line for different callid
                        previousCallId = currentCallId;
                    }
//                    String json = extractJson(line, searchString);
                    String json = line;
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
        int startIndex = line.indexOf("\"sessionId\":\"");
        // 在起始位置之后寻找逗号的位置，作为子字符串的结束位置
        int endIndex = line.indexOf("\",\"", startIndex);
        // 返回从起始位置到结束位置之间的子字符串
        return line.substring(startIndex, endIndex);
    }

    private static String extractJson(String line, String searchString) {
        int startIndex = line.indexOf(searchString) + searchString.length();
        int endIndex = line.length();
        return line.substring(startIndex, endIndex);
    }
}
