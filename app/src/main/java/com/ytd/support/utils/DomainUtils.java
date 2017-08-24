package com.ytd.support.utils;

import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.support.constants.fixed.UrlConstants;

/**
 * Created by tanlifei on 2017/8/22.
 */

public class DomainUtils {


    private static IConfigPresenter presenter;
    private static volatile DomainUtils instance = null;

    // private constructor suppresses
    private DomainUtils(){
    }

    public static DomainUtils getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (DomainUtils.class) {
                if (instance == null) {
                    instance = new DomainUtils();
                    presenter =new ConfigPresenterImpl();
                }
            }
        }

        return instance;
    }

    public String domain(){
        try {
            if(presenter==null){
                presenter =new ConfigPresenterImpl();
            }
            ConfigBean bean = presenter.find();
            if(null!=bean){
                return  bean.getUrl();
            }else{
                return UrlConstants.DOMAIN_NAME;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return UrlConstants.DOMAIN_NAME;
        }
    }


}
