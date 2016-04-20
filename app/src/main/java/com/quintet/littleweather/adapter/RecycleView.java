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
   public class RecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<item> products1;
        private List<item> products2;
        private List<item> products3;
        private List<item> products4;
        //建立枚举 4个item 类型
        public enum ITEM_TYPE
        {
            ITEM1,
            ITEM2,
            ITEM3,
            ITEM4
        }
       public RecycleView(List<item> list1,List<item> list2,List<item> list3,List<item> list4)
       {
           products1 =list1;
           products2 =list2;
           products3 =list3;
           products4 =list4;
       }

        //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
        @Override
        public int getItemViewType(int position)
        {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
            int type=0;
            switch (position)
            {
                case 0:
                    type=ITEM_TYPE.ITEM1.ordinal();
                    break;
                case 1:
                    type=ITEM_TYPE.ITEM2.ordinal();
                    break;
                case 2:
                    type=ITEM_TYPE.ITEM3.ordinal();
                    break;
                case 3:
                    type=ITEM_TYPE.ITEM4.ordinal();
                    break;
            }
            return type;
        }
        @Override
        public int getItemCount()
        {
            return products1.size();
        }

        //在此处找到并加载RecycleView内不同item的布局。
       public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
       {
           RecyclerView.ViewHolder holder=null;
           //加载Item View的时候根据不同TYPE加载不同的布局,因为建立的是4个的枚举类型，所以只能加载四个item的布局。
           if (i == ITEM_TYPE.ITEM1.ordinal())
           {
               View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent1,viewGroup, false);
               holder=new itemView1(view);
           }
           else
           if (i==ITEM_TYPE.ITEM2.ordinal())
           {
               View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent2,viewGroup, false);
               holder=new itemView2(view);
           }
           else
           if(i==ITEM_TYPE.ITEM3.ordinal())
           {
               View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent3,viewGroup, false);
               holder=new itemView3(view);
           }
           else
           if(i==ITEM_TYPE.ITEM4.ordinal())
           {
               View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent4,viewGroup, false);
               holder=new itemView4(view);
           }
           return holder;
       }

        //在此处将布局控件中需要设置的项目都抽取出来。
        //item1的ViewHolder
        public static class itemView1 extends  RecyclerView.ViewHolder
        {
            TextView textView;
            public itemView1(View itemView)
            {
                super(itemView);
        // textView= (TextView) itemView.findViewById(R.id.TV);
            }
        }

        //item2的ViewHolder
        public static class itemView2 extends  RecyclerView.ViewHolder
        {
            TextView textView;
            public itemView2(View itemView)
            {
                super(itemView);
                // textView= (TextView) itemView.findViewById(R.id.TV);
            }
        }

        //item3的ViewHolder
        public static class itemView3 extends  RecyclerView.ViewHolder
        {
            TextView textView;
            public itemView3(View itemView)
            {
                super(itemView);
                // textView= (TextView) itemView.findViewById(R.id.TV);
            }
        }

        //item4的ViewHolder
        public static class itemView4 extends  RecyclerView.ViewHolder
        {
            TextView textView;
            public itemView4(View itemView)
            {
                super(itemView);
                // textView= (TextView) itemView.findViewById(R.id.TV);
            }
        }

        //在此向每项布局控件中抽取出来的项目填充你想要的东西，填充来源就是各个list。
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
             if(holder instanceof itemView1)
             {

             }
             else
             if(holder instanceof itemView2)
             {

             }
             else
             if(holder instanceof itemView3)
             {

             }
             else
             if(holder instanceof itemView4)
             {

             }
        }
}
