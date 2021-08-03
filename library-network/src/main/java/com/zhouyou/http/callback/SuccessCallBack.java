/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhouyou.http.callback;

import com.zhouyou.http.exception.ApiException;

/**
 * <p>描述：成功的回调,默认可以使用该回调，不用关注其他回调方法</p>
 * 使用该回调默认只需要处理onSuccess方法既成功<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/29 10:06<br>
 * 版本： v2.0<br>
 */
public abstract class SuccessCallBack<T> extends SimpleCallBack<T> {

    @Override
    public void onError(ApiException e) {
        //若要显示异常，使用SimpleCallBack
//        ToastUtils.showShort(e.getMessage());
    }


}
