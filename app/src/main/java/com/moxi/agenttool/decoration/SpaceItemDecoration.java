package com.moxi.agenttool.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @description: RecyclerView行间隔
 * usage:recycleView.addItemDecoration(new SpaceItemDecoration(10)); //px,vertical
 * usage:recycleView.addItemDecoration(new SpaceItemDecoration(context,10,20,15).horizontal()); //dp,horizontal
 * usage:recycleView.addItemDecoration(new SpaceItemDecoration(context,10,20).grid(2)); //dp,grid
 * @author: YangYong
 * @sence: 2021/1/12
 * @version: 2.0
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {


        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //因为每行都只有3个，因此第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) %2==0) {
                outRect.left = 0;
            }
        }


}
