package org.mybatis.generator.plugins;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mybatis代码生成插件调用者.
 */
public class Generator {

    @Test
    public void generate() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        InputStream stream = Generator.class.getClassLoader().getResourceAsStream("generatorConfig.xml");

        ConfigurationParser cp = new ConfigurationParser(warnings);
        InputStreamReader reader = new InputStreamReader(stream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line = bufferedReader.readLine();
        StringBuffer buffer = new StringBuffer();
        while (line != null) {
            buffer.append(line + "\n");
            line = bufferedReader.readLine();
        }
        String xmlWithParam = buffer.toString();
        
        System.out.println("------- xml config begin -------");
        System.out.println(xmlWithParam);
        System.out.println("------- xml config end -------");

        final Configuration config = cp.parseConfiguration(new ByteArrayInputStream(xmlWithParam.getBytes("utf-8")));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        System.out.println("------- generator begin -------");
        ProgressCallback cb = new ProgressCallback() {
            private Pattern SqlProviderPattern = Pattern.compile("\\w*SqlProvider\\.java");
            private List<String> taskNames = new ArrayList();

            @Override
            public void startTask(String taskName) {
                /*
                 * 输出这些信息从而知道 生成那些类了 
                * Generating Example class for table demo
                * Generating Primary Key class for table demo 
                * Generating Record class for table demo 
                * Generating Mapper Interface for table demo 
                * Generating SQL Provider for table demo 
                * Saving file DemoExample.java
                * Saving file DemoKey.java Saving file Demo.java
                * Saving file DemoMapper.java
                * Saving file DemoSqlProvider.java
                 */
                // System.out.println(taskName);
                taskNames.add( taskName);
            }

            @Override
            public void saveStarted(int arg0) {}

            @Override
            public void introspectionStarted(int arg0) {}

            @Override
            public void generationStarted(int arg0) {}

            @Override
            public void done() {
                for(String taskName : taskNames){
                Matcher matcher = SqlProviderPattern.matcher(taskName);
                    if (matcher.find()) {
                        final String SqlProviderFilename = matcher.group();
                        System.out.println("处理生成文件,selectByExample  增加mysql分页: " + SqlProviderFilename);
                        List<Context> contexts = config.getContexts();
                        FilenameFilter filter = new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String name) {
                                return SqlProviderFilename.equalsIgnoreCase(name);
                            }
                        };
                        boolean done = false;
                        for (Context ctx : contexts) {
                            if(done){
                                break;
                            }
                            String targetProject = ctx.getJavaClientGeneratorConfiguration().getTargetProject();
                            String targetPackage = ctx.getJavaClientGeneratorConfiguration().getTargetPackage();
                            String dir = targetProject.replaceAll("\\.", "\\\\") + "\\" + targetPackage.replaceAll("\\.", "\\\\");
                            System.out.println(System.getProperty("user.dir") + dir);
                            File mapperDir = new File(System.getProperty("user.dir"), dir);
                            File[] files = mapperDir.listFiles(filter);
                            if (files != null && files.length > 0) {
                                File sqlProviderFile = files[0];
                                try {
                                    Generator.addMysqlLimit(sqlProviderFile);
                                    done = true;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(!done){
                             System.out.println("转换失败!!!! selectByExample  增加mysql分页: " + SqlProviderFilename);
                        } else {
                            System.out.println("转换成功!!!! selectByExample  增加mysql分页: " + SqlProviderFilename);
                        }
                    }
                }
            }

            @Override
            public void checkCancel() throws InterruptedException {}
        };
        myBatisGenerator.generate(cb);
        for (String warning : warnings) {
            System.out.println(warning);
        }
        System.out.println("------- generator end -------");
        // System.out.println(config.getClassPathEntries());
    }

    private static void addMysqlLimit(File sqlProviderFile) throws Exception {
        /*
         * 这是自动生成的selectByExample 中的代码片段
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause()); // 整个文件唯一
        }
        
        return SQL();
        
         * 将return SQL()改为下面片段即可:

        String sqlString = SQL();
        if (example != null && example.getLimit() != null) {
            sqlString += " limit " + example.getLimit();
        }
        if (example != null && example.getOffset() != null) {
            sqlString += " offset " + example.getOffset();
        }
        return sqlString;
         */
        BufferedReader reader = new BufferedReader( new FileReader(sqlProviderFile));
        List<String> lines = IOUtils.readLines(reader);
        reader.close();
        String limitString = "        String sqlString = SQL();\n" +
                "        if (example != null && example.getLimit() != null) {\n" +
                "            sqlString += \" limit \" + example.getLimit();\n" +
                "        }\n" +
                "        if (example != null && example.getOffset() != null) {\n" +
                "            sqlString += \" offset \" + example.getOffset();\n" +
                "        }\n" +
                "        return sqlString;";
        ArrayList<String> newLines = new ArrayList<String>();

        
        for (int i=0; i< lines.size();++i) {
            String line = lines.get(i);
            newLines.add(line );
            if(line.replaceAll(" ", "") .equalsIgnoreCase("ORDER_BY(example.getOrderByClause());")) {
                // 添加下一行大括号和空白行
                ++i;
                newLines.add(lines.get(i));
                ++i;
                newLines.add(lines.get(i));
                
                ++i; // 跳过 return SQL();
                newLines.addAll(Arrays.asList( limitString.split("\n")));
            }
        }
        
//        for (String line : newLines) {
//            System.out.println(line);
//        }
        FileOutputStream writer = new FileOutputStream(sqlProviderFile);
        IOUtils.writeLines(newLines, "\n",writer,"UTF-8");
        writer.close();
    }
    
    public static void main(String[] args) throws Exception {
       new Generator().generate();
    }
}
