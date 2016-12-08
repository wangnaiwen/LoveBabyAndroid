package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.bean.ArticleCoverItem;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by wnw on 2016/12/8.
 */

public class CollegeLvAdapter extends BaseAdapter{
    private Context context;
    private List<ArticleCoverItem>  articleCoverItemList;

    public CollegeLvAdapter(Context context, List<ArticleCoverItem>  articleCoverItems){
        this.context = context;
        this.articleCoverItemList = articleCoverItems;
    }

    public void setArticleCoverItemList(List<ArticleCoverItem>  articleCoverItems){
        this.articleCoverItemList = articleCoverItems;
    }

    @Override
    public int getCount() {
        return articleCoverItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return articleCoverItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LvHolder lvHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.college_lv_item, null);
            lvHolder = new LvHolder();
            lvHolder.imageView = (ImageView)view.findViewById(R.id.article_cover_icon);
            lvHolder.titleTv = (TextView)view.findViewById(R.id.article_title);
            lvHolder.timeTv = (TextView) view.findViewById(R.id.article_publish_time);
            lvHolder.readNumTv = (TextView)view.findViewById(R.id.article_read_num);
            lvHolder.praiseNumTv = (TextView)view.findViewById(R.id.article_praise_num);
            view.setTag(lvHolder);
        }else{
            lvHolder = (LvHolder)view.getTag();
        }
        ArticleCoverItem articleCoverItem = articleCoverItemList.get(i);
        lvHolder.imageView.setBackgroundResource(articleCoverItem.getImage());
        lvHolder.titleTv.setText(articleCoverItem.getTitle());
        lvHolder.timeTv.setText(articleCoverItem.getPublishTime());
        lvHolder.readNumTv.setText(articleCoverItem.getReadNum()+"");
        lvHolder.praiseNumTv.setText(articleCoverItem.getPraiseNum()+"");
        return view;
    }

    private class  LvHolder{
        ImageView imageView;
        TextView titleTv;
        TextView timeTv;
        TextView readNumTv;
        TextView praiseNumTv;
    }
}
