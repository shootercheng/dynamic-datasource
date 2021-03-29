package com.scd.cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.scd.model.po.TaskParam;

import java.util.ArrayList;
import java.util.List;

public class KryoSerializer {


    public static byte[] serialize(Object object) {
        Kryo kryo = new Kryo();
        Output output = new Output(200, -1);
        kryo.writeClassAndObject(output, object);
        return output.toBytes();
    }

    public static Object unserialize(byte[] bytes) {
        Kryo kryo = new Kryo();
        Input input = new Input();
        input.setBuffer(bytes);
        return kryo.readClassAndObject(input);
    }

    public static void main(String[] args) {
        List<TaskParam> list = new ArrayList<>();
        TaskParam taskParam = new TaskParam();
        taskParam.setId(1);
        taskParam.setParamType("type1");
        taskParam.setParamValue("value1");
        byte[] serialBytes = serialize(taskParam);
        TaskParam res = (TaskParam) unserialize(serialBytes);
        list.add(res);
        byte[] byteList = serialize(list);
        List<TaskParam> bList = (List) unserialize(byteList);
        System.out.println(bList);
    }
}
