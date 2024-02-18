import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.platform.commons.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileTest {
    public static void main(String[] args) {
        String s = "{\n" +
                "  \"cccc_0\": \"etl_dt\",\n" +
                "  \"cccc_1\": \"id\",\n" +
                "  \"cccc_2\": \"parnt_repay_id\",\n" +
                "  \"cccc_3\": \"repay_id\",\n" +
                "  \"cccc_4\": \"pay_id\",\n" +
                "  \"cccc_5\": \"user_pin\",\n" +
                "  \"cccc_6\": \"loan_id\",\n" +
                "  \"cccc_7\": \"ordr_id\",\n" +
                "  \"cccc_8\": \"plan_id\",\n" +
                "  \"cccc_9\": \"bill_dtl_id\",\n" +
                "  \"cccc_10\": \"curr_plan_num\",\n" +
                "  \"cccc_11\": \"stat_code\",\n" +
                "  \"cccc_12\": \"paid_prin\",\n" +
                "  \"cccc_13\": \"paid_plan_fee_amt\",\n" +
                "  \"cccc_14\": \"paid_day_fee_amt\",\n" +
                "  \"cccc_15\": \"paid_over_amt\",\n" +
                "  \"cccc_16\": \"prin_exceed_amt\",\n" +
                "  \"cccc_17\": \"fee_exceed_amt\",\n" +
                "  \"cccc_18\": \"day_exceed_amt\",\n" +
                "  \"cccc_19\": \"over_exceed_amt\",\n" +
                "  \"cccc_20\": \"pay_time\",\n" +
                "  \"cccc_21\": \"limit_pay_time\",\n" +
                "  \"cccc_22\": \"consm_time\",\n" +
                "  \"cccc_23\": \"card_id\",\n" +
                "  \"cccc_24\": \"bank_code\",\n" +
                "  \"cccc_25\": \"card_type_code\",\n" +
                "  \"cccc_26\": \"pay_emrt_code\",\n" +
                "  \"cccc_27\": \"inev_type_code\",\n" +
                "  \"cccc_28\": \"src_type_code\",\n" +
                "  \"cccc_29\": \"sys_code\",\n" +
                "  \"cccc_30\": \"biz_code\",\n" +
                "  \"cccc_31\": \"bill_ind\",\n" +
                "  \"cccc_32\": \"create_time\",\n" +
                "  \"cccc_33\": \"update_time\",\n" +
                "  \"cccc_34\": \"plan_type_code\",\n" +
                "  \"cccc_35\": \"paid_fine_amt\",\n" +
                "  \"cccc_36\": \"paid_next_fine_amt\",\n" +
                "  \"cccc_37\": \"paid_cash_fee_amt\",\n" +
                "  \"cccc_38\": \"chnl_code\",\n" +
                "  \"cccc_39\": \"paid_prin_prefr_amt\",\n" +
                "  \"cccc_40\": \"pay_prefr_amt\",\n" +
                "  \"cccc_41\": \"dt\"\n" +
                "}";

        JSONObject head = JSON.parseObject(s);

        String url = "jdbc:mysql://127.0.0.1:3306/idm";
        String user = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "idm_f02_cf_xbt_repay_dtl_s_d", null);
            Map<String,String> columnTypeMap = new HashMap<>();
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                System.out.println("Column Name: " + columnName + ", Column Type: " + columnType);
                columnTypeMap.put(columnName,columnType);
            }
            //读取内容
            String filePath = "/Users/yaoheng5/Desktop/日记/2024/ll17739782700.txt";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = JSON.parseArray(content);
            // 连接数据库

            // 插入数据
            List<String> sortColumn = head.keySet().stream().sorted((s1, s2) -> {
                String k1 = s1.replace("cccc_", "");
                String k2 = s2.replace("cccc_", "");
                if (Integer.parseInt(k1) > Integer.parseInt(k2)) {
                    return 1;
                } else {
                    return -1;
                }
            }).collect(Collectors.toList());

            //获取表列
            sortColumn.remove("cccc_41");
            String sqlPre = "INSERT INTO idm_f02_cf_xbt_repay_dtl_s_d  ";
            List<String> columnList = new ArrayList<>();
            for (String key : sortColumn) {
                columnList.add(head.getString(key));
            }
            String column = "(" + columnList.stream().collect(Collectors.joining(",")) + ")";
            sqlPre = sqlPre + column + " VALUES ";
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonValue = jsonArray.getJSONObject(i);
                List<String> valueList = new ArrayList<>();
                for (String key : sortColumn) {
                    String type2 = columnTypeMap.get(head.getString(key));
                    String value2 = jsonValue.getString(key);
                    if(value2.equals("NULL")){
                        value2 = "";
                    }
                    if (type2.equals("VARCHAR")) {
                        valueList.add("'" + value2 + "'");
                    } else  if(type2.equals("DOUBLE")){
                        if(StringUtils.isBlank(value2)){
                            valueList.add("0.0");
                        }else {
                            valueList.add(value2);
                        }
                    }else {
                        if(StringUtils.isBlank(value2)){
                            valueList.add("0");
                        }else {
                            valueList.add(value2);
                        }
                    }
                }
                String value = "(" + valueList.stream().collect(Collectors.joining(",")) + ")";
//                value = value.replace("NULL", "''");
                String sql = sqlPre + value;
                System.out.println(sql);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
