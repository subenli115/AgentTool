package com.moxi.agenttool.ui.bean;


import java.util.HashMap;
import java.util.Map;

/**
 * @description: 地区对象选择列表
 * @author: YangYong
 * @sence: 2021/1/21
 * @version: 2.0
 */
public class AreaPickerBean  {

    public static final Integer ITEM_INDEX_0 = 0;
    public static final Integer ITEM_INDEX_1 = 1;
    public static final Integer ITEM_INDEX_2 = 2;

    private int changedOption = 0;

    /**
     * 列表项与对应选中项
     */
    Map<Integer, Integer> map = new HashMap<>();

    public AreaPickerBean() {
        setOptions(0, 0, 0);
    }


    /**
     * 设置列表项与对应选中项
     *
     * @param itemIndex      列表项
     * @param choicePosition 选中项
     */
    public void setChoicePosition(int itemIndex, int choicePosition) {
        map.put(itemIndex, choicePosition);
    }

    /**
     * 获取列表项对应的选中项
     *
     * @param itemIndex 列表项
     * @return 选中项
     */
    public Integer getChoicePosition(int itemIndex) {
        return map.get(itemIndex);
    }


    /**
     * 设置选择项
     *
     * @param options1 第1项选择位置
     * @param options2 第2项选择位置
     * @param options3 第3项选择位置
     */
    public void setOptions(int options1, int options2, int options3) {
        changedOption = getChangedOption(options1, options2, options3);
        map.put(ITEM_INDEX_0, options1);
        map.put(ITEM_INDEX_1, options2);
        map.put(ITEM_INDEX_2, options3);
    }

    /**
     * 获取option
     */
    private int getOption(int option) {
        return map.get(option);
    }

    public int getOption1() {
        return getOption(0);
    }

    public int getOption2() {
        return getOption(1);
    }

    public int getOption3() {
        return getOption(2);
    }

    /**
     * 判断改变的item
     */
    private int getChangedOption(Integer options1, Integer options2, Integer options3) {
        if (map.get(ITEM_INDEX_2) != options3) {
            return ITEM_INDEX_2;
        } else if (map.get(ITEM_INDEX_1) != options2) {
            return ITEM_INDEX_1;
        } else if (map.get(ITEM_INDEX_0) != options1) {
            return ITEM_INDEX_0;
        }
        return 0;
    }

    /**
     * 重置后面项
     */
    public void resetChangeChildOptions() {
        if (changedOption == ITEM_INDEX_1) {
            map.put(ITEM_INDEX_2, 0);//重置后面项
        } else if (changedOption == ITEM_INDEX_0) {
            map.put(ITEM_INDEX_1, 0);//重置后面项
            map.put(ITEM_INDEX_2, 0);//重置后面项
        }
    }

    /**
     * 获取改变的item位置
     */
    public int getChangedOption() {
        return changedOption;
    }


    /**
     * 获取选中地区
     */
    public String getSelectedArea() {
        return getAreaProvinceName() + " " + getAreaCityName() + " " + getAreaDistrictName();
    }

    private String areaCityId;
    private String areaCityName;
    private String areaDistrictId;
    private String areaDistrictName;
    private String areaProvinceId;
    private String areaProvinceName;

    public String getAreaCityId() {
        return areaCityId;
    }

    public String getAreaCityName() {
        return areaCityName;
    }

    public String getAreaDistrictId() {
        return areaDistrictId;
    }

    public String getAreaDistrictName() {
        return areaDistrictName;
    }

    public String getAreaProvinceId() {
        return areaProvinceId;
    }

    public String getAreaProvinceName() {
        return areaProvinceName;
    }

    public void setAreaCityId(String areaCityId) {
        this.areaCityId = areaCityId;
    }

    public void setAreaCityName(String areaCityName) {
        this.areaCityName = areaCityName;
    }

    public void setAreaDistrictId(String areaDistrictId) {
        this.areaDistrictId = areaDistrictId;
    }

    public void setAreaDistrictName(String areaDistrictName) {
        this.areaDistrictName = areaDistrictName;
    }

    public void setAreaProvinceId(String areaProvinceId) {
        this.areaProvinceId = areaProvinceId;
    }

    public void setAreaProvinceName(String areaProvinceName) {
        this.areaProvinceName = areaProvinceName;
    }
}
