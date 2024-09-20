package com.pond.build.controller;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pond.build.mapper.TableDesignMapper;
import com.pond.build.model.Field;
import com.pond.build.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class TableDesignController {


    @Autowired
    private TableDesignMapper tableDesignMapper;


    private static final String TRANS_API_HOST = "https://fanyi-api.baidu.com/api/trans/vip/translate";


    @Value("${translate.appid}")
    private String appid;

    @Value("${translate.securityKey}")
    private String securityKey;


    @GetMapping("/tableDesign/{tableName}/{dataBase}")
    public List<Field> getTableDesignByName(@PathVariable("tableName") String tableName,@PathVariable("dataBase") String dataBase){
        if(Objects.equals(dataBase,"sqlserver")){
            return tableDesignMapper.getSqlServerTableDesignByTableName(tableName);
        }else{
            return null;
        }

    }

    @GetMapping("/tableDesign/translate/{fieldName}")
    public Map<String,Object> translate(@PathVariable("fieldName") String fieldName){

        Map<String, Object> returnMap = null;
        try {
            Map<String, String> params = new HashMap<String, String>();

            returnMap = new HashMap<String, Object>();
            params.put("q", fieldName);
            params.put("from", "zh");
            params.put("to", "en");

            params.put("appid", appid);

            // 随机数
            String salt = String.valueOf(System.currentTimeMillis());
            params.put("salt", salt);

            // 签名
            String src = appid + fieldName + salt + securityKey; // 加密前的原文
            params.put("sign", MD5.md5(src));


            String sendUrl = getUrlWithQueryString(TRANS_API_HOST, params);

            String finalResult = HttpRequest.get(sendUrl).execute().body();
            //userName
            JSONObject translateJson = JSON.parseObject(finalResult);
            if(translateJson.containsKey("trans_result")) {
                String translateResult = translateJson.getJSONArray("trans_result").getJSONObject(0).get("dst").toString();

                String withUnderscores = translateResult.replace(" ", "_").toUpperCase();

                returnMap.put("code",200);
                returnMap.put("data",withUnderscores);
                return returnMap;

            }
            returnMap.put("code",500);
            returnMap.put("msg","不知道的错误");
            return returnMap;

        } catch (Exception e) {
            returnMap.put("code",500);
            returnMap.put("msg",e.getMessage());
            return returnMap;
        }


    }



    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }



    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

}
