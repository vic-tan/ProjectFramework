package com.ytd.support.utils;

import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.http.okhttp.builder.PostFormBuilder;
import com.tlf.basic.utils.MapUtils;
import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.support.constants.fixed.UrlConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanlifei on 2017/8/25.
 */

public class HttpRequestUtils {


    private static IConfigPresenter presenter;
    private static volatile HttpRequestUtils instance = null;

    // private constructor suppresses
    private HttpRequestUtils() {
    }

    public static HttpRequestUtils getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (DomainUtils.class) {
                if (instance == null) {
                    instance = new HttpRequestUtils();
                    presenter = new ConfigPresenterImpl();
                }
            }
        }

        return instance;
    }

    public String domain() {
        try {
            if (presenter == null) {
                presenter = new ConfigPresenterImpl();
            }
            ConfigBean bean = presenter.find();
            if (null != bean) {
                return bean.getUrl();
            } else {
                return UrlConstants.DOMAIN_NAME;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return UrlConstants.DOMAIN_NAME;
        }
    }


    public Map<String, String> headers(Map<String, String> headerParams) {
        if (presenter == null) {
            presenter = new ConfigPresenterImpl();
        }
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        if (!MapUtils.isEmpty(headerParams)) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }


    public PostFormBuilder postTokenFormBuilder(String url, Map<String, String> map, int urlTag) {
        String reqUrl = "";
        if (urlTag == 1) {//在配置上取的全url
            reqUrl = url;
        } else if (urlTag == 2) {//重新获取本地url
            reqUrl = DomainUtils.getInstance().domain() + url;
        }
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(reqUrl).headers(headers(new HashMap<String, String>()));
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                postFormBuilder.addParams(entry.getKey(), entry.getValue());
            }
        }
        return postFormBuilder;
    }

    public PostFormBuilder postFormBuilder(String url) {
        return postFormBuilder(url, new HashMap<String, String>(), new HashMap<String, String>());
    }

    public PostFormBuilder postFormBuilder(String url, Map<String, String> map) {
        return postFormBuilder(url, map, new HashMap<String, String>());
    }




    public PostFormBuilder postFormBuilder(String url, Map<String, String> map, Map<String, String> headerParams) {
        if (MapUtils.isEmpty(headerParams)) {
            if (presenter == null) {
                presenter = new ConfigPresenterImpl();
            }
            String token = "Bearer " + presenter.find().getAccess_token();
            headerParams = new HashMap<>();
            headerParams.put("Authorization", token);
        }
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(DomainUtils.getInstance().domain() + url).headers(headers(headerParams));
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                postFormBuilder.addParams(entry.getKey(), entry.getValue());
            }
        }
        return postFormBuilder;
    }

    public PostFormBuilder postTestFormBuilder(String url) {
        return postTestFormBuilder(url, new HashMap<String, String>(), new HashMap<String, String>());
    }

    public PostFormBuilder postTestFormBuilder(String url, Map<String, String> map) {
        return postTestFormBuilder(url, map, new HashMap<String, String>());
    }

    public PostFormBuilder postTestFormBuilder(String url, Map<String, String> map, Map<String, String> headerParams) {
        if (MapUtils.isEmpty(headerParams)) {
            if (presenter == null) {
                presenter = new ConfigPresenterImpl();
            }
            String token = "Bearer " + presenter.find().getAccess_token();
            headerParams = new HashMap<>();
            headerParams.put("Authorization", token);
        }
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url("http://192.168.1.102:8080" + url).headers(headers(headerParams));
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                postFormBuilder.addParams(entry.getKey(), entry.getValue());
            }
        }
        return postFormBuilder;
    }

}
