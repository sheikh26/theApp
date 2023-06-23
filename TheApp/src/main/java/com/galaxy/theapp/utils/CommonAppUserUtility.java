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
import org.apache.log4j.Logger;
import com.galaxy.theapp.bean.AppUserForgetPasswordOtpBean;
import com.galaxy.theapp.bean.AppUserTaskBean;
import com.galaxy.theapp.bean.AppUserTokenBean;
import com.galaxy.theapp.bean.InAppPurchaseBean;
import com.galaxy.theapp.bean.OpenFireUserBean;
import com.galaxy.theapp.bean.AppUserBean;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.model.AppUserForgetPasswordOtp;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.model.InAppPurchase;
import com.galaxy.theapp.model.OpenFireUser;
import com.galaxy.theapp.model.AppUser;

/**
 * This <java>class</java> CommonUserUtility set all the beans related to User.
 * 
 * @author param Sheikh
 * @GWL
 */

public class CommonAppUserUtility
{
    public static final Logger LOG = Logger.getLogger(CommonAppUserUtility.class);
    static Properties prop = new Properties();
    static CommonUtility cu = new CommonUtility();

    public static boolean validateupdateProfileRequest(AppUserBean appUserBean)
    {
        if (appUserBean.getFirstName() != null && appUserBean.getLastName() != null
                && appUserBean.getAddress1() != null && appUserBean.getProfilePic() != null)

        {
            return true;
        }
        return false;

    }

    public static boolean validateUserRegistrationRequest(AppUserBean appUserBean)
    {
        if (appUserBean.getEmail() != null && appUserBean.getPassword() != null
                && appUserBean.getFirstName() != null && appUserBean.getLastName() != null)

        {
            return true;
        }
        return false;

    }

    public static AppUser prepareTaUserFromTaUserBean(AppUserBean appUserBean)
    {
        LOG.info("prepareTaUserFromTaUserBean() call");
        AppUser taUserModel = new AppUser();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
        taUserModel.setEmail(appUserBean.getEmail());
        taUserModel.setPassword(appUserBean.getPassword());
        taUserModel.setFirstName(appUserBean.getFirstName());
        taUserModel.setLastName(appUserBean.getLastName());
        taUserModel.setCreatedOn(formatter.format(date));
        taUserModel.setIsOtp(appUserBean.getIsOtp());

        return taUserModel;
    }

    public static AppUserToken prepareTaUserTokenFromTaUserTokenBean(AppUserTokenBean appUserTokenBean)
    {
        LOG.info("prepareTaUserTokenFromTaUserTokenBean() call");
        AppUserToken taUserTokenModel = new AppUserToken();
        taUserTokenModel.setDeviceToken(appUserTokenBean.getDeviceToken());
        taUserTokenModel.setAppToken(appUserTokenBean.getAppToken());
        taUserTokenModel.setUserId(appUserTokenBean.getUserId());

        return taUserTokenModel;
    }

    public static OpenFireUser prepareOpenFireUserFromOpenFireUserBean(OpenFireUserBean openFireUserBean)
    {
        LOG.info("prepareOpenFireUserFromOpenFireUserBean() call");
        OpenFireUser openFireUser = new OpenFireUser();
        openFireUser.setCreationDate(openFireUserBean.getCreationDate());
        openFireUser.setEmail(openFireUserBean.getEmail());
        openFireUser.setModificationDate(openFireUserBean.getModificationDate());
        openFireUser.setPlainPassword(openFireUserBean.getPlainPassword());
        openFireUser.setUsername(openFireUserBean.getUsername());
        openFireUser.setName(openFireUserBean.getName());
        return openFireUser;
    }

    public static AppUser prepareTaUserModelFromTaUserBeanForUpdateProfile(AppUserBean appUserBean)
    {
        LOG.info("prepareTaUserModelFromTaUserBeanForUpdateProfile() call");
        AppUser taUserModel = new AppUser();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
        taUserModel.setAddress1(appUserBean.getAddress1());
        taUserModel.setAddress2(appUserBean.getAddress2());
        taUserModel.setFirstName(appUserBean.getFirstName());
        taUserModel.setLastName(appUserBean.getLastName());
        taUserModel.setProfilePic(appUserBean.getProfilePic());
        taUserModel.setMobileNo(appUserBean.getMobileNo());
        taUserModel.setUserId(appUserBean.getUserId());
        taUserModel.setCraetedBy(appUserBean.getUserId());
        taUserModel.setCreatedOn(formatter.format(date));
        taUserModel.setModifiedBy(appUserBean.getUserId());
        taUserModel.setModifiedOn(formatter.format(date));
        return taUserModel;
    }

