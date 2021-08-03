package com.moxi.agenttool.wdiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;

/**
 * @ClassName: HXLinePagerIndicator
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/22 14:44
 */

public class HXLinePagerIndicator extends LinePagerIndicatorEx {
    public HXLinePagerIndicator(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LinearGradient lg = new LinearGradient(getLineRect().left, getLineRect().top, getLineRect().right, getLineRect().bottom, new int[]{ 0xFFFFD0BC,0xFFFF7730}, null, LinearGradient.TileMode.CLAMP);
        getPaint().setShader(lg);
        canvas.drawRoundRect(getLineRect(), getRoundRadius(), getRoundRadius(), getPaint());
    }
}
