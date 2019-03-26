package com.hunter_lc.idcard.db;

import org.litepal.crud.DataSupport;

import java.sql.Blob;

public class User extends DataSupport {
    private int id;       //编号 5
    private String nickName;  //昵称255
    private String name;      //真实姓名255
    private String loginTime;  //注册时间
    private String  birth;        //生日
    private int sex;           // 1男 0女
    private String account;         //账户255
    private String password;       //密码255
    private byte[] personalPhoto;     //头像
    private int actLevel;    //0 普通用户 1管理员

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String  loginTime) {
        this.loginTime = loginTime;
    }

    public String  getBirth() {
        return birth;
    }

    public void setBirth(String  birth) {
        this.birth = birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(byte[] personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public int getActLevel() {
        return actLevel;
    }

    public void setActLevel(int actLevel) {
        this.actLevel = actLevel;
    }

}

