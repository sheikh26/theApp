/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import com.galaxy.theapp.bean.AppErrorBean;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.model.AppError;
import org.apache.log4j.Logger;

/**
 * This <java>class</java> CommonErrorUtility set all the beans related to
 * Error.
 * 
 * @author param Sheikh
 * @GWL
 */

public class CommonAppErrorUtility
{
    public static final Logger LOG = Logger.getLogger(CommonAppErrorUtility.class);
    static Properties prop = new Properties();
    static CommonUtility cu = new CommonUtility();

    public static AppError prepareTaUserFromTaUserBean(AppErrorBean appErrorBean)
    {
        Date date = new Date();
        LOG.info("prepareTaErrorBean() call");
        AppError ta_ERROR = new AppError();
        ta_ERROR.setErrorDescription(appErrorBean.getErrorDescription());
        ta_ERROR.setEvent(appErrorBean.getEvent());
        ta_ERROR.setUserID(appErrorBean.getUserID());
        ta_ERROR.setCraetedBy(Integer.parseInt(appErrorBean.getUserID()));
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
        ta_ERROR.setCreatedOn(formatter.format(date));

        return ta_ERROR;
    }

    public static boolean validateErrorRequest(AppErrorBean appErrorBean)
    {
        if ((appErrorBean.getErrorDescription() != null) && (appErrorBean.getEvent() != null)
                && (appErrorBean.getUserID() != null))
        {
            return true;
        }
        return false;
    }
}
