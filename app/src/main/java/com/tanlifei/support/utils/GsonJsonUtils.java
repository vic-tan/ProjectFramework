package com.tanlifei.support.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanlifei on 16/7/28.
 */
public class GsonJsonUtils {

    public static  <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        List<T> lst =  new ArrayList<T>();
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for(final JsonElement elem : array){
                lst.add(new Gson().fromJson(elem, clazz));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }finally {
            return lst;
        }
    }
}
