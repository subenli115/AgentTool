package com.moxi.agenttool.app;

import android.content.Context;

import com.moxi.agenttool.BuildConfig;
import com.moxi.agenttool.R;
import com.moxi.agenttool.ui.bean.TagBean;
import com.moxi.agenttool.ui.login.LoginActivity;
import com.moxi.agenttool.utils.ACache;
import com.moxi.agenttool.utils.GsonUtils;
import com.moxi.agenttool.utils.PreferenceManager;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.GsonDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;

import org.litepal.LitePal;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * Created by goldze on 2017/7/16.
 */

public class AppApplication extends BaseApplication {

    private static String BASE_URL;
    private static final String OK_BASE_URL = "http://11.0.0.61:527/api/";
    private static final String TEST_BASE_URL = "http://11.0.0.61:527/api/";


    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        LitePal.initialize(this);
        //初始化缓存配置，包括磁盘缓存路径，缓存大小，内存缓存大小，加密策略等。
        // 最后调用.install(this)方法完成初始化
        //初始化全局异常崩溃
        initCrash();
//        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
        AutoSizeConfig.getInstance()
                .setUseDeviceSize(true);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MaterialHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
        PreferenceManager.init(this);
        EasyHttp.init(this);
        if (BuildConfig.DEBUG) {
            EasyHttp.getInstance().debug("easyhttp", true);
        }
        setDebug(false);


        EasyHttp.getInstance()
                .setBaseUrl(BASE_URL)
                .setReadTimeOut(15 * 1000)
                .setWriteTimeOut(15 * 1000)
                .setConnectTimeout(15 * 1000)
//                .setConnectTimeout(600 * 1000)
                .setRetryCount(1)
                .setRetryDelay(500)
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setCacheDiskConverter(new GsonDiskConverter())
                .setCacheMode(CacheMode.ONLYREMOTE);

