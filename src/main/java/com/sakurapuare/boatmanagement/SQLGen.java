package com.sakurapuare.boatmanagement;


import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SQLGen {

    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://127.0.0.1:3306/boatmanagement?characterEncoding=utf-8&useInformationSchema=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("com.sakurapuare.boatmanagement");

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);
        globalConfig.enableEntity();
        globalConfig.enableMapper();
        globalConfig.enableService();
        globalConfig.enableServiceImpl();
        globalConfig.enableController();
        globalConfig.enableTableDef();

//        globalConfig.getStrategyConfig().setGenerateSchema("boatmanagement");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(21);


        return globalConfig;
    }
}