/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserForgetPasswordOtp;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.model.InAppPurchase;

/**
 * This <java>interface</java> AppUserDao interface has all the abstract methods
 * related to TaUser.
 * 
 * @author param Sheikh
 * @GWL
 */

public interface AppUserDao
{
    /**
     * This method createNewTaUserToken.
     * 
     * @paparam taUserTokenModel
     *            <code>AppUserToken</code> to be saved in database
     * @return AppUserToken <code>AppUserToken</code> saved in database
     * @throws Exception
     *             error while saving information in database
     */
    public AppUserToken createNewTaUserToken(AppUserToken taUserTokenModel);

    /**
     * This method createNewTaUser.
     * 
     * @paparam taUserModel
     *            <code>AppUser</code> to be saved in database
     * @return AppUser <code>AppUser</code> saved in database
     * @throws Exception
     *             error while saving information in database
     */
    public AppUser createNewTaUser(AppUser taUserModel);

    /**
     * This method inAppPurchased.
     * 
     * @paparam inAppPurchase
     *            <code>InAppPurchase</code> to be saved in database
     * @return InAppPurchase <code>InAppPurchase</code> saved in database
     * @throws Exception
     *             error while saving information in database
     */
    public InAppPurchase inAppPurchased(InAppPurchase inAppPurchase);

    /**
     * This method updateProfile.
     * 
     * @paparam taUserModel
     *            <code>AppUser</code> to be update in database
     * @return AppUser <code>AppUser</code> update in database
     * @throws Exception
     *             error while updateProfile information in database
     */
    public AppUser updateProfile(AppUser taUserModel);

    /**
     * This method checkValidRequestForUserLogin.
     * 
     * @paparam taUserModel
     *            <code>AppUser</code> to be check Valid Request for UserLogin
     *            in database
     * @return AppUser <code>AppUser</code> saved in database
     * @throws Exception
     *             error while checkValidRequestForUserLogin information in
     *             database
     */
    public AppUser checkValidRequestForUserLogin(AppUser taUserModel);

    /**
     * This method getUserProfile.
     * 
     * @paparam userId
     *            <code>AppUser</code> to be getUserProfile from database
     * @return AppUser <code>AppUser</code> getUserProfile from database
     * @throws Exception
     *             error while getUserProfile information in database
     */
    public AppUser getUserProfile(int userId);

    /**
     * This method getCoinBalance.
     * 
     * @paparam userId
     *            <code>AppUserCoinBalance</code> to be getCoinBalance from
     *            database
     * @return AppUserCoinBalance <code>AppUserCoinBalance</code> getCoinBalance
     *         from database
     * @throws Exception
     *             error while getCoinBalance information in database
     */
    public AppUserCoinBalance getCoinBalance(int userId);

    /**
     * This method getInAppCoinBalance.
     * 
     * @paparam userId
     *            <code>InAppPurchase</code> to be getInAppCoinBalance from
     *            database
     * @return InAppPurchase <code>InAppPurchase</code> getInAppCoinBalance from
     *         database
     * @throws Exception
     *             error while getInAppCoinBalance information in database
     */
    public InAppPurchase getInAppCoinBalance(int userId);

    /**
     * This method getcreditAmountCoinBalance.
     * 
     * @paparam userId
     *            <code>AppUserCoinBalance</code> to be
     *            getcreditAmountCoinBalance from database
     * @return BigDecimal <code>AppUserCoinBalance</code>
     *         getcreditAmountCoinBalance from database
     * @throws Exception
     *             error while getcreditAmountCoinBalance information in
     *             database
     */
    public BigDecimal getcreditAmountCoinBalance(int userId);

    /**
     * This method getdebitAmountCoinBalance.
     * 
     * @paparam userId
     *            <code>AppUserCoinBalance</code> to be
     *            getdebitAmountCoinBalance from database
     * @return BigDecimal <code>AppUserCoinBalance</code>
     *         getdebitAmountCoinBalance from database
     * @throws Exception
     *             error while getdebitAmountCoinBalance information in database
     */
    public BigDecimal getdebitAmountCoinBalance(int userId);

    /**
     * This method getUserList.
     * 
     * @paparam userId
     *            ,pageNo,recordPerPage,searchByAlphabet, taskId,senderType
     *            <code>AppUser</code> to be getUserList from database
     * @return AppUser <code>AppUser</code> getUserList from database
     * @throws Exception
     *             error while getUserList information in database
     */
    public ArrayList<AppUser> getUserList(int userId, int pageNo, int recordPerPage, String searchByAlphabet,
            String taskId, String senderType);

    /**
     * This method checkNewGeneratedToken.
     * 
     * @paparam token
     *            <code>AppUser</code> to be check NewGenerated Token from
     *            database
     * @return AppUserToken <code>AppUser</code> check New Generated Token from
     *         database
     * @throws Exception
     *             error while checkNewGeneratedToken information in database
     */
    public AppUserToken checkNewGeneratedToken(String token);

    /**
     * This method getUserId.
     * 
     * @paparam token
     *            <code>AppUserToken</code> to be getUserId from database
     * @return AppUserToken <code>AppUser</code> getUserId from database
     * @throws Exception
     *             error while getUserId information in database
     */

    public AppUserToken getUserId(String token);

