package cn.edu.zucc.g4.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @author ：qhh
 * @date ：Created in 2022/6/24 10:19
 */
public class Generator {
    public static void main(String[] args) {
        generate();
    }

    public static void generate(){
        FastAutoGenerator.create("jdbc:mysql://110.40.223.130:3306/medicine_helper?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Aisa/Shanghai", "root", "nyw+43")
                .globalConfig(builder -> {
                    builder.author("G04") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Documents\\Project\\2022Summer-G04\\project-g04\\medicine-helper-backend\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cn.edu.zucc.g4") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Documents\\Project\\2022Summer-G04\\project-g04\\medicine-helper-backend\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("doctor"); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
