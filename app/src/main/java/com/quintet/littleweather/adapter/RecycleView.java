package com.quintet.littleweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quintet.littleweather.R;
import com.quintet.littleweather.bean.item;

import java.util.List;

/**
 * Created by Administrator on 16-4-19.
 */
   public class RecycleView extends RecyclerView.Adapter<RecycleView.itemView>
    {
       private List<item> products;
        //建立枚举 2个item 类型
        public enum ITEM_TYPE
        {
            ITEM1,
            ITEM2
        }
       public RecycleView(List<item> list)
       {
           products=list;
       }

        //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
        @Override
        public int getItemViewType(int position)
        {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
            return position % 2 == 0 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
        }
        @Override
        public int getItemCount()
        {
            return products.size();
        }

        //在此处找到并加载RecycleView内不同item的布局。
       public itemView onCreateViewHolder(ViewGroup viewGroup, int i)
       {
           //加载Item View的时候根据不同TYPE加载不同的布局,因为建立的是2个的枚举类型，所以只能加载两个item的布局。
           if (i == ITEM_TYPE.ITEM1.ordinal())
           {
               View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent1,viewGroup, false);
               return new itemView(view);
           }
           else
           {
               View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent2,viewGroup, false);
               return new itemView(view);
           }
       }

        //在此处将布局控件中需要设置的项目都抽取出来。
        public static class itemView extends  RecyclerView.ViewHolder
        {
            TextView textView;
            public itemView(View itemView)
            {
                super(itemView);
        // textView= (TextView) itemView.findViewById(R.id.TV);
            }
        }

        //在此向每项布局控件中抽取出来的项目填充你想要的东西，填充来源就是list。
        public void onBindViewHolder(itemView holder, int position)
        {
        //holder.textView.setText("item"+position);
        }
}
