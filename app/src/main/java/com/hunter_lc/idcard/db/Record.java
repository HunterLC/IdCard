package com.hunter_lc.idcard.db;

import org.litepal.crud.DataSupport;
import java.sql.Blob;

public class Record extends DataSupport {
    private int id;  //表id
    private int userId;  //用户id
    private byte[] uploadPhoto;   //上传照片
    private String uploadTime;   //上传时间
    private int photoState;   //照片加密状态
    private String photoPw;   //解密密钥
    private byte[] mosaicPhoto;   //关键区域马赛克照片
    private String key;            //加密密码
    private byte[] keyPhoto;    //加密后的图
    private String originPhoto;  //原始图片

    public String getReceiveUserAccount() {
        return receiveUserAccount;
    }

    public void setReceiveUserAccount(String receiveUserAccount) {
        this.receiveUserAccount = receiveUserAccount;
    }

    private String receiveUserAccount;   //接收者id


    public String  getOriginPhoto() {
        return originPhoto;
    }

    public void setOriginPhoto(String originPhoto) {
        this.originPhoto = originPhoto;
    }

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

    public byte[] getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(byte[] uploadPhoto) {
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

    public byte[] getMosaicPhoto() {
        return mosaicPhoto;
    }

    public void setMosaicPhoto(byte[] mosaicPhoto) {
        this.mosaicPhoto = mosaicPhoto;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getKeyPhoto() {
        return keyPhoto;
    }

    public void setKeyPhoto(byte[] keyPhoto) {
        this.keyPhoto = keyPhoto;
    }

}
