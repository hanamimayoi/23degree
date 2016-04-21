package com.quintet.littleweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quintet.littleweather.R;
import com.quintet.littleweather.bean.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-4-19.
 */
   public class RecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
       static private List<item> card1;
       static private List<item> card2;
       static private List<item> card3;
       static private List<item> card4;
       static private Context context;

        //建立枚举 4个item 类型
        public enum ITEM_TYPE
        {
            ITEM1,
            ITEM2,
            ITEM3,
            ITEM4
        }
       public RecycleView(List<item> list1,List<item> list2,List<item> list3,List<item> list4,Context context)
       {
           card1 =list1;
           card2 =list2;
           card3 =list3;
           card4 =list4;
           this.context=context;
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
            return card1.size();
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
            ImageView item1_weather_image;
            TextView item1_temp_flu_text;
            TextView item1_temp_max_text;
            TextView item1_temp_min_text;
            TextView item1_temp_pm;
            TextView item1_temp_quality;
            public itemView1(View itemView)
            {
                super(itemView);
                //item1天气图标
                item1_weather_image = (ImageView) itemView.findViewById(R.id.iv_weather);
                //item1温度
                item1_temp_flu_text = (TextView) itemView.findViewById(R.id.tv_temp_flu);
                //item1最高温
                item1_temp_max_text = (TextView) itemView.findViewById(R.id.tv_temp_max);
                //item1最低温
                item1_temp_min_text = (TextView) itemView.findViewById(R.id.tv_temp_min);
                //item1 PM2.5值
                item1_temp_pm = (TextView) itemView.findViewById(R.id.tv_temp_pm);
                //item1空气质量
                item1_temp_quality = (TextView) itemView.findViewById(R.id.tv_temp_quality);
            }
        }

        //item2的ViewHolder
        public static class itemView2 extends  RecyclerView.ViewHolder
        {
            List<ImageView> item2_tvtime_image_list;
            List<TextView> item2_tvtime_text_list;
            List<ImageView> item2_tvtemp_image_list;
            List<TextView> item2_tvtemp_text_list;
            List<ImageView> item2_tvhumidity_image_list;
            List<TextView> item2_tvhumidity_text_list;
            List<ImageView> item2_tvwindspeed_image_list;
            List<TextView> item2_tvwindspeed_text_list;
            public itemView2(View itemView)
            {
                super(itemView);
                LinearLayout item2list= (LinearLayout)itemView.findViewById(R.id.item2_LL_theouterfirst);
                item2_tvtime_image_list = new ArrayList<ImageView>();
                item2_tvtime_text_list = new ArrayList<TextView>();
                item2_tvtemp_image_list = new ArrayList<ImageView>();
                item2_tvtemp_text_list = new ArrayList<TextView>();
                item2_tvhumidity_image_list = new ArrayList<ImageView>();
                item2_tvhumidity_text_list = new ArrayList<TextView>();
                item2_tvwindspeed_image_list = new ArrayList<ImageView>();
                item2_tvwindspeed_text_list = new ArrayList<TextView>();
                for(int i=0;i<card2.size();i++)
                {
                    View view=View.inflate(context,R.layout.itemcontent2,null);
                    View itemview=view.findViewById(R.id.item2_LL_theoutersecond);
                    //item2时钟图片
                    item2_tvtime_image_list.add((ImageView)(itemview.findViewById(R.id.item2_tvtime_image)));
                    //item2时间点text
                    item2_tvtime_text_list.add((TextView) (itemview.findViewById(R.id.item2_tvtime_text)));
                    //item2温度图片
                    item2_tvtemp_image_list.add((ImageView) (itemview.findViewById(R.id.item2_tvtemp_image)));
                    //item2温度text
                    item2_tvtemp_text_list.add((TextView) (itemview.findViewById(R.id.item2_tvtemp_text)));
                    //item2湿度图片
                    item2_tvhumidity_image_list.add((ImageView) (itemview.findViewById(R.id.item2_tvhumidity_image)));
                    //item2湿度text
                    item2_tvhumidity_text_list.add((TextView) (itemview.findViewById(R.id.item2_tvhumidity_text)));
                    //item2风速图片
                    item2_tvwindspeed_image_list.add((ImageView) (itemview.findViewById(R.id.item2_tvwindspeed_image)));
                    //item2风速text
                    item2_tvwindspeed_text_list.add((TextView) (itemview.findViewById(R.id.item2_tvwindspeed_text)));
                    item2list.addView(itemview);
                }
            }
        }

        //item3的ViewHolder
        public static class itemView3 extends  RecyclerView.ViewHolder
        {
            TextView clothtxt;
            TextView sporttxt;
            TextView traveltxt;
            TextView flutxt;
            public itemView3(View itemView)
            {
                super(itemView);
                //item3的穿衣指数值
                clothtxt= (TextView) itemView.findViewById(R.id.cloth_txt);
                //item3的运动指数值
                sporttxt= (TextView) itemView.findViewById(R.id.sport_txt);
                //item3的旅游指数值
                traveltxt= (TextView) itemView.findViewById(R.id.travel_txt);
                //item3的感冒指数值
                flutxt= (TextView) itemView.findViewById(R.id.flu_txt);
            }
        }

        //item4的ViewHolder
        public static class itemView4 extends  RecyclerView.ViewHolder
        {
            List<ImageView> item4_left_image_list;
            List<TextView> item4_date_text_list;
            List<TextView> item4_temp_text_list;
            List<TextView> item4_advice_text_list;
            public itemView4(View itemView)
            {
                super(itemView);
                LinearLayout item4list= (LinearLayout)itemView.findViewById(R.id.item4_LL_theouterfirst);
                item4_left_image_list = new ArrayList<ImageView>();
                item4_date_text_list = new ArrayList<TextView>();
                item4_temp_text_list = new ArrayList<TextView>();
                item4_advice_text_list = new ArrayList<TextView>();
                for(int i=0;i<card4.size();i++)
                {
                    View view = View.inflate(context, R.layout.itemcontent4, null);
                    View itemview = view.findViewById(R.id.item4_LL_theoutersecond);
                    item4_left_image_list.add((ImageView)(itemview.findViewById(R.id.item4_left_image)));
                    item4_date_text_list.add((TextView)(itemview.findViewById(R.id.item4_date_text)));
                    item4_temp_text_list.add((TextView)(itemview.findViewById(R.id.item4_temp_text)));
                    item4_advice_text_list.add((TextView)(itemview.findViewById(R.id. item4_advice_text)));
                    item4list.addView(itemview);
                }
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