    /**
     * This method getTotalRecords.
     * 
     * @paparam userId
     *            <code>AppUser</code> to be getTotalRecords from database
     * @return Long <code>AppUser</code> getTotalRecords from database
     * @throws Exception
     *             error while getTotalRecords information in database
     */

    public Long getTotalRecords(int userId);

    /**
     * This method checkEmailId.
     * 
     * @paparam email
     *            <code>AppUser</code> to be checkEmailId from database
     * @return boolean <code>AppUser</code> checkEmailId from database
     * @throws Exception
     *             error while checkEmailId information in database
     */

    public boolean checkEmailId(String email);

    /**
     * This method performed database operation related to updateToken.
     * 
     * @paparam token
     *            ,emailId,password <code>AppUser</code> to be updateToken from
     *            database
     * @return int <code>AppUser</code> updateToken from database
     * @throws Exception
     *             error while updateToken information in database
     */

    public int updateToken(String token, String emailId, String password);

    /**
     * This method updateAppToken.
     * 
     * @paparam token
     *            ,userId,deviceToken <code>AppUser</code> to be updateAppToken
     *            from database
     * @return int <code>AppUser</code> updateAppToken from database
     * @throws Exception
     *             error while updateAppToken information in database
     */

    public int updateAppToken(String token, int userId, String deviceToken);

    /**
     * This method saveNewDeviceToken.
     * 
     * @paparam userId
     *            ,token,deviceToken <code>AppUser</code> to be
     *            saveNewDeviceToken from database
     * @return AppUserToken <code>AppUser</code> saveNewDeviceToken from
     *         database
     * @throws Exception
     *             error while saveNewDeviceToken information in database
     */
    public AppUserToken saveNewDeviceToken(Integer userId, String token, String deviceToken);

    /**
     * This method deleteTask.
     * 
     * @paparam userTask
     *            <code>AppUserTask</code> to be deleteTask from database
     * @return AppUserTask <code>AppUserTask</code> deleteTask from database
     * @throws Exception
     *             error while deleteTask information in database
     */
    public AppUserTask deleteTask(AppUserTask userTask);

    /**
     * This method getUserTokenList.
     * 
     * @paparam userId
     *            ,deviceToken <code>AppUser</code> to be getUserTokenList from
     *            database
     * @return AppUserToken<code>AppUser</code> getUserTokenList from database
     * @throws Exception
     *             error while getUserTokenList information in database
     */
    public ArrayList<AppUserToken> getUserTokenList(int userId, String deviceToken) throws Exception;

    /**
     * This method appUserLogout.
     * 
     * @paparam appUserToken
     *            <code>AppUser</code> to be appUserLogout from database
     * @return boolean <code>AppUser</code> appUserLogout from database
     * @throws Exception
     *             error while appUserLogout information in database
     */
    public boolean appUserLogout(AppUserToken appUserToken);

    /**
     * This method checkOldPasswordExist.
     * 
     * @paparam userId
     *            ,oldPassword <code>AppUser</code> to be checkOldPasswordExist
     *            from database
     * @return AppUser <code>AppUser</code> checkOldPasswordExist from database
     * @throws Exception
     *             error while checkOldPasswordExist information in database
     */

    public AppUser checkOldPasswordExist(int userId, String oldPassword);

    /**
     * This method updateNewPassword.
     * 
     * @paparam userId
     *            ,newPassword <code>AppUser</code> to be updateNewPassword from
     *            database
     * @return int <code>AppUser</code> updateNewPassword from database
     * @throws Exception
     *             error while updateNewPassword information in database
     */

    public int updateNewPassword(int userId, String newPassword);

    /**
     * This method getUserDetail.
     * 
     * @paparam email
     *            <code>AppUser</code> to be getUserDetail from database
     * @return AppUser <code>AppUser</code> getUserDetail from database
     * @throws Exception
     *             error while getUserDetail information in database
     */

    public AppUser getUserDetail(String email);

    /**
     * This method saveOtp.
     * 
     * @paparam appUserForgetPasswordOtp
     *            <code>AppUserForgetPasswordOtp</code> to be saveOtp from
     *            database
     * @return AppUserForgetPasswordOtp <code>AppUserForgetPasswordOtp</code>
     *         saveOtp from database
     * @throws Exception
     *             error while saveOtp information in database
     */

    public AppUserForgetPasswordOtp saveOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp);

    /**
     * This method checkValidRequestForUserLoginUsingOtp.
     * 
     * @paparam userId
     *            ,Otp <code>AppUserForgetPasswordOtp</code> to be
     *            checkValidRequestForUserLoginUsingOtp from database
     * @return AppUserForgetPasswordOtp <code>AppUserForgetPasswordOtp</code>
     *         checkValidRequestForUserLoginUsingOtp from database
     * @throws Exception
     *             error while checkValidRequestForUserLoginUsingOtp information
     *             in database
     */

    public AppUserForgetPasswordOtp checkValidRequestForUserLoginUsingOtp(Integer userId, String Otp);

    /**
     * This method removeOtp.
     * 
     * @paparam appUserForgetPasswordOtp
     *            <code>AppUserForgetPasswordOtp</code> to be removeOtp from
     *            database
     * @return AppUserForgetPasswordOtp <code>AppUserForgetPasswordOtp</code>
     *         removeOtp from database
     * @throws Exception
     *             error while removeOtp information in database
     */

    public AppUserForgetPasswordOtp removeOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp);

}
