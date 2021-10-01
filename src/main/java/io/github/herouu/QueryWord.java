package io.github.herouu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.herouu.entity.Oxford;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.SQLiteStyle;

import javax.sql.DataSource;
import java.util.*;

public class QueryWord {

    private QueryWord() {
    }

    private static final QueryWord QUERY_WORD = new QueryWord();

    public static QueryWord getInstance() {
        return QUERY_WORD;
    }

    private SQLManager getSqlManager() {
        return sqlManager;
    }

    private static SQLManager sqlManager;

    private DataSource datasource() {
        String path = QueryWord.class.getResource("/db.properties").getPath();
        HikariDataSource ds = new HikariDataSource(new HikariConfig(path));
        return ds;
    }

    public static void initSqlManager() {
        if (Objects.isNull(sqlManager)) {
            //得到一个数据源
            DataSource dataSource = getInstance().datasource();
            //得到一个ConnectionSource， 单数据源
            ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
            //SQLManagerBuilder 唯一必须的参数就是ConnectionSource
            SQLManagerBuilder builder = new SQLManagerBuilder(source);
            //命名转化，数据库表和列名下划线风格，转化成Java对应的首字母大写，比如create_time 对应ceateTime
            builder.setNc(new UnderlinedNameConversion());
            //拦截器，非必须，这里设置一个debug拦截器，可以详细查看执行后的sql和sql参数
            // builder.setInters(new Interceptor[]{new DebugInterceptor()});
            //数据库风格，因为用的是H2,所以使用H2Style,
            builder.setDbStyle(new SQLiteStyle());
            sqlManager = builder.build();
        }
    }

    public static void main(String[] args) throws Exception {
        SQLManager sqlManager = getInstance().getSqlManager();
        //初始化数据脚本，执行后，内存数据库将有一个sys_user表和模拟数据
        // DBInitHelper.executeSqlScript(sqlManager,"db/schema.sql");
        // DBInitHelper.executeSqlScript(sqlManager,"db/en_words.sql");
        // 得到数据库的所有表

        Scanner scan = new Scanner(System.in);
        String line = null;
        boolean flag = true;
        List<Oxford> select = null;
        while (flag) {
            line = scan.nextLine();
            if (":e".equals(line)) {
                flag = false;
                continue;
            }
            if (CollectionUtil.isNotEmpty(select) && NumberUtil.isNumber(line)) {
                int i = NumberUtil.parseInt(line);
                if (NumberUtil.parseInt(line) <= 20) {
                    Oxford oxford = select.get(i - 1);
                    System.out.println(oxford.getWordName() + "\n\t\t" + oxford.getWordValue());
                }
            }
            select = sqlManager.lambdaQuery(Oxford.class).andLike(Oxford::getWordName, line + "%").asc(Oxford::getWordName).limit(1, 20).select();
            int index = 1;
            for (Oxford enWords : select) {
                System.out.println(StrUtil.format("{} {}", index, enWords.getWordName()));
                index++;
            }
        }
    }

    public static LinkedHashMap<Integer, Oxford> query(String word, Integer dict) {
        List<Oxford> select = getInstance().getSqlManager().lambdaQuery(Oxford.class).andLike(Oxford::getWordName, word + "%").asc(Oxford::getWordName).limit(1, 20).select();
        int index = 1;
        LinkedHashMap<Integer, Oxford> map = new LinkedHashMap<>();
        for (Oxford enWords : select) {
            map.put(index, enWords);
            index++;
        }
        return map;
    }
}