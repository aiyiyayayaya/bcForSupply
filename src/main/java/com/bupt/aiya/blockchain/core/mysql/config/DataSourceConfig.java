package com.bupt.aiya.blockchain.core.mysql.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * Created by aiya on 2019/3/17 下午7:35
 */
@Configuration
//配置Mybatis mapper的扫描路径
@MapperScan("com.mindata.blockchain.core.dao")
public class DataSourceConfig {

    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        //关闭连接后不自动提交
        dataSource.setAutoCommitOnClose(false);
        System.out.println("username = "+jdbcUser);

        return dataSource;
    }
}
//    @Value("${spring.datasource.url}")
//    private static String dbUrl;
//
//    @Value("${spring.datasource.username}")
//    private static String username;
//
//    @Value("${spring.datasource.password}")
//    private static String password;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private static String driverClassName;
//
//    @Value("${spring.datasource.initialSize}")
//    private static int initialSize;
//
//    @Value("${spring.datasource.minIdle}")
//    private static int minIdle;
//
//    @Value("${spring.datasource.maxActive}")
//    private static int maxActive;
//
//    @Value("${spring.datasource.maxWait}")
//    private static int maxWait;


    //private boolean connectionProperties;


//    @Bean
//    public ServletRegistrationBean druidServletRegistrantionBean(){
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//        servletRegistrationBean.setServlet(new StatViewServlet());
//        servletRegistrationBean.addUrlMappings("/druid/*");
//
//        servletRegistrationBean.addInitParameter("loginUsername", "root");
//        servletRegistrationBean.addInitParameter("loginPassword", "000000");
//        return servletRegistrationBean;
//    }
//
//    //
//    @Bean
//    public FilterRegistrationBean druidFilterRegistrationBean(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        Map<String, String> initParams = new HashMap<>();
//
//        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//    @Bean(name = "dataSource")
//    @ConfigurationProperties("spring.datasource")
//    @Primary
//    public DataSource dataSource() throws SQLException {
//        System.out.println("start to deploy druidDataSource");
//        //DruidDataSource druidDataSource = new DruidDataSource();
////        druidDataSource.setDriverClassName(driverClassName);
////        druidDataSource.setUsername(username);
////        druidDataSource.setPassword(password);
////        druidDataSource.setUrl(dbUrl);
////        druidDataSource.setFilters("stat,wall");
////        druidDataSource.setInitialSize(initialSize);
////        druidDataSource.setMinIdle(minIdle);
////        druidDataSource.setMaxActive(55);
//
//        //关闭连接后不自动提交
////        druidDataSource.setDefaultAutoCommit(false);
////        druidDataSource.setUseGloalDataSourceStat(true);
//        //System.out.println("username:"+username);
//
//        //druidDataSource.setConnectProperties(connectionProperties);
//
//        return druidDataSource;
//    }

