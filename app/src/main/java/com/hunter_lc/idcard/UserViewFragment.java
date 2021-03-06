package com.hunter_lc.idcard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hunter_lc.idcard.db.User;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class UserViewFragment extends Fragment {

    public static Fragment newUserInstance(){
        return  new UserViewFragment();
    }
    final List<String> userList = new ArrayList<>();
    public static List<User> users;
    User user = new User();

    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.userinfo_recyclerview, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

                super.onViewCreated(view, savedInstanceState);
                initUserData();
                mRecyclerView=view.findViewById(R.id.userinfo_recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                mRecyclerView.setAdapter(new UserViewPagerAdapter(user,userList));
    }

    private void initUserData() {
        //获取用户信息
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        users = DataSupport.select("*")
                                      .where("account = ?",sharedPreferences.getString("account",null))
                                      .find(User.class);
        user.setPersonalPhoto(users.get(0).getPersonalPhoto());
            /*user("000");//用户头像
            user.setId(2019);//用户编号
            user.setName("刘畅");//用户姓名
            user.setTitle("项目经理");//用户职称
            user.setUsername("畅畅快报");//用户名
            user.setMale(1);//用户性别*/
            //将用户信息加入到list列表中
            //userList.add(String.user.getPersonalPhoto());
        userList.add("000");
        userList.add(String.valueOf(users.get(0).getId()));
        userList.add(users.get(0).getName());
        userList.add(users.get(0).getBirth());
        userList.add(users.get(0).getNickName());
        userList.add(users.get(0).getSex()==1?"男":"女");


    }

}
