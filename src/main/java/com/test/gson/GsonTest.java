package com.test.gson;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-09 00:15
 * @Description: TODO
 */
public class GsonTest {

//    public static void main(String[] args) {
//        Fish fish = new Fish("RedFish", "Red", 1, Arrays.asList(new Fish.SubFish("dick"), new Fish.SubFish("pussy")),
//                Map.of("dick1", new Fish.SubFish("dick"), "pussy1", new Fish.SubFish("pussy")));
//        String json = new Gson().toJson(fish);
//        System.out.println(json);
//
//        String json2 = "{\"name\":\"RedFish\",\"color\":\"Red\",\"age\":1,\"subFishes\":[{\"name\":\"dick\"},{\"name\":\"pussy\"}]}";
//        Fish fish2 = new Gson().fromJson(json2, Fish.class);
//        System.out.println(fish2);
//
//    }

    @AllArgsConstructor@ToString
    public static class Fish{
        private String name;
        private String color;
        private int age;
        private List<SubFish> subFishes;
        private Map<String, SubFish> map;

        @AllArgsConstructor@ToString
        private static class SubFish{
            private String name;
        }
    }
}
