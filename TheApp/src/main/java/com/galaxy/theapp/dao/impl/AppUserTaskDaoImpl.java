/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Repository;

import com.galaxy.theapp.common.utility.CommonConstants;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.dao.AppUserTaskDao;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserCoinTransaction;
import com.galaxy.theapp.model.AppUserTaskAssign;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskSharing;

/**
 * This <java>class</java> AppUserTaskDaoImpl use to perform all our DB related
 * logics for the userLogin. users.
 * 
 * @author param Sheikh
 * @GWL
 */
@Repository("userTaskDao")
public class AppUserTaskDaoImpl implements AppUserTaskDao
{

    public static final Logger LOG = Logger.getLogger(AppUserTaskDaoImpl.class);
    @Autowired
    public SessionFactory sessionFactory;
    static Properties prop = new Properties();
    static CommonUtility cu = new CommonUtility();

    /**
     * This method save user new task on DB saveUserTaskCreation().
     * 
     */
    public AppUserTask saveUserTask(AppUserTask userModel)
    {
        Query query = null;
        AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
        int newBalance = 0;
        try
        {
            sessionFactory.getCurrentSession().save(userModel);
            if (userModel != null)
            {
                AppUserCoinTransaction appTransaction = new AppUserCoinTransaction();
                appTransaction.setUserId(userModel.getUserId());
                appTransaction.setCraetedBy(userModel.getUserId());
                Date date = new Date();
                prop = cu.getFromProperty();
                DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
                appTransaction.setCreatedOn(formatter.format(date));
                appTransaction.setFoparamount(userModel.getCoinValue());
                appTransaction.setTransactionType(userModel.getUserTaskId());
                appTransaction.setAmountType(CommonConstants.TransactionAmountType.DEBITED_AMOUNT);
                sessionFactory.getCurrentSession().save(appTransaction);

                query = sessionFactory.getCurrentSession().createQuery(
                        "from AppUserCoinBalance where userId= :userId");
                query.setLong("userId", userModel.getUserId());

                appCoinBalance = (AppUserCoinBalance) query.uniqueResult();

                if (appCoinBalance != null)
                {
                    newBalance = (appCoinBalance.getCoin_balance()) - (userModel.getCoinValue());

                    query = sessionFactory.getCurrentSession().createQuery(
                            "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                    query.setInteger("coin_balance", newBalance);
                    query.setInteger("userId", userModel.getUserId());

                    int modification = query.executeUpdate();
                    appCoinBalance.setCoin_balance(newBalance);

                } else
                {
                    LOG.info("else part in saveUserTask");
                }

            } else
            {

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userModel;

    }

    /**
     * This method getTaskList from DB.
     * 
     */
    @SuppressWarnings("unchecked")
    public List<AppUserTask> getTaskList(int userId)
    {
        List<AppUserTask> userList = null;
        try
        {
            userList = (List<AppUserTask>) sessionFactory.getCurrentSession()
                    .createCriteria(AppUserTask.class).add(Restrictions.eq("userId", userId)).list();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * This method EditUserTask edit the user task.
     * 
     */
    public AppUserTask editUserTask(AppUserTask editTask)
    {
        try
        {
            sessionFactory.getCurrentSession().update(editTask);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return editTask;
    }

    /**
     * This method appUserTaskInvite save the invitee.
     * 
     */
    public AppUserTaskInvite appUserTaskInvite(AppUserTaskInvite appUserTaskInvite)
    {
        try
        {
            sessionFactory.getCurrentSession().save(appUserTaskInvite);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserTaskInvite;
    }

    /**
     * This method updateUserTask update the user task.
     * 
     */
    @SuppressWarnings("unused")
    public AppUserTask updateUserTask(AppUserTask userTask, int newCoins)
    {
        Query query = null;
        int newBalance = 0;
        AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
        try
        {
            AppUserTask userTaskModel = (AppUserTask) sessionFactory.getCurrentSession().load(
                    AppUserTask.class, userTask.getUserTaskId());
            userTaskModel.setCoinValue(userTask.getCoinValue());
            userTaskModel.setTaskTitle(userTask.getTaskTitle());
            userTaskModel.setDescription(userTask.getDescription());
            userTaskModel.setDate(userTask.getDate());
            userTaskModel.setDuration(userTask.getDuration());
            userTaskModel.setEndDate(userTask.getEndDate());
            userTaskModel.setStartDate(userTask.getStartDate());
            userTaskModel.setModifiedBy(userTask.getModifiedBy());
            userTaskModel.setModifiedOn(userTask.getModifiedOn());
            userTaskModel.setTaskStatus(userTask.getTaskStatus());
            sessionFactory.getCurrentSession().update(userTaskModel);
            sessionFactory.getCurrentSession().flush();

            if (newCoins != 0)
            {
                query = sessionFactory.getCurrentSession().createQuery(
                        "from AppUserCoinBalance where userId= :userId");
                query.setLong("userId", userTask.getUserId());

                appCoinBalance = (AppUserCoinBalance) query.uniqueResult();
                if (appCoinBalance != null)
                {
                    newBalance = (appCoinBalance.getCoin_balance()) - (newCoins);

                    query = sessionFactory.getCurrentSession().createQuery(
                            "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                    query.setInteger("coin_balance", newBalance);
                    query.setInteger("userId", userTask.getUserId());

                    int modification = query.executeUpdate();
                } else
                {
                    LOG.info("else part in updateUserTask");
                }
                if (userTask != null)
                {
                    AppUserCoinTransaction appTransaction = new AppUserCoinTransaction();
                    appTransaction.setUserId(userTask.getUserId());
                    appTransaction.setCraetedBy(userTask.getUserId());
                    Date date = new Date();
                    prop = cu.getFromProperty();
                    DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
                    appTransaction.setCreatedOn(formatter.format(date));
                    appTransaction.setFoparamount(newBalance);
                    appTransaction.setTransactionType(userTask.getUserTaskId());
                    appTransaction.setAmountType(CommonConstants.TransactionAmountType.DEBITED_AMOUNT);
                    sessionFactory.getCurrentSession().save(appTransaction);

                    query = sessionFactory.getCurrentSession().createQuery(
                            "from AppUserCoinBalance where userId= :userId");
                    query.setLong("userId", userTask.getUserId());

                    appCoinBalance = (AppUserCoinBalance) query.uniqueResult();
                } else
                {
                    LOG.info("else part in updateUserTask 2");
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userTask;
    }

    /**
     * This method updateInviteUser update the invitee.
     * 
     */
    public AppUserTaskInvite updateInviteUser(AppUserTaskInvite appUserTaskInvite)
    {
        int modifications = 0;
        AppUserTaskInvite appUserTaskInviteUpdate = new AppUserTaskInvite();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskInvite.class);
            criteria.add(Restrictions.eq("inviteUserId", appUserTaskInvite.getInviteUserId()));
            criteria.add(Restrictions.eq("taskId", appUserTaskInvite.getTaskId()));
            appUserTaskInviteUpdate = (AppUserTaskInvite) criteria.uniqueResult();
            if (appUserTaskInviteUpdate == null)
            {
                Query query = sessionFactory
                        .getCurrentSession()
                        .createQuery(
                                "update AppUserTaskInvite set inviteUserId=:inviteUserId,taskStatus=:taskStatus,modifiedBy=:modifiedBy,modifiedOn=:modifiedOn where userId=:userId "
                                        + "and taskId=:taskId");
                query.setInteger("inviteUserId", appUserTaskInvite.getInviteUserId());
                query.setString("modifiedOn", appUserTaskInvite.getModifiedOn());
                query.setInteger("taskStatus", CommonConstants.UpdateTaskStatus.Open_Task);
                query.setInteger("modifiedBy", appUserTaskInvite.getModifiedBy());
                query.setInteger("userId", appUserTaskInvite.getUserId());
                query.setString("taskId", appUserTaskInvite.getTaskId());
                modifications = query.executeUpdate();
                LOG.info("modification in updateInviteUser:" + modifications);
                if (modifications == 1)
                {
                    return appUserTaskInvite;
                }
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method sharingTask share the task.
     * 
     */
    public AppUserTaskSharing sharingTask(AppUserTaskSharing user_Sharing_Task, AppUserTask appUserTask)
    {
        try
        {
            sessionFactory.getCurrentSession().save(user_Sharing_Task);
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "update AppUserTask set taskStatus=:taskStatus,reSaleCoinValue=:reSaleCoinValue where userTaskId=:userTaskId");
            query.setInteger("taskStatus", CommonConstants.UpdateTaskStatus.Pending_Share_Invitee);
            query.setInteger("reSaleCoinValue", appUserTask.getReSaleCoinValue());
            query.setString("userTaskId", user_Sharing_Task.getTaskId());
            int modifications = query.executeUpdate();

            String sql1 = "UPDATE ta_tasksharing set TASK_STATUS='0' where TASK_ID="
                    + user_Sharing_Task.getTaskId() + " and RECEIVER_ID=" + user_Sharing_Task.getRecieverId()
                    + "";

            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
            sqlQuery.executeUpdate();

            LOG.info("modification in sharingTask:" + modifications);
            return user_Sharing_Task;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return user_Sharing_Task;
    }

    /**
     * This method getTaskListAccordingUser get the task list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTask> getTaskListAccordingUser(int userId, int taskType)
    {
        ArrayList<AppUserTask> ownerTaskList = new ArrayList<AppUserTask>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTask.class);
            criteria.add(Restrictions.eq("userId", userId));
            criteria.add(Restrictions.eq("status", CommonConstants.DeleteTask.ACTIVE_TASK));
            ownerTaskList = (ArrayList<AppUserTask>) criteria.list();
            return ownerTaskList;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ownerTaskList;
    }

    /**
     * This method getInviteesData get the invitee list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId)
    {
        ArrayList<AppUserTaskInvite> getInviteesData = new ArrayList<AppUserTaskInvite>();
        try
        {

            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskInvite.class);
            criteria.add(Restrictions.eq("userId", userId));
            criteria.add(Restrictions.eq("taskId", taskId));
            getInviteesData = (ArrayList<AppUserTaskInvite>) criteria.list();
            return getInviteesData;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getInviteesData;
    }

    /**
     * This method getInviteesDataForSharing get the invitee list for sharing.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskInvite> getInviteesDataForSharing(int userId, String taskId)
    {
        ArrayList<AppUserTaskInvite> getInviteesData = new ArrayList<AppUserTaskInvite>();
        try
        {

            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskInvite.class);
            criteria.add(Restrictions.eq("inviteUserId", userId));
            criteria.add(Restrictions.eq("taskId", taskId));
            getInviteesData = (ArrayList<AppUserTaskInvite>) criteria.list();
            return getInviteesData;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getInviteesData;
    }

    /**
     * This method getInviteesData get the invitee list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId, int reciverId)
    {
        ArrayList<AppUserTaskInvite> getInviteesData = new ArrayList<AppUserTaskInvite>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskInvite.class);
            criteria.add(Restrictions.eq("userId", userId));
            criteria.add(Restrictions.eq("taskId", taskId));
            criteria.add(Restrictions.eq("inviteUserId", reciverId));
            getInviteesData = (ArrayList<AppUserTaskInvite>) criteria.list();
            return getInviteesData;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getInviteesData;
    }

    /**
     * This method getInviteUserInformation get the invitee information.
     * 
     */
    public AppUser getInviteUserInformation(int inviteUserId)
    {
        AppUser getInviteUserInformation = new AppUser();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUser.class);
            criteria.add(Restrictions.eq("userId", inviteUserId));
            getInviteUserInformation = (AppUser) criteria.uniqueResult();
            return getInviteUserInformation;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getInviteUserInformation;
    }

    /**
     * This method getInviteeList get the invitee list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskInvite> getInviteeList(int userId)
    {
        ArrayList<AppUserTaskInvite> getInviteeList = new ArrayList<AppUserTaskInvite>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskInvite.class);
            criteria.add(Restrictions.eq("inviteUserId", userId));
            criteria.add(Restrictions.ne("taskStatus", CommonConstants.UpdateTaskStatus.Rejected_By_Invitee));
            getInviteeList = (ArrayList<AppUserTaskInvite>) criteria.list();
            return getInviteeList;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getInviteeList;
    }

    /**
     * This method getSharingTaskData get the sharing list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskSharing> getSharingTaskData(int recieverId, int taskType)
    {
        ArrayList<AppUserTaskSharing> getSharingTaskData = new ArrayList<AppUserTaskSharing>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskSharing.class);
            if (taskType == 1)
            {

                criteria.add(Restrictions.eq("recieverId", recieverId));
                criteria.add(Restrictions
                        .ne("taskStatus", CommonConstants.UpdateTaskStatus.Rejected_By_Share));

            } else if (taskType == 3)
            {

                criteria.add(Restrictions.eq("senderId", recieverId));
                criteria.add(Restrictions
                        .ne("taskStatus", CommonConstants.UpdateTaskStatus.Rejected_By_Share));

            }
            getSharingTaskData = (ArrayList<AppUserTaskSharing>) criteria.list();
            return getSharingTaskData;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getSharingTaskData;
    }

    /**
     * This method getSharingTaskData get the sharing task list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskSharing> getSharingTaskData(int userId, String taskId)
    {
        ArrayList<AppUserTaskSharing> getSharingTaskData = new ArrayList<AppUserTaskSharing>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskSharing.class);
            criteria.add(Restrictions.eq("senderId", userId));
            criteria.add(Restrictions.eq("taskId", taskId));
            getSharingTaskData = (ArrayList<AppUserTaskSharing>) criteria.list();
            return getSharingTaskData;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getSharingTaskData;
    }

    /**
     * This method getTaskListAccordingUserForInvitee get the task list.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTask> getTaskListAccordingUserForInvitee(int userId, String taskId)
    {
        ArrayList<AppUserTask> ownerTaskList = new ArrayList<AppUserTask>();
        try
        {
            String sql1 = "SELECT * FROM ta_usertask WHERE USERTASK_ID=" + taskId + " AND (TASK_STATUS="
                    + CommonConstants.UpdateTaskStatus.Open_Task + " OR TASK_STATUS="
                    + CommonConstants.UpdateTaskStatus.Rejected_By_Share + ") AND USER_ID=" + userId
                    + " AND STATUS=" + CommonConstants.DeleteTask.ACTIVE_TASK;
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql1);
            query.addEntity(AppUserTask.class);

            ownerTaskList = (ArrayList<AppUserTask>) query.list();

            return ownerTaskList;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ownerTaskList;
    }

    /**
     * This method getTaskListAccordingUserForSharing get the task list for the
     * user.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTask> getTaskListAccordingUserForSharing(int inviteUserId, String taskId)
    {
        ArrayList<AppUserTask> ownerTaskList = new ArrayList<AppUserTask>();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTask.class);
            criteria.add(Restrictions.eq("userId", inviteUserId));
            criteria.add(Restrictions.eq("userTaskId", taskId));
            ownerTaskList = (ArrayList<AppUserTask>) criteria.list();

            return ownerTaskList;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ownerTaskList;
    }

    /**
     * This method getTaskInformation get the task list for the user.
     * 
     */
    public AppUserTask getTaskInformation(String taskId, int taskType)
    {
        AppUserTask appUserTask = new AppUserTask();
        try
        {
            if (taskType == 3)
            {
                String sql1 = "SELECT * FROM ta_usertask WHERE USERTASK_ID=" + taskId + " AND (TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Accepted_By_Share + " OR TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Completed_By_Share + " " + "OR TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Pending_Share_Invitee + ") AND STATUS="
                        + CommonConstants.DeleteTask.ACTIVE_TASK;
                SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                query.addEntity(AppUserTask.class);
                appUserTask = (AppUserTask) query.uniqueResult();
            } else if (taskType == 4)
            {
                String sql1 = "SELECT * FROM ta_usertask WHERE USERTASK_ID=" + taskId + " AND (TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Accepted_By_Share + " OR TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Accepted_By_Invitee + " OR TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Completed_By_Share + " OR TASK_STATUS="
                        + CommonConstants.UpdateTaskStatus.Completed_By_Invitee + ")";
                SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                query.addEntity(AppUserTask.class);
                appUserTask = (AppUserTask) query.uniqueResult();
            } else if (taskType == 1)
            {
                Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTask.class);
                criteria.add(Restrictions.eq("userTaskId", taskId));
                criteria.add(Restrictions.eq("status", CommonConstants.DeleteTask.ACTIVE_TASK));
                appUserTask = (AppUserTask) criteria.uniqueResult();
            }
            return appUserTask;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserTask;
    }

    /**
     * This method updateTaskStatus update the task status.
     * 
     */
    public AppUserTask updateTaskStatus(AppUserTask updateTaskStatus)
    {
        try
        {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "update AppUserTask set taskStatus=:taskStatus where userTaskId=:userTaskId");
            query.setInteger("taskStatus", updateTaskStatus.getTaskStatus());
            query.setString("userTaskId", updateTaskStatus.getUserTaskId());
            int modifications = query.executeUpdate();
            LOG.info("modification in updateTaskStatus:" + modifications);

            if (updateTaskStatus.getTaskStatus() == 5)
            {

                String sql1 = "UPDATE ta_taskinvitation set TASK_STATUS='5' where TASK_ID="
                        + updateTaskStatus.getUserTaskId() + " and RECEIVER_ID="
                        + updateTaskStatus.getUserId() + "";

                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                sqlQuery.executeUpdate();

                query = sessionFactory.getCurrentSession().createQuery(
                        "from AppUserTask where userTaskId= :userTaskId");
                query.setString("userTaskId", updateTaskStatus.getUserTaskId());
                AppUserTask appTask = (AppUserTask) query.uniqueResult();

                AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
                int newBalance = 0;
                try
                {
                    if (updateTaskStatus != null)
                    {
                        AppUserCoinTransaction appTransaction = new AppUserCoinTransaction();
                        appTransaction.setUserId(updateTaskStatus.getUserId());
                        appTransaction.setCraetedBy(updateTaskStatus.getUserId());
                        Date date = new Date();
                        prop = cu.getFromProperty();
                        DateFormat formatter = new SimpleDateFormat(
                                prop.getProperty("app.sample.date.formate"));
                        appTransaction.setCreatedOn(formatter.format(date));
                        appTransaction.setFoparamount(appTask.getCoinValue());
                        appTransaction.setTransactionType(updateTaskStatus.getUserTaskId());
                        appTransaction.setAmountType(CommonConstants.TransactionAmountType.CREDITED_AMOUNT);
                        sessionFactory.getCurrentSession().save(appTransaction);

                        query = sessionFactory.getCurrentSession().createQuery(
                                "from AppUserCoinBalance where userId= :userId");
                        query.setLong("userId", updateTaskStatus.getUserId());

                        appCoinBalance = (AppUserCoinBalance) query.uniqueResult();

                        if (appCoinBalance != null)
                        {
                            newBalance = (appCoinBalance.getCoin_balance()) + (appTask.getCoinValue());

                            query = sessionFactory
                                    .getCurrentSession()
                                    .createQuery(
                                            "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                            query.setInteger("coin_balance", newBalance);
                            query.setInteger("userId", updateTaskStatus.getUserId());

                            int modification = query.executeUpdate();
                            updateTaskStatus.setCoinValue(newBalance);
                            return updateTaskStatus;
                        } else
                        {
                            appCoinBalance = new AppUserCoinBalance();
                            appCoinBalance.setUserId(updateTaskStatus.getUserId());
                            appCoinBalance.setCoin_balance(appTask.getCoinValue());
                            appCoinBalance.setCraetedBy(updateTaskStatus.getUserId());
                            appCoinBalance.setCreatedOn(formatter.format(date));

                            sessionFactory.getCurrentSession().save(appCoinBalance);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            } else if (updateTaskStatus.getTaskStatus() == 6)
            {

                String sql1 = "UPDATE ta_tasksharing set TASK_STATUS='6' where TASK_ID="
                        + updateTaskStatus.getUserTaskId() + " and RECEIVER_ID="
                        + updateTaskStatus.getUserId() + "";

                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                sqlQuery.executeUpdate();

                query = sessionFactory.getCurrentSession().createQuery(
                        "from AppUserTask where userTaskId= :userTaskId");
                query.setString("userTaskId", updateTaskStatus.getUserTaskId());
                AppUserTask appTask = (AppUserTask) query.uniqueResult();

                query = sessionFactory.getCurrentSession().createQuery(
                        "from AppUserTaskSharing where taskId= :taskId");
                query.setString("taskId", updateTaskStatus.getUserTaskId());
                AppUserTaskSharing appSharing = (AppUserTaskSharing) query.uniqueResult();

                int sum = (appTask.getCoinValue()) - (appTask.getReSaleCoinValue());

                AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();
                int newBalance = 0;
                try
                {

                    if (updateTaskStatus != null)
                    {
                        AppUserCoinTransaction appTransaction = new AppUserCoinTransaction();
                        appTransaction.setUserId(updateTaskStatus.getUserId());
                        appTransaction.setCraetedBy(updateTaskStatus.getUserId());
                        Date date = new Date();
                        prop = cu.getFromProperty();
                        DateFormat formatter = new SimpleDateFormat(
                                prop.getProperty("app.sample.date.formate"));
                        appTransaction.setCreatedOn(formatter.format(date));
                        appTransaction.setFoparamount(appTask.getReSaleCoinValue());
                        appTransaction.setTransactionType(updateTaskStatus.getUserTaskId());
                        appTransaction.setAmountType(CommonConstants.TransactionAmountType.CREDITED_AMOUNT);
                        sessionFactory.getCurrentSession().save(appTransaction);

                        appTransaction = new AppUserCoinTransaction();
                        appTransaction.setUserId(appSharing.getSenderId());
                        appTransaction.setCraetedBy(appSharing.getSenderId());
                        appTransaction.setCreatedOn(formatter.format(date));
                        appTransaction.setFoparamount(sum);
                        appTransaction.setTransactionType(updateTaskStatus.getUserTaskId());
                        appTransaction.setAmountType(CommonConstants.TransactionAmountType.CREDITED_AMOUNT);
                        sessionFactory.getCurrentSession().save(appTransaction);

                        query = sessionFactory.getCurrentSession().createQuery(
                                "from AppUserCoinBalance where userId= :userId");
                        query.setLong("userId", updateTaskStatus.getUserId());

                        appCoinBalance = (AppUserCoinBalance) query.uniqueResult();

                        if (appCoinBalance != null)
                        {
                            newBalance = (appCoinBalance.getCoin_balance()) + (appTask.getReSaleCoinValue());

                            query = sessionFactory
                                    .getCurrentSession()
                                    .createQuery(
                                            "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                            query.setInteger("coin_balance", newBalance);
                            query.setInteger("userId", updateTaskStatus.getUserId());

                            int modification = query.executeUpdate();
                            updateTaskStatus.setCoinValue(newBalance);
                            return updateTaskStatus;
                        } else
                        {
                            appCoinBalance = new AppUserCoinBalance();
                            appCoinBalance.setUserId(updateTaskStatus.getUserId());
                            appCoinBalance.setCoin_balance(appTask.getReSaleCoinValue());
                            appCoinBalance.setCraetedBy(updateTaskStatus.getUserId());
                            appCoinBalance.setCreatedOn(formatter.format(date));

                            sessionFactory.getCurrentSession().save(appCoinBalance);
                        }

                        query = sessionFactory.getCurrentSession().createQuery(
                                "from AppUserCoinBalance where userId= :userId");
                        query.setLong("userId", appSharing.getSenderId());

                        appCoinBalance = (AppUserCoinBalance) query.uniqueResult();

                        if (appCoinBalance != null)
                        {
                            newBalance = (appCoinBalance.getCoin_balance()) + (sum);

                            query = sessionFactory
                                    .getCurrentSession()
                                    .createQuery(
                                            "update AppUserCoinBalance set coin_balance=:coin_balance where userId=:userId");
                            query.setInteger("coin_balance", newBalance);
                            query.setInteger("userId", appSharing.getSenderId());

                            int modification = query.executeUpdate();
                            updateTaskStatus.setCoinValue(newBalance);
                            return updateTaskStatus;
                        } else
                        {
                            appCoinBalance = new AppUserCoinBalance();
                            appCoinBalance.setUserId(appSharing.getSenderId());
                            appCoinBalance.setCoin_balance(sum);
                            appCoinBalance.setCraetedBy(appSharing.getSenderId());
                            appCoinBalance.setCreatedOn(formatter.format(date));

                            sessionFactory.getCurrentSession().save(appCoinBalance);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            if (modifications == 1)
            {
                return updateTaskStatus;
            } else
            {
                return null;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return updateTaskStatus;
    }

    /**
     * This method createAssignee create the assignee.
     * 
     */
    public AppUserTaskAssign createAssignee(AppUserTaskAssign appUserTaskAssign,
            AppUserTaskInvite appUserTaskInvite)
    {
        try
        {

            if (appUserTaskInvite.getTaskStatus() == 1)
            {
                String sql1 = "UPDATE ta_taskinvitation set TASK_STATUS=" + appUserTaskInvite.getTaskStatus()
                        + " where TASK_ID=" + appUserTaskInvite.getTaskId() + " and RECEIVER_ID="
                        + appUserTaskInvite.getUserId() + "";

                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                sqlQuery.executeUpdate();
                sessionFactory.getCurrentSession().save(appUserTaskAssign);
            } else if (appUserTaskInvite.getTaskStatus() == 2)
            {
                String sql1 = "UPDATE ta_taskinvitation set TASK_STATUS=" + appUserTaskInvite.getTaskStatus()
                        + " where TASK_ID=" + appUserTaskInvite.getTaskId() + " and RECEIVER_ID="
                        + appUserTaskInvite.getUserId() + "";

                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                sqlQuery.executeUpdate();

            } else if (appUserTaskInvite.getTaskStatus() == 3)
            {
                String sql1 = "UPDATE ta_tasksharing set TASK_STATUS=" + appUserTaskInvite.getTaskStatus()
                        + " where TASK_ID=" + appUserTaskInvite.getTaskId() + " and RECEIVER_ID="
                        + appUserTaskInvite.getUserId() + "";

                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                sqlQuery.executeUpdate();
                sessionFactory.getCurrentSession().save(appUserTaskAssign);
            } else if (appUserTaskInvite.getTaskStatus() == 4)
            {
                String sql1 = "UPDATE ta_tasksharing set TASK_STATUS=" + appUserTaskInvite.getTaskStatus()
                        + " where TASK_ID=" + appUserTaskInvite.getTaskId() + " and RECEIVER_ID="
                        + appUserTaskInvite.getUserId() + "";

                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql1);
                sqlQuery.executeUpdate();
            } else
            {

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return appUserTaskAssign;
    }

    /**
     * This method getAssignListData get the assignee.
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AppUserTaskAssign> getAssignListData(int userId)
    {
        ArrayList<AppUserTaskAssign> userAssignTaskList = new ArrayList<AppUserTaskAssign>();

        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskAssign.class);
            criteria.add(Restrictions.eq("recieverId", userId));
            userAssignTaskList = (ArrayList<AppUserTaskAssign>) criteria.list();
            return userAssignTaskList;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return userAssignTaskList;
    }

    /**
     * This method getExistSharingData get the Exist Sharing Data.
     * 
     */
    public AppUserTaskSharing getExistSharingData(int userId, String taskId)
    {
        AppUserTaskSharing appUserTaskSharing = new AppUserTaskSharing();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTaskSharing.class);
            criteria.add(Restrictions.eq("senderId", userId));
            criteria.add(Restrictions.eq("taskId", taskId));
            appUserTaskSharing = (AppUserTaskSharing) criteria.uniqueResult();
            return appUserTaskSharing;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appUserTaskSharing;
    }

    /**
     * This method updateAssignee update the assignee.
     * 
     */
    public AppUserTaskAssign updateAssignee(AppUserTaskAssign updateAssignee)
    {
        try
        {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "update AppUserTaskAssign set senderId=:senderId,recieverId=:recieverId where taskId=:taskId");
            query.setInteger("senderId", updateAssignee.getSenderId());
            query.setInteger("recieverId", updateAssignee.getRecieverId());
            query.setString("taskId", updateAssignee.getTaskId());

            int modifications = query.executeUpdate();
            LOG.info("modification in updateAssignee:" + modifications);
            if (modifications == 1)
            {
                return updateAssignee;
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return updateAssignee;
    }

    /**
     * This method getCoinBalance get the Coin Balance for the user.
     * 
     */
    public AppUserCoinBalance getCoinBalance(int userId)
    {
        Query query = null;
        int newBalance = 0;
        AppUserCoinBalance appCoinBalance = new AppUserCoinBalance();

        query = sessionFactory.getCurrentSession().createQuery(
                "from AppUserCoinBalance where userId= :userId");
        query.setLong("userId", userId);

        appCoinBalance = (AppUserCoinBalance) query.uniqueResult();

        if (appCoinBalance != null)
        {
            newBalance = appCoinBalance.getCoin_balance();

            appCoinBalance.setCoin_balance(newBalance);
            return appCoinBalance;
        }
        return appCoinBalance;
    }

    /**
     * This method getTask get task List according to taskId.
     * 
     */
    public AppUserTask getTask(String taskId)
    {
        AppUserTask appTask = new AppUserTask();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppUserTask.class);
            criteria.add(Restrictions.eq("userTaskId", taskId));
            appTask = (AppUserTask) criteria.uniqueResult();
            return appTask;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return appTask;
    }

}
