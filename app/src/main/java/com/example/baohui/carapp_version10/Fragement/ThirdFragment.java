package com.example.baohui.carapp_version10.Fragement;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baohui.carapp_version10.R;
import com.example.baohui.carapp_version10.view.SixangleView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class ThirdFragment extends Fragment {

    private LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sixangle, container, false);
        //数据接口信息
        /**
         * 此数据的接口包括，
         * 统计数据库中心率所有该用户的正常数据条数count_xinlvpresent，统计数据库中心率所有异常条数count_xinlvover
         * 统计数据库中血压所有该用户的正常数据条数count_xueyapresent，统计数据库中心率所有异常条数cout_xueyaover
         * 统计数据库中速度所有该用户的正常数据条数sudu_present，统计数据库中血压所有异常条数cout_xueyaover
         * 统计数据库中驾驶时间所有正常条数cout_xueyaover，统计数据库中驾驶时间所有异常条数
         */
        //分值给出的计算
        int count_xinlvpresent=100;
        int count_xinlvover=10;
        int xinlv_count=10*(count_xinlvpresent/(count_xinlvover+count_xinlvpresent));

        int count_xueyapresent=90;
        int cout_xueyaover=10;
        int xueya_count=10*(count_xueyapresent/(cout_xueyaover+count_xueyapresent));

        int sudu_present=90;
        int sudu_over=10;
        int sudu_count=10*(sudu_present/(sudu_over+sudu_present));

        int time_present=100;
        int time_over=10;
        int time_count=10*(sudu_present/(sudu_over+sudu_present));

        //记录计算结构
        String xinlv="8.5";
        String jiashixingwei="9.5";
        String pilao="10";
        String chesu="9";
        String shentijiankang="9";
        String xueya="8";

        int n=(int)(Math.random()*3);
        if(n==0)
        {
            xinlv="8.5";
            jiashixingwei="9.5";
            pilao="10";
            chesu="9";
            shentijiankang="9";
            xueya="8";
        }
        else if(n==1)
        {
            xinlv="8.5";
            jiashixingwei="7.5";
            pilao="8";
            chesu="7";
            shentijiankang="9";
            xueya="8";
        }
        else if(n==2)
        {
            xinlv="7.6";
            jiashixingwei="8";
            pilao="9";
            chesu="7";
            shentijiankang="7.8";
            xueya="8";
        }

        //获取内容插件
        SixangleView sixangleView =(SixangleView) view.findViewById(R.id.sixangleview);
        //驾驶行为指数
        TextView drive=(TextView)view.findViewById(R.id.sixanggle_textview_jiashixingwei);
        //车速稳定指数
        TextView speed=(TextView)view.findViewById(R.id.sixangle_textview_pilao);
        //身体健康指数
        TextView health=(TextView) view.findViewById(R.id.sixanggle_textview_shentijiankang);
        //心率健康指数
        TextView heart=(TextView) view.findViewById(R.id.sixanggle_textview_xinlv);
        //驾驶员评估
        TextView pinggumain=(TextView) view.findViewById(R.id.sixanggle_pinggu_contentmain);
        //评估第一句
        TextView content1=(TextView) view.findViewById(R.id.sixanggle_pinggu_content1);
        TextView content2=(TextView) view.findViewById(R.id.sixanggle_pinggu_content2);
        TextView content3=(TextView) view.findViewById(R.id.sixanggle_pinggu_content3);

        //六边形指数报告策略
        //对六边形插件进行赋值
        sixangleView.setData(xinlv,jiashixingwei,pilao,chesu,shentijiankang,xueya);
        //对评分报告进行赋值
        drive.setText("    驾驶行为指数： "+jiashixingwei);
        speed.setText("    疲劳健康指数："+pilao+"           车速稳定指数："+chesu);
        health.setText("    身体健康指数："+shentijiankang);
        heart.setText("    心率健康指数："+xinlv+"           血压健康指数："+xueya);
        //对驾驶员进行评估鉴定。k

        if(n==0)
        {
            //身体》8，车速》8    低中速正常行驶
            pinggumain.setText("    经系统鉴定，您属于[ 平稳驾驶员 ]：" );
            content1.setText("    操作动作稳定自如，行车中不急躁，不开快车");
            content2.setText("    驾驶员不易受外界的干扰，能较严格地执行交通规则");
            content3.setText("    这类驾驶员性子慢，能够很好的避免交通事故");
        }
        else if(n==1)
        {
            //身体》8，车速《8   高速正常行驶
            pinggumain.setText("    经系统鉴定，您属于[ 高速驾驶员 ]：" );
            content1.setText("    操作动作敏捷，反应较快，处理情况准确");
            content2.setText("    驾驶员不易受外界的干扰，能较严格地执行交通规则");
            content3.setText("    这类驾驶员性格沉稳，行车意识强");
        }
        else if(n==2)
        {
            //身体《8，车速《8    高速激动行驶
            pinggumain.setText("    经系统鉴定，您属于[ 胆汁驾驶员 ]：" );
            content1.setText("    操作动作干脆有力，处理情况果断，开车速度较快");
            content2.setText("    处理危险情况时不够沉着仔细，喜欢冒险尝试");
            content3.setText("    在车辆的恶性事故中，此类人比较多");
        }

        //身体《8.车速》8   低中速，身体状况异常//这种非正常


        return view;
    }


}