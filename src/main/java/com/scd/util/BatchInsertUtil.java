package com.scd.util;

import com.scd.service.impl.TestServiceImpl;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author chengdu
 * @date 2020/1/12
 */
public class BatchInsertUtil {

    public static <T> int batchInsert(List<T> intputList, Function<List<T>, Integer> function, int batchNum) {
        if (CollectionUtils.isEmpty(intputList)) {
            return 0;
        }
        if (intputList.size() < batchNum) {
            return function.apply(intputList);
        }
        int sum = 0;
        int startIndex = 0;
        while (startIndex + batchNum < intputList.size()) {
            List<T> batchList = intputList.subList(startIndex, startIndex + batchNum);
            sum = sum + function.apply(batchList);
            startIndex = startIndex + batchNum;
        }
        List<T> leftList = intputList.subList(startIndex, intputList.size());
        sum = sum + function.apply(leftList);
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        List<Integer> subList = list.subList(0, 9);
        List<Integer> subList2 = list.subList(0, list.size());
        System.out.println(subList);
        TestServiceImpl testService = new TestServiceImpl();
        batchInsert(list, testService::insert,  3);
    }
}
