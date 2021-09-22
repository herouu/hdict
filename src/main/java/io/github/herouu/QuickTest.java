package io.github.herouu;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.herouu.model.EnWords;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.MySqlStyle;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class QuickTest {
    private static DataSource datasource() {
        String path = QuickTest.class.getResource("/db.properties").getPath();
        HikariDataSource ds = new HikariDataSource(new HikariConfig(path));
        return ds;
    }

    private static SQLManager getSQLManager() {
        //得到一个数据源
        DataSource dataSource = datasource();
        //得到一个ConnectionSource， 单数据源
        ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
        //SQLManagerBuilder 唯一必须的参数就是ConnectionSource
        SQLManagerBuilder builder = new SQLManagerBuilder(source);
        //命名转化，数据库表和列名下划线风格，转化成Java对应的首字母大写，比如create_time 对应ceateTime
        builder.setNc(new UnderlinedNameConversion());
        //拦截器，非必须，这里设置一个debug拦截器，可以详细查看执行后的sql和sql参数
        // builder.setInters(new Interceptor[]{new DebugInterceptor()});
        //数据库风格，因为用的是H2,所以使用H2Style,
        builder.setDbStyle(new MySqlStyle());
        SQLManager sqlManager = builder.build();
        return sqlManager;
    }

    public static void main(String[] args) throws Exception {
        SQLManager sqlManager = getSQLManager();
        //初始化数据脚本，执行后，内存数据库将有一个sys_user表和模拟数据
        // DBInitHelper.executeSqlScript(sqlManager,"db/schema.sql");
        // 得到数据库的所有表
        Set<String> all = sqlManager.getMetaDataManager().allTable();
        System.out.println(all);

        Scanner scan = new Scanner(System.in);
        String line = null;
        boolean flag = true;
        while (flag) {
            line = scan.nextLine();
            if (":e".equals(line)) {
                flag = false;
                continue;
            }
            List<EnWords> select = sqlManager.lambdaQuery(EnWords.class).andLike(EnWords::getWord, line + "%").asc(EnWords::getWord).limit(1, 20).select();
            for (EnWords enWords : select) {
                System.out.println(enWords.getWord() + "\n\t" + enWords.getTranslation());
            }
        }

    }

}