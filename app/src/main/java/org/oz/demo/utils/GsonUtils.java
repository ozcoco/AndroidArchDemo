package org.oz.demo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @author oz
 * @version 1.0
 * @name GsonUtils
 * @package org.oz.code.util
 * @time 13/03/18 22:35
 * @email 857527916@qq.com
 * @describe 基于Gson的一个工具类
 */

public final class GsonUtils {
    //线程安全的
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()//打开Export注解，但打开了这个注解,副作用，要转换和不转换都要加注解
//              .serializeNulls()  //是否序列化空值
                .setDateFormat("yyyy-MM-dd HH:mm:ss")//序列化日期格式  "yyyy-MM-dd"
//              .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写
                .setPrettyPrinting() //自动格式化换行
//              .setVersion(1.0)  //需要结合注解使用，有的字段在1。0的版本的时候解析，但0。1版本不解析
                .create();
    }

    private GsonUtils() {
        throw new UnsupportedOperationException("can't be instantiated");
    }

    /**
     * 将一个对象转换为JSON格式的串
     *
     * @param object 任意对象
     * @return JSON格式的字符串
     */
    public static String convertVO2String(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 将一个JSON格式的字符串转换为Java对象
     *
     * @param jsonStr     要转换的JSON格式的字符串
     * @param targetClass 要将这个JSON格式的字符串转换为什么类型的对象
     * @return 转换之后的Java对象
     */
    public static <T> T convertString2Object(String jsonStr, Class<T> targetClass) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, targetClass);
    }

    /**
     * 将一个json转换成一个集合对象
     *
     * @param jsonStr   要转换的JSON格式的字符串
     * @param typeToken TypeToken<这里指定集合类型和泛型信息>
     * @return 转换之后的集合对象
     */
    public static <T> T convertString2Collection(String jsonStr, TypeToken<T> typeToken) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();

            if (isJson(jsonStr)) {
                T t = gson.fromJson(jsonStr, typeToken.getType());
                return t;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("Demo", "convertString2Collection \r\n  error = \r\n    " + Log.getStackTraceString(e));
            return null;
        }
    }

    public static <T> List<T> string2List(String json, Class<T> clazz) {
        final Type type = new ParameterizedType() {

            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{clazz};
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };

        List<T> list = new Gson().fromJson(json, type);

        return list;
    }

    public static boolean isJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject = null;
            return true;
        } catch (JSONException e1) {
            Log.e("Demo", "isJson \r\n  error = \r\n    " + Log.getStackTraceString(e1));
            return false;
        }
    }

    public static ArrayList<String> convertJson2Array(String str) {
        ArrayList<String> strs = new ArrayList<String>();
        try {
            JSONArray arr = new JSONArray(str);
            for (int i = 0; i < arr.length(); i++) {
                strs.add(arr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strs;
    }

    //获取gson解析器
    public static Gson getGson() {
        return GSON;
    }

    //对象转换为json
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    //JSON转换为对象1--普通类型
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    //JSON转换为对象-针对泛型的类型
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }


}
