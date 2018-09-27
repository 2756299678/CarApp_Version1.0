package com.example.baohui.carapp_version10.Fragement;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baohui.carapp_version10.Activity.AboutUsActivity;
import com.example.baohui.carapp_version10.Dao.PhoneDao;
import com.example.baohui.carapp_version10.Dao.UserDao;
import com.example.baohui.carapp_version10.Activity.LoginActivity;
import com.example.baohui.carapp_version10.Activity.PersonInformationActivity;
import com.example.baohui.carapp_version10.R;
import com.example.baohui.carapp_version10.Bean.User;
import com.example.baohui.carapp_version10.Bean.UserManager;

import java.lang.reflect.Field;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class FourthFragment extends Fragment {

    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg4, container, false);
        Bmob.initialize(this.getActivity(), "f9987278ef15f8430aa510ca5ba93f87");
        UserDao userDao= new UserDao();
        TextView username=(TextView) view.findViewById(R.id.set_text_view_my_phone);
        TextView nickname=(TextView) view.findViewById(R.id.set_text_view_my_name);
        username.setText(userDao.find_phone(getActivity()));
        if(userDao.find_nickname(getActivity())!=null)
        {
            nickname.setText(userDao.find_nickname(getActivity()));
        }

        //在fragment视图中的按钮
        Button btn = (Button)view.findViewById(R.id.set_button_exit);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //在这里使用getActivity
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout person=(LinearLayout) view.findViewById(R.id.set_liner_person);
        person.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //在这里使用getActivity
                BmobUser u = new User();
                u = UserManager.getInstance().getUserInfo(getActivity());
                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject(u.getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User u, BmobException e) {
                        System.out.print(u.getUsername());

                        //Bundle data = new Bundle();
                        //data.putBoolean("sex", u.isSex());
                        //data.putString("name", u.getPickname());
                        //data.putDouble("heavy", u.getHeavy());

                        Intent intent = new Intent(getActivity(),PersonInformationActivity.class);
                        //intent.putExtra("sex", u.isSex());
                        //intent.putExtra("name", u.getPickname());
                        //intent.putExtra("heavy", u.getHeavy());
                        //System.out.println(u.getPickname());
                        //System.out.print(data.getString("name"));
                        startActivity(intent);
                    }
                });
            }
        });
        ImageView img=(ImageView) view.findViewById(R.id.set_image_touxiang);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            img.setImageDrawable(drawable);
        }else{
            /**
             *  如果SD里面没有则需要从本地取头像，取回来的头像再保存在SD中
             */
            //img.setImageResource(R.drawable.icon);
        }

        //联系人设置
        TextView my_message=(TextView) view.findViewById(R.id.set_text_view_my_message);

        my_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        //关于我们
        LinearLayout aboutus=(LinearLayout) view.findViewById(R.id.set_liner_function);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AboutUsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void showInputDialog() {

        final EditText editText = new EditText(this.getActivity());
        editText.setHint("手机号");
        PhoneDao phoneDao1=new PhoneDao();
        if(phoneDao1.checkUser_exit(getActivity()))
        {
            String phone_number=phoneDao1.find_phone(getActivity());
            editText.setText(phone_number);
        }
        editText.setGravity(Gravity.CENTER);
        AlertDialog dialog = new AlertDialog.Builder(this.getActivity())
                .setTitle("输入手机号").setView(editText)
                .setIcon(R.drawable.icon)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        String phone=editText.getText().toString();
                        PhoneDao phoneDao=new PhoneDao();
                        if(phoneDao.checkUser_exit(getActivity()))
                        {
                            phoneDao.updatephone(phone,getActivity());
                        }
                        else
                        {
                            phoneDao.addphone(phone,getActivity());
                        }

                    }
                })
                .create();
        dialog.show();

// 在dialog执行show之后才能来设置
        TextView tvMsg = (TextView) dialog.findViewById(android.R.id.message);
        tvMsg.setTextSize(16);
        tvMsg.setTextColor(Color.parseColor("#4E4E4E"));

        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextSize(16);
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8C8C8C"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DA6DD"));

        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object alertController = mAlert.get(dialog);

            Field mTitleView = alertController.getClass().getDeclaredField("mTitleView");
            mTitleView.setAccessible(true);

            TextView tvTitle = (TextView) mTitleView.get(alertController);
            if (null != tvTitle) {
                tvTitle.setTextSize(16);
                tvTitle.setTextColor(Color.parseColor("#000000"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}