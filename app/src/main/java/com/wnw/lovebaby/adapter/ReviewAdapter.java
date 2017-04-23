package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.util.TimeConvert;

import java.util.List;

/**
 * Created by wnw on 2017/4/2.
 */

public class ReviewAdapter extends BaseAdapter {
    private Context context;
    private List<Pr> prList;

    public ReviewAdapter(Context context, List<Pr> prs){
        this.context = context;
        this.prList = prs;
    }

    public void setPrList(List<Pr> prs){
        this.prList = prs;
    }

    @Override
    public int getCount() {
        return prList.size();
    }

    @Override
    public Object getItem(int i) {
        return prList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PrHolder prHolder = null;
        if(view == null){
            prHolder = new PrHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_product_review,null);
            prHolder.nickNameTv = (TextView)view.findViewById(R.id.tv_nickname);
            prHolder.timeTv = (TextView)view.findViewById(R.id.tv_time);
            prHolder.productScoreImg = (RatingBar)view.findViewById(R.id.product_score);
            prHolder.serviceScoreImg = (RatingBar)view.findViewById(R.id.service_score);
            prHolder.logisticsScoreImg = (RatingBar)view.findViewById(R.id.logistics_score);
            prHolder.deatailReviewTv = (TextView)view.findViewById(R.id.tv_detail_review);
            view.setTag(prHolder);
        }else {
            prHolder = (PrHolder)view.getTag();
        }
        Pr pr = prList.get(i);
        prHolder.nickNameTv.setText(pr.getUserNickName());
        prHolder.timeTv.setText(TimeConvert.getTime(pr.getTime()));
        prHolder.productScoreImg.setNumStars(pr.getProductScore());
        prHolder.serviceScoreImg.setNumStars(pr.getServiceScore());
        prHolder.logisticsScoreImg.setNumStars(pr.getLogisticsScore());
        prHolder.deatailReviewTv.setText(pr.getEvaluation());
        return view;
    }

    private class PrHolder{
        TextView nickNameTv;
        TextView timeTv;
        RatingBar productScoreImg;
        RatingBar serviceScoreImg;
        RatingBar logisticsScoreImg;
        TextView deatailReviewTv;
    }
}
