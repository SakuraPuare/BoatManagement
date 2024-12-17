package com.sakurapuare.boatmanagement;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.sakurapuare.boatmanagement.pojo.entity.BaseEntity;
import com.zaxxer.hikari.HikariDataSource;

public class SQLGen {

    public static void main(String[] args) {
        // 配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(
                "jdbc:mariadb://127.0.0.1:3306/boatmanagement?characterEncoding=utf-8&useInformationSchema=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        GlobalConfig globalConfig = createGlobalConfigUseStyle();
        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // 生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.enableEntity();
        globalConfig.enableMapper();
        globalConfig.enableService();
        globalConfig.enableServiceImpl();
        globalConfig.enableController();
        // globalConfig.enableTableDef();
        // globalConfig.enableMapperXml();

        //设置根包
        globalConfig.setBasePackage("com.sakurapuare.boatmanagement");
        globalConfig.getPackageConfig()
                .setControllerPackage("com.sakurapuare.boatmanagement.controller.admin")
                .setEntityPackage("com.sakurapuare.boatmanagement.pojo.entity");

        globalConfig.getEntityConfig()
                .setSuperClass(BaseEntity.class)
//                .setOverwriteEnable(true)
                .setWithLombok(true)
                .setWithSwagger(true)
                .setJdkVersion(21);

        globalConfig.getMapperConfig()
//                .setOverwriteEnable(true)
        ;

        globalConfig.getServiceConfig()
//                .setOverwriteEnable(true)
        ;

        globalConfig.getServiceImplConfig()
                .setCacheExample(true)
        // .setOverwriteEnable(true)
        ;

        globalConfig.getControllerConfig()
                .setClassPrefix("Admin")
//                .setOverwriteEnable(true)
        ;

        // globalConfig.getTableDefConfig()
        //         .setOverwriteEnable(true);

        // globalConfig.getMapperXmlConfig()
        //         .setOverwriteEnable(true);

        return globalConfig;
    }
}