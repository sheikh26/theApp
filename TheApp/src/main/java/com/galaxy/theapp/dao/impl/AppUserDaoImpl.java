/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springfparamework.beans.factory.annotation.Autowired;

import com.galaxy.theapp.common.utility.CommonConstants;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.dao.AppUserDao;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserCoinTransaction;
import com.galaxy.theapp.model.AppUserForgetPasswordOtp;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUserTaskSharing;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.model.InAppPurchase;

/**
 * This <java>class</java> AppUserDaoImpl use to perform all our DB related
 * logics for the TaUser.
 * 
 * @author param Sheikh
 * @GWL
 */

public class AppUserDaoImpl implements AppUserDao
{
    public static final Logger LOG = Logger.getLogger(AppUserDaoImpl.class);
    @Autowired
    SessionFactory sessionFactory;

    static Properties prop = new Properties();
    static CommonUtility cu = new CommonUtility();

    /**
     * This method createNewTaUser on DB.
     * 
     */
    public AppUser createNewTaUser(AppUser taUserModel)
    {
        try
        {
            sessionFactory.getCurrentSession().save(taUserModel);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserModel;

    }

    /**
     * This method updateProfile on DB.
     * 
     */
    public AppUser updateProfile(AppUser taUserModel)
    {
        try
        {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "update AppUser set firstName=:firstName,lastName=:lastName,profilePic=:profilePic,address1=:address1,address2=:address2,mobileNo=:mobileNo where userId=:userId");
            query.setString("firstName", taUserModel.getFirstName());
            query.setString("lastName", taUserModel.getLastName());
            query.setString("profilePic", taUserModel.getProfilePic());
            query.setString("address1", taUserModel.getAddress1());
            query.setString("address2", taUserModel.getAddress2());
            query.setString("mobileNo", taUserModel.getMobileNo());
            query.setInteger("userId", taUserModel.getUserId());

            int modifications = query.executeUpdate();
            LOG.info("modification in updateProfile:" + modifications);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserModel;

    }

    /**
     * This method checkValidRequestForUserLogin().
     * 
     */
    public AppUser checkValidRequestForUserLogin(AppUser taUserModel)
    {
        AppUser userData = new AppUser();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            criteria.add(Restrictions.eq("email", taUserModel.getEmail()));
            criteria.add(Restrictions.eq("password", taUserModel.getPassword()));
            userData = (AppUser) criteria.uniqueResult();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userData;
    }

    /**
     * This method getUserProfile() from DB.
     * 
     */
    public AppUser getUserProfile(int userId)
    {
        AppUser getUserProfile = null;
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            criteria.add(Restrictions.eq("userId", userId));
            getUserProfile = (AppUser) criteria.uniqueResult();
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
    @SuppressWarnings("unchecked")
    public ArrayList<AppUser> getUserList(int userId, int pageNo, int recordPerPage, String searchByAlphabet,
            String taskId, String senderType)
    {
        ArrayList<AppUser> userList = null;
        AppUserTask appUserTask = new AppUserTask();
        ArrayList<AppUserTaskInvite> inviteUserList = new ArrayList<AppUserTaskInvite>();
        ArrayList<AppUserTaskSharing> sharingTaskUserList = new ArrayList<AppUserTaskSharing>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskInvite.class);
            criteria.add(Restrictions.eq("taskId", taskId));
            criteria.add(Restrictions.eq("taskStatus", CommonConstants.UpdateTaskStatus.Rejected_By_Invitee));
            inviteUserList = (ArrayList<AppUserTaskInvite>) criteria.list();

            Criteria shareUser = sessionFactory.getCurrentSession().createCriteria(AppUserTaskSharing.class);
            shareUser.add(Restrictions.eq("taskId", taskId));
            shareUser.add(Restrictions.eq("taskStatus", CommonConstants.UpdateTaskStatus.Rejected_By_Share));
            sharingTaskUserList = (ArrayList<AppUserTaskSharing>) shareUser.list();

            Criteria taskInfo = sessionFactory.getCurrentSession().createCriteria(AppUserTask.class);
            taskInfo.add(Restrictions.eq("userTaskId", taskId));

            appUserTask = (AppUserTask) taskInfo.uniqueResult();

            Criteria cr = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            if (senderType.equals("2"))
            {
                if (searchByAlphabet == null)
                {

                    cr.setProjection(Projections.projectionList()
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("profilePic"), "profilePic")
                            .add(Projections.property("userId"), "userId"));
                    cr.add(Restrictions.ne("userId", userId));

                    for (int i = 0; i < inviteUserList.size(); i++)
                    {
                        AppUserTaskInvite taskInviteData = new AppUserTaskInvite();
                        taskInviteData = inviteUserList.get(i);
                        cr.add(Restrictions.ne("userId", taskInviteData.getInviteUserId()));
                    }
                    cr.setResultTransformer(Transformers.aliasToBean(AppUser.class));
                    cr.setFirstResult((pageNo - 1) * recordPerPage);
                    cr.setMaxResults(recordPerPage);
                } else
                {
                    cr.setProjection(Projections.projectionList()
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("profilePic"), "profilePic")
                            .add(Projections.property("userId"), "userId"));
                    cr.add(Restrictions.ne("userId", userId));
                    for (int i = 0; i < inviteUserList.size(); i++)
                    {
                        AppUserTaskInvite taskInviteData = new AppUserTaskInvite();
                        taskInviteData = inviteUserList.get(i);
                        cr.add(Restrictions.ne("userId", taskInviteData.getInviteUserId()));
                    }
                    cr.add(Restrictions.like("firstName", searchByAlphabet + "%"));
                    cr.setResultTransformer(Transformers.aliasToBean(AppUser.class));
                    cr.setFirstResult((pageNo - 1) * recordPerPage);
                    cr.setMaxResults(recordPerPage);
                }
            } else if (senderType.equals("1"))
            {
                if (searchByAlphabet == null)
                {

                    cr.setProjection(Projections.projectionList()
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("profilePic"), "profilePic")
                            .add(Projections.property("userId"), "userId"));
                    cr.add(Restrictions.ne("userId", userId));
                    cr.add(Restrictions.ne("userId", appUserTask.getUserId())); // task
                                                                                // owner
                                                                                // Id
                    for (int i = 0; i < sharingTaskUserList.size(); i++)
                    {
                        AppUserTaskSharing sharingTaskData = new AppUserTaskSharing();
                        sharingTaskData = sharingTaskUserList.get(i);
                        cr.add(Restrictions.ne("userId", sharingTaskData.getRecieverId()));
                    }
                    cr.setResultTransformer(Transformers.aliasToBean(AppUser.class));
                    cr.setFirstResult((pageNo - 1) * recordPerPage);
                    cr.setMaxResults(recordPerPage);
                } else
                {
                    cr.setProjection(Projections.projectionList()
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("profilePic"), "profilePic")
                            .add(Projections.property("userId"), "userId"));
                    cr.add(Restrictions.ne("userId", userId));
                    if (appUserTask != null)
                    {
                        cr.add(Restrictions.ne("userId", appUserTask.getUserId()));
                    }
                    for (int i = 0; i < sharingTaskUserList.size(); i++)
                    {
                        AppUserTaskSharing sharingTaskData = new AppUserTaskSharing();
                        sharingTaskData = sharingTaskUserList.get(i);
                        cr.add(Restrictions.ne("userId", sharingTaskData.getRecieverId()));
                    }
                    cr.add(Restrictions.like("firstName", searchByAlphabet + "%"));
                    cr.setResultTransformer(Transformers.aliasToBean(AppUser.class));
                    cr.setFirstResult((pageNo - 1) * recordPerPage);
                    cr.setMaxResults(recordPerPage);
                }
            } else
            {
                if (searchByAlphabet == null)
                {

                    cr.setProjection(Projections.projectionList()
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("profilePic"), "profilePic")
                            .add(Projections.property("userId"), "userId"));
                    cr.add(Restrictions.ne("userId", userId));
                    cr.setResultTransformer(Transformers.aliasToBean(AppUser.class));
                    cr.setFirstResult((pageNo - 1) * recordPerPage);
                    cr.setMaxResults(recordPerPage);
                } else
                {
                    cr.setProjection(Projections.projectionList()
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("profilePic"), "profilePic")
                            .add(Projections.property("userId"), "userId"));
                    cr.add(Restrictions.ne("userId", userId));
                    cr.add(Restrictions.like("firstName", searchByAlphabet + "%"));
                    cr.setResultTransformer(Transformers.aliasToBean(AppUser.class));
                    cr.setFirstResult((pageNo - 1) * recordPerPage);
                    cr.setMaxResults(recordPerPage);
                }
            }

            userList = (ArrayList<AppUser>) cr.list();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * This method checkNewGeneratedToken() validate the newly generated token
     * from DB.
     * 
     */
    public AppUserToken checkNewGeneratedToken(String token)
    {
        AppUserToken taUserToken = null;
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserToken.class);
            criteria.add(Restrictions.eq("appToken", token));
            taUserToken = (AppUserToken) criteria.uniqueResult();
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
    public AppUserToken getUserId(String token)
    {
        AppUserToken getUserProfile = new AppUserToken();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserToken.class);
            criteria.add(Restrictions.eq("appToken", token));
            getUserProfile = (AppUserToken) criteria.uniqueResult();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getUserProfile;
    }

    /**
     * This method getTotalRecords() fetch TotalRecords from DB.
     * 
     */
    public Long getTotalRecords(int userId)
    {
        Long count = null;
        try
        {
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            crit.setProjection(Projections.rowCount());
            crit.add(Restrictions.ne("userId", userId));
            count = (Long) crit.uniqueResult();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * This method checkEmailId() check EmailId from DB.
     * 
     */
    public boolean checkEmailId(String email)
    {
        AppUser userData = null;
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            criteria.add(Restrictions.eq("email", email));
            userData = (AppUser) criteria.uniqueResult();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (userData != null)
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * This method updateToken() update token from DB.
     * 
     */
    public int updateToken(String token, String emailId, String password)
    {
        int modification = 0;
        try
        {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "update AppUser set token=:token where email=:email and password=:password");
            query.setString("token", token);
            query.setString("email", emailId);
            query.setString("password", password);

            modification = query.executeUpdate();
            LOG.info("modification in updateToken:" + modification);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return modification;
    }

    /**
     * This method createNewTaUserToken() create token from DB.
     * 
     */
    public AppUserToken createNewTaUserToken(AppUserToken taUserTokenModel)
    {

        try
        {
            sessionFactory.getCurrentSession().save(taUserTokenModel);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return taUserTokenModel;

    }

    /**
     * This method getUserTokenList() get TokenList from DB.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserToken> getUserTokenList(int userId, String deviceToken)
    {
        ArrayList<AppUserToken> userTokenList = null;
        try
        {
            Criteria cr = sessionFactory.getCurrentSession().createCriteria(AppUserToken.class);
            cr.add(Restrictions.eq("userId", userId));
            cr.add(Restrictions.eq("deviceToken", deviceToken));
            userTokenList = (ArrayList<AppUserToken>) cr.list();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userTokenList;
    }

    /**
     * This method updateAppToken() update TokenList from DB.
     * 
     */
    public int updateAppToken(String token, int userId, String deviceToken)
    {
        int modification = 0;
        try
        {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "update AppUserToken set appToken=:appToken where userId=:userId and deviceToken=:deviceToken");
            query.setString("appToken", token);
            query.setInteger("userId", userId);
            query.setString("deviceToken", deviceToken);
            modification = query.executeUpdate();
            LOG.info("modification in updateAppToken:" + modification);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return modification;
    }

    /**
     * This method saveNewDeviceToken() save NewDeviceToken from DB.
     * 
     */
    public AppUserToken saveNewDeviceToken(Integer userId, String token, String deviceToken)
    {
        AppUserToken appUserToken = new AppUserToken();
        appUserToken.setAppToken(token);
        appUserToken.setDeviceToken(deviceToken);
        appUserToken.setUserId(userId);
        sessionFactory.getCurrentSession().save(appUserToken);
        return appUserToken;
    }

    /**
     * This method appUserLogout() logout User from DB.
     * 
     */
    public boolean appUserLogout(AppUserToken appUserToken)
    {
        boolean status = false;
        try
        {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "update AppUserToken set appToken=:appToken where userId=:userId and deviceToken=:deviceToken");
            query.setString("appToken", null);
            query.setInteger("userId", appUserToken.getUserId());
            query.setString("deviceToken", appUserToken.getDeviceToken());

            int modification = query.executeUpdate();
            if (modification == 1)
            {
                status = true;
            }
            LOG.info("modification in appUserLogout:" + modification);
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
    public InAppPurchase inAppPurchased(InAppPurchase inAppPurchase)
    {
        Query query = null;
        AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
        try
        {

            query = sessionFactory.getCurrentSession()
                    .createQuery("from InAppPurchase where userId= :userId");
            query.setLong("userId", inAppPurchase.getUserId());
            InAppPurchase inPurchase = (InAppPurchase) query.uniqueResult();
            int newBalance = 0;

            if (inPurchase != null)
            {
                newBalance = (inPurchase.getPointPurchased()) + (inAppPurchase.getPointPurchased());

                query = sessionFactory.getCurrentSession().createQuery(
                        "update InAppPurchase set pointPurchased=:pointPurchased where userId=:userId");
                query.setInteger("pointPurchased", newBalance);
                query.setInteger("userId", inAppPurchase.getUserId());

                int modification = query.executeUpdate();
            } else
            {
                sessionFactory.getCurrentSession().save(inAppPurchase);
            }

            query = sessionFactory.getCurrentSession().createQuery(
                    "from AppUserCoinBalance where userId= :userId");
            query.setLong("userId", inAppPurchase.getUserId());

            appCoinBalance = (AppUserCoinBalance) query.uniqueResult();

            if (appCoinBalance != null)
            {
                newBalance = (appCoinBalance.getCoin_balance()) + (inAppPurchase.getPointPurchased());

                query = sessionFactory.getCurrentSession().createQuery(
                        "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                query.setInteger("coin_balance", newBalance);
                query.setInteger("userId", inAppPurchase.getUserId());

                int modification = query.executeUpdate();

            } else
            {
                appCoinBalance = new AppUserCoinBalance();
                appCoinBalance.setUserId(inAppPurchase.getUserId());
                appCoinBalance.setCoin_balance(inAppPurchase.getPointPurchased());
                Date date = new Date();
                prop = cu.getFromProperty();
                DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
                appCoinBalance.setCraetedBy(inAppPurchase.getUserId());
                appCoinBalance.setCreatedOn(formatter.format(date));

                sessionFactory.getCurrentSession().save(appCoinBalance);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inAppPurchase;

    }

    /**
     * This method deleteTask ,delete the task to DB.
     * 
     */
    public AppUserTask deleteTask(AppUserTask userTask)
    {
        AppUserTask appUserTask = new AppUserTask();
        if (userTask.getTaskStatus() == 0 || userTask.getTaskStatus() == 2)
        {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "update AppUserTask set status=:status where userTaskId=:userTaskId AND userId= :userId");
            query.setString("userTaskId", userTask.getUserTaskId());
            query.setInteger("userId", userTask.getUserId());
            query.setInteger("status", CommonConstants.DeleteTask.INACTIVE_TASK);
            query.executeUpdate();

            query = sessionFactory.getCurrentSession().createQuery(
                    "from AppUserTask where userTaskId= :userTaskId");
            query.setInteger("userTaskId", Integer.parseInt(userTask.getUserTaskId()));
            AppUserTask appTask = (AppUserTask) query.uniqueResult();

            if (appTask != null)
            {
                AppUserCoinTransaction appTransaction = new AppUserCoinTransaction();
                appTransaction.setUserId(userTask.getUserId());
                appTransaction.setCraetedBy(userTask.getUserId());
                Date date = new Date();
                prop = cu.getFromProperty();
                DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
                appTransaction.setCreatedOn(formatter.format(date));
                appTransaction.setFoparamount(appTask.getCoinValue());
                appTransaction.setTransactionType(appTask.getUserTaskId());
                appTransaction.setAmountType(CommonConstants.TransactionAmountType.CREDITED_AMOUNT);
                sessionFactory.getCurrentSession().save(appTransaction);

                AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
                query = sessionFactory.getCurrentSession().createQuery(
                        "from AppUserCoinBalance where userId= :userId");
                query.setLong("userId", userTask.getUserId());

                appCoinBalance = (AppUserCoinBalance) query.uniqueResult();
                int newBalance = 0;
                if (appCoinBalance != null)
                {
                    newBalance = (appCoinBalance.getCoin_balance()) + (appTask.getCoinValue());

                    query = sessionFactory.getCurrentSession().createQuery(
                            "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                    query.setInteger("coin_balance", newBalance);
                    query.setInteger("userId", userTask.getUserId());

                    int modification = query.executeUpdate();

                }

            } else
            {
                LOG.info("else part in deleteTask");
            }

        }
        return appUserTask;
    }

    /**
     * This method checkOldPasswordExist check to DB.
     * 
     */
    public AppUser checkOldPasswordExist(int userId, String oldPassword)
    {
        AppUser userData = new AppUser();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            criteria.add(Restrictions.eq("userId", userId));
            criteria.add(Restrictions.eq("password", oldPassword));
            userData = (AppUser) criteria.uniqueResult();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userData;
    }

    /**
     * This method updateNewPassword update new pass to DB.
     * 
     */
    public int updateNewPassword(int userId, String newPassword)
    {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "update AppUser set password=:password where  userId= :userId");
        query.setString("password", newPassword);
        query.setInteger("userId", userId);
        int modification = query.executeUpdate();
        return modification;
    }

    /**
     * This method AppUserCoinBalance update the coin for the user to DB.
     * 
     */
    public AppUserCoinBalance getCoinBalance(int userId)
    {
        AppUserCoinBalance appCoinBalance = null;
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserCoinBalance.class);
            criteria.add(Restrictions.eq("userId", userId));
            appCoinBalance = (AppUserCoinBalance) criteria.uniqueResult();
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
    public InAppPurchase getInAppCoinBalance(int userId)
    {
        InAppPurchase inaPurchase = null;
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InAppPurchase.class);
            criteria.add(Restrictions.eq("userId", userId));
            inaPurchase = (InAppPurchase) criteria.uniqueResult();
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
    public BigDecimal getcreditAmountCoinBalance(int userId)
    {
        SQLQuery query = null;
        try
        {

            String sql1 = "SELECT SUM(FOR_AMOUNT) from ta_user_coin_transaction where USER_ID=" + userId
                    + " and AMOUNT_TYPE='1'";

            query = sessionFactory.getCurrentSession().createSQLQuery(sql1);

            return (BigDecimal) query.uniqueResult();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return (BigDecimal) query.uniqueResult();
    }

    /**
     * This method getdebitAmountCoinBalance get the coin for the user to DB.
     * 
     */
    public BigDecimal getdebitAmountCoinBalance(int userId)
    {
        SQLQuery query = null;
        try
        {

            String sql1 = "SELECT SUM(FOR_AMOUNT) from ta_user_coin_transaction where USER_ID=" + userId
                    + " and AMOUNT_TYPE='0'";

            query = sessionFactory.getCurrentSession().createSQLQuery(sql1);

            return (BigDecimal) query.uniqueResult();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return (BigDecimal) query.uniqueResult();
    }

    /**
     * This method getUserDetail get the user details from DB.
     * 
     */
    public AppUser getUserDetail(String email)
    {
        AppUser userData = new AppUser();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            criteria.add(Restrictions.eq("email", email));
            userData = (AppUser) criteria.uniqueResult();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userData;
    }

    /**
     * This method AppUserForgetPasswordOtp generate OTP from DB.
     * 
     */
    public AppUserForgetPasswordOtp saveOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp)
    {

        try
        {
            sessionFactory.getCurrentSession().save(appUserForgetPasswordOtp);
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "update AppUser set isOtp=:isOtp where  userId= :userId");
            query.setInteger("isOtp", CommonConstants.ForgetPassword.IS_OTP_ONE);
            query.setInteger("userId", appUserForgetPasswordOtp.getUserId());
            int modification = query.executeUpdate();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserForgetPasswordOtp;
    }

    /**
     * This method checkValidRequestForUserLoginUsingOtp login using Otp OTP
     * from DB.
     * 
     */
    public AppUserForgetPasswordOtp checkValidRequestForUserLoginUsingOtp(Integer userId, String Otp)
    {
        AppUserForgetPasswordOtp userData = new AppUserForgetPasswordOtp();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
                    AppUserForgetPasswordOtp.class);
            criteria.add(Restrictions.eq("userId", userId));
            criteria.add(Restrictions.eq("otp", Otp));
            userData = (AppUserForgetPasswordOtp) criteria.uniqueResult();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userData;
    }

    /**
     * This method removeOtp remove OTP from DB.
     * 
     */
    public AppUserForgetPasswordOtp removeOtp(AppUserForgetPasswordOtp appUserForgetPasswordOtp)
    {
        try
        {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "delete from AppUserForgetPasswordOtp  where  userId= :userId");
            query.setInteger("userId", appUserForgetPasswordOtp.getUserId());
            int i = query.executeUpdate();
            if (i == 1)
            {
                Query query1 = sessionFactory.getCurrentSession().createQuery(
                        "update AppUser set isOtp=:isOtp where  userId= :userId");
                query1.setInteger("isOtp", CommonConstants.ForgetPassword.IS_OTP_ZERO);
                query1.setInteger("userId", appUserForgetPasswordOtp.getUserId());
                int j = query1.executeUpdate();
                if (j == 1)
                {
                    return appUserForgetPasswordOtp;
                } else
                {
                    return null;
                }
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserForgetPasswordOtp;
    }
}
