/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-27  11:53:31
 * @Description:
 * @Version: 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/***
 * @Description:
 * domain ：real_qa
 * appid：随便填写
 * 地址：20.1.57.156:18091/asr
 * @Author: yaoheng5
 * @date 2024/2/27 15:39
 * @return
 */
public class ASRTest {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType mediaType = MediaType.parse("application/octet-stream");

    public static void main(String[] args) {
        String url = "https://asrapi-base.jd.com/asr";
        String application_id = "9d54337c-6178-4e01-a2ce-692337efb48a-guowang";
        String domain = "general_8k";
        String file_path = "/Users/yaoheng5/Desktop/项目文档/产品文档/质检/口头语不扣-20231226144609/audio/output.wav";

        int packageLen = 4000;

        if (args.length > 0) {
            file_path = args[0];
        }
        if (args.length > 1) {
            application_id = args[1];
        }
        if (args.length > 2) {
            domain = args[2];
        }
        if (args.length > 3) {
            url = args[3];
        }
        if (args.length > 4) {
            packageLen = Integer.parseInt(args[4]);
        }
        testSingle(url, file_path, packageLen, application_id, domain);
    }

    private static void testSingle(String url, String audioFile, int packageLen, String Application_id, String Domain) {
        File file = new File(audioFile);
        String psroperty = "{\"autoend\":false, \"encode\":{\"channel\":1,\"format\":\"wav\", \"sample_rate\":8000,\"post_process\":-1,\"partial_result\":-1,\"punc_partial_process\":-1, \"punc_end_process\":-1}, \"platform\":\"Linux&Centos&7.3\", \"version\":\"0.0.0.1\", \"longspeech\":true}";
        JSONObject propertyJson = JSON.parseObject(psroperty);

        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Domain", Domain)
                .addHeader("Application-Id", Application_id)
                .addHeader("Request-Id", UUID.randomUUID().toString())
                .addHeader("Sequence-Id", "-1")
                .addHeader("Asr-Protocol", "3")
                .addHeader("Net-State", "2")
                .addHeader("Applicator", "1")
                .addHeader("Property", propertyJson.toJSONString());

        int seq = 1;
        try {
            byte[] fileData = Files.readAllBytes(file.toPath());
            int offset = 0;
            while (offset < fileData.length) {
                int length = Math.min(packageLen, fileData.length - offset);
                byte[] body = new byte[length];
                System.arraycopy(fileData, offset, body, 0, length);
                if (length < packageLen) {
                    builder.header("Sequence-Id", String.valueOf(-seq));
//                    builder.header("Sequence-Id", String.valueOf(-1));
                } else {
                    builder.header("Sequence-Id", String.valueOf(seq));
                }
                Request request = builder.post(RequestBody.create(mediaType, body)).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                }
                offset += packageLen;
                seq++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
