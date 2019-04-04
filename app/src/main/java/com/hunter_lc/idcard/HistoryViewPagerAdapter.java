package com.hunter_lc.idcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter_lc.idcard.db.Record;
import com.hunter_lc.idcard.db.User;
import com.hunter_lc.idcard.farkas.tdk.app.MyApp;

import org.litepal.crud.DataSupport;

import java.util.List;

public class HistoryViewPagerAdapter extends RecyclerView.Adapter<HistoryViewPagerAdapter.mViewHolder>{

    Context context;
    List<Record> newList;
    public HistoryViewPagerAdapter(List<Record> newList) {
        this.newList = newList;
    }

    static class mViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView uIdView,upLoadTimeView;

        public mViewHolder(View itemView) {
            super(itemView);
            //拿到所有控件
            cardView= itemView.findViewById(R.id.card_view);
            uIdView= itemView.findViewById(R.id.user_id_view);
            upLoadTimeView=itemView.findViewById(R.id.send_time_view);

        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null)
            context = parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.content_cardview,parent,false);//加载item_cardView布局
        final mViewHolder holder=new mViewHolder(v);//加入内部类
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Record recordInfo = newList.get(position);
                Intent intent = new Intent(context,IdCardDetailActivity.class);
                intent.putExtra(IdCardDetailActivity.ROOM_NAME,recordInfo.getKey());
                intent.putExtra(IdCardDetailActivity.ROOM_IMAGE_ID,"000");
                context.startActivity(intent);
            }
        });
        Log.i("suc","布局载入成功");
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        int i=position;
        SharedPreferences sharedPreferences = MyApp.getInstance().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        List<User> users1 = DataSupport.select("*")
                .where("id = ?",String.valueOf(newList.get(i).getUserId()))
                .find(User.class);
        List<User> users2 = DataSupport.select("*")
                .where("account = ?",newList.get(i).getReceiveUserAccount())
                .find(User.class);
        String text = null;
        if(sharedPreferences.getString("account",null).equals(newList.get(i).getReceiveUserAccount())){
            text = "接收来自"+users1.get(0).getName()+"的消息";
        }else{
            text = "发送给"+users2.get(0).getName()+"的消息";
        }
        holder.uIdView.setText(text);
        holder.upLoadTimeView.setText(newList.get(i).getUploadTime());

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

}

