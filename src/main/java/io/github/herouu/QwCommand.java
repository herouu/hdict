package io.github.herouu;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import io.github.herouu.entity.Oxford;
import javazoom.jl.decoder.JavaLayerException;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@CommandLine.Command(name = "qw", description = "词典命令行工具")
public class QwCommand implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0", paramLabel = "word", description = "请输入查询的单词")
    private String word;

    @CommandLine.Option(names = "-f", description = "只显示首个单词详情")
    Boolean first;

    @CommandLine.Option(names = "-r", description = "获取单词发音")
    Boolean read;

    @CommandLine.Option(names = "-i", description = "单词位置")
    Integer index;

    @CommandLine.Option(names = "-d", description = "词典")
    Integer dict;


    @CommandLine.Parameters(hidden = true)
    List<String> allParameters;

    private static ConcurrentHashMap<Integer, Oxford> concurrentHashMap;

    public static ConcurrentHashMap<Integer, Oxford> getConcurrentHashMap() {
        return concurrentHashMap;
    }

    @Override
    public void run() {
        if (Objects.isNull(index)) {
            index = 1;
        }
        if (Objects.isNull(dict)) {
            dict = 1;
        }
        LinkedHashMap<Integer, Oxford> query = QueryWord.query(word, dict);
        Oxford oxford = query.get(index);
        if (Objects.isNull(oxford)) {
            return;
        }
        if (BooleanUtil.isTrue(first)) {
            printWord(oxford);
        } else {
            query.forEach((key, value) -> {
                        if (key.equals(index)) {
                            printWord(oxford);
                        } else {
                            System.out.println(StrUtil.format("{} {} ", key, value.getWordName()));
                        }
                    }
            );
            concurrentHashMap = new ConcurrentHashMap<>(query);
        }

        if (BooleanUtil.isTrue(read)) {
            try {
                YoudaoTTS.audio(oxford.getWordName());
            } catch (JavaLayerException e) {
                System.err.println("获取音频失败");
            }
        }
    }

    private void printWord(Oxford oxford) {
        BufferedReader reader = new BufferedReader(new StringReader(oxford.getWordValue()));
        StringBuilder sb = new StringBuilder();
        try {
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n\t\t");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(StrUtil.format("{} {} \n\t\t {}", index, oxford.getWordName(), sb.toString()));
    }
}