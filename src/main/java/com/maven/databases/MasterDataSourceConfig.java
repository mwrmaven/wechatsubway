package com.maven.databases;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@SpringBootConfiguration
@MapperScan(basePackages = "com.maven.mapper.master", sqlSessionFactoryRef = "masterSqlSessionFactory")
// basePackages 指向的是mapper的文件夹，后面的sqlSessionFactoryRef关联的是下面的名称为masterSqlSessionFactory的bean,这个可以按需求配置，不一定固定写哪一个
public class MasterDataSourceConfig {

    @Bean(name = "masterDataSource")
    @ConfigurationProperties("spring.master") // 这里的参数是配置文件中参数名中 url前面的参数
    @Primary // 当多数据源时，使用@Primary标示该数据源为主要的
    public DataSource theDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary // 当多数据源时，使用@Primary标示该数据源为主要的
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception { // Qualifier中配置的是上面的数据源的beanname
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/master/*.xml"));
        return bean.getObject();
    }

}
