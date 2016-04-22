package com.quintet.littleweather.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.quintet.littleweather.R;

import java.util.ArrayList;

/**
 * Created by hugo on 2016/2/19 0019.
 */
public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.CityViewHolder> {
    private Context mContext;
    private ArrayList<String> dataList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private int mDuration = 300;
    // private int mLastPosition = -1;


    public SelectCityAdapter(Context context, ArrayList<String> dataList) {
        mContext = context;
        this.dataList = dataList;
    }


    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_selectcity, parent, false));
    }


    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        holder.itemCity.setText(dataList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });


        // CustomAnimation animation = new CustomAnimation();

        ScaleInAnimation animation = new ScaleInAnimation();

       // SlideInLeftAnimation animation = new SlideInLeftAnimation();
        for (Animator anim : animation.getAnimators(holder.cardView)) {
            anim.setDuration(mDuration).start();
        }


    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView itemCity;
        private CardView cardView;


        public CityViewHolder(View itemView) {
            super(itemView);
            itemCity = (TextView) itemView.findViewById(R.id.item_city);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }


    public class ScaleInAnimation extends BaseAnimation {

        private static final float DEFAULT_SCALE_FROM = .5f;
        private final float mFrom;

        public ScaleInAnimation() {
            this(DEFAULT_SCALE_FROM);
        }

        public ScaleInAnimation(float from) {
            mFrom = from;
        }

        @Override
        public Animator[] getAnimators(View view) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
            return new ObjectAnimator[]{scaleX, scaleY};
        }
    }

    public abstract class BaseAnimation {
        public abstract Animator[] getAnimators(View view);
    }

}
