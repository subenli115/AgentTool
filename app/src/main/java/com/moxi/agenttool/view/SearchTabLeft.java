package com.moxi.agenttool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.moxi.agenttool.R;
import com.moxi.agenttool.ui.base.adapter.SearchItemAdapter;
import com.moxi.agenttool.ui.bean.AreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:expandTab 左 地区item
 * @Author:ben
 * @Since:2016年4月13日下午2:31:58
 */
public class SearchTabLeft extends RelativeLayout implements ViewBaseAction {

    private ListView mListView;
    private List<AreaBean> areaList;
    private OnSelectListener mOnSelectListener;
    private SearchItemAdapter adapter;
    private String mDistance;
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
        // setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
        mListView = (ListView) findViewById(R.id.listViewSingle);
        areaList = new ArrayList<AreaBean>();
        // test

        adapter = new SearchItemAdapter(context, areaList, 0, 0, 0);
        adapter.setSelectedPosition(0);
        adapter.setTextSize(17);
//            for ( int i = 0 ; i < areaList.size () ; i++ ) {
//                if (areaList.get (i).getAreaCode ().equals(selectId)) {
//                    adapter.setSelectedPositionNoNotify (i);
//                    showText = areaList.get (i).getAreaName ();
//                    break;
//                }
//            }

        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SearchItemAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (mOnSelectListener != null) {
                    showText = areaList.get(position).getAreaName();
                    mOnSelectListener.getValue(areaList.get(position).getAreaName(), areaList.get(position).getAreaName());
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public void upData(List list) {
//        adapter.testData(industryListBeans);

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
