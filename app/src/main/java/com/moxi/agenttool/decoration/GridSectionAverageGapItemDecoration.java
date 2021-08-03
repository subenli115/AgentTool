package com.moxi.agenttool.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 应用于RecyclerView的GridLayoutManager，水平方向上固定间距大小，从而使条目宽度自适应。<br>
 * 配合Brvah的Section使用，不对Head生效，仅对每个Head的子Grid列表生效<br>
 * Section Grid中Item的宽度应设为MATCH_PARAENT
 *
 * @author : renpeng
 * @Since : 2018/9/29
 */
public class GridSectionAverageGapItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets( Rect outRect, View view,  RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = dp2px(20f);
            outRect.right = dp2px(10f);

        } else if (parent.getChildAdapterPosition(view) % 2 == 1) {
            outRect.left = dp2px(10f);
            outRect.right = dp2px(20f);
        }
    }

    public int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
