package com.example.baohui.carapp_version10.Fragement;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.Manifest;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;


import com.example.baohui.carapp_version10.Dao.PhoneDao;
import com.example.baohui.carapp_version10.R;
import com.example.baohui.carapp_version10.view.CirclePgBar;
import com.example.baohui.carapp_version10.view.ColorArcProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Bh on 18-5-1.
 * 首页
 */
public class FirstFragment extends Fragment implements TextToSpeech.OnInitListener {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private Context mContext;
    //创建震动服务对象
    private Vibrator mVibrator;

    private  TextView   text_view_time;//测量时间
    private CirclePgBar  circlePgBar;//引用view
    private TextView  tips;//建议
    private TextView  xueya;//血压
    private TextView  tiwen;//体温
    private Button jingbao;//警报按钮

    private ColorArcProgressBar ProgressBar;//轮盘图形

    //记录报警
    private int  status=0;//记录时间
    private int  judge=0;

    //TTS语音播报
    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);

        //定时更新///////////////////////////////////////////////////////////////////////////////////////////////////////
        timerTask(); // 定时执行
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        // String intXinLv="85 ";
        //circlePgBar=(CirclePgBar) view.findViewById(R.id.circleBar);
        //circlePgBar.setCurrentValues(intXinLv);
        //生成随机数
        Random rand = new Random();
        int i = rand.nextInt(40)+60;

        int intXin=i;
        ProgressBar=(ColorArcProgressBar) view.findViewById(R.id.color_arc_progressbar1);
        ProgressBar.setNeedProgress(true);
        ProgressBar.setCurrentValues(intXin);

        //初始化TTS
        tts = new TextToSpeech(this.getActivity(), this);

        //测量时间
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        //获取当前时间
        String   strTime   =  "  测量时间: "+ formatter.format(curDate);

        String time =strTime;
        text_view_time=(TextView) view.findViewById(R.id.text_view_time1);
        text_view_time.setText(time);

        tips=(TextView) view.findViewById(R.id.text_view_tip1);
        tips.setText("您的心率很正常，请继续保持良好的生活习惯");

        xueya=(TextView) view.findViewById(R.id.text_view_xueya1);
        xueya.setText("    扩张压：105mmhg    收缩压：88mmhg");

        tiwen=(TextView) view.findViewById(R.id.text_view_tiwen1);
        tiwen.setText("    体温：37.0℃");

        /**
        //拨打电话
        jingbao = (Button) view.findViewById(R.id.fg1_button_baojing);
        jingbao.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("检测到点击");
                //手机震动
                //获取手机震动服务
                mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {100,1000,100,1800}; // 停止 开启 停止 开启
                //第二个参数表示使用pattern第几个参数作为震动时间重复震动，如果是-1就震动一次
                mVibrator.vibrate(pattern,-1);

                //打电话
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                            Manifest.permission.CALL_PHONE)) {
                        // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(mContext, "请授权！", Toast.LENGTH_LONG).show();

                    }else{
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions((Activity) mContext,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                }else {
                    // 已经获得授权，可以打电话
                    callContact();
                }

            }
        });
         */
        //firstText = (TextView) view.findViewById(R.id.textViewheart);
        //firstText.setTextColor(dark);
        return view;
    }

    private void callContact() {
        String number = "";
        PhoneDao phoneDao = new PhoneDao();
        if (phoneDao.checkUser_exit(getActivity())) {
            //从数据库中读取数据
            number=phoneDao.find_phone(getActivity());

        } else
        {

            number = "10086";
        }
        Uri contactUri  = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor c = getActivity().getContentResolver().query(contactUri, null, null, null, null);
        try{
            if(c.getCount() == 0){
                return;
            }
            c.moveToFirst();
            String contactId = number;
            Intent callContact = new Intent(Intent.ACTION_CALL);
            callContact.setData(Uri.parse("tel:" + contactId));
            startActivity(callContact);

        }finally {
            c.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    callContact();
                }else{
                    Toast.makeText(mContext,"You denied the permission", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //定时更新(线程)///////////////////////////////////////////////////////////////////////////////////////////////////
    /**（把获取当前时间和心率图挪到此块）
     * 消息处理器的应用
     */
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:

                    //测量时间
                    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    //获取当前时间
                    String   strTime   =  "  测量时间: "+ formatter.format(curDate);

                    String time =strTime;
                    text_view_time=(TextView) getActivity().findViewById(R.id.text_view_time1);
                    text_view_time.setText(time);

                    //心率监控图
                    Random rand=new Random();
                    int i=0 ;
                    //心率正常
                    if(status<30||status==30)
                    {
                        i= rand.nextInt(30)+70;
                    }
                    //发生异常
                    else if(status>30&& status<80)
                    {
                        i=rand.nextInt(20)+100;
                    }
                    //60秒后模拟正常
                    else
                    {
                        //status=0;
                        i= rand.nextInt(30)+70;
                    }
                    System.out.print(i);

                    //////////////////////fragement之间的数据传输

                    //////////////////////////////////完成

                    //int value = (int) (Math.random() * 100) + 3;
                    ProgressBar=(ColorArcProgressBar) getActivity().findViewById(R.id.color_arc_progressbar1);
                    ProgressBar.setNeedProgress(true);
                    ProgressBar.setCurrentValues(i);
                    ////当持续监测10s的心率异常  进行报警///////////////////////////
                    //当发生异常
                    if(i>100)
                    {
                        //记录此时时间
                        judge++;
                        if(judge==10)
                        {
                            //进行收集震动并语音警报
                            //手机震动
                            //获取手机震动服务
                            mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            long [] pattern = {100,1000,100,1800}; // 停止 开启 停止 开启
                            //第二个参数表示使用pattern第几个参数作为震动时间重复震动，如果是-1就震动一次
                            mVibrator.vibrate(pattern,-1);
                            //语音进行警报
                            String speech="检测到您的身体状况异常，请稳定您的情绪，小心驾驶或靠边停车，即将帮您联系紧急联系人";
                            if (tts.isSpeaking())
                            {
                                tts.stop();
                            }
                            else
                            {
                                tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);

                            }
                        }
                        if(judge==30)
                        {
                            //打电话
                            if (ContextCompat.checkSelfPermission(mContext,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                                // 没有获得授权，申请授权
                                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                                        Manifest.permission.CALL_PHONE)) {
                                    // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                                    // 弹窗需要解释为何需要该权限，再次请求授权
                                    Toast.makeText(mContext, "请授权！", Toast.LENGTH_LONG).show();

                                }else{
                                    // 不需要解释为何需要该权限，直接请求授权
                                    ActivityCompat.requestPermissions((Activity) mContext,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                }
                            }else {
                                // 已经获得授权，可以打电话
                                callContact();
                            }
                        }
                    }

                    /////////结束报警/////////////////////////////////////////////
                    tips=(TextView) getActivity().findViewById(R.id.text_view_tip1);
                    if(i<100)
                    {
                        tips.setText("您的心率很正常，请继续保持良好的生活习惯");
                    }
                   else if(i>100)
                    {
                        tips.setText("您的心率发生异常，请稳定情绪靠边停车");
                    }

                    int kuozhang = rand.nextInt(20)+100;
                    int shousuo =rand.nextInt(20)+80;
                    int sudu=rand.nextInt(60)+80;

                    xueya=(TextView) getActivity().findViewById(R.id.text_view_xueya1);
                    xueya.setText("    扩张压："+kuozhang+"mmhg    收缩压："+shousuo+"mmhg");

                    tiwen=(TextView) getActivity().findViewById(R.id.text_view_tiwen1);
                    tiwen.setText("    速度："+sudu+"km/h");

                    System.out.println("Handler --> ID IS "
                            + Thread.currentThread().getId());// Handler处于UI线程中,更新界面的操作在此处执行
                    break;
                case 2:
                    mTimer.cancel();//
                    mTimer=null;
            }
            super.handleMessage(msg);
        }
    };
    public Timer mTimer = new Timer();// 定时器

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束
    ////////////////////////////////////////////////////////定时更新/////////////////////////////////////////////////////////////////
    public void timerTask() {
        //创建定时线程执行更新任务
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask-->Id is "
                        + Thread.currentThread().getId());// TimerTask在它自己的线程中
                mHandler.sendEmptyMessage(1);// 向Handler发送消息
                status++;
            }
        }, 0, 1000);// 定时任务
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束

    ////////////////////////////////////////////定时更新，退出时撤销更新///////////////////////////////////////////////////
    @Override
    public void onStop() {
        super.onStop();
        System.out.println("页面停止");
        //mTimer.cancel();// 程序退出时cancel timer
        mTimer.cancel();// 程序退出时cancel timer
    }
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("页面暂停");
        mTimer.cancel();// 程序退出时cancel timer
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("页面重启");
        // mTimer.cancel();// 程序退出时cancel timer
        timerTask(); // 定时执行
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束
    //////////////////////////////////////////TTS语音播报初始化////////////////////////////////////////
    @Override
    public void onInit(int status) {
        // 判断是否转化成功
        if (status == TextToSpeech.SUCCESS){
            //默认设定语言为中文，原生的android貌似不支持中文。
            int result = tts.setLanguage(Locale.CHINESE);
            /*if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(XinLvActivity.this, R.string.notAvailable, Toast.LENGTH_SHORT).show();
            }else{
                //不支持中文就将语言设置为英文
                tts.setLanguage(Locale.US);
            }*/
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束

}