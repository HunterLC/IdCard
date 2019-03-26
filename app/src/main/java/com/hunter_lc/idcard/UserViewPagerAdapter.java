package com.hunter_lc.idcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter_lc.idcard.db.User;

import java.util.ArrayList;
import java.util.List;

public class UserViewPagerAdapter extends RecyclerView.Adapter<UserViewPagerAdapter.mViewHolder>{

    Context context;
    User user = new User();
    List<String> userList = new ArrayList<String>();
    List<String> label = new ArrayList<>();  //信息标签
    public UserViewPagerAdapter(User user,List<String> userList) {
        this.userList = userList;
        this.user = user;
        initLabelList();
    }

    public void initLabelList(){
        label.add("我的头像");
        label.add("我的编号");
        label.add("我的姓名");
        label.add("我的生日");
        label.add("我的昵称");
        label.add("我的性别");
    }
    static class mViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title,decription;
        ImageView faceimg,picture;

        public mViewHolder(View itemView) {
            super(itemView);
            //拿到所有控件
            cardView= itemView.findViewById(R.id.userinfo_card_view);
            title = itemView.findViewById(R.id.userinfo_title);
            decription = itemView.findViewById(R.id.userinfo_description);
            faceimg = itemView.findViewById(R.id.userinfo_faceimg);
            picture = itemView.findViewById(R.id.userinfo_picture);
        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_infoview,parent,false);//加载item_cardView布局
        mViewHolder holder=new mViewHolder(v);//加入内部类
        Log.i("suc","布局载入成功");
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        int i=position;
        holder.title.setText(label.get(i));
        if(label.get(i).equals("我的头像"))
           holder.faceimg.setImageBitmap(getPicFromBytes(user.getPersonalPhoto(),null));
        else
            holder.faceimg.setImageBitmap(null);
        if(!label.get(i).equals("我的头像"))
            holder.decription.setText(userList.get(i));
        else
            holder.decription.setText("");
        if(label.get(i).equals("我的编号")||label.get(i).equals("我的姓名")||label.get(i).equals("我的生日"))
            holder.picture.setImageBitmap(null);
        else
            ;

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**将字节数组转换为ImageView可调用的Bitmap对象
     * @param
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }
    /**  图片缩放
     * @param
     * @param bitmap 对象
     * @param w 要缩放的宽度
     * @param h 要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }


}
