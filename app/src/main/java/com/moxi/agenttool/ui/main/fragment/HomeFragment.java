package com.moxi.agenttool.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.lifecycle.Observer;

import com.github.lany192.pinyin.PingYinUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.FragmentHomeBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.fragment.BaseFragment;
import com.moxi.agenttool.ui.bean.FilterHouseBean;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.ImportantBean;
import com.moxi.agenttool.ui.bean.KeynoteClientBean;
import com.moxi.agenttool.ui.main.activity.MatchingActivity;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.wdiget.MyRefreshLottieHeader;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * @ClassName: homeFragment
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/21 11:43
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, MainViewModel> implements OnRefreshLoadMoreListener, View.OnClickListener {
    private Context context;
    private MyRefreshLottieHeader mRefreshLottieHeader;
    private String beikeURlWithLocation;

    private List<FilterHouseBean> list = new ArrayList<>();
    //创建基本线程池
    static ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    private Document document;
    private Timer timer;
    private List<FilterHouseResult.DataDTO> dataDTOList;
    private Timer timer1;

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        context = getContext();
        binding.tvClick.setOnClickListener(this);
        /**
         *字体设置
         */
        //设置 Header 为 贝塞尔雷达 样式
        mRefreshLottieHeader = new MyRefreshLottieHeader(context);
        setHeader(mRefreshLottieHeader);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshLoadMoreListener(this);
        viewModel.getKeyCustomers();
        binding.tvNum.setText("0");
        binding.rtv.setText("0");
        WebSettings webSettings = binding.webView.getSettings();
// 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        binding.webView.loadUrl("file:///android_asset/occalljs.html");
        timer = new Timer();
        timer1 = new Timer();
    }

    public void getHouseList(final String beikeURlWithLocation, final KeynoteClientBean keyData) {
        final String BeiKeUrl = "https://cq.fang.ke.com";
        parseDocument(beikeURlWithLocation, keyData, BeiKeUrl);
    }

    private void parseDocument(final String beikeURlWithLocation, final KeynoteClientBean keyData, final String beiKeUrl) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = beikeURlWithLocation;
                try {

                    document = Jsoup.connect(url).get();
                    Log.d("TTTT", "jsoup:" + document);
                    // TODO Auto-generated method stub
                    Elements elementsByClass = document.getElementsByClass("resblock-have-find");
                    String value = elementsByClass.get(0).getElementsByClass("value").get(0).text();
                    int resultCount = Integer.parseInt(value);
                    Elements lis = document.getElementsByClass("resblock-list post_ulog_exposure_scroll has-results");
                    int n = 0;
                    if (resultCount >= 6) {
                        n = 6;
                    } else {
                        n = lis.size();
                    }
                    Log.e("n数量", "n数量" + n + "id" + keyData.getNum());
                    ArrayList<House> houses = new ArrayList<>();
                    houses.clear();
                    if (lis.size() > 0) {
                        for (int i = 0; i < n; i++) {
                            House house = new House();
                            Element li = lis.get(i);

                            Element locationA = li.getElementsByClass("resblock-location").get(0);
                            Element imgA = li.getElementsByClass("resblock-img-wrapper").get(0);
                            String imgUrl = imgA.getElementsByTag("img").get(0).attr("data-original");
                            Element houseA = li.getElementsByClass("resblock-room").get(0);
                            String locationStr = locationA.text();
                            String href = imgA.attr("href");
                            String title = imgA.attr("title");
                            String detailUrl = beiKeUrl + href;
                            // 楼盘户型
                            String houseType = "";
                            Elements spans = houseA.getElementsByTag("span");
                            for (int j = 0; j < spans.size(); j++) {
                                Element span = spans.get(j);

                                if (!span.getClass().equals("area")) {
                                    houseType = houseType + span.text();
                                }
                            }
                            // 房屋面积
                            String houseArea = "";
                            if (houseA.getElementsByClass("area").size() == 1) {
                                houseArea = houseA.getElementsByClass("area").get(0).text();
                            }
                            // 楼盘单价
                            String priceStr = li.getElementsByClass("number").get(0).text();
                            String priceDesc = "";
                            if (li.getElementsByClass("desc").size() == 1) {
                                priceDesc = li.getElementsByClass("desc").get(0).text();
                            }
                            // 楼盘总价
                            String priceSecond = "";
                            if (li.getElementsByClass("second").size() == 1) {
                                priceSecond = li.getElementsByClass("second").get(0).text();
                            }
                            house.setArea(houseArea);
                            house.setDetailUrl(detailUrl);
                            house.setImgUrl(imgUrl);
                            house.setName(title);
                            house.setLocation(locationStr);
                            house.setPriceFirst(priceStr + priceDesc);
                            house.setPriceSecond(priceSecond);
                            house.setType(houseType);
                            Log.e("househouse", "" + house);
                            houses.add(house);
                        }

                        FilterHouseBean filterHouseBean = new FilterHouseBean();
                        ImportantBean.DataDTO dataDTO = keyData.getDataDTO();

                        filterHouseBean.setHouseList(houses);
                        filterHouseBean.setClientId(keyData.getDataDTO().getId());
                        filterHouseBean.setClientUpdateTime(dataDTO.getCreateTime());
                        filterHouseBean.setArea(dataDTO.getArea());
                        filterHouseBean.setAvatar(dataDTO.getAvatar());
                        filterHouseBean.setCity(dataDTO.getCity());
                        filterHouseBean.setCreateTime(dataDTO.getCreateTime());
                        filterHouseBean.setIsprivatephone(dataDTO.getIsprivatephone());
                        filterHouseBean.setName(dataDTO.getName());
                        filterHouseBean.setPhone(dataDTO.getPhone());
                        filterHouseBean.setRemark(dataDTO.getRemark());
                        filterHouseBean.setSex(dataDTO.getSex());
                        list.add(filterHouseBean);
                        Log.e("keyData", "" + keyData.getNum());
                        Log.e("keylist", "" + list.size());
                    }
                } catch (Exception interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }).start();
    }
    static boolean isEnd = false; //控制TimerTask的结束标识
    static int count = 0; //循环计数器


    @Override
    public void initViewObservable() {
        viewModel.importTrantMutableLiveData.observe(this, new Observer<List<ImportantBean.DataDTO>>() {
            @Override
            public void onChanged(final List<ImportantBean.DataDTO> dataDTOS) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        viewModel.getFilterClientHouse(list);
                        System.out.println("执行了1次" + list.size());
                    }
                }, dataDTOS.size()*1000+5000);
                count=0;
                if(timer1==null){
                    timer1=new Timer();
                }
                timer1.schedule(new TimerTask(){
                    public void run(){
                        //延时或循环执行的内容
                        if(dataDTOS.size()>count){
                            beikeURlWithLocation = getBeikeURlWithLocation(dataDTOS.get(count));
                            final KeynoteClientBean keynoteClientBean = new KeynoteClientBean();
                            keynoteClientBean.setDataDTO(dataDTOS.get(count));
                            keynoteClientBean.setNum(count);
                            threadPoolExecutor.execute(new SubThread(count, keynoteClientBean));
                            Log.e("beikeURlWithLocation", beikeURlWithLocation);
                            count++;
                        }
                    }
                }, 3000,1000);
            }
        });
        viewModel.filterHouseResultMutableLiveData.observe(this, new Observer<List<FilterHouseResult.DataDTO>>() {
            @Override
            public void onChanged(List<FilterHouseResult.DataDTO> dataDTOS) {
                dataDTOList = dataDTOS;
                binding.refreshLayout.finishRefresh(true);//传入false表示刷新失败
                for(int i=0;i<dataDTOList.size();i++){
                    if(dataDTOList.get(i).getHouseList().size()==0){
                        dataDTOList.remove(i);
                    }
                }
                LiveDataBus.get().with(AppConstans.BusTag.ADDBEAN).setValue(dataDTOList);
            }
        });

        viewModel.filterSize.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e("filterSize", "" + integer.toString());
                binding.tvNum.setText(integer + "");
                binding.rtv.setText(integer + "");
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {

    }

    /**
     * @author nineday
     */
    public class SubThread extends Thread {

        private final int i;
        private KeynoteClientBean keynoteClientBean;

        public SubThread(int i, KeynoteClientBean keynoteClientBean) {
            this.i = i;
            this.keynoteClientBean = keynoteClientBean;
        }

        @Override
        public void run() {
            getHouseList(beikeURlWithLocation, keynoteClientBean);
        }
    }

    public static String object2json(Object obj) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }


    /**
     * @param dataDTO 根据客户获取贝壳url
     * @return 贝壳url
     */
    public String getBeikeURlWithLocation(ImportantBean.DataDTO dataDTO) {
        String bkUrl = "https://cq.fang.ke.com/loupan/";


        StringBuilder urlStringBuilder = new StringBuilder();

        String pingYin = PingYinUtil.getPingYin(dataDTO.getArea());

        if (StringUtils.equals("yuzhongqu", pingYin)) {
            pingYin = pingYin.substring(0, 7);

        }
        Log.e("pingYin", pingYin);
        urlStringBuilder.append(bkUrl);

        urlStringBuilder.append(pingYin + "/");
        String builtAreaStr = "";
        Integer builtArea = Integer.parseInt(dataDTO.getBuiltArea());
        /**
         *
         * 面积
         */

        if (builtArea == 1) {
            builtAreaStr = "bba0eba30";
        } else if (builtArea == 2) {
            builtAreaStr = "bba30eba50";
        } else if (builtArea == 3) {
            builtAreaStr = "bba50eba70";
        } else if (builtArea == 4) {
            builtAreaStr = "bba70eba90";
        } else if (builtArea == 5) {
            builtAreaStr = "bba90eba120";
        } else if (builtArea == 6) {
            builtAreaStr = "bba120eba150";
        } else if (builtArea == 7) {
            builtAreaStr = "bba150eba200";
        } else if (builtArea == 8) {
            builtAreaStr = "bba200eba300";
        } else if (builtArea == 9) {
            builtAreaStr = "bba300eba100000";
        }
        urlStringBuilder.append(builtAreaStr);
        Integer budget = Integer.parseInt(dataDTO.getBudget
                ());

        /**
         *
         * 总价
         */
        String budgetStr = "";
        if (budget == 1) {
            budgetStr = "bp0ep40";
        } else if (budget == 2) {
            budgetStr = "bp40ep60";
        } else if (budget == 3) {
            budgetStr = "bp60ep80";
        } else if (budget == 4) {
            budgetStr = "bp80ep100";
        } else if (budget == 5) {
            budgetStr = "bp100ep150";
        } else if (budget == 6) {
            budgetStr = "bp150ep200";
        } else if (budget == 7) {
            budgetStr = "bp200ep300";
        } else if (budget == 8) {
            budgetStr = "bp300ep100000";
        }
        urlStringBuilder.append(budgetStr);
        String houseType = dataDTO.getHouseType();

        String[] houseTypes = houseType.split(",");

        String houseTypeStr = "";
        for (int i = 0; i < houseTypes.length; i++) {
            if (StringUtils.equals(houseTypes[i], "1")) {
                houseTypeStr = "l1";
            } else if (StringUtils.equals(houseTypes[i], "2")) {
                houseTypeStr = "l2";
            } else if (StringUtils.equals(houseTypes[i], "3")) {
                houseTypeStr = "l3";
            } else if (StringUtils.equals(houseTypes[i], "4")) {
                houseTypeStr = "l4";
            } else if (StringUtils.equals(houseTypes[i], "5")) {
                houseTypeStr = "l5";
            } else if (StringUtils.equals(houseTypes[i], "6")) {
                houseTypeStr = "l6";
            }
            urlStringBuilder.append(houseTypeStr + "/");
        }


        return urlStringBuilder.toString();

    }

    public List<FilterHouseResult.DataDTO> getDataDTOList() {
        return dataDTOList;
    }


    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        if (binding.refreshLayout.isRefreshing()) {
            binding.refreshLayout.finishRefresh();
        }
        binding.refreshLayout.setRefreshHeader(header);
    }

    @Override
    public void onClick(View v) {
            MatchingActivity.actionStart(context, dataDTOList);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        Log.e("onRefresh", "onRefresh");
        list.clear();
        viewModel.getKeyCustomers();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                viewModel.getFilterClientHouse(list);
//                System.out.println("执行了1次" + list.size());
//            }
//        }, 8000);
    }

    @Override
    public void reload() {

    }
}
