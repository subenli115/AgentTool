package com.moxi.agenttool.utils;

import android.util.Log;

import com.github.lany192.pinyin.PingYinUtil;
import com.moxi.agenttool.ui.bean.FilterHouseBean;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.ImportantBean;
import com.moxi.agenttool.ui.bean.KeynoteClientBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;

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
 * 作者： 周旭 on 2017年9月20日 0020.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class MatchUtils {
    private static List<FilterHouseBean> list = new ArrayList<>();
    private static Timer timer=new Timer();;
    private static Timer timer1=new Timer();;
    //创建基本线程池
    static ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    private static int count;
    private static String beikeURlWithLocation;
    private static Document document;

    /**
     * @Date 2018/3/14 15:12
     * @Description
     */
    public static void getMatchData(final List<ImportantBean.DataDTO> dataDTOS, final MainViewModel viewModel) {
        list.clear();
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

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                viewModel.getFilterClientHouse(list);
            }
        }, dataDTOS.size()*1000+5000);
    }

    /**
     * @author nineday
     */
    public static class SubThread extends Thread {

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

    public static void getHouseList(final String beikeURlWithLocation, final KeynoteClientBean keyData) {
        final String BeiKeUrl = "https://cq.fang.ke.com";
        parseDocument(beikeURlWithLocation, keyData, BeiKeUrl);
    }

    private static void parseDocument(final String beikeURlWithLocation, final KeynoteClientBean keyData, final String beiKeUrl) {

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
                } catch (Exception interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }).start();
    }



    /**
     * @param dataDTO 根据客户获取贝壳url
     * @return 贝壳url
     */
    public static String getBeikeURlWithLocation(ImportantBean.DataDTO dataDTO) {
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
}
