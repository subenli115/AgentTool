package com.moxi.agenttool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moxi.agenttool.R;
import com.moxi.agenttool.ui.adapter.ClientTagAdapter;
import com.moxi.agenttool.ui.bean.AreaBean;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:expandTab 左 地区item
 * @Author:ben
 * @Since:2016年4月13日下午2:31:58
 */
public class SearchTabLeft extends RelativeLayout implements ViewBaseAction {

    private RecyclerView mRecyLerView;
    private TextView tvQd;
    private List<AreaBean> areaList;
    private OnSelectListener mOnSelectListener;
    private ClientTagAdapter adapter;
    private String showText = "地区";
    private Context mContext;
    private String selectId;

    public String getShowText() {
        return showText;
    }

    public SearchTabLeft(Context context) {
        super(context);
        init(context);
    }

    public SearchTabLeft(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SearchTabLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * @param context
     * @Description: 初始化视图
     * @Author:胡帅
     * @Since: 2016年4月13日下午4:30:58
     */
    private void init(Context context) {


        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_single_item, this, true);
        adapter = new ClientTagAdapter(R.layout.item_tag_client_view, mContext);
        mRecyLerView = (RecyclerView) findViewById(R.id.listViewSingle);
        tvQd = (TextView) findViewById(R.id.tv_qd);
        mRecyLerView.setHasFixedSize(true);
        mRecyLerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyLerView.setAdapter(adapter);
        areaList = new ArrayList<AreaBean>();
        // test
        tvQd.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });

    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public void upData(List list) {
            adapter.setNewData(list);
    }




    public interface OnSelectListener {

        public void getValue(String itemCode, String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
