package com.example.baohui.carapp_version10.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.Button;

import com.example.baohui.carapp_version10.R;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class TimeCountUtil extends CountDownTimer {
    private Activity activity;
    private Button btn;//按钮

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    //一个是总的时间millisInFuture，一个是countDownInterval
    public TimeCountUtil(Activity activity, long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.activity = activity;
        this.btn = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
//        btn.setClickable(false); //设置不能点击
        btn.setEnabled(false);
        btn.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时的时间
        btn.setBackground(activity.getResources().getDrawable(R.drawable.button_selector));//不能点击的时候的按钮的背景色
        Spannable span = new SpannableString(btn.getText().toString()); //获取按钮的文字
        btn.setText(span);
    }

    @Override
    public void onFinish() {
        btn.setText("重新获取验证码");
//        btn.setClickable(true);//重新获得点击
        btn.setEnabled(true);
        btn.setBackground(activity.getResources().getDrawable(R.drawable.button_selector));//还原背景色
        btn.setText("重新获取验证码");
        btn.setTextColor(Color.WHITE);
    }
}