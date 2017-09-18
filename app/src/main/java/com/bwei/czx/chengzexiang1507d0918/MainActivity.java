package com.bwei.czx.chengzexiang1507d0918;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private XListView xListView;
    private boolean flag;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        xListView = (XListView) findViewById(R.id.xlistview);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        ForData();

    }
    public void ForData(){
        getData("http://ic.snssdk.com/2/article/v25/stream/?category=news_car&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000");
    }
    //请求数据
    public void getData(String url){
        new AsyncTask<String,Void,String>(){
            @Override
            protected void onPostExecute(String s) {
                if(s == null){
                    return;
                }
                Gson gson = new Gson();
                MyDataBean myDataBean = gson.fromJson(s, MyDataBean.class);
                final List<MyDataBean.DataBean> beanList = myDataBean.getData();
                //适配
                if(adapter == null){
                    adapter = new MyAdapter(MainActivity.this,beanList);
                    xListView.setAdapter(adapter);
                }else{
                    adapter.loadMore(beanList,flag);
                }
                xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                        intent.putExtra("url",beanList.get(position).getUrl());
                        startActivity(intent);
                    }
                });
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url1 = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream is = connection.getInputStream();
                        return StreamTools.getstr(is);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);
    }
    //上拉加载，下拉刷新
    @Override
    public void onRefresh() {
        ForData();
        flag = true;
        xListView.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        ForData();
        flag = false;
        xListView.stopLoadMore();
    }
}
