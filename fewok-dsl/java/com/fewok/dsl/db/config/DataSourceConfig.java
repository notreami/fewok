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


    public static HikariDataSource getHikariDataSource(String driverClassName, String jdbcUrl, String userName, String password, int initialSize) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);//指定driver的类名，默认从jdbc url中自动探测.
        hikariConfig.setJdbcUrl(jdbcUrl);//指定JDBC URL
        hikariConfig.setUsername(userName);//指定用户名
        hikariConfig.setPassword(password);//指定数据库密码

//        hikariConfig.setAllowPoolSuspension(false);//使用Hikari pool时，是否允许连接池暂停，默认为: false
//        hikariConfig.setAutoCommit(true);//指定updates是否自动提交
//        hikariConfig.setCatalog();//指定默认的catalog
        hikariConfig.setConnectionInitSql("SET NAMES utf8mb4");//指定连接被创建，再被添加到连接池之前执行的sql.
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
     * tomcat-jdbc配置
     *
     * @param driverClassName
     * @param jdbcUrl
     * @param userName
     * @param password
     * @param initialSize
     * @return
     */
//    public static DataSource getTomcatDataSource(String driverClassName, String jdbcUrl, String userName, String password, int initialSize) {
//        PoolProperties p = new PoolProperties();
//        p.setDriverClassName(driverClassName);//驱动的完整有效的java类名
//        p.setUrl(jdbcUrl);//建立连接的URL
//        p.setUsername(userName);//连接用户名
//        p.setPassword(password);//连接密码
//
//        p.setDefaultAutoCommit(false);//连接池创建的连接的默认的auto-commit 状态(事务处理每条sql自动提交，而不是等最后提交，会导致无法事务回滚)
//        p.setDefaultReadOnly(false);//连接池创建的连接的默认的read-only 状态
//        p.setRollbackOnReturn(true);//通过在连接上调用回滚来终止事务
//        p.setCommitOnReturn(false);//通过在连接上调用commit来完成事务。如果 rollbackOnReturn==true，则忽略该属性
//        p.setDefaultTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);//连接池创建的连接的默认的TransactionIsolation 状态。Read uncommitted 读未提交，Read committed 读提交，Repeatable read 重复读，Serializable 序列化
//        p.setFairQueue(true);//从线程池里获取线程使用公平队列模式(FIFO方式公平对待)。默认值是true
//        p.setUseDisposableConnectionFacade(true);//是否防止线程继续引用一个已被关闭的连接。默认值是true
//        p.setIgnoreExceptionOnPreLoad(false);//是否在初始化池时忽略连接创建的错误。默认值是false
//        p.setJmxEnabled(true);//是否利用 JMX 注册连接池。默认为true
//        p.setUseEquals(true);//ProxyConnection 类使用 String.equals。默认值是true
//        p.setUseStatementFacade(true);//启用equals()和hashCode()调用方法。默认值是true
//
//        p.setInitialSize(initialSize);//连接池初始创建的连接数量。默认值是10
//        p.setMaxActive(20);//可以同时从该池中分配的最大活动连接数。默认值是100  推荐的公式：((core_count * 2) + effective_spindle_count)
//        p.setMaxIdle(20);//保留在池中的最大连接数。默认值是100
//        p.setMinIdle(initialSize);//保留在池中的最小连接数。默认值是10
//
//        p.setTestOnConnect(false);//创建连接时是否验证连接。默认值是false
//        p.setTestOnBorrow(true);//从连接池获取连接时，是否验证连接。默认值是false
//        p.setTestOnReturn(false);//归还到连接池之前，是否验证连接。默认值是false
//        p.setTestWhileIdle(true);//连接空闲时，是否验证连接。默认值是false
//
//        p.setInitSQL("SET NAMES utf8mb4");//连接创建时执行的初始化语句
//        p.setMaxWait(3000);//等待获取连接池连接的时间。默认值是30000（30秒）
//
//        p.setValidationQuery("SELECT 1");//SQL查询将用于验证来自此池的连接，然后再将其返回给调用者
//        p.setValidationQueryTimeout(3);//连接验证查询失败之前的超时时间（秒）
//        p.setValidationInterval(30000);//避免过多的验证，只能在这个频率上以毫秒为单位运行验证。如果连接到期进行验证，但在此间隔内已被验证，则不会再次进行验证。默认值是3000（3秒）
//        p.setTimeBetweenEvictionRunsMillis(60000);//间隔多久才进行一次检测，检测需要关闭的空闲连接。默认值是5000（5秒）
//        p.setMinEvictableIdleTimeMillis(300000);//一个连接在池中最小生存的时间。默认值是60000（60秒）
//        p.setLogValidationErrors(false);//是否打印验证异常日志。默认值是false
//
////        p.setRemoveAbandonedTimeout(10);//从连接池中取出连接，未归还的超时时间。默认值是60（60秒）
////        p.setRemoveAbandoned(true);//清除未归还（removeAbandonedTimeout）的连接。默认值是false
////        p.setSuspectTimeout(10);//超时未归还连接池的连接则关闭。默认值是0
////        p.setLogAbandoned(false);//是否打印RemoveAbandoned的日志。默认值是false
//
//        /*
//         * 建立新连接时将发送到JDBC驱动程序的连接属性。字符串的格式必须是[propertyName = property;]
//         */
//        p.setConnectionProperties(StringUtils.join(Arrays.asList(
//                "useJDBCCompliantTimezoneShift=true",
//                "useLegacyDatetimeCode=false",
//                "serverTimezone=GMT+8",
//                "useUnicode=true",
//                "characterEncoding=UTF-8",
//                "useSSL=false",
//                "zeroDateTimeBehavior=convertToNull",
//                "connectTimeout=3000",
//                "socketTimeout=10000"
//        ), ";"));
//
//        /*
//         * 插件
//         */
//        p.setJdbcInterceptors(StringUtils.join(Arrays.asList(
//                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer",
//                "org.apache.tomcat.jdbc.pool.interceptor.QueryTimeoutInterceptor(queryTimeout=5)",//查询超时拦截器，属性queryTimeout，单位秒，默认1秒
//                "org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReport(threshold=3000,maxQueries=1800,logSlow=true,logFailed=true)"//慢查询记录，属性threshold超时纪录阈值单位毫秒，默认1000
//        ), ";"));
//
//        return new DataSource(p);
//    }

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