    public static boolean checkValidRequestForUserLogin(AppUserBean appUserBean)
    {
        LOG.info("checkValidRequestForUserLogin() call");
        if (appUserBean.getEmail() != null && appUserBean.getPassword() != null)
        {
            return true;
        }
        return false;
    }

    public static AppUser prepareTaUserModelFromTaUserBeanForUserLogin(AppUserBean appUserBean)
    {
        LOG.info("prepareTaUserModelFromTaUserBeanForUserLogin() call");
        AppUser taUserLoginModel = new AppUser();
        taUserLoginModel.setEmail(appUserBean.getEmail());
        taUserLoginModel.setPassword(appUserBean.getPassword());
        return taUserLoginModel;
    }

    public static boolean validategetUserList(String token, int pageNo, int recordPerPage)
    {
        if (token != null && pageNo != 0 && recordPerPage != 0)

        {
            return true;
        }
        return false;

    }

    public static boolean checkValidategetProfile(String token)
    {
        if (token != null)

        {
            return true;
        }
        return false;

    }

    public static boolean checkValidRequestForUserLogout(AppUserTokenBean appUserTokenBean)
    {
        if (appUserTokenBean.getAppToken() != null && appUserTokenBean.getDeviceToken() != null
                && appUserTokenBean.getUserId() != 0)
        {
            return true;
        }

        return false;
    }

    public static AppUserToken prepareTaUserModelFromTaUserBeanForUserLogout(AppUserTokenBean appUserTokenBean)
    {
        AppUserToken appUserToken = new AppUserToken();
        appUserToken.setAppToken(appUserTokenBean.getAppToken());
        appUserToken.setDeviceToken(appUserTokenBean.getDeviceToken());
        appUserToken.setUserId(appUserTokenBean.getUserId());
        return appUserToken;
    }

    public static boolean checkValidRequestForInAppPurchase(InAppPurchaseBean innAppPurchaseBean)
    {
        if (innAppPurchaseBean.getInAppProductId() != null && innAppPurchaseBean.getPointPurchased() != null
                && innAppPurchaseBean.getTransactionDate() != null
                && innAppPurchaseBean.getTransactionId() != null && innAppPurchaseBean.getUserId() != null)
        {
            return true;
        }

        return false;
    }

    public static boolean checkValidRequestForDeleteTask(AppUserTaskBean appUserTaskBean)
    {
        if (appUserTaskBean.getUserId() != null && appUserTaskBean.getUserTaskId() != null
                && appUserTaskBean.getTaskStatus() != null)
        {
            return true;
        }

        return false;
    }

    public static InAppPurchase prepareInAppModelFromInAppBeanForInAppPurchase(
            InAppPurchaseBean inAppPurchaseBean)
    {
        InAppPurchase inAppPurchase = new InAppPurchase();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
        inAppPurchase.setInAppProductId(inAppPurchaseBean.getInAppProductId());
        inAppPurchase.setPointPurchased(inAppPurchaseBean.getPointPurchased());
        inAppPurchase.setTransactionId(inAppPurchaseBean.getTransactionId());
        inAppPurchase.setTransactionDate(inAppPurchaseBean.getTransactionDate());
        inAppPurchase.setUserId(inAppPurchaseBean.getUserId());

        inAppPurchase.setCraetedBy(inAppPurchaseBean.getUserId());
        inAppPurchase.setCreatedOn(formatter.format(date));
        return inAppPurchase;
    }

    public static AppUserTask prepareInAppModelFromInAppBeanForDeleteTask(AppUserTaskBean appUserTaskBean)
    {
        AppUserTask appUserTask = new AppUserTask();

        appUserTask.setUserId(appUserTaskBean.getUserId());
        appUserTask.setUserTaskId(appUserTaskBean.getUserTaskId());
        appUserTask.setTaskStatus(appUserTaskBean.getTaskStatus());

        return appUserTask;
    }

    public static boolean checkValidRequestForUserChangePassword(AppUserTokenBean appUserTokenBean)
    {
        if (appUserTokenBean.getUserId() != 0 && appUserTokenBean.getAppToken() != null)
        {
            return true;
        }
        return false;
    }

    public static AppUserForgetPasswordOtp prepareAppUserForgetPasswordOtpModelFromAppUserForgetPasswordOtpBean(
            AppUserForgetPasswordOtpBean appUserForgetPasswordOtpBean)
    {
        AppUserForgetPasswordOtp appUserForgetPasswordOtp = new AppUserForgetPasswordOtp();
        appUserForgetPasswordOtp.setDeviceToken(appUserForgetPasswordOtpBean.getDeviceToken());
        appUserForgetPasswordOtp.setOtp(appUserForgetPasswordOtpBean.getOtp());
        appUserForgetPasswordOtp.setUserId(appUserForgetPasswordOtpBean.getUserId());
        return appUserForgetPasswordOtp;
    }

}
