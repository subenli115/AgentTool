package com.moxi.agenttool.ui.base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.moxi.agenttool.R;
import com.moxi.agenttool.ui.bean.AreaBean;
import com.moxi.agenttool.ui.bean.UserTagBean;

import java.util.List;

/**
 *@Description:适配器 控制选中状态 可设置选中图片
 *@Author:胡帅
 *@Since:2016年4月13日下午1:28:37  
 */
public class SearchItemAdapter extends ArrayAdapter<AreaBean> {

    private Context             mContext;
    private List<AreaBean>      mListData;
    private String[]            mArrayData;
    private int                 selectedPos  = -1;
    private String              selectedText = "";
    private int                 normalDrawbleId;
    private Drawable            selectedDrawble;
    private float               textSize;
    private OnClickListener     onClickListener;
    private OnItemClickListener mOnItemClickListener;
    private int                 mSelectType  = 0;    // 0=father 1=son

    /** 
    *Description: searchtab 构造器 可以自定义样式
    * @param context
    * @param listData
    * @param sId 选中bg id
    * @param nId 选择bg id  e.g 打勾样式
    * @param type 
    */
    public SearchItemAdapter(Context context, List<AreaBean> listData, int sId, int nId, int type) {
        super (context, R.string.no_data, listData);
        mContext = context;
        mListData = listData;
        mSelectType = type;
        // selectedDrawble = mContext.getResources().getDrawable(sId);
        // normalDrawbleId = nId;

        init ();
    }

    public void testData(List<UserTagBean.DataDTO> dataDTOS){
        AreaBean bean;
        for ( int i = 0 ; i < dataDTOS.size() ; i++ ) {
            bean = new AreaBean ();
            bean.setAreaName (dataDTOS.get(i).getName());
            bean.setAreaCode (dataDTOS.get(i).getId());
            mListData.add (bean);
        }
        setSelectedPosition (0);
       notifyDataSetChanged();
    }

    private void init(){
        onClickListener = new OnClickListener () {

            @Override
            public void onClick(View view){
                selectedPos = (Integer) view.getTag ();
                setSelectedPosition (selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick (view, selectedPos);
                }
            }
        };
    }

    /**
     * 设置选中的position,并通知列表刷新
     */
    public void setSelectedPosition(int pos){
        if (mListData != null && pos < mListData.size ()) {
            selectedPos = pos;
            selectedText = mListData.get (pos).getAreaName ();
            notifyDataSetChanged ();
        }
        // else if (mArrayData != null && pos < mArrayData.length) {
        // selectedPos = pos;
        // selectedText = mArrayData[pos];
        // notifyDataSetChanged();
        // }

    }

    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos){
        selectedPos = pos;
        if (mListData != null && pos < mListData.size ()) {
            selectedText = mListData.get (pos).getAreaName ();
        }
        // else if (mArrayData != null && pos < mArrayData.length) {
        // selectedText = mArrayData[pos];
        // }
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition(){
        if (mArrayData != null && selectedPos < mArrayData.length) { return selectedPos; }
        if (mListData != null && selectedPos < mListData.size ()) { return selectedPos; }

        return -1;
    }

    /**
     * 设置列表字体大小
     */
    public void setTextSize(float tSize){
        textSize = tSize;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from (mContext).inflate (R.layout.search_choose_tag_item, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setTag (position);
        String mString = "";
        if (mListData != null) {
            if (position < mListData.size ()) {
                mString = mListData.get (position).getAreaName ();
            }
        }
        // else if (mArrayData != null) {
        // if (position < mArrayData.length) {
        // mString = mArrayData[position];
        // }
        // }
        if (mString.contains ("不限")) view.setText ("不限");
        else view.setText (mString);
        view.setTextSize (TypedValue.COMPLEX_UNIT_SP, textSize);

        if (selectedText != null && selectedText.equals (mString)) {
            // view.setBackgroundDrawable(selectedDrawble);//设置选中的背景图片
            view.setTextColor (mContext.getResources ().getColor (R.color.black_15));
            view.setBackgroundColor (mContext.getResources ().getColor (R.color.white));
        } else {
            view.setBackgroundColor (mContext.getResources ().getColor (R.color.black_15));
            view.setTextColor (mContext.getResources ().getColor (R.color.white));
        }
        view.setPadding (20, 0, 0, 0);
        view.setOnClickListener (onClickListener);
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener l){
        mOnItemClickListener = l;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {

        public void onItemClick(View view, int position);
    }

}