        initData();


    }

    private void initData() {

        TagBean topicBean = new TagBean();
        ArrayList<TagBean.ListBean> listBeans = new ArrayList<>();


        listBeans.add(new TagBean.ListBean("40万以下", 1));
        listBeans.add(new TagBean.ListBean("40-60万", 2));
        listBeans.add(new TagBean.ListBean("60-80万", 3));
        listBeans.add(new TagBean.ListBean("80-100万", 4));
        listBeans.add(new TagBean.ListBean("100-150万", 5));
        listBeans.add(new TagBean.ListBean("150-200万", 6));
        listBeans.add(new TagBean.ListBean("200-300万", 7));
        listBeans.add(new TagBean.ListBean("300万以上", 8));
        topicBean.setList(listBeans);
        topicBean.setType("budget");
        ACache.get(this).put("budget", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("0.4万以下", 1));
        listBeans.add(new TagBean.ListBean("0.4-0.6万", 2));
        listBeans.add(new TagBean.ListBean("0.6-0.8万", 3));
        listBeans.add(new TagBean.ListBean("0.8-1.0万", 4));
        listBeans.add(new TagBean.ListBean("1.0-1.5万", 5));
        listBeans.add(new TagBean.ListBean("1.5-2.0万", 6));
        listBeans.add(new TagBean.ListBean("2.0-3.0万", 6));
        listBeans.add(new TagBean.ListBean("3.0万以上", 6));
        topicBean.setList(listBeans);
        topicBean.setType("budget_unit");
        ACache.get(this).put("budget_unit", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("近期开盘", 1));
        listBeans.add(new TagBean.ListBean("未来一个月", 2));
        listBeans.add(new TagBean.ListBean("未来三个月", 3));
        listBeans.add(new TagBean.ListBean("未来半年", 4));
        listBeans.add(new TagBean.ListBean("过去一个月", 5));
        listBeans.add(new TagBean.ListBean("过去三个月", 6));
        topicBean.setList(listBeans);
        topicBean.setType("opening");
        ACache.get(this).put("opening", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("住宅", 1));
        listBeans.add(new TagBean.ListBean("别墅", 2));
        listBeans.add(new TagBean.ListBean("商业", 3));
        listBeans.add(new TagBean.ListBean("写字楼", 4));
        listBeans.add(new TagBean.ListBean("底商", 5));
        topicBean.setList(listBeans);
        topicBean.setType("Property");
        ACache.get(this).put("Property", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("在售", 1));
        listBeans.add(new TagBean.ListBean("未开盘", 2));
        listBeans.add(new TagBean.ListBean("售罄", 3));
        topicBean.setList(listBeans);
        topicBean.setType("status");
        ACache.get(this).put("status", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("一室", 1));
        listBeans.add(new TagBean.ListBean("二室", 2));
        listBeans.add(new TagBean.ListBean("三室", 3));
        listBeans.add(new TagBean.ListBean("四室", 4));
        listBeans.add(new TagBean.ListBean("五室", 5));
        listBeans.add(new TagBean.ListBean("五室以上", 6));
        topicBean.setList(listBeans);
        topicBean.setType("type");
        ACache.get(this).put("type", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("30㎡以下", 1));
        listBeans.add(new TagBean.ListBean("30-50㎡", 2));
        listBeans.add(new TagBean.ListBean("50-70㎡", 3));
        listBeans.add(new TagBean.ListBean("70-90㎡", 4));
        listBeans.add(new TagBean.ListBean("90-120㎡", 5));
        listBeans.add(new TagBean.ListBean("120-150㎡", 6));
        listBeans.add(new TagBean.ListBean("150-200㎡", 7));
        listBeans.add(new TagBean.ListBean("200-300㎡", 8));
        listBeans.add(new TagBean.ListBean("300㎡以上", 9));
        topicBean.setList(listBeans);
        topicBean.setType("area");
        ACache.get(this).put("area", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("满五年", 1));
        listBeans.add(new TagBean.ListBean("40-满两年", 2));
        listBeans.add(new TagBean.ListBean("近地铁", 3));
        listBeans.add(new TagBean.ListBean("红本在手", 4));
        topicBean.setList(listBeans);
        topicBean.setType("trait");
        ACache.get(this).put("trait", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);

        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("南北", 1));
        listBeans.add(new TagBean.ListBean("朝南", 2));
        listBeans.add(new TagBean.ListBean("朝东", 3));
        listBeans.add(new TagBean.ListBean("朝北", 4));
        listBeans.add(new TagBean.ListBean("朝西", 5));
        topicBean.setList(listBeans);
        topicBean.setType("orientation");
        ACache.get(this).put("orientation", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);

        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("低楼层", 1));
        listBeans.add(new TagBean.ListBean("中楼层", 2));
        listBeans.add(new TagBean.ListBean("高楼层", 3));
        topicBean.setList(listBeans);
        topicBean.setType("floor");
        ACache.get(this).put("floor", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("5年以内", 1));
        listBeans.add(new TagBean.ListBean("10年以内", 2));
        listBeans.add(new TagBean.ListBean("15年以内", 3));
        listBeans.add(new TagBean.ListBean("20年以内", 4));
        listBeans.add(new TagBean.ListBean("20年以上", 5));
        topicBean.setList(listBeans);
        topicBean.setType("age");
        ACache.get(this).put("age", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("精装修", 1));
        listBeans.add(new TagBean.ListBean("普通装修", 2));
        listBeans.add(new TagBean.ListBean("毛坯房", 3));
        topicBean.setList(listBeans);
        topicBean.setType("renovation");
        ACache.get(this).put("renovation", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);


        topicBean = new TagBean();
        listBeans = new ArrayList<>();

        listBeans.add(new TagBean.ListBean("普通住宅", 1));
        listBeans.add(new TagBean.ListBean("商业类", 2));
        listBeans.add(new TagBean.ListBean("别墅", 3));
        listBeans.add(new TagBean.ListBean("四合院", 4));
        listBeans.add(new TagBean.ListBean("其它", 5));
        topicBean.setList(listBeans);
        topicBean.setType("purpose");
        ACache.get(this).put("purpose", GsonUtils.toJson(topicBean), ACache.TIME_DAY * 99);
    }

    public static void setDebug(boolean debug) {
        BASE_URL = debug ? TEST_BASE_URL : OK_BASE_URL;
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(LoginActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
