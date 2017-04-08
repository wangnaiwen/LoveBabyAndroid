package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Pr;

import java.util.List;

/**
 * Created by wnw on 2017/4/2.
 */

public class ReviewAdapter extends BaseAdapter {
    private Context context;
    private List<Pr> prList;

    private int[] starImg = new int[]{
            R.drawable.star0,
            R.drawable.star1,
            R.drawable.star2,
            R.drawable.star3,
            R.drawable.star4,
            R.drawable.star5
    };

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
        PrHorder prHorder = null;
        if(view == null){
            prHorder = new PrHorder();
            view = LayoutInflater.from(context).inflate(R.layout.item_product_review,null);
            prHorder.nickNameTv = (TextView)view.findViewById(R.id.tv_nickname);
            prHorder.timeTv = (TextView)view.findViewById(R.id.tv_time);
            prHorder.productScoreImg = (ImageView)view.findViewById(R.id.product_score);
            prHorder.serviceScoreImg = (ImageView)view.findViewById(R.id.service_score);
            prHorder.logisticsScoreImg = (ImageView)view.findViewById(R.id.logistics_score);
            prHorder.deatailReviewTv = (TextView)view.findViewById(R.id.tv_detail_review);
            view.setTag(prHorder);
        }else {
            prHorder = (PrHorder)view.getTag();
        }
        Pr pr = prList.get(i);
        prHorder.nickNameTv.setText(pr.getUserNickName());
        prHorder.timeTv.setText(pr.getTime());
        prHorder.productScoreImg.setImageResource(starImg[pr.getProductScore()]);
        prHorder.serviceScoreImg.setImageResource(starImg[pr.getServiceScore()]);
        prHorder.logisticsScoreImg.setImageResource(starImg[pr.getLogisticsScore()]);
        prHorder.deatailReviewTv.setText(pr.getEvaluation());
        return view;
    }

    private class PrHorder{
        TextView nickNameTv;
        TextView timeTv;
        ImageView productScoreImg;
        ImageView serviceScoreImg;
        ImageView logisticsScoreImg;
        TextView deatailReviewTv;
    }
}
