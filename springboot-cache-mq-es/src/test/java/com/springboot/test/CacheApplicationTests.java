package com.springboot.test;

import com.springboot.domain.Department;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    Department department;

    /**
     * 添加索引
     */
    @Test
    public void contextLoads() {
        // 构建index
        Index index = new Index.Builder(department).index("department").type("medical").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据json结构化查询
     */
    @Test
    public void search() {
        String json = "{\n" +
                "    \"query\": {\n" +
                "        \"match\": {\n" +
                "            \"id\": \"1\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search search = new Search.Builder(json).addIndex("department").addType("medical").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
