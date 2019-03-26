package com.hunter_lc.idcard.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.hunter_lc.idcard.gson.Login;
import com.google.gson.Gson;

import org.json.JSONObject;

public class Utility {


    /**
     * 检查json中是否有BOM头，如果有则剔除utf-8的BOM头
     * @param in
     * @return
     */
    public static String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {    //剔除utf-8的BOM头
            in = in.substring(1);
        }
        return in;
    }
    /**
     * 将返回的json数据解析成为Login实体类
     * @param response
     * @return
     */
    public static Login handleLoginResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(JSONTokener(response));
            //JSONArray jsonArray = jsonObject.getJSONArray("Login");
            //String loginContent = jsonArray.getJSONObject(0).toString();
            String loginContent = jsonObject.toString();
            return new Gson().fromJson(loginContent,Login.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**将字节数组转换为ImageView可调用的Bitmap对象
     * @param
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }
    /**  图片缩放
     * @param
     * @param bitmap 对象
     * @param w 要缩放的宽度
     * @param h 要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

}
