package com.moxi.agenttool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.bumptech.glide.Glide;
import com.moxi.agenttool.R;
import com.moxi.agenttool.utils.CommonUtils;
import com.moxi.agenttool.utils.EmptyUtils;


/**
 * author 冯
 * create  2017/3/31 10
 * des
 * <p>
 * updateAuthor
 * updateDate
 * updateDes
 */


public class ItemGroupView extends RelativeLayout {
    /**
     * 左侧的文本控件
     */
    private TextView tvLeftText;
    /**
     * 右侧的文本控件
     */
    private TextView tvRightText;
    /**
     * 左侧图片 ，右侧图片控件
     */
    private ImageView ivLeft, ivRight;
    /**
     * 开关控件
     */
    public SwitchButton mSwitchButton;

    /**
     * 底部边线控件
     */
    private View viewBottomLine;

    /**
     * 左侧图标，
     */
    private Drawable leftDrawable;

    /**
     * 右侧图标
     */
    private Drawable rightDrawable;

    /**
     * 左侧文本
     */
    private String leftText;
    /**
     * 右侧文本
     */
    private String rightText;

    /**
     * 左侧字体颜色
     */
    private int leftTextColor;
    /**
     * 右侧字体颜色
     */
    private int rightTextColor;

    /**
     * 左侧字体大小
     */
    private int leftTextSize;

    /**
     * 右侧字体大小
     */
    private int rightTextSize;

    /**
     * 标记是否显示底部边线
     */
    private boolean showBottomLine;

    /**
     * 标记是否显示右侧导向
     */
    private boolean showRightAllow;

    /**
     * 标记是否显示右侧图片
     */
    private boolean showivRight;
    /**
     * 标记是否显示开关
     */
    private boolean showSlideSwitch;

    /**
     * 右侧文本内间距
     */
    private int rightTextpaddRight;
    private TextView tvLeftHintText;
    private String leftHintText;
    private boolean leftTextIcon;

    public ItemGroupView(Context context) {
        this(context, null);
    }

