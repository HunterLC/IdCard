package com.hunter_lc.idcard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hunter_lc.idcard.db.Record;
import com.hunter_lc.idcard.db.User;
import com.hunter_lc.idcard.farkas.tdk.util.MyUtil;
import com.hunter_lc.idcard.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class KeyActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    //private String filePath = Environment.getExternalStorageDirectory().getPath()+ "/test/test.jpg";
    private String filePath = MyUtil.SaveFile.getAbsolutePath();
    // AES加密后的文件
    private static final String outPath = Environment.getExternalStorageDirectory().getPath()+ "/DCIM/OCR/encrypt.jpg";
    // 混入字节加密后文件
    private static final String bytePath = Environment.getExternalStorageDirectory().getPath()+ "/test/byte.jpg";
    //AES加密使用的秘钥，注意的是秘钥的长度必须是16位
    private static final String AES_KEY = "MyDifficultPassw";
    //混入的字节
    private static final String BYTE_KEY = "MyByte";

    ImageView picOne,picTwo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_key);
        picOne = (ImageView)findViewById(R.id.pic_one_show);
        picTwo = (ImageView)findViewById(R.id.pic_two_show);
        picOne.setImageBitmap(MyUtil.MosaicsBitmap);
        showTips();

    }

    public void showTips(){
        builder = new AlertDialog.Builder(this,R.style.dialog)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("是否需要添加额外的一串秘密文本信息？")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                        aesEncrypt();
                        aesDecrypt();
                    }
                })
                .setNegativeButton("暂不", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendTo();
                    }
                });
        builder.create().show();
    }

    public void sendTo(){
        final EditText et = new EditText(this);
        builder = new AlertDialog.Builder(this,R.style.dialog)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("请输入需要发送对象的ID")
                .setView(et)
                .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //获取用户信息
                        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        List<User> users = DataSupport.select("*")
                                .where("account = ?",sharedPreferences.getString("account",null))
                                .find(User.class);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                        Record record = new Record();
                        record.setUserId(users.get(0).getId());
                        record.setUploadTime(sdf.format(new Date()));
                        record.setMosaicPhoto(Utility.BitmapToBytes(MyUtil.MosaicsBitmap));
                        record.save();
                        Toast.makeText(KeyActivity.this,"发送成功"+record.getUploadTime()+record.getMosaicPhoto().toString(),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
    }

    /**
     * 混入字节加密
     */
    public  void addByte(){
        try {
            //获取图片的字节流
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            FileOutputStream fops = new FileOutputStream(bytePath);
            //混入的字节流
            byte[] bytesAdd = BYTE_KEY.getBytes();
            fops.write(bytesAdd);
            fops.write(bytes);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除混入的字节解密图片
     */
    public  void removeByte(){
        try {
            FileInputStream stream = null;
            stream = new FileInputStream(new File(bytePath));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            int i=0;
            while ((n = stream.read(b)) != -1) {
                if(i==0){
                    //第一次写文件流的时候，移除我们之前混入的字节
                    out.write(b, BYTE_KEY.length(), n-BYTE_KEY.length());
                }else{
                    out.write(b, 0, n);
                }
                i++;
            }
            stream.close();
            out.close();
            //获取字节流显示图片
            byte[] bytes= out.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            //img.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用AES加密标准进行加密
     */
    public void aesEncrypt()  {
        try {
            FileInputStream fis = null;
            Log.d("22222222222222222222222",filePath);
            fis = new FileInputStream(filePath);
            FileOutputStream fos = new FileOutputStream(outPath);
            //SecretKeySpec此类来根据一个字节数组构造一个 SecretKey
            SecretKeySpec sks = new SecretKeySpec(AES_KEY.getBytes(),
                    "AES");
            //Cipher类为加密和解密提供密码功能,获取实例
            Cipher cipher = Cipher.getInstance("AES");
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            //CipherOutputStream 为加密输出流
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            int b;
            byte[] d = new byte[1024];
            while ((b = fis.read(d)) != -1) {
                cos.write(d, 0, b);
            }
            cos.flush();
            cos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用AES标准解密
     */
    public void aesDecrypt() {
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(outPath);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            SecretKeySpec sks = new SecretKeySpec(AES_KEY.getBytes(),
                    "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sks);
            //CipherInputStream 为加密输入流
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            int b;
            byte[] d = new byte[1024];
            while ((b = cis.read(d)) != -1) {
                out.write(d, 0, b);
            }
            out.flush();
            out.close();
            cis.close();
            //获取字节流显示图片
            byte[] bytes= out.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            picTwo.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
