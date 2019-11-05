package com.atguigu.gmall.search;

import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang.ArrayUtils;
import org.apache.lucene.util.QueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class GmallSearchApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Test
    void contextLoads() throws IOException {

    }

    @Test
    void index() throws IOException {
        Index index = new Index.Builder(new User("liuyan", "123456", 18)).index("user").type("info").id("5").build();

        jestClient.execute(index);
    }

    @Test
    void update() throws IOException {
        User user = new User("zhangsan", "595959", null);
        HashMap<String, User> map = new HashMap<>();
        map.put("doc", user);
        Update update = new Update.Builder(map).index("user").type("info").id("2").build();
        jestClient.execute(update);
    }

    @Test
    public void search() throws IOException {
        // 查询表达式
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(query).addIndex("user").addType("info").build();
        SearchResult searchResult = jestClient.execute(search);
        //System.out.println(searchResult.getSourceAsObject(User.class, false));
        List<SearchResult.Hit<User, Void>> hits = searchResult.getHits(User.class);
        hits.forEach(hit -> {
            System.out.println(hit.source);
        });


    }

    @Test
    public void delete() throws IOException {
        Delete delete = new Delete.Builder("1").index("user").type("info").build();
        jestClient.execute(delete);
    }


}


@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private String name;
    private String password;
    private Integer age;
}