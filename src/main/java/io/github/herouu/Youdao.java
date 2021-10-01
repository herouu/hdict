package io.github.herouu;

import com.ejlchina.okhttps.HTTP;

import java.util.UUID;

import static cn.hutool.crypto.digest.DigestUtil.sha256Hex;

public class Youdao {

    static final HTTP http = HTTP.builder()
            .baseUrl("https://openapi.youdao.com")    // 设置 BaseUrl
            .build();

    public static void main(String[] args) {
        String input = "vert";
        String appKey = "46c46d5afd6fcea0";
        String appSecret = "tLmgIm4dHlgaHfzcAVAdXdQFZU10nQCw";
        String salt = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis() / 1000;
        String originalString = appKey + input + salt + currentTime + appSecret;
        String sign = sha256Hex(originalString);
        String s = http.sync("/api").addBodyPara("q", input)
                .addBodyPara("from", "en")
                .addBodyPara("to", "zh-CHS")
                .addBodyPara("appKey", appKey)
                .addBodyPara("salt", salt)
                .addBodyPara("sign", sign)
                .addBodyPara("signType", "v3")
                .addBodyPara("curtime", currentTime)
                .addBodyPara("docType", "json")
                .post().getBody().toString();
        System.out.println(s);
    }
}
