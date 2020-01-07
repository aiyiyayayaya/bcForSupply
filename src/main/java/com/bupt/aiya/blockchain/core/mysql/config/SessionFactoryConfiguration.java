package com.bupt.aiya.blockchain.core.mysql.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by aiya on 2019/3/17 下午9:24
 */
@Configuration
@EnableTransactionManagement
///@MapperScan("com.")
public class SessionFactoryConfiguration {
    //mybatis配置文件的路径
    @Value("${mybatis.config-location}")
    private String mybatisConfigFilePath;

    //mapper文件所在路径
    @Value("${mybatis.mapper-locations}")
    private String mapperPath = "/mapper/**.xml";

    //实体类所在PACKAGE
    @Value("${mybatis.entity_package}")
    private String  entityPackage;



    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean createSqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);

        return sqlSessionFactoryBean;
    }
}
