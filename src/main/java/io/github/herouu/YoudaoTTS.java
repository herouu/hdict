package io.github.herouu;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import javazoom.jl.player.Player;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.UUID;

import static cn.hutool.crypto.digest.DigestUtil.sha256Hex;

public class YoudaoTTS {

    static final HTTP http = HTTP.builder()
            .baseUrl("https://openapi.youdao.com")    // 设置 BaseUrl
            .build();

    @SneakyThrows
    public static void main(String[] args) {
        String input = "vertiooo";
        String appKey = "46c46d5afd6fcea0";
        String appSecret = "tLmgIm4dHlgaHfzcAVAdXdQFZU10nQCw";
        String salt = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis() / 1000;
        String originalString = appKey + input + salt + currentTime + appSecret;
        String sign = sha256Hex(originalString);
        HttpResult post = http.sync("/ttsapi").addBodyPara("q", input)
                .addBodyPara("langType", "en-USA")
                .addBodyPara("voice", "1")
                .addBodyPara("appKey", appKey)
                .addBodyPara("salt", salt)
                .addBodyPara("sign", sign)
                .addBodyPara("signType", "v3")
                .addBodyPara("curtime", currentTime)
                .addBodyPara("docType", "json")
                .post();
        String header = post.getHeader("Content-Type");
        if ("audio/mp3".equals(header)) {
            //如果响应是wav
            InputStream stream = post.getBody().toByteStream();
            Player player = new Player(stream);
            player.play();
        }else{
            /** 响应不是音频流，直接显示结果 */
            String str = post.getBody().toString();
            System.out.println(str);
        }
    }
}
