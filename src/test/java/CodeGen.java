import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.herouu.QuickTest;
import org.beetl.core.ReThrowConsoleErrorHandler;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.SQLiteStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.gen.SourceBuilder;
import org.beetl.sql.gen.SourceConfig;
import org.beetl.sql.gen.simple.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CodeGen {


    private static DataSource datasource() {
        String path = QuickTest.class.getResource("/db.properties").getPath();
        HikariDataSource ds = new HikariDataSource(new HikariConfig(path));
        return ds;
    }

    private  static SQLManager getSQLManager(){
        //得到一个数据源
        DataSource dataSource = datasource();
        //得到一个ConnectionSource， 单数据源
        ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
        //SQLManagerBuilder 唯一必须的参数就是ConnectionSource
        SQLManagerBuilder builder = new SQLManagerBuilder(source);
        //命名转化，数据库表和列名下划线风格，转化成Java对应的首字母大写，比如create_time 对应ceateTime
        builder.setNc(new UnderlinedNameConversion());
        //拦截器，非必须，这里设置一个debug拦截器，可以详细查看执行后的sql和sql参数
        builder.setInters(new Interceptor[]{new DebugInterceptor()});
        //数据库风格，因为用的是H2,所以使用H2Style,
        builder.setDbStyle(new SQLiteStyle());
        SQLManager sqlManager = builder.build();
        return sqlManager;
    }

    public static void main(String[] args) {
        List<SourceBuilder> sourceBuilder = new ArrayList<>();
        SourceBuilder entityBuilder = new EntitySourceBuilder();
        SourceBuilder mapperBuilder = new MapperSourceBuilder();
        SourceBuilder mdBuilder = new MDSourceBuilder();

        sourceBuilder.add(entityBuilder);
        // sourceBuilder.add(mapperBuilder);
        // sourceBuilder.add(mdBuilder);

        SourceConfig config = new SourceConfig(getSQLManager(),sourceBuilder);

//如果有错误，抛出异常而不是继续运行1
        EntitySourceBuilder.getGroupTemplate().setErrorHandler(new ReThrowConsoleErrorHandler() );

        SimpleMavenProject project = new SimpleMavenProject("io.github.herouu");
        String tableName = "oxford";
//可以在控制台看到生成的所有代码
        config.gen(tableName,project);
    }
}
