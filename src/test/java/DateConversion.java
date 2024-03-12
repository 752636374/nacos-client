/**
 * @Author: yaoheng5
 * @CreateTime: 2024-03-12  17:30:07
 * @Description:
 * @Version: 1.0
 */

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
* @Description: 读取文件，并写入新文件。
 * 1、"14/5/2023" 转换成 "2023/05/14" ,
*2、"15/5/2023 00:00:00" 转换成 "2023/05/15 00:00:00"
* @Author: yaoheng5
* @date 2024/3/12 18:20
* @return
*/
public class DateConversion {
    public static void main(String[] args) {
        String fileDir = "/Users/yaoheng5/Documents/jon库表";
        File folder = new File(fileDir); // 设置文件夹路径

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv")); // 获取文件夹下所有的CSV文件

        for (File file : files) {
            if (file.getName().contains("new")) {
                continue;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file));

                 BufferedWriter bw = new BufferedWriter(new FileWriter(fileDir + "/" + "new_"+file.getName().replace(".csv","") ))) {

                String line;
                while ((line = br.readLine()) != null) {
                    // 对每一行进行处理，替换日期格式
                    String[] parts = line.split(","); // 假设CSV文件中数据以逗号分隔
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = modifyDate(parts[i]);
                    }
                    String newLine = String.join(",", parts);
                    bw.write(newLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 修改日期格式
    private static String modifyDate(String inputDate) {
        String[] dateParts = inputDate.split("/");
        if (dateParts.length == 3) {
            if(dateParts[0].replace("\"","").matches("-?\\d+(\\.\\d+)?")){
                if (inputDate.length() <= 12) {
                    return "\"" + formatDate(inputDate.replace("\"", "") )+ "\"";
                } else {
                    return "\"" + formatDateTime(inputDate.replace("\"", ""))+ "\"";
                }
            }
        }
        return inputDate;
    }

    // 格式化日期
    private static String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "日期格式错误";
        }
    }

    // 格式化日期和时间
    private static String formatDateTime(String inputDateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date dateTime = inputFormat.parse(inputDateTime);
            return outputFormat.format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "日期时间格式错误";
        }
    }
}
