/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserForgetPasswordOtp;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.model.InAppPurchase;

/**
 * This <java>interface</java> AppUserService defines all abstract methods of
 * TA_USER.
 * 
 * @author param Sheikh
 * @GWL
 */
public interface AppUserService
{

    /**
     * This method createNewTaUserToken
     * 
     * @paparam taUserTokenModel
     *            : <code>AppUserToken</code> information to be saved in
     *            database
     * @return AppUserToken
     * @throws Exception
     *             error while saving AppUserToken information.
     */
    public AppUserToken createNewTaUserToken(AppUserToken taUserTokenModel) throws Exception;

    /**
     * This method createNewTaUser
     * 
     * @paparam taUserModel
     *            : <code>AppUser</code> information to be saved in database
     * @return AppUser
     * @throws Exception
     *             error while saving taUserModel information.
     */
    public AppUser createNewTaUser(AppUser taUserModel) throws Exception;

    /**
     * This method inAppPurchased
     * 
     * @paparam inAppPurchase
     *            : <code>InAppPurchase</code> information to be saved in
     *            database
     * @return InAppPurchase
     * @throws Exception
     *             error while saving inAppPurchase information.
     */
    public InAppPurchase inAppPurchased(InAppPurchase inAppPurchase) throws Exception;

    /**
     * This method deleteTask
     * 
     * @paparam appUserTask
     *            : <code>appUserTask</code> information to be delete in
     *            database
     * @return AppUserTask
     * @throws Exception
     *             error while deleting appUserTask information.
     */
    public AppUserTask deleteTask(AppUserTask appUserTask) throws Exception;

    /**
     * This method updateProfile
     * 
     * @paparam taUserModel
     *            : <code>AppUser</code> information to be updateProfile in
     *            database
     * @return AppUser
     * @throws Exception
     *             error while updateProfile information.
     */
    public AppUser updateProfile(AppUser taUserModel) throws Exception;

    /**
     * This method checkValidRequestForUserLogin
     * 
     * @paparam taUserModel
     *            : <code>AppUser</code> information to be
     *            checkValidRequestForUserLogin in database
     * @return AppUser
     * @throws Exception
     *             error while checkValidRequestForUserLogin information.
     */
    public AppUser checkValidRequestForUserLogin(AppUser taUserModel) throws Exception;

    /**
     * This method getUserProfile
     * 
     * @paparam userId
     *            : <code>AppUser</code> get information to getUserProfile in
     *            database
     * @return AppUser
     * @throws Exception
     *             error while fetching getUserProfile information.
     */
    public AppUser getUserProfile(int userId) throws Exception;

    /**
     * This method getCoinBalance
     * 
     * @paparam userId
     *            : <code>AppUserCoinBalance</code> information to
     *            getCoinBalance in database
     * @return AppUserCoinBalance
     * @throws Exception
     *             error while fetching getCoinBalance information.
     */
    public AppUserCoinBalance getCoinBalance(int userId) throws Exception;

    /**
     * This method getInAppCoinBalance
     * 
     * @paparam userId
     *            : <code>InAppPurchase</code> information to
     *            getInAppCoinBalance in database
     * @return InAppPurchase
     * @throws Exception
     *             error while fetching getInAppCoinBalance information.
     */
    public InAppPurchase getInAppCoinBalance(int userId) throws Exception;

    /**
     * This method getcreditAmountCoinBalance
     * 
     * @paparam userId
     *            : <code>AppUserCoinBalance</code> information to
     *            getcreditAmountCoinBalance in database
     * @return BigDecimal
     * @throws Exception
     *             error while fetching getcreditAmountCoinBalance information.
     */
    public BigDecimal getcreditAmountCoinBalance(int userId) throws Exception;

    /**
     * This method getdebitAmountCoinBalance
     * 
     * @paparam userId
     *            : <code>userId</code> information to getdebitAmountCoinBalance
     *            in database
     * @return BigDecimal
     * @throws Exception
     *             error while fetching getdebitAmountCoinBalance information.
     */
    public BigDecimal getdebitAmountCoinBalance(int userId) throws Exception;

    /**
     * This method getUserTokenList
     * 
     * @paparam userId
     *            ,deviceToken <code>AppUserToken</code> information to
     *            getUserTokenList in database
     * @return AppUserToken
     * @throws Exception
     *             error while getUserTokenList information.
     */
    public ArrayList<AppUserToken> getUserTokenList(int userId, String deviceToken) throws Exception;

    /**
     * This method getUserList
     * 
     * @paparam userId
     *            ,pageNo,recordPerPage,searchByAlphabet,taskId,senderType :
     *            <code>AppUser</code> information to getUserList in database
     * @return AppUser
     * @throws Exception
     *             error while getUserList information.
     */
    public ArrayList<AppUser> getUserList(int userId, int pageNo, int recordPerPage, String searchByAlphabet,
            String taskId, String senderType) throws Exception;

