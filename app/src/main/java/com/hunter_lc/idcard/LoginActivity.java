package com.hunter_lc.idcard;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hunter_lc.idcard.db.User;
import com.hunter_lc.idcard.gson.Login;
import com.hunter_lc.idcard.util.HttpUtil;
import com.hunter_lc.idcard.util.Utility;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class LoginActivity extends AppCompatActivity
{

    private TransitionView mAnimView;
    SpinKitView spinKitView;
    Button loginButton;
    EditText userAccount,userPassword;
    public static String LOGIN_SUCCESS_TOKEN = null;  //全局使用的token

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        Connector.getDatabase();  //创建数据库
        SharedPreferences prefs = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        if(prefs.getString("account",null)!=null && prefs.getString("password",null)!=null)
            gotoMainActivity();
        else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            User user = new User();
            user.setAccount("88886666");
            user.setActLevel(0);
            user.setBirth("1998-11-19");
            user.setId(1);
            user.setLoginTime(sdf.format(new Date()));
            user.setName("高作缘");
            user.setNickName("Stonecutter");
            user.setSex(1);
            user.setPassword("123456");
            user.setPersonalPhoto(img(R.drawable.ic_icon));
            user.save();
        }

        mAnimView = findViewById(R.id.ani_view);
        loginButton = (Button)findViewById(R.id.btn_registered_login);

        mAnimView.setOnAnimationEndListener(new TransitionView.OnAnimationEndListener()
        {
            @Override
            public void onEnd()
            {
                //跳转到主页面
                gotoMainActivity();
            }
        });
    }
    //将drawable转换成可以用来存储的byte[]类型
    public byte[] img(int id)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private void gotoMainActivity()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void singUp(View view)  //相应登录按键
    {
        requestLogin();
    }

    public void requestLogin(){
        userAccount = (EditText)findViewById(R.id.Ed_uerPhoneNumber);
        userPassword = (EditText)findViewById(R.id.Ed_uerPassword);
        final String account = userAccount.getText().toString();
        final String password = userPassword.getText().toString();
        spinKitView = (SpinKitView)findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        spinKitView.setIndeterminateDrawable(circle);
        spinKitView.setVisibility(View.VISIBLE);
        List<User> users = DataSupport.findAll(User.class);
        for(User loginUser: users)
            if(loginUser.getAccount().equals(account) && loginUser.getPassword().equals(password)) {   //登录成功
                SharedPreferences loginSP = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);  //保存登录信息，只能被本应用所访问
                loginSP.edit()              //记住密码自动登录
                        .putString("account", account)
                        .putString("password", password)
                        .apply();
                //IN_SUCCESS_TOKEN = login.result.token;   //全局使用的token
                spinKitView.setVisibility(View.INVISIBLE);//关闭加载动画
                loginButton.setEnabled(false);//登陆键不可触碰
                userAccount.setFocusable(false);//账号编辑框不可点击
                userPassword.setFocusable(false);//密码框不可点击
                mAnimView.startAnimation();   //登陆成功动画
                Toast.makeText(LoginActivity.this,"头像信息"+loginUser.getPersonalPhoto().toString(),Toast.LENGTH_LONG).show();
            }else{
                spinKitView.setVisibility(View.INVISIBLE);//关闭加载动画
                Toast.makeText(LoginActivity.this,"获取登录信息失败",Toast.LENGTH_LONG).show();
            }
    }
}

