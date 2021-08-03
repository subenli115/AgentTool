package com.moxi.agenttool.source.local;


import com.moxi.agenttool.global.SPKeyGlobal;
import com.moxi.agenttool.source.LocalDataSource;

import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 本地数据源，可配合Room框架使用
 * Created by feng wen jun on 2019/3/26.
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private LocalDataSourceImpl() {
        //数据库Helper构建
    }

    @Override
    public void saveUserName(String userName) {
        SPUtils.getInstance().put(SPKeyGlobal.USERNAME, userName);
    }

    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put(SPKeyGlobal.PASSWORD, password);
    }

    @Override
    public void saveToken(String token) {
        SPUtils.getInstance().put(SPKeyGlobal.TOKEN, token);
    }

    @Override
    public void saveRefreshToken(String reToken) {
        SPUtils.getInstance().put(SPKeyGlobal.R_TOKEN, reToken);
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString(SPKeyGlobal.USERNAME);
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString(SPKeyGlobal.PASSWORD);
    }

    @Override
    public String getToken() {
        return null;
    }


}
