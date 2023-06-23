/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springfparamework.beans.factory.annotation.Autowired;
import com.galaxy.theapp.dao.AppUserDao;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserForgetPasswordOtp;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.model.InAppPurchase;
import com.galaxy.theapp.service.AppUserService;

/**
 * This <java>class</java> AppUserServiceImpl use to perform all our service
 * related logics for the TaUser.
 * 
 * @author param Sheikh
 * @GWL
 */

public class AppUserServiceImpl implements AppUserService
{

    public static final Logger LOG = Logger.getLogger(AppUserServiceImpl.class);

    @Autowired
    AppUserDao appUserDao;

    /**
     * This method createNewTaUser on DB.
     * 
     */

    public AppUser createNewTaUser(AppUser taUserModel) throws Exception
    {
        AppUser taUser = new AppUser();
        try
        {
            taUser = appUserDao.createNewTaUser(taUserModel);
            return taUser;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUser;
    }

    /**
     * This method updateProfile on DB.
     * 
     */

    public AppUser updateProfile(AppUser taUserModel) throws Exception
    {
        AppUser taUser = new AppUser();
        try
        {
            taUser = appUserDao.updateProfile(taUserModel);
            return taUser;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUser;
    }

    /**
     * This method checkValidRequestForUserLogin().
     * 
     */

    public AppUser checkValidRequestForUserLogin(AppUser taUserModel) throws Exception
    {
        AppUser taUserLoginModel = new AppUser();
        try
        {
            taUserLoginModel = appUserDao.checkValidRequestForUserLogin(taUserModel);
            return taUserLoginModel;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserLoginModel;
    }

    /**
     * This method getUserProfile() from DB.
     * 
     */
    public AppUser getUserProfile(int userId) throws Exception
    {
        AppUser getUserProfile = new AppUser();
        try
        {
            getUserProfile = appUserDao.getUserProfile(userId);
            return getUserProfile;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getUserProfile;
    }

    /**
     * This method getUserList() return all userList.
     * 
     */

    public ArrayList<AppUser> getUserList(int userId, int pageNo, int recordPerPage, String searchByAlphabet,
            String taskId, String senderType) throws Exception
    {
        ArrayList<AppUser> getUserList = new ArrayList<AppUser>();
        try
        {
            getUserList = appUserDao.getUserList(userId, pageNo, recordPerPage, searchByAlphabet, taskId,
                    senderType);
            return getUserList;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getUserList;

    }

    /**
     * This method checkNewGeneratedToken() validate the newly generated token
     * from DB.
     * 
     */
    public AppUserToken checkNewGenaratedToken(String token) throws Exception
    {
        AppUserToken taUserToken = null;
        try
        {
            taUserToken = appUserDao.checkNewGeneratedToken(token);
            return taUserToken;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserToken;

    }

    /**
     * This method getUserId() fetch UserId from DB.
     * 
     */
    public AppUserToken getUserId(String token) throws Exception
    {
        AppUserToken taUser = null;
        try
        {
            taUser = appUserDao.getUserId(token);
            return taUser;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUser;
    }

    /**
     * This method getTotalRecords() fetch TotalRecords from DB.
     * 
     */
    public Long getTotalRecords(int userId)
    {
        long totalRecords = 0;
        try
        {
            totalRecords = appUserDao.getTotalRecords(userId);
            return totalRecords;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return totalRecords;

    }

    /**
     * This method checkEmailId() check EmailId from DB.
     * 
     */
    public boolean checkEmailId(String email) throws Exception
    {
        boolean existEmailId = false;
        try
        {
            existEmailId = appUserDao.checkEmailId(email);
            return existEmailId;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return existEmailId;
    }

    /**
     * This method updateToken() update token from DB.
     * 
     */
    public int updateToken(String token, String emailId, String password) throws Exception
    {
        int updateToken = 0;
        try
        {
            updateToken = appUserDao.updateToken(token, emailId, password);
            return updateToken;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return updateToken;
    }

    /**
     * This method createNewTaUserToken() create token from DB.
     * 
     */
    public AppUserToken createNewTaUserToken(AppUserToken taUserTokenModel) throws Exception
    {
        AppUserToken taUserToken = new AppUserToken();
        try
        {
            taUserToken = appUserDao.createNewTaUserToken(taUserTokenModel);
            return taUserToken;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserToken;
    }

    /**
     * This method getUserTokenList() get TokenList from DB.
     * 
     */
    public ArrayList<AppUserToken> getUserTokenList(int userId, String deviceToken) throws Exception
    {
        ArrayList<AppUserToken> getUserTokenList = new ArrayList<AppUserToken>();
        try
        {
            getUserTokenList = appUserDao.getUserTokenList(userId, deviceToken);
            return getUserTokenList;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getUserTokenList;
    }

    /**
     * This method updateAppToken() update TokenList from DB.
     * 
     */
    public int updateAppToken(String token, int userId, String deviceToken) throws Exception
    {
        int updateToken = 0;
        try
        {
            updateToken = appUserDao.updateAppToken(token, userId, deviceToken);
            return updateToken;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return updateToken;
    }

    /**
     * This method saveNewDeviceToken() save NewDeviceToken from DB.
     * 
     */
    public AppUserToken saveNewDeviceToken(Integer userId, String token, String deviceToken) throws Exception
    {
        AppUserToken appUserToken = new AppUserToken();
        try
        {
            appUserToken = appUserDao.saveNewDeviceToken(userId, token, deviceToken);
            return appUserToken;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserToken;
    }

    /**
     * This method appUserLogout() logout User from DB.
     * 
     */
    public boolean appUserLogout(AppUserToken appUserToken) throws Exception
    {
        boolean status = false;
        try
        {
            status = appUserDao.appUserLogout(appUserToken);
            return status;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * This method Add the inAppPurchased() Coins to DB.
     * 
     */
    public InAppPurchase inAppPurchased(InAppPurchase inAppPurchase) throws Exception
    {
        InAppPurchase inPurchase = new InAppPurchase();
        try
        {
            inPurchase = appUserDao.inAppPurchased(inAppPurchase);
            return inPurchase;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inPurchase;
    }

    /**
     * This method deleteTask ,delete the task to DB.
     * 
     */
    public AppUserTask deleteTask(AppUserTask appUserTask) throws Exception
    {
        AppUserTask appTask = new AppUserTask();
        try
        {
            appTask = appUserDao.deleteTask(appUserTask);
            return appTask;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appTask;
    }

    /**
     * This method checkOldPasswordExist check to DB.
     * 
     */
    public AppUser checkOldPasswordExist(int userId, String oldPassword) throws Exception
    {
        AppUser checkOldPassword = new AppUser();
        try
        {
            checkOldPassword = appUserDao.checkOldPasswordExist(userId, oldPassword);
            return checkOldPassword;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return checkOldPassword;
    }

    /**
     * This method updateNewPassword update new pass to DB.
     * 
     */
    public boolean updateNewPassword(int userId, String newPassword) throws Exception
    {
        boolean status = false;
        try
        {
            int i = appUserDao.updateNewPassword(userId, newPassword);
            if (i == 1)
            {
                status = true;
            } else
            {
                status = false;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * This method AppUserCoinBalance update the coin for the user to DB.
     * 
     */
    public AppUserCoinBalance getCoinBalance(int userId) throws Exception
    {
        AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
        try
        {
            appCoinBalance = appUserDao.getCoinBalance(userId);
            return appCoinBalance;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appCoinBalance;
    }

    /**
     * This method getInAppCoinBalance get the coin for the user to DB.
     * 
     */
    public InAppPurchase getInAppCoinBalance(int userId) throws Exception
    {
        InAppPurchase inaPurchase = new InAppPurchase();
        try
        {
            inaPurchase = appUserDao.getInAppCoinBalance(userId);
            return inaPurchase;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inaPurchase;
    }

    /**
     * This method getcreditAmountCoinBalance get the coin for the user to DB.
     * 
     */
    public BigDecimal getcreditAmountCoinBalance(int userId) throws Exception
    {
        BigDecimal cparamount = null;
        try
        {
            cparamount = appUserDao.getcreditAmountCoinBalance(userId);
            return cparamount;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return cparamount;
    }

    /**
     * This method getdebitAmountCoinBalance get the coin for the user to DB.
     * 
     */
    public BigDecimal getdebitAmountCoinBalance(int userId) throws Exception
    {
        BigDecimal cparamount = null;
        try
        {
            cparamount = appUserDao.getdebitAmountCoinBalance(userId);
            return cparamount;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return cparamount;
    }

    /**
     * This method getUserDetail get the user details from DB.
     * 
     */
    public AppUser getUserDetail(String email) throws Exception
    {
        AppUser appUser = new AppUser();
        try
        {
            appUser = appUserDao.getUserDetail(email);
            return appUser;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUser;
    }

    /**
     * This method AppUserForgetPasswordOtp generate OTP from DB.
     * 
     */
    public AppUserForgetPasswordOtp SaveOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp)
    {
        AppUserForgetPasswordOtp appUserForgetPasswordOtp2 = new AppUserForgetPasswordOtp();
        try
        {
            appUserForgetPasswordOtp2 = appUserDao.saveOtp(appUserForgetPasswordOtp);
            return appUserForgetPasswordOtp2;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserForgetPasswordOtp2;
    }

    /**
     * This method checkValidRequestForUserLoginUsingOtp login using Otp OTP
     * from DB.
     * 
     */
    public AppUserForgetPasswordOtp checkValidRequestForUserLoginUsingOtp(Integer userId, String Otp)
    {
        AppUserForgetPasswordOtp taUserLoginModel = new AppUserForgetPasswordOtp();
        try
        {
            taUserLoginModel = appUserDao.checkValidRequestForUserLoginUsingOtp(userId, Otp);
            return taUserLoginModel;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserLoginModel;
    }

    /**
     * This method removeOtp remove OTP from DB.
     * 
     */
    public AppUserForgetPasswordOtp removeOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp)
            throws Exception
    {
        AppUserForgetPasswordOtp remove_otp = new AppUserForgetPasswordOtp();
        try
        {
            remove_otp = appUserDao.removeOtp(appUserForgetPasswordOtp);
            return remove_otp;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return remove_otp;
    }
}
