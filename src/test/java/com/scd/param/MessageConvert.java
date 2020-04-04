package com.scd.param;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.scd.model.po.Edge;
import com.scd.model.po.EdgeIdeaSetter;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * @author James
 */
public class MessageConvert {

    @Test
    public void testJson() {
        ObjectMapper objectMapper = new ObjectMapper();
//        JavaType
//        objectMapper.readValue(inputMessage.getBody(), javaType)
//        AbstractMessageConverterMethodArgumentResolver
//        objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructType(Edge.class);
        String json = "{\n" +
                "  \"spoint\": \"a\",\n" +
                "  \"tpoint\": \"b\"\n" +
                "}";
        try {
            Object object = objectMapper.readValue(json, javaType);
            System.out.println(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertEdge() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Assert.assertFalse(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructType(Edge.class);
        String json = "{\n" +
                "  \"sPoint\": \"a\",\n" +
                "  \"tPoint\": \"b\"\n" +
                "}";
        try {
            Object object = objectMapper.readValue(json, javaType);
            System.out.println(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertJsonPath() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructType(Edge.class);
        String jsonPath = "json/edge.json";
        PushbackInputStream pushbackInputStream = null;
        try {
            pushbackInputStream = new PushbackInputStream(new FileInputStream(jsonPath));
            Object object = objectMapper.readValue(pushbackInputStream, javaType);
            System.out.println(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pushbackInputStream != null) {
                try {
                    pushbackInputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Test
    public void testConvertIdeaSetterJsonPath() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructType(EdgeIdeaSetter.class);
        String jsonPath = "json/edge.json";
        PushbackInputStream pushbackInputStream = null;
        try {
            pushbackInputStream = new PushbackInputStream(new FileInputStream(jsonPath));
            Object object = objectMapper.readValue(pushbackInputStream, javaType);
            System.out.println(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pushbackInputStream != null) {
                try {
                    pushbackInputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Test
    public void testConvertError() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructType(Edge.class);
        String json = "{\n" +
                "  \"spoint\": \"a\",\n" +
                "  \"tpoint\": \"b\",\n" +
                "  \"s\":\"1\"\n" +
                "}";
        try {
            Object object = objectMapper.readValue(json, javaType);
            System.out.println(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
