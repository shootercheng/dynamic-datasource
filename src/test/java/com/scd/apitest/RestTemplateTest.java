package com.scd.apitest;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengdu
 * @date 2019/8/25.
 */
public class RestTemplateTest {

    private static RestTemplate restTemplate = new RestTemplate();

    @Test
    public void multiExecuteTask(){
        String url = "http://localhost:9999/datasource/task/execute";
        HttpMethod httpMethod = HttpMethod.POST;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.add("project-id", "1334");
        Map<String, Object> map = new HashMap<>();
        map.put("id", 123);
        map.put("paramType","111");
        map.put("paramValue", "23324");
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        String result = responseEntity.getBody();
    }
}
