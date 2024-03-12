/**
 * @Author: yaoheng5
 * @CreateTime: 2024-03-12  17:42:02
 * @Description:
 * @Version: 1.0
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConversion {
    public static void main(String[] args) {
        String date1 = "\"14/5/2023\"";
        String date2 = "\"15/5/2023 00:00:00\"";

        String formattedDate1 = formatDate(date1);
        String formattedDate2 = formatDateTime(date2);

        System.out.println("Formatted Date 1: " + formattedDate1);
        System.out.println("Formatted Date 2: " + formattedDate2);
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