    public ItemGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initViews();
        initDatas();
    }

    protected int getResourseLayoutId() {
        return R.layout.customview_item;
    }

    /**
     * 初始化 xml 属性
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ItemGroupView);
        leftText = t.getString(R.styleable.ItemGroupView_itemView_leftText);
        leftHintText = t.getString(R.styleable.ItemGroupView_itemView_leftHintText);
        rightText = t.getString(R.styleable.ItemGroupView_itemView_rightText);
        showBottomLine = t.getBoolean(R.styleable.ItemGroupView_showBottomLine, true);
        showRightAllow = t.getBoolean(R.styleable.ItemGroupView_showRightArrow, true);
        showivRight = t.getBoolean(R.styleable.ItemGroupView_showRightImage, false);
        showSlideSwitch = t.getBoolean(R.styleable.ItemGroupView_showSlideSwitch, false);
        rightDrawable = t.getDrawable(R.styleable.ItemGroupView_rightIcon);
        leftDrawable = t.getDrawable(R.styleable.ItemGroupView_leftIcon);
        leftTextIcon = t.getBoolean(R.styleable.ItemGroupView_leftTextIcon, false);
        leftTextColor = t.getColor(R.styleable.ItemGroupView_itemView_leftText_color, Color.GRAY);
        rightTextColor = t.getColor(R.styleable.ItemGroupView_itemView_rightText_color, Color.GRAY);
        leftTextSize = t.getDimensionPixelSize(R.styleable.ItemGroupView_itemView_leftTextSize, 20);
        rightTextSize = t.getDimensionPixelSize(R.styleable.ItemGroupView_itemView_rightTextSize, 18);
        t.recycle();
    }

    /**
     * 根元素
     */
    protected View rootView;

    /**
     * 初始化控件
     */
    private void initViews() {
        rootView = LayoutInflater.from(getContext()).inflate(getResourseLayoutId(), this, true);
        tvLeftText = (TextView) findViewById(R.id.tv_item_group_left_text);
        tvLeftHintText = (TextView) findViewById(R.id.tv_item_group_left_hint);
        tvRightText = (TextView) findViewById(R.id.tv_item_group_right_text);
        tvRightText.setPadding(0, 0, rightTextpaddRight, 0);
        tvRightText.setCompoundDrawablePadding(15);
        viewBottomLine = findViewById(R.id.view_item_group_bottom_line);
        showivRight();
        if (leftDrawable != null) {
            ivLeft = (ImageView) findViewById(R.id.iv_item_group_left);
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageDrawable(leftDrawable);
        }
        if(leftTextIcon){
            TextView ivLeft = (TextView) findViewById(R.id.iv_tv_left);
            ivLeft.setVisibility(VISIBLE);
        }else {
            tvLeftText.setTypeface(null, Typeface.BOLD);
        }
        if (leftDrawable != null) {
            ivLeft = (ImageView) findViewById(R.id.iv_item_group_left);
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageDrawable(leftDrawable);
        }
        if (showSlideSwitch) {
            ViewStub viewstub = (ViewStub) findViewById(R.id.vs_item_group_switch);
            View inflate = viewstub.inflate();
            if (inflate instanceof SwitchButton) {
                mSwitchButton = (SwitchButton) inflate;
            }
        }

        showBottomLine(showBottomLine);
        showRightAllow(showRightAllow);
    }

    /**
     * 显示右侧图片
     */
    private void showivRight() {
        if (showivRight) {
            if (ivRight == null) {
                ViewStub viewstub_image = (ViewStub) findViewById(R.id.vs_item_group_image);
                View inflate = viewstub_image.inflate();
                if (inflate instanceof ImageView) {
                    ivRight = (ImageView) inflate;
                    if (rightDrawable != null) ivRight.setImageDrawable(rightDrawable);
                }
            } else {
                ivRight.setVisibility(VISIBLE);
            }

        }
    }

    /**
     * 设置是否显示底部边线
     *
     * @param showBottomLine true 显示  FALSE 隐藏
     */
    public void showBottomLine(boolean showBottomLine) {
        viewBottomLine.setVisibility(showBottomLine ? VISIBLE : GONE);
    }

    /**
     * 设置是否显示右侧导向
     *
     * @param showRightAllow true 显示  FALSE 隐藏
     */
    public void showRightAllow(boolean showRightAllow) {
        if (showRightAllow) {
            rightDrawable = getResources().getDrawable(R.mipmap.ic_home_arrowright);
            rightTextpaddRight = (int) CommonUtils.dp2px(getContext(), 12);
        } else {
            rightDrawable = null;
            rightTextpaddRight = (int) CommonUtils.dp2px(getContext(), 12);
            tvRightText.setPadding(0, 0, 0, 0);
        }
        updateDraw();
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        setText(tvLeftText, leftText);
        setText(tvLeftHintText, leftHintText);
        setText(tvRightText, rightText);
        tvLeftText.setTextColor(leftTextColor);
        tvRightText.setTextColor(rightTextColor);
        if (leftHintText != null && leftHintText.length() > 0) {
            LayoutParams layoutParams1 = (LayoutParams) tvLeftText.getLayoutParams();
            layoutParams1.removeRule(RelativeLayout.CENTER_VERTICAL);
            LayoutParams layoutParams2 = (LayoutParams) tvRightText.getLayoutParams();
            layoutParams2.removeRule(RelativeLayout.CENTER_VERTICAL);
        }

    }

    /**
     * 设置TextViev 文本
     *
     * @param tv   文本控件
     * @param text 文本内容
     */
    public void setText(TextView tv, String text) {
        tv.setText(text);
    }

    /**
     * 设置左侧显示的文本
     *
     * @param text 要显示的内容
     */
    public void setLeftText(String text) {
        tvLeftText.setText(text);
    }

    /**
     * 设置右侧显示的文本
     *
     * @param text 要显示的内容
     */

    public void setRigthText(String text) {
        tvRightText.setText(text);
    }

    /**
     * 设置右侧电话显示的文本
     *
     * @param text 要显示的内容
     */

    public void setMobileRigthText(String text) {
        if (EmptyUtils.isNotEmpty(text)&&text.length()==11) {
            tvRightText.setText(text.substring(0, 3) + "****" + text.substring(7, 11));
        }
    }

    public static void setText(TextView textView, CharSequence text) {
        if (textView != null && !TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    /**
     * 设置右侧显示的文本
     */

    public void setRigthTextSize() {
        tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
    }

    /**
     * 设置右侧文本是否显示
     *
     * @param visibility
     */
    public void setRightTextVisibility(boolean visibility) {
        tvRightText.setVisibility(visibility ? VISIBLE : GONE);
    }

    /**
     * 获取右侧图片控件
     *
     * @return
     */
    public ImageView getivRight() {
        return ivRight;
    }

    /**
     * 加载右侧图片
     *
     * @param url 图片的url
     */
    public void setImagePath(String url) {
        if (ivRight == null || TextUtils.isEmpty(url)) return;
        Glide.with(getContext()).load(url).into(ivRight);
    }

    /**
     * 加载右侧图片
     *
     * @param url        图片的url
     * @param loadingRes 加载中和加载错误的默认图片
     */

    public void setImagePath(String url, int loadingRes) {
        if (ivRight == null || TextUtils.isEmpty(url)) return;
        Glide.with(getContext()).load(url).into(ivRight);
    }


    public void setLeftImage() {
        if (ivLeft != null) {
            LayoutParams params = (LayoutParams) ivLeft.getLayoutParams();
            params.height = (int) CommonUtils.dp2px(getContext(), 30);
            params.width = (int) CommonUtils.dp2px(getContext(), 30);
            ivLeft.setLayoutParams(params);
            ivLeft.setImageDrawable(leftDrawable);
        }
    }

    public void setRightImage() {
        if (ivRight != null) {
            LayoutParams params = (LayoutParams) ivLeft.getLayoutParams();
            params.height = (int) CommonUtils.dp2px(getContext(), 30);
            params.width = (int) CommonUtils.dp2px(getContext(), 30);
            ivLeft.setLayoutParams(params);
            ivLeft.setImageDrawable(leftDrawable);
        }
    }

    private void updateDraw() {
        tvLeftText.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
        tvLeftText.setCompoundDrawablePadding(8);
        tvRightText.setPadding(0, 0, rightTextpaddRight, 0);
    }

    public TextView gettvLeftText() {
        return tvLeftText;
    }

    public TextView getRightTextView() {
        return tvRightText;
    }

    public String getRightText() {
        return tvRightText.getText().toString();
    }

    public void edit() {
        tvRightText.setEnabled(true);
        reqFocus(tvRightText);

        if (tvRightText instanceof EditText) {
            ((EditText) tvRightText).setSelection(tvRightText.length());
        }
    }

    public static void reqFocus(View view) {
        view.requestFocus();
        view.setFocusableInTouchMode(true);
    }

    public void setState(boolean isOpen) {
        if (mSwitchButton != null) {
            mSwitchButton.setChecked(isOpen);
        }
    }

    // SET 方法
    @BindingAdapter("y_change_open")
    public static void setStr(ItemGroupView igv, boolean isOpen) {
        if (igv != null) {
            igv.mSwitchButton.setChecked(isOpen);
        }
    }

    // GET 方法
    @InverseBindingAdapter(attribute = "y_change_open", event = "contentAttrChanged")
    public static boolean getStr(ItemGroupView igv) {
        return igv.mSwitchButton.isChecked();
    }

    // 监听,如果有变动就调用listener中的onChange方法
    @BindingAdapter(value = "contentAttrChanged", requireAll = false)
    public static void setChangeListener(ItemGroupView igv, final InverseBindingListener listener) {
        igv.mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                listener.onChange();
            }
        });
    }






    /**
     * 设置开关监听
     *
     * @param slideListener
     */
    public void setSlideListener(SwitchButton.OnCheckedChangeListener slideListener) {
        if (mSwitchButton != null) {
            mSwitchButton.setOnCheckedChangeListener(slideListener);
        }
    }

    public void showHot() {
        showivRight = true;
        showivRight();
    }

    public void hideHot() {
        ivRight.setVisibility(GONE);
    }

    public void setRigthColor(int color) {
        rightTextColor = color;
        tvRightText.setTextColor(color);
    }
}
