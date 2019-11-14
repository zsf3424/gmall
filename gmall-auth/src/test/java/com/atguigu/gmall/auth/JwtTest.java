package com.atguigu.gmall.auth;

import com.atguigu.core.utils.JwtUtils;
import com.atguigu.core.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsf
 * @create 2019-11-14 11:10
 */
@SpringBootTest
public class JwtTest {

    private static final String pubKeyPath = "E:\\Workspace\\workspace_idea\\gmall\\gmall-auth\\rsa\\rsa.pub";

    private static final String priKeyPath = "E:\\Workspace\\workspace_idea\\gmall\\gmall-auth\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "2fds345#$%4d5fea0df21fa4");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "11");
        map.put("username", "liuyan");
        // 生成token
        String token = JwtUtils.generateToken(map, privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6IjExIiwidXNlcm5hbWUiOiJsaXV5YW4iLCJleHAiOjE1NzM3MDIzMzF9.Kh5fmFLNn7TqpWpbnqGkBEkbfhNhBN8sCaZXBwpWTn6TTPXIvPqjf_nWHDytgFVYjqcT3LSXQaVePfLNkuDm8UQmAhfM_K2fvfd1KG-HAfEfNtiDJifCCTK36QlH-r8fUD7olDioQDKx36_X9AuRsQQTnRYD8AsxBCGSmFR32b7phVEXNnHgvagCvRC8LcgZq3Zpwp4MUch-vpAvrO4x0BC86wFlZSQdMqke5MzVhWwcypS1tL7p5kiYafVyswOykzJ4_ydxx-kUKz1pM0uiHa63_k74_c-ENaud8PiDOf2FkQOWVyv0E8xOxzlHurfRcSi_404hRZr0CpdtmyhKxA";

        // 解析token
        Map<String, Object> map = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + map.get("id"));
        System.out.println("userName: " + map.get("username"));
    }
}
