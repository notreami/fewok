package com.fewok.dsl.db.config;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

/**
 * 数据源配置
 *
 * @author notreami on 17/5/8.
 */
public class DataSourceConfig {


    /**
     * 数据源配置
     *
     * @param driverClassName
     * @param jdbcUrl
     * @param userName
     * @param password
     * @param initialSize
     * @return
     */
    public static HikariDataSource getHikariDataSource(String driverClassName, String jdbcUrl, String userName, String password, int initialSize) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);//指定driver的类名，默认从jdbc url中自动探测.
        hikariConfig.setJdbcUrl(jdbcUrl);//指定JDBC URL
        hikariConfig.setUsername(userName);//指定用户名
        hikariConfig.setPassword(password);//指定数据库密码

//        hikariConfig.setAllowPoolSuspension(false);//使用Hikari pool时，是否允许连接池暂停，默认为: false
//        hikariConfig.setAutoCommit(true);//指定updates是否自动提交
//        hikariConfig.setCatalog();//指定默认的catalog
        hikariConfig.setConnectionInitSql("SELECT 1");//指定连接被创建，再被添加到连接池之前执行的sql.
        hikariConfig.setConnectionTestQuery("SELECT 1");//指定校验连接合法性执行的sql语句
        hikariConfig.setConnectionTimeout(120000);//指定连接的超时时间，毫秒单位.缺省:30秒
//        hikariConfig.setDataSourceClassName();//指定数据源的全限定名
//        hikariConfig.setDataSourceJNDI();//指定jndi的地址
//        hikariConfig.setHealthCheckRegistry();//心跳检查注册
//        hikariConfig.setIdleTimeout(600000);//指定连接多久没被使用时，被设置为空闲，默认为10分钟
//        hikariConfig.setInitializationFailFast();//当创建连接池时，没法创建指定最小连接数量是否抛异常
//        hikariConfig.setIsolateInternalQueries();//指定内部查询是否要被隔离，默认为false
//        hikariConfig.setLeakDetectionThreshold();//使用Hikari connection pool时，多少毫秒检测一次连接泄露
        hikariConfig.setMinimumIdle(initialSize);//指定连接维护的最小空闲连接数，当使用HikariCP时指定
        //指定连接池中连接的最大生存时间，毫秒单位.缺省:30分钟，建议设置比数据库超时时长少30秒以上，
        //参考MySQL wait_timeout参数（show variables like '%timeout%';）
//        hikariConfig.setMaxLifetime(1765000);
        //指定连接池最大的连接数，包括使用中的和空闲的连接,缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
        hikariConfig.setMaximumPoolSize(64);//15
//        hikariConfig.setMetricRegistry();
//        hikariConfig.setPoolName();//指定连接池名字
//        hikariConfig.setReadOnly();//当使用Hikari connection pool时，是否标记数据源只读
//        hikariConfig.setRegisterMbeans();//指定Hikari connection pool是否注册JMX MBeans.
//        hikariConfig.setTransactionIsolation();//指定连接的事务的默认隔离级别
//        hikariConfig.setValidationTimeout(SECONDS.toMillis(5));//设定连接校验的超时时间，当使用Hikari connection pool时指定

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

    /**
     * 分页插件
     * 分页说明:https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
     *
     * @return
     */
    public static PageInterceptor getPageHelper() {
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");//指定分页插件使用哪种方言
        properties.setProperty("offsetAsPageNum", "true");//使用RowBounds有效。将 RowBounds 中的 offset 参数当成 pageNum 使用。默认为false
        properties.setProperty("rowBoundsWithCount", "true");//使用RowBounds有效。使用 RowBounds 分页会进行 count 查询。默认为false
        properties.setProperty("pageSizeZero", "false");//pageSize=0或者RowBounds.limit = 0就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是Page类型）。默认为false
        properties.setProperty("reasonable", "true");//分页参数合理化，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页。默认为false
        properties.setProperty("supportMethodsArguments", "false");//支持通过Mapper接口参数来传递分页参数。默认为false
        properties.setProperty("autoRuntimeDialect", "false");//允许在运行时根据多数据源自动识别对应方言的分页。默认为false
        properties.setProperty("closeConn", "true");

        PageInterceptor pageInterceptor = new PageInterceptor();

        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
