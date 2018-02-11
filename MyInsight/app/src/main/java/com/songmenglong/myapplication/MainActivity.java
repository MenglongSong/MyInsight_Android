package com.songmenglong.myapplication;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //用于添加每一个选项卡的id
    private String[] tags = {"A_tag", "B_tag", "C_tag", "D_tag"};
    //所添加选项卡的文本信息
    private String[] titles = {"新闻", "图片", "视频", "收藏"};
    //所添加选项卡的图片信息
    private int[] images = {R.drawable.tihu, R.drawable.tihu, R.drawable.tihu, R.drawable.tihu};
    //用于跳转至不同的Activity
    private Intent[] intents = new Intent[4];

    //ListView listView;  //声明一个ListView对象
    //private List<Info> mlistInfo = new ArrayList<Info>();  //声明一个list，动态存储要显示的信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listView = (ListView)this.findViewById(R.id.listView);    //将listView与布局对象关联
//
//        setInfo();  //给信息赋值函数，用来测试
//
//        Log.i("滚滚长江东逝水", mlistInfo.toString());
//
//        listView.setAdapter(new ListViewAdapter(mlistInfo));
//
//        //处理Item的点击事件
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Info getObject = mlistInfo.get(position);   //通过position获取所点击的对象
//                int infoId = getObject.getId(); //获取信息id
//                String infoTitle = getObject.getTitle();    //获取信息标题
//                String infoDetails = getObject.getDetails();    //获取信息详情
//
//                //Toast显示测试
//                Toast.makeText(MainActivity.this, "信息ID:"+infoId,Toast.LENGTH_SHORT).show();
//
//                // 进入到下一页面
//                // 给bnt1添加点击响应事件
//                Intent intent =new Intent(MainActivity.this, PlaySound.class);
//                //启动
//                startActivity(intent);
//            }
//        });
//
//        //长按菜单显示
//        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenu.ContextMenuInfo info) {
//                conMenu.setHeaderTitle("菜单");
//                conMenu.add(0, 0, 0, "条目一");
//                conMenu.add(0, 1, 1, "条目二");
//                conMenu.add(0, 2, 2, "条目三");
//            }
//        });




        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);

        //tabWidget.setDividerDrawable(null); //设置tabWeight没有竖线分割
        tabWidget.setBackgroundColor(Color.WHITE);
        //初始化activity管理者
        LocalActivityManager manager = new LocalActivityManager(MainActivity.this, false);
        //通过管理者保存当前页面状态
        manager.dispatchCreate(savedInstanceState);
        //将管理者类对象添加至TabHost
        tabHost.setup(manager);

        init_intent();

        for (int i = 0; i < intents.length; i++) {
            //加载底部导航栏布局
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.tab, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_item);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            textView.setText(titles[i]);
            imageView.setImageResource(images[i]);
            //创建选项卡
            TabHost.TabSpec spec = tabHost.newTabSpec(tags[i]);
            spec.setIndicator(view);
            //设置每个页面的内容
            spec.setContent(intents[i]);
            //将创建的选项卡添加至tabHost上
            tabHost.addTab(spec);
        }

    }


    //每个页面放置的Activity
    public void init_intent() {
        intents[0] = new Intent(this, NewsActivity.class);
        intents[1] = new Intent(this, PhotoActivity.class);
        intents[2] = new Intent(this, VideoActivity.class);
        intents[3] = new Intent(this, CollectionActivity.class);
    }

    // 长按菜单处理函数
    public boolean onContextItemSelected(MenuItem aItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
        switch (aItem.getItemId()) {
            case 0:
                Toast.makeText(MainActivity.this, "你点击了条目一",Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(MainActivity.this, "你点击了条目二",Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                Toast.makeText(MainActivity.this, "你点击了条目三",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    // 适配器
    public class ListViewAdapter extends BaseAdapter {
        View[] itemViews;

        public ListViewAdapter(List<Info> mlistInfo) {
            // TODO Auto-generated constructor stub
            itemViews = new View[mlistInfo.size()];
            for(int i=0;i<mlistInfo.size();i++){
                Info getInfo=(Info) mlistInfo.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(
                        getInfo.getTitle(), getInfo.getDetails(),getInfo.getAvatar()
                );
            }
        }

        public int getCount() {
            return itemViews.length;
        }

        public View getItem(int position) {
            return itemViews[position];
        }

        public long getItemId(int position) {
            return position;
        }

        //绘制Item的函数
        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.item, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView) itemView.findViewById(R.id.title);
            title.setText(strTitle);    //填入相应的值
            //TextView text = (TextView) itemView.findViewById(R.id.info);
            //text.setText(strText);
            ImageView image = (ImageView) itemView.findViewById(R.id.img);
            image.setImageResource(resId);

            return itemView;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                return itemViews[position];
            return convertView;
        }
    }

//    public void setInfo() {
//        //mlistInfo.clear();
//        int i = 0;
//        while(i < 20) {
//            Info information = new Info();
//            information.setId(1000+i);
//            information.setTitle("标题"+i);
//            information.setDetails("详细信息"+i);
//            information.setAvatar(R.drawable.tihu); //图片没有
//
//            // 将新的info对象加入到信息列表中
//            mlistInfo.add(information);
//            i++;
//        }
//    }

}


/*
* Android的demo
*
* 百度地图
*
* 谷歌地图
*
* OpenCV：人脸识别等
*
* FFmpeg: 视频播放
*
*
* */

