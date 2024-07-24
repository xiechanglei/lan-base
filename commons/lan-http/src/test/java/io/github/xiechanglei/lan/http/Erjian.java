package io.github.xiechanglei.lan.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.xiechanglei.lan.json.JsonHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Erjian {
    public static void main(String[] args) throws IOException, InterruptedException {
        String baomdetailGuid = "0e757464-65a0-4ac2-b3e1-f61194fa7e2e";

        String courseGuid = "c98dedff-2d01-4036-b2cd-ba8f4a99440c";
        String guid = "CE396436-1861-4652-97EC-B038243FCF7F";
        int totalTime = 1589000;
        int startTime = 3049322;
        updateRecord(courseGuid, guid, baomdetailGuid, totalTime, startTime);
    }

    public static void updateRecord(String courseGuid, String guid, String baomdetailGuid, int totalTime, int startTime) throws IOException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("courseGuid", courseGuid);
        map.put("guid", guid);
        map.put("baomdetailGuid", baomdetailGuid);
        map.put("time", totalTime);
        HttpRequest request = HttpHelper.buildBrowserClient();
        while (startTime < totalTime) {
            startTime += 1000 * 10;
            //产生一个1000以内的随机数
            double random = Math.random();
            random = (int) (random * 1000) / 1000.0;
            int seed = (int) (Math.random() * 3);
            map.put("currentTime", startTime + seed + random);
            request.payload(payload -> {
                payload.header("Accept", "application/json, text/plain, */*");
                payload.header("Accept-Encoding", "gzip, deflate");
                payload.header("Accept-Language", "zh-CN,zh;q=0.9");
                payload.header("Connection", "keep-alive");
                payload.header("Content-Type", "application/x-www-form-urlencoded");
                payload.header("Referer", "http://117.68.7.59:8001/kspxqy/szjs/ahkspx/portal/index");
                payload.cookie("sid=8ADD9E5CBEA448B9B0B077D092CDEDA4; JSESSIONID=392CAA3CC74F927BB31FC51710047394; epoint_local=zh_CN");

                try {
                    payload.param("params", JsonHelper.toJson(map));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            HttpResponse post = request.post("http://117.68.7.59:8001/kspxqy/rest/onlineportalcontroller/updatePlayRecord");
            System.out.println(post.body());
            Thread.sleep(1000 * 10);
        }
    }
}
