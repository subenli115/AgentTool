package com.moxi.agenttool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.moxi.agenttool.R;
import com.moxi.agenttool.ui.base.adapter.SearchSysParamAdapter;
import com.moxi.agenttool.ui.bean.AreaPickerBean;
import com.moxi.agenttool.ui.bean.SysParamsBean;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @Description:expandTab 中间 星级item
 * @Author:ben
 * @Since:2016年4月13日下午2:32:59
 */
public class SearchTabMiddle extends LinearLayout implements ViewBaseAction {

    private ListView regionListView;
    private Context mContext;
    private ListView plateListView;
    private ArrayList<SysParamsBean> groups = new ArrayList<SysParamsBean>();
    private LinkedList<SysParamsBean> childrenItem = new LinkedList<SysParamsBean>();
    private SparseArray<LinkedList<SysParamsBean>> children = new SparseArray<LinkedList<SysParamsBean>>();
    private SearchSysParamAdapter plateListViewAdapter;
    private SearchSysParamAdapter earaListViewAdapter;
    private OnSelectListener mOnSelectListener;
    private OnSelectListener2 mOnSelectListener2;
    private int tEaraPosition = 0;
    private int tBlockPosition = 0;
    private String showString = "不限";

    public SearchTabMiddle(Context context, AreaPickerBean data) {
        super(context);
        mContext = context;
        init(context, data);
    }

    public SearchTabMiddle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
//        init(context);
    }

    public void updateShowText(String showArea, String showBlock) {
        if (showArea == null || showBlock == null) {
            return;
        }
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getParName().equals(showArea)) {
                earaListViewAdapter.setSelectedPosition(i);
                childrenItem.clear();
                if (i < children.size()) {
                    childrenItem.addAll(children.get(i));
                }
                tEaraPosition = i;
                break;
            }
        }
        for (int j = 0; j < childrenItem.size(); j++) {
            if (childrenItem.get(j).getParName().replace("不限", "").equals(showBlock.trim())) {
                plateListViewAdapter.setSelectedPosition(j);
                tBlockPosition = j;
                break;
            }
        }
        setDefaultSelect();
    }

    /**
     * @Description: 模拟数据
     * @Since: 2016年4月13日下午4:29:01
     */
    public void testData(AreaPickerBean areaPickerBean) {
        SysParamsBean bean;

        groups.clear();
//        for (int i = 0; i < strings.size(); i++) {
//            bean = new SysParamsBean();
//            bean.setParCode(stringCodes.get(i));
//            bean.setParName(strings.get(i));
//            groups.add(bean);
//
//        }
//        LinkedList<SysParamsBean> tItem = new LinkedList<SysParamsBean>();
//        tItem.clear();
//        for (int j = 0; j < stringChild.size(); j++) {
//            SysParamsBean mBean = new SysParamsBean();
//            mBean.setParCode(stringChildCode.get(j));
//            mBean.setParName(stringChild.get(j));
//            tItem.add(mBean);
//
//        }
//        childrenItem.clear();
//        childrenItem.addAll(tItem);
        earaListViewAdapter.notifyDataSetChanged();
        plateListViewAdapter.notifyDataSetChanged();

    }

    private void updateChild(LinkedList<SysParamsBean> tItem) {
        childrenItem.clear();
        //重新获取
        childrenItem.addAll(tItem);
        plateListViewAdapter.notifyDataSetChanged();
    }


    /**
     * @param context
     * @param data
     * @Description:初始化视图
     * @Since: 2016年4月13日下午4:29:16
     */
    private void init(Context context, AreaPickerBean data) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_double_item, this, true);
        regionListView = (ListView) findViewById(R.id.listViewLvOne);
        plateListView = (ListView) findViewById(R.id.listViewLvTwo);
        // setBackgroundDrawable(getResources().getDrawable(
        // R.drawable.choosearea_bg_left));

        // 测试数据
        earaListViewAdapter = new SearchSysParamAdapter(context, groups, 0, 0, 0);
        earaListViewAdapter.setTextSize(17);
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);

        regionListView.setAdapter(earaListViewAdapter);
        earaListViewAdapter.setOnItemClickListener(new SearchSysParamAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                    if (mOnSelectListener != null) {
                        mOnSelectListener.getValue(groups.get(position).getParCode(), position);
                    }
            }
        });
//        if (tEaraPosition < children.size()) childrenItem.addAll(children.get(tEaraPosition));
        plateListViewAdapter = new SearchSysParamAdapter(context, childrenItem, 0, 0, 1);
        plateListViewAdapter.setTextSize(15);
        // plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
        plateListView.setAdapter(plateListViewAdapter);
        plateListViewAdapter.setOnItemClickListener(new SearchSysParamAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                if (mOnSelectListener2 != null) {

                    mOnSelectListener2.getValue(childrenItem.get(position).getParCode(), position);
                }

            }
        });
        if (tBlockPosition < childrenItem.size())
            showString = childrenItem.get(tBlockPosition).getParName();
        if (showString.contains("不限")) {
            showString = showString.replace("不限", "");
        }
        setDefaultSelect();

    }

    /**
     * @Description: 设置默认选中项
     * @Author:胡帅
     * @Since: 2016年4月13日下午4:27:29
     */
    public void setDefaultSelect() {
        regionListView.setSelection(tEaraPosition);
        plateListView.setSelection(tBlockPosition);
    }

    public String getShowText() {
        return showString;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {

        public void getValue(String itemLvTwocode, int position);
    }

    public void setOnSelectListener2(OnSelectListener2 onSelectListener) {
        mOnSelectListener2 = onSelectListener;
    }

    public interface OnSelectListener2 {

        public void getValue(String itemLvTwocode, int position);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
