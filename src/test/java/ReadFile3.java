import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ReadFile3 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://idenew.jbdp.jd.com/idenew/sqlexec/runData.ajax");

        // 设置表单数据
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("runId", "130294085"));
        form.add(new BasicNameValuePair("preKey", "cccc_"));
        form.add(new BasicNameValuePair("cPage", "1"));
        form.add(new BasicNameValuePair("pSize", "999"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, "UTF-8");
        httpPost.setEntity(entity);
        Map<String,String> map = new LinkedHashMap<>();
        map.put("cccc_0","user_ping");
        map.put("cccc_1","prin_exceed_amt_avg");
        map.put("cccc_2","fee_exceed_amt_avg");
        map.put("cccc_3","day_exceed_amt_avg");
        map.put("cccc_4","over_exceed_amt_avg");
        map.put("cccc_5","pay_prefr_amt_avg");
        map.put("cccc_6","paid_fine_amt_1m");
        map.put("cccc_7","paid_fine_amt_3m");
        map.put("cccc_8","paid_fine_amt_6m");
        map.put("cccc_9","paid_fine_amt_12m");

        // 设置Cookie
        httpPost.setHeader("Cookie", "ide_cookie_tab=%5B%7B%22i%22%3A14140608%2C%22l%22%3A%22%u6807%u7B7E%u98751%22%2C%22it%22%3A1%7D%5D; ide_cookie_tab_active=ide_open_14140608; shshshfpa=525e7326-1767-1cdc-ca1d-b1cb82937a21-1683558746; shshshfpx=525e7326-1767-1cdc-ca1d-b1cb82937a21-1683558746; 3AB9D23F7A4B3CSS=jdd03Q4LWZUQUQQWCGKD5YZLQQP5M6R43L6CMS2CRKGDM37YCVMMBGA37SIIDUV4YY3HQADK5DYEFNJNYFD3HWURS6COPKAAAAAMNECFM5HIAAAAACLRGTDA563W6IYX; shshshfpb=BApXe30eCI-hAqN025SDyaHjU2Lw5Vcv7B8EUgGlq9xJ1MoRDXo-2; jd.erp.lang=zh_CN; jdd69fo72b8lfeoe=W4HQQZ5R6TPLB73OH7BTXMLEQAY7EMV4DSNSCBB5XT33WQ74ZVANFJERE76CNMEBQWGIA3R2274WFTSMP5UK3IGAJU; __jdv=162094517|direct|-|none|-|1706066390879; fp=78d89a473253b43002e9a62707d8af64; __jdu=16842420171221107052668; verify_uuid=f812576e-c047-43c2-9555-6b1560f47478; wlfstk_smdl=3q47izvu3lkr864ofm6c5y04m37h2z15; 3AB9D23F7A4B3C9B=JM6GRNIDZDSVGQ74II7CMBDROXEPOA46ISHVKPYOTBD6YCDBKSIRPZWKVC2Z4OOPAHDPERDOWDQT7CPS45S3GIVMDY; focus-token=0aeb111e4208287a93bb09f413ed22f9; focus-team-id=00046419; focus-client=WEB; sso.jd.com=BJ.BF0053A29711448033E66B2B4FEAA539.7720240204200249; ssa.global.ticket=1598DD49B53672FA2D0DFFC0D2544945; __jdc=240971990; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImxYZjBVUWdUcUhWTDNqdmRhdVdqIiwiZXhwIjoxNzA3NjY1MDM0LCJpYXQiOjE3MDcwNjAyMzR9.25EbnlMh94P2S6xkAIvExE9rh5gq12hv-P-YUbFkWKY; __jda=240971990.16842420171221107052668.1684242017.1707041840.1707061713.273; __jdb=240971990.1.16842420171221107052668|273.1707061713");
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity responseEntity = response.getEntity();
                // 处理响应内容
                System.out.println(response.getStatusLine());
                String result = EntityUtils.toString(responseEntity);
                JSONArray jsonArray = JSON.parseObject(result).getJSONArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String s = jsonObject.getString("cccc_3");
                    if (

//                            !jsonObject.getString("cccc_1").contains("N")&& !jsonObject.getString("cccc_1").equals("0.0")  && !jsonObject.getString("cccc_1").equals("0") &&
//                            !jsonObject.getString("cccc_2").contains("N")&& !jsonObject.getString("cccc_2").equals("0.0")  && !jsonObject.getString("cccc_2").equals("0") &&
//                            jsonObject.getString("cccc_0").equals("jd_7de8a8c278700")&&
                            !jsonObject.getString("cccc_3").contains("N")&& !jsonObject.getString("cccc_3").equals("0.0")  && !jsonObject.getString("cccc_3").equals("0") &&
                            !jsonObject.getString("cccc_4").contains("N")&& !jsonObject.getString("cccc_4").equals("0.0")  && !jsonObject.getString("cccc_4").equals("0") &&
                            !jsonObject.getString("cccc_5").contains("N")&& !jsonObject.getString("cccc_5").equals("0.0")  && !jsonObject.getString("cccc_5").equals("0") &&
                            !jsonObject.getString("cccc_6").contains("N")&& !jsonObject.getString("cccc_6").equals("0.0")  && !jsonObject.getString("cccc_6").equals("0") &&
                            !jsonObject.getString("cccc_7").contains("N")&& !jsonObject.getString("cccc_7").equals("0.0")  && !jsonObject.getString("cccc_7").equals("0") &&
                            !jsonObject.getString("cccc_8").contains("N")&& !jsonObject.getString("cccc_8").equals("0.0")  && !jsonObject.getString("cccc_8").equals("0") &&
                            !jsonObject.getString("cccc_9").contains("N")&& !jsonObject.getString("cccc_9").equals("0.0")  && !jsonObject.getString("cccc_9").equals("0") ){

                        for(Map.Entry<String,String> entry :map.entrySet()){
                            System.out.println(entry.getValue()+":"+jsonObject.getString(entry.getKey()));
                        }
                        System.out.println(jsonObject.toJSONString());
                    }
                }
                System.out.println("大小："+jsonArray.size());
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
