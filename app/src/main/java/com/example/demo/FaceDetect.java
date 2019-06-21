package com.example.demo;



import android.util.Log;

import com.example.json.JsonRootBean;
import com.example.json.Location;
import com.google.gson.Gson;

import java.util.*;

/**
 * 人脸检测与属性分析
 */
public class FaceDetect {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static Location detect(String base64str) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
//            map.put("image_type", "FACE_TOKEN");
//            map.put("image", "027d8308a2ec665acb1bdf63e513bcb9");
            map.put("image_type", "BASE64");
            map.put("image", base64str);
            map.put("face_field", "faceshape,facetype");
            map.put("max_face_num", 3);


            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.aa1d6eb3fd50c89eaa264928a6f7b44d.2592000.1563496349.282335-9533086";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            JsonRootBean jsonRootBean = new Gson().fromJson(result, JsonRootBean.class);
            Location location = jsonRootBean.getResult().getFace_list().get(0).getLocation();


            Log.e("FACE", result);
            return location;
        } catch (Exception e) {
            Log.e("FACE",e.toString());
        }
        return null;
    }

}