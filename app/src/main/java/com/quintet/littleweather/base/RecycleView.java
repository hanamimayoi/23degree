package com.quintet.littleweather.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quintet.littleweather.R;
import com.quintet.littleweather.config.item;

import java.util.List;

/**
 * Created by Administrator on 16-4-19.
 */
   public class RecycleView extends RecyclerView.Adapter<RecycleView.itemView>
    {
       private List<item> products;
       public RecycleView(List<item> list)
       {
           products=list;
       }

       @Override
       public itemView onCreateViewHolder(ViewGroup viewGroup, int i)
       {
           View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemdemo,viewGroup, false);
           return new itemView(view);
       }

        @Override
        //在此向每一个CardView中填充你想要的东西
        public void onBindViewHolder(itemView holder, int position)
        {
            holder.textView.setText("item"+position);
        }

       @Override
       public int getItemCount()
       {
           return products.size();
       }

       public static class itemView extends  RecyclerView.ViewHolder
       {
           TextView textView;
           public itemView(View itemView)
           {
               super(itemView);
               textView= (TextView) itemView.findViewById(R.id.TV);
           }
       }
}
