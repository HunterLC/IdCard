package com.hunter_lc.idcard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hunter_lc.idcard.db.Record;
import com.hunter_lc.idcard.db.User;
import com.hunter_lc.idcard.farkas.tdk.util.MyUtil;
import com.hunter_lc.idcard.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IdCardDetailActivity extends AppCompatActivity {

    public static final String ROOM_NAME = "room_name";
    public static final String ROOM_IMAGE_ID = "room_image_id";
    private AlertDialog.Builder builder;
    public ImageView roomImageView,showIdCard;
    public Button keyButton;
    public SharedPreferences sharedPreferences;
    public String secretKey;
    public List<Record> records;
    public byte[] keyBytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_detail);
        Intent intent = getIntent();
        secretKey = intent.getStringExtra(ROOM_NAME);
        int roomImageId = intent.getIntExtra(ROOM_IMAGE_ID,0);
        Toolbar toolbar = (Toolbar)findViewById(R.id.meetingdetail_toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        roomImageView = (ImageView)findViewById(R.id.meetingdetail_image_view);

        //获取用户信息
        sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        records = DataSupport.select("mosaicPhoto")
                .where("key = ?",secretKey)
                .find(Record.class);

        showIdCard = (ImageView)findViewById(R.id.idcard_detail_photo);
        showIdCard.setImageBitmap(Utility.getPicFromBytes(records.get(0).getMosaicPhoto(),null));  //获得马赛克图片;
        keyButton = (Button)findViewById(R.id.idcard_detail_button);
        keyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTips();
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(secretKey);
        Glide.with(this).load(roomImageId).into(roomImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showTips(){
        records = DataSupport.select("originPhoto")
                .where("key = ?",secretKey)
                .find(Record.class);
        final EditText et = new EditText(this);
        builder = new AlertDialog.Builder(this,R.style.dialog)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("请输入解密序列")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String inputKey = et.getText().toString();
                        if(inputKey.equals(secretKey)){
                            try{
                                FileInputStream stream = new FileInputStream(new File(records.get(0).getOriginPhoto()));
                                ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
                                byte[] b = new byte[1024];
                                int n;
                                int k=0;
                                while ((n = stream.read(b)) != -1) {
                                    out.write(b, 0, n);
                                    k++;
                                }
                                stream.close();
                                out.close();
                                //获取字节流显示图片
                                keyBytes= out.toByteArray();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(keyBytes, 0, keyBytes.length);
                                showIdCard.setImageBitmap(bitmap);  //原图

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else
                            Toast.makeText(IdCardDetailActivity.this,"解密序列错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }
}
