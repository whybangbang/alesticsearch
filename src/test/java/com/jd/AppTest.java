package com.jd;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppTest {

    File file;

    @Before
    public void setUp(){
        List<Document> documents = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("car.txt").getFile());
    }


    /**
     * CREATE TABLE car_brand_models_dict(
     id int NOT NULL PRIMARY KEY comment '主键',
     brand_id int comment '车辆品牌ID',
     `name` varchar (50) comment '车型名称',
     first_letter varchar(2)  comment '首字母',
     parent_id int comment '车型分类ID',
     parent_name varchar(50) comment '车型分类名称',
     parent_first_letter varchar(2) comment '车型分类首字母'
     )
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        FileUtils.readLines(file).stream().forEach(System.out::println);
    }



    public List<Document> readDocs() throws IOException {

        List<Document> docs = new ArrayList<>();

        List<String> list = FileUtils.readLines(file);

        List<String []> newData = list.stream().map((t) -> t.split(",")).filter((t) -> t.length == 7).collect(Collectors.toList());
        for(String [] ss: newData){

            Document document = new Document();
            document.add();
            for(String s: ss){
                s = s.trim().replace("'", "");

            }
        }

        return docs;
    }
}