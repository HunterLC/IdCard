package com.hunter_lc.idcard.db;

import org.litepal.crud.DataSupport;
import java.sql.Blob;

public class Record extends DataSupport {
    private int id;  //表id
    private int userId;  //用户id
    private Blob uploadPhoto;   //上传照片
    private String uploadTime;   //上传时间
    private int photoState;   //照片加密状态
    private String photoPw;   //解密密钥
    private Blob mosaicPhoto;   //关键区域马赛克照片
    private String key;            //加密密码
    private Blob keyPhoto;    //加密后的图

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Blob getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(Blob uploadPhoto) {
        this.uploadPhoto = uploadPhoto;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getPhotoState() {
        return photoState;
    }

    public void setPhotoState(int photoState) {
        this.photoState = photoState;
    }

    public String getPhotoPw() {
        return photoPw;
    }

    public void setPhotoPw(String photoPw) {
        this.photoPw = photoPw;
    }

    public Blob getMosaicPhoto() {
        return mosaicPhoto;
    }

    public void setMosaicPhoto(Blob mosaicPhoto) {
        this.mosaicPhoto = mosaicPhoto;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Blob getKeyPhoto() {
        return keyPhoto;
    }

    public void setKeyPhoto(Blob keyPhoto) {
        this.keyPhoto = keyPhoto;
    }
}
