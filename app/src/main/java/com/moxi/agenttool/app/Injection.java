package com.moxi.agenttool.app;

import com.moxi.agenttool.data.DemoRepository;
import com.moxi.agenttool.data.source.HttpDataSource;
import com.moxi.agenttool.data.source.LocalDataSource;
import com.moxi.agenttool.data.source.http.HttpDataSourceImpl;
import com.moxi.agenttool.data.source.http.service.DemoApiService;
import com.moxi.agenttool.data.source.local.LocalDataSourceImpl;
import com.moxi.agenttool.utils.RetrofitClient;


/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。（根据项目实际情况搭建，千万不要为了架构而架构）
 * Created by goldze on 2019/3/26.
 */
public class Injection {
    public static DemoRepository provideDemoRepository() {
        //网络API服务
        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return DemoRepository.getInstance(httpDataSource, localDataSource);
    }
}
