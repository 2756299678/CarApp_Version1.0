package com.example.baohui.carapp_version10.Fragement;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;

import com.example.baohui.carapp_version10.R;
import com.example.baohui.carapp_version10.view.ColorArcProgressBar;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

//////////////语音播报
import java.util.Locale;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View.OnClickListener;
////////////////////////////


/////定时更新界面////////////////////////////////////////////
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
//////////////////////////////////////////////////////////////
/**
 * Created by zh on 18-5-1.
 * 第二个页面
 */
public class SecondFragment extends Fragment implements OnInitListener {
    //private ColorArcProgressBar arcProgressBar;
    private TextView textView10;//建议
    private TextView textView11;//报告
    private TextView time;
    //private ProgressBar progressBar;
    private ColorArcProgressBar color_arc_progressbar;
    //private ProgressBar color_arc_progressbar;

    //TTS语音播报
    //定义控件
    private ImageView speechButton;
    private TextView speechText;
    private TextToSpeech tts;
    private TextView textView14;


    int judge=0;
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
                    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    //获取当前时间
                    String   strTime   =  "  测量时间: "+ formatter.format(curDate);
                    //System.out.print(strTime);
                    textView14=(TextView) getActivity().findViewById(R.id.text_view_time2);
                    textView14.setText(strTime);
                    //心率监控图
                    Random rand=new Random();
                    //模拟正常监控
                    int i = rand.nextInt(30)+70;
                    System.out.print(i);
                    //int value = (int) (Math.random() * 100) + 3;
                    color_arc_progressbar=(ColorArcProgressBar) getActivity().findViewById(R.id.color_arc_progressbar);
                    color_arc_progressbar.setNeedProgress(true);
                    color_arc_progressbar.setCurrentValues(i);

                    judge++;
                    if(judge==3)
                    {
                        System.out.println("进行语音播报");
                        tts.speak(speechText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

                    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg2, container, false);
        //定时更新///////////////////////////////////////////////////////////////////////////////////////////////////////
        int n=(int)(Math.random()*5);
        String choose=n+"";
        timerTask(); // 定时执行
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束
       // Intent intent = null;
        //intent = getActivity().getIntent();//Fragment中没有getIntent方法,需要通过Activity来。
        //生成随机数
        Random rand = new Random();
        int i = rand.nextInt(40)+60;
        //////////////////////////////接收第一个页面的心率信息

        ///////////////////////////////////////////////////完成

        //加载心率表盘数据
        int intXinLv=i;
        color_arc_progressbar=(ColorArcProgressBar) view.findViewById(R.id.color_arc_progressbar);
        color_arc_progressbar.setNeedProgress(true);
        color_arc_progressbar.setCurrentValues(intXinLv);

        textView10=(TextView) view.findViewById(R.id.textView10);
        if(i<100)
        {
            textView10.setText("您的心率很正常，请继续保持良好的生活习惯");
        }
        else if(i>100)
        {
            textView10.setText("您的心率发生异常，请稳定情绪靠边停车");
        }
        /**
         * 数据接口信息：
         * 模拟心率值
         * 模拟驾驶时间
         */
        textView11=(TextView) view.findViewById(R.id.textView11);

        String []str=new String [5];
        //当模拟的平均心率78~90
        str[0]="健康报告分析：\n\t\t\t\t您的身体处于健康状态，心率血压正常，请保持您的饮食运动，保持良好的生活习惯\n" +
                "\t\t\t\t最近一周您的心率水平很稳定，血压略微升高，请注意您的饮食习惯，多吃一些蔬菜水果，才能有更加健康的身体哦\n" +
                "\t\t\t\t您的身体情况未发生异常，身体健康指数已超过90%以上人群，请注意保持";
        //当模拟的平均心率90~100
        str[1]="健康状况分析：\n\t\t\t\t您的身体处于健康状况，心率血压略高，易产生轻度疲劳，请保持您的饮食运动，保持良好的生活习惯，保证正常的睡眠时间\n"+
                "\t\t\t\t最近一周您的心率水平整体较稳定，血压略微升高，请注意您的饮食习惯，多吃一些蔬菜水果，保证充足的睡眠，才能有更加健康的身体\n"+
                "\t\t\t\t您的身体情况未发生明显异常，身体健康指数已超过85%以上人群，请注意保持";
        //当模拟的平均心率60~70
        str[2]="健康状况分析：\n\t\t\t\t您的身体处于健康状况，心率血压略低，易受压力影响，请加强体育锻炼，保持良好的生活习惯，保证正常的睡眠时间\n"+
                "\t\t\t\t最近一周您的心率水平整体较稳定，血压略微降低，请注意您的饮食习惯，多吃一些蔬菜水果谷物杂粮，注意保证足够的运动时间和充足的睡眠\n"+
                "\t\t\t\t您的身体情况未发生明显异常，身体健康指数已超过85%以上人群，请注意保持";
        //当模拟的平均心率60~78
        str[3]="健康状况分析：\n\t\t\t\t您的身体处于亚健康状态，心率血压明显异常，请及时到医院就诊，避免病情恶化，此外要保证良好的生活习惯、饮食习惯，多参加体育锻炼，避免不健康饮食。近期内尽可能频繁地对身体各项指标进行测量，如发现异常，尽快诊治。";

        //当驾驶时间》2h时
        str[4]="健康状况分析：\n\t\t\t\t您在进行长途行车，要避免疲劳驾驶，建议在行车两小时左右将车停至安全地带或就近服务区，到车外活动十几分钟，呼吸新鲜空气，变换一下体位姿势，活动一下关节，使肌肉、神经、感官等都得到短暂的休息、松驰。";

        textView11.setText(str[n]);

        //获取系统的日期
        //测量时间
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        //获取当前时间
        String   strTime   =   formatter.format(curDate);

        String timestr ="  测量时间: "+strTime;
        time=(TextView) view.findViewById(R.id.text_view_time2);
        time.setText(timestr);

        //////////////语音播报////////////////////
        //初始化TTS
        tts = new TextToSpeech(this.getActivity(), this);
        //获取控件
        speechText = (TextView) view.findViewById(R.id.textView11);


        speechButton = (ImageView) view.findViewById(R.id.imageView5);
        //为button添加监听
        speechButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO Auto-generated method stub
                if (tts.isSpeaking())
                {
                    tts.stop();
                }
                else
                {
                    tts.speak(speechText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

                }
            }
        });
        //////////////////////////////////////
        return view;
    }

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

    ////////////////////////////////////////////////////////定时更新/////////////////////////////////////////////////////////////////
    public void timerTask() {
        //创建定时线程执行更新任务
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask-->Id is "
                        + Thread.currentThread().getId());// TimerTask在它自己的线程中
                mHandler.sendEmptyMessage(1);// 向Handler发送消息
            }
        }, 0, 1000);// 定时任务
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束

    ////////////////////////////////////////////定时更新，退出时撤销更新///////////////////////////////////////////////////
    @Override
    public void onStop() {
        super.onStop();
        //mTimer.cancel();// 程序退出时cancel timer
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////结束

}