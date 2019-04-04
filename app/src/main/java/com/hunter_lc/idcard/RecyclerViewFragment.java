package com.hunter_lc.idcard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hunter_lc.idcard.db.MeetingInfo;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.hunter_lc.idcard.db.Record;
import com.hunter_lc.idcard.db.User;
import com.hunter_lc.idcard.farkas.tdk.util.MyUtil;
import com.hunter_lc.idcard.util.Utility;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    public static Fragment newHistoryInstance(){
        return  new RecyclerViewFragment();
    }

    List<Record> items = new ArrayList<>();
    static final int ITEMS = 8;


    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        initHistoryData();
        mRecyclerView.setAdapter(new HistoryViewPagerAdapter(items));
    }

    private void initHistoryData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        List<User> users = DataSupport.select("*")
                .where("account = ?",sharedPreferences.getString("account",null))
                .find(User.class);
        List<Record> records = DataSupport.select("*")
                                          .where("userId = ? or receiveUserAccount = ?",String.valueOf(users.get(0).getId()),users.get(0).getAccount())
                                          .order("uploadTime desc")
                                          .find(Record.class);
        for(Record record:records){
            items.add(record);
            Log.d("Record",record.getUploadTime());
        }

    }

}

