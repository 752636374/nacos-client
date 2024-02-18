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
import java.util.ArrayList;
import java.util.List;

public class ReadFile2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://idenew.jbdp.jd.com/idenew/sqlexec/runData.ajax");

        // 设置表单数据
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("runId", "130170184"));
        form.add(new BasicNameValuePair("preKey", "cccc_"));
        form.add(new BasicNameValuePair("cPage", "1"));
        form.add(new BasicNameValuePair("pSize", "1000"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, "UTF-8");
        httpPost.setEntity(entity);

        // 设置Cookie
        httpPost.setHeader("Cookie", "ide_cookie_tab=%5B%7B%22i%22%3A14140608%2C%22l%22%3A%22%u6807%u7B7E%u98751%22%2C%22it%22%3A1%7D%5D; ide_cookie_tab_active=ide_open_14140608; shshshfpa=525e7326-1767-1cdc-ca1d-b1cb82937a21-1683558746; shshshfpx=525e7326-1767-1cdc-ca1d-b1cb82937a21-1683558746; 3AB9D23F7A4B3CSS=jdd03Q4LWZUQUQQWCGKD5YZLQQP5M6R43L6CMS2CRKGDM37YCVMMBGA37SIIDUV4YY3HQADK5DYEFNJNYFD3HWURS6COPKAAAAAMNECFM5HIAAAAACLRGTDA563W6IYX; shshshfpb=BApXe30eCI-hAqN025SDyaHjU2Lw5Vcv7B8EUgGlq9xJ1MoRDXo-2; jd.erp.lang=zh_CN; jdd69fo72b8lfeoe=W4HQQZ5R6TPLB73OH7BTXMLEQAY7EMV4DSNSCBB5XT33WQ74ZVANFJERE76CNMEBQWGIA3R2274WFTSMP5UK3IGAJU; __jdv=162094517|direct|-|none|-|1706066390879; fp=78d89a473253b43002e9a62707d8af64; focus-token=1c5c2ec484929a56fcfc495f4c8e64ea; focus-team-id=00046419; focus-client=WEB; __jdu=16842420171221107052668; sso.jd.com=BJ.DE97428FEC632C7E9A34FFA828898F57.7720240201174934; ssa.global.ticket=662fe29b550e7d5afe0726a1d6a52cf39be10f933418b324ad27cec4fc0b72d0; verify_uuid=f812576e-c047-43c2-9555-6b1560f47478; wlfstk_smdl=3q47izvu3lkr864ofm6c5y04m37h2z15; 3AB9D23F7A4B3C9B=JM6GRNIDZDSVGQ74II7CMBDROXEPOA46ISHVKPYOTBD6YCDBKSIRPZWKVC2Z4OOPAHDPERDOWDQT7CPS45S3GIVMDY; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImxYZjBVUWdUcUhWTDNqdmRhdVdqIiwiZXhwIjoxNzA3NDYxMjE3LCJpYXQiOjE3MDY4NTY0MTd9.FdLfiiKqESgSEgp0c1eKk-BNfHF1vtPr8ncHnZMb3LQ; __jda=240971990.16842420171221107052668.1684242017.1706689394.1706752683.271; __jdc=240971990; __jdb=240971990.21.16842420171221107052668|271.1706752683");


        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity responseEntity = response.getEntity();
                // 处理响应内容
                System.out.println(response.getStatusLine());
                String result = EntityUtils.toString(responseEntity);
                JSONArray jsonArray = JSON.parseObject(result).getJSONArray("data");
                Double count = 0.0;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String s = jsonObject.getString("cccc_19");
                    if (jsonObject != null && s != null && s != "NULL" && !s.contains("N") ){
                        count += Double.parseDouble(s);
                    }
                }
                System.out.println(jsonArray.size());
                System.out.println(count);
                System.out.println(count / jsonArray.size());
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