    /**
     * This method checkNewGenaratedToken
     * 
     * @paparam token
     *            : <code>AppUserToken</code> checkNewGenaratedToken information
     *            to be get from database
     * @return AppUserToken
     * @throws Exception
     *             error while validate token information.
     */
    public AppUserToken checkNewGenaratedToken(String token) throws Exception;

    /**
     * This method getUserId
     * 
     * @paparam token
     *            : <code>AppUserToken</code> getUserId information to be get
     *            from database
     * @return AppUserToken
     * @throws Exception
     *             error while validate getUserId information.
     */

    public AppUserToken getUserId(String token) throws Exception;

    /**
     * This method getTotalRecords
     * 
     * @paparam userId
     *            : <code>Long</code> getTotalRecords information to be get from
     *            database
     * @return Long
     * @throws Exception
     *             error while validate getTotalRecords information.
     */

    public Long getTotalRecords(int userId) throws Exception;

    /**
     * This method checkEmailId
     * 
     * @paparam email
     *            : <code>boolean</code> checkEmailId information to be get from
     *            database
     * @return boolean
     * @throws Exception
     *             error while validate checkEmailId information.
     */

    public boolean checkEmailId(String email) throws Exception;

    /**
     * This method updateToken
     * 
     * @paparam email
     *            ,token,password : <code>int</code> updateToken information to
     *            be get from database
     * @return email,token,password
     * @throws Exception
     *             error while validate updateToken information.
     */

    public int updateToken(String token, String emailId, String password) throws Exception;

    /**
     * This method updateAppToken
     * 
     * @paparam token
     *            ,userId,deviceToken : <code>AppUserToken</code> updateAppToken
     *            information to be get from database
     * @return int
     * @throws Exception
     *             error while validate updateAppToken information.
     */

    public int updateAppToken(String token, int userId, String deviceToken) throws Exception;

    /**
     * This method saveNewDeviceToken
     * 
     * @paparam userId
     *            ,token,deviceToken : <code>AppUserToken</code>
     *            saveNewDeviceToken information to be get from database
     * @return AppUserToken
     * @throws Exception
     *             error while validate saveNewDeviceToken information.
     */

    public AppUserToken saveNewDeviceToken(Integer userId, String token, String deviceToken) throws Exception;

    /**
     * This method appUserLogout
     * 
     * @paparam appUserToken
     *            : <code>AppUserToken</code> appUserLogout information to be
     *            get from database
     * @return boolean
     * @throws Exception
     *             error while validate appUserLogout information.
     */

    public boolean appUserLogout(AppUserToken appUserToken) throws Exception;

    /**
     * This method checkOldPasswordExist
     * 
     * @paparam userId
     *            ,oldPassword : <code>AppUser</code> checkOldPasswordExist
     *            information to be get from database
     * @return AppUser
     * @throws Exception
     *             error while validate checkOldPasswordExist information.
     */

    public AppUser checkOldPasswordExist(int userId, String oldPassword) throws Exception;

    /**
     * This method updateNewPassword
     * 
     * @paparam userId
     *            ,newPassword : <code>AppUser</code> updateNewPassword
     *            information to be get from database
     * @return boolean
     * @throws Exception
     *             error while validate updateNewPassword information.
     */

    public boolean updateNewPassword(int userId, String newPassword) throws Exception;

    /**
     * This method updateNewPassword
     * 
     * @paparam email
     *            : <code>AppUser</code> updateNewPassword information to be get
     *            from database
     * @return AppUser
     * @throws Exception
     *             error while validate updateNewPassword information.
     */

    public AppUser getUserDetail(String email) throws Exception;

    /**
     * This method SaveOtp
     * 
     * @paparam appUserForgetPasswordOtp
     *            : <code>AppUserForgetPasswordOtp</code> SaveOtp information to
     *            be save in database
     * @return AppUserForgetPasswordOtp
     * @throws Exception
     *             error while validate SaveOtp information.
     */

    public AppUserForgetPasswordOtp SaveOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp);

    /**
     * This method checkValidRequestForUserLoginUsingOtp
     * 
     * @paparam userId
     *            ,Otp : <code>AppUserForgetPasswordOtp</code>
     *            checkValidRequestForUserLoginUsingOtp information to be get
     *            from database
     * @return AppUserForgetPasswordOtp
     * @throws Exception
     *             error while validate checkValidRequestForUserLoginUsingOtp
     *             information.
     */

    public AppUserForgetPasswordOtp checkValidRequestForUserLoginUsingOtp(Integer userId, String Otp);

    /**
     * This method removeOtp
     * 
     * @paparam appUserForgetPasswordOtp
     *            : <code>AppUserForgetPasswordOtp</code> removeOtp information
     *            to be in database
     * @return AppUserForgetPasswordOtp
     * @throws Exception
     *             error while validate removeOtp information.
     */

    public AppUserForgetPasswordOtp removeOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp)
            throws Exception;

}
