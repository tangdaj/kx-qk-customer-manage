package com.kaxin.qkcustomermanage.util;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

/**
 * mybatis-plus generator
 *
 * @author tangdj
 * @date 2021/11/17
 */
public class Generator {

    public static void main(String[] args) {
        System.out.println("请输入模块名称：");
        Scanner input = new Scanner(System.in);
        String modelName = input.next();
        input.close();
        System.out.println("-------开始生成-------");
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(getGlobalConfig());
        generator.setDataSource(getDataSourceConfig());
        generator.setStrategy(getStrategyConfig(modelName));
        generator.setPackageInfo(getPackageConfig(modelName));
        generator.execute();
    }

    private static GlobalConfig getGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("D:\\Program Files\\generator");
        globalConfig.setAuthor("tangdj");
        globalConfig.setOpen(false);
        globalConfig.setKotlin(false);
        globalConfig.setSwagger2(true);
        globalConfig.setFileOverride(true);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setServiceName("%sService");
        return globalConfig;
    }

    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql:///kx_qk_customer_manage?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");

        return dataSourceConfig;
    }

    private static StrategyConfig getStrategyConfig(String modelName) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("kx_");
        strategyConfig.setLikeTable(new LikeTable("kx_" + modelName, SqlLike.RIGHT));

        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSkipView(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
//        List<TableFill> tableFillList=new ArrayList<>();
//        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
//        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
//        tableFillList.add(new TableFill("update_by", FieldFill.INSERT));
//        tableFillList.add(new TableFill("update_by", FieldFill.INSERT));
//        strategyConfig.setTableFillList(tableFillList);
        strategyConfig.setSuperMapperClass("com.kaxin.qkcustomermanage.servlet.BaseMapper");
        strategyConfig.setSuperServiceClass("com.kaxin.qkcustomermanage.servlet.BaseService");
        strategyConfig.setSuperServiceImplClass("com.kaxin.qkcustomermanage.servlet.BaseServiceImpl");
        return strategyConfig;
    }

    private static PackageConfig getPackageConfig(String modelName) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.kaxin." + modelName);
        packageConfig.setEntity("entity.PO");
        return packageConfig;
    }
}
