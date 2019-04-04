package com.hunter_lc.idcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.hunter_lc.idcard.db.User;

public class RegisterActivity extends AppCompatActivity {

    public TextView textPassword;
    public TextView textAccount;
    public Button myButton;
    public RadioButton radiomale;
    public int txtsex;
    public TextView realName,nickName,birthday;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myButton = findViewById(R.id.register_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPassword = findViewById(R.id.password);
                textAccount = findViewById(R.id.name);
                radiomale = findViewById(R.id.male);
                realName = findViewById(R.id.realName);
                nickName = findViewById(R.id.nickName);
                birthday = findViewById(R.id.birthday);
                User user = new User();
                user.setAccount(textAccount.getText().toString());
                user.setPassword(textPassword.getText().toString());
                user.setNickName(nickName.getText().toString());
                user.setBirth(birthday.getText().toString());
                user.setName(realName.getText().toString());
                if (radiomale.isChecked()) {
                    txtsex = 1;
                } else {
                    txtsex = 0;
                }
                user.setSex(txtsex);
                user.save();
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}