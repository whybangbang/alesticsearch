package com.jd;

import org.junit.Test;

import java.io.IOException;

public class AppTest {


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
//        FileUtils.readLines(file).stream().map((t) -> t.toString().replaceAll("'", "")).forEach(System.out::println);
        LoadData loadData = new LoadData();
        loadData.readDocs();
    }


}