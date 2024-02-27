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

public class ASRTest {
    private static final String url = "https://asrapi-base.jd.com/asr";
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType mediaType = MediaType.parse("application/octet-stream");

    public static void main(String[] args) {
        String uncompressedFile = "/Users/yaoheng5/Desktop/项目文档/产品文档/质检/口头语不扣-20231226144609/audio/output.wav";
        int packageLen = 40000;
        if (args.length > 0) {
            uncompressedFile = args[0];
        }
        if (args.length > 1) {
            packageLen = Integer.parseInt(args[1]);
        }
        testSingle(uncompressedFile, packageLen);
    }

    private static void testSingle(String audioFile, int packageLen) {
        File file = new File(audioFile);
        String psroperty = "{\"autoend\":false, \"encode\":{\"channel\":1,\"format\":\"wav\", \"sample_rate\":8000,\"post_process\":-1,\"partial_result\":-1,\"punc_partial_process\":-1, \"punc_end_process\":-1}, \"platform\":\"Linux&Centos&7.3\", \"version\":\"0.0.0.1\", \"longspeech\":true}";
        JSONObject propertyJson = JSON.parseObject(psroperty);

        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Domain", "general_8k")
                .addHeader("Application-Id", "9d54337c-6178-4e01-a2ce-692337efb48a-guowang")
                .addHeader("Request-Id", UUID.randomUUID().toString())
                .addHeader("Sequence-Id", "-1")
                .addHeader("Asr-Protocol", "3")
                .addHeader("Net-State", "2")
                .addHeader("Applicator", "1")
                .addHeader("Property",propertyJson.toJSONString());

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
                } else {
                    builder.header("Sequence-Id", String.valueOf(seq));
                }
                Request request = builder.post(RequestBody.create(mediaType, body)).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                    // 处理返回结果
                    // ...
                }
                offset += packageLen;
                seq++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
