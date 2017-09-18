package com.bwei.czx.chengzexiang1507d0918;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**适配器
 * Created by czx on 2017/9/18.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<MyDataBean.DataBean> list;

    public MyAdapter(Context context, List<MyDataBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void loadMore(List<MyDataBean.DataBean> beanList,boolean flag){
        if(flag){
            list.addAll(0,beanList);
        }else{
            list.addAll(beanList);
        }
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.item_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.source = (TextView) convertView.findViewById(R.id.item_source);
            viewHolder.remove = (ImageView) convertView.findViewById(R.id.item_remove);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getMiddle_image().getUrl(),viewHolder.img,StreamTools.getOptions());
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.source.setText(list.get(position).getSource());
        viewHolder.remove.setImageResource(R.mipmap.abc_ic_clear_search_api_holo_light);
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View vPopWindow=inflater.inflate(R.layout.mypopwindow_layout, null);
                final PopupWindow popWindow = new PopupWindow(vPopWindow,300,300,true);
                TextView name = (TextView) vPopWindow.findViewById(R.id.popname);
                ImageView guan = (ImageView) vPopWindow.findViewById(R.id.popguan);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(list.get(position));
                        notifyDataSetChanged();
                        popWindow.dismiss();
                    }
                });
                guan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                    }
                });
                popWindow.showAtLocation(parent,Gravity.CENTER,0,0);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView title;
        TextView source;
        ImageView img;
        ImageView remove;
    }
}
