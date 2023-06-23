/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Service;
import org.springfparamework.transaction.annotation.Propagation;
import org.springfparamework.transaction.annotation.Transactional;

import com.galaxy.theapp.dao.AppUserTaskDao;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTaskAssign;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskSharing;
import com.galaxy.theapp.service.AppUserTaskService;

/**
 * This <java>class</java> UserTaskCreationServiceImpl use to perform all our
 * service related logics for the userTask.
 * 
 * @author param Sheikh
 * @GWL
 */

@Service("userTaskCreationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AppUserTaskServiceImpl implements AppUserTaskService
{

    public static final Logger LOG = Logger.getLogger(AppUserTaskServiceImpl.class);

    @Autowired
    public AppUserTaskDao appUserTaskDao;

    /**
     * This method save user new task on DB saveUserTaskCreation().
     * 
     */

    public AppUserTask saveUserTask(AppUserTask userTaskModel) throws Exception

    {
        AppUserTask userTask = new AppUserTask();
        try
        {
            userTask = appUserTaskDao.saveUserTask(userTaskModel);
            LOG.info("saveUserTask-task added successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return userTask;
    }

    /**
     * This method getTaskList from DB.
     * 
     */

    public List<AppUserTask> getTaskList(int userId)
    {
        return appUserTaskDao.getTaskList(userId);
    }

    /**
     * This method EditUserTask edit the user task.
     * 
     */
    public AppUserTask editUserTask(AppUserTask editTask) throws Exception
    {
        try
        {
            editTask = appUserTaskDao.editUserTask(editTask);
            LOG.info("editUserTask-task edited successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return editTask;
    }

    /**
     * This method appUserTaskInvite save the invitee.
     * 
     */
    public AppUserTaskInvite appUserTaskInvite(AppUserTaskInvite appUserTaskInvite) throws Exception
    {

        AppUserTaskInvite gdFriends = new AppUserTaskInvite();
        try
        {
            gdFriends = appUserTaskDao.appUserTaskInvite(appUserTaskInvite);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return gdFriends;

    }

    /**
     * This method updateUserTask update the user task.
     * 
     */
    public AppUserTask updateUserTask(AppUserTask userTask, int newCoins) throws Exception
    {
        AppUserTask updateUserTask = new AppUserTask();
        try
        {
            updateUserTask = appUserTaskDao.updateUserTask(userTask, newCoins);
            LOG.info("updateUserTask updated successfully");

        } catch (Exception e)
        {

            LOG.error("Error in UserTaskCreationServiceImpl.updateUserTask while updateUserTask", e);

            throw new Exception(e);

        }
        return updateUserTask;
    }

    /**
     * This method updateInviteUser update the invitee.
     * 
     */
    public AppUserTaskInvite UpdateinviteUser(AppUserTaskInvite appUserTaskInvite) throws Exception
    {
        AppUserTaskInvite gdFriends = new AppUserTaskInvite();
        try
        {
            gdFriends = appUserTaskDao.updateInviteUser(appUserTaskInvite);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return gdFriends;
    }

    /**
     * This method sharingTask share the task.
     * 
     */
    public AppUserTaskSharing sharingTask(AppUserTaskSharing user_Sharing_Task, AppUserTask appUserTask)
            throws Exception
    {
        AppUserTaskSharing sharingTask = new AppUserTaskSharing();
        try
        {
            sharingTask = appUserTaskDao.sharingTask(user_Sharing_Task, appUserTask);
            LOG.info("sharingTask-task shared successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return sharingTask;
    }

    /**
     * This method getTaskListAccordingUser get the task list.
     * 
     */
    public ArrayList<AppUserTask> getTaskListAccordingUser(int userId, int taskType) throws Exception
    {
        return appUserTaskDao.getTaskListAccordingUser(userId, taskType);
    }

    /**
     * This method getInviteesData get the invitee list.
     * 
     */
    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId) throws Exception
    {
        return appUserTaskDao.getInviteesData(userId, taskId);
    }

    /**
     * This method getInviteUserInformation() get the InviteUserInformation.
     * 
     * 
     */
    public AppUser getInviteUserInformation(int inviteUserId) throws Exception
    {
        AppUser getInviteUserInformation = new AppUser();
        try
        {
            getInviteUserInformation = appUserTaskDao.getInviteUserInformation(inviteUserId);
            LOG.info("getInviteUserInformation successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return getInviteUserInformation;
    }

    /**
     * This method getInviteeList() get the InviteeList.
     * 
     * 
     */
    public ArrayList<AppUserTaskInvite> getInviteeList(int userId) throws Exception
    {
        return appUserTaskDao.getInviteeList(userId);
    }

    /**
     * This method getSharingTaskData() get the SharingTaskData.
     * 
     * 
     */
    public ArrayList<AppUserTaskSharing> getSharingTaskData(int inviteUserId, int taskType) throws Exception
    {
        return appUserTaskDao.getSharingTaskData(inviteUserId, taskType);
    }

    /**
     * This method getSharingData() get the SharingData.
     * 
     * 
     */
    public ArrayList<AppUserTaskSharing> getSharingData(int userId, String taskId) throws Exception
    {
        return appUserTaskDao.getSharingTaskData(userId, taskId);
    }

    /**
     * This method getTaskListAccordingUserForInvitee() get the TaskList
     * According UserForInvitee.
     * 
     * 
     */
    public ArrayList<AppUserTask> getTaskListAccordingUserForInvitee(int userId, String taskId)
            throws Exception
    {
        return appUserTaskDao.getTaskListAccordingUserForInvitee(userId, taskId);
    }

    /**
     * This method getInviteesData() get the InviteesData.
     * 
     * 
     */
    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId, int reciverId)
            throws Exception
    {
        return appUserTaskDao.getInviteesData(userId, taskId, reciverId);
    }

    /**
     * This method getInviteesDataForSharing() get the InviteesData ForSharing.
     * 
     * 
     */
    public ArrayList<AppUserTaskInvite> getInviteesDataForSharing(int senderId, String taskId)
            throws Exception
    {
        return appUserTaskDao.getInviteesDataForSharing(senderId, taskId);
    }

    /**
     * This method getTaskListAccordingUserForSharing() get the TaskList
     * According UserForSharing.
     * 
     * 
     */
    public ArrayList<AppUserTask> getTaskListAccordingUserForSharing(int inviteUserId, String taskId)
            throws Exception
    {
        return appUserTaskDao.getTaskListAccordingUserForSharing(inviteUserId, taskId);
    }

    /**
     * This method getTaskInformation() get the TaskInformation.
     * 
     * 
     */
    public AppUserTask getTaskInformation(String taskId, int taskType) throws Exception
    {
        AppUserTask userTask = new AppUserTask();
        try
        {
            userTask = appUserTaskDao.getTaskInformation(taskId, taskType);
            LOG.info("getTaskInformation successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return userTask;
    }

    /**
     * This method updateTaskStatus() update the TaskStatus.
     * 
     * 
     */
    public AppUserTask updateTaskStatus(AppUserTask updateTaskStatus) throws Exception
    {
        AppUserTask taskStatus = new AppUserTask();
        try
        {
            taskStatus = appUserTaskDao.updateTaskStatus(updateTaskStatus);
            LOG.info("updateTaskStatus successfully");

        } catch (Exception e)
        {

            LOG.error("Error in UserTaskCreationServiceImpl.updateUserTask while updateUserTask", e);

            throw new Exception(e);

        }
        return taskStatus;
    }

    /**
     * This method createAssignee() create the Assignee.
     * 
     * 
     */
    public AppUserTaskAssign createAssignee(AppUserTaskAssign appUserTaskAssign,
            AppUserTaskInvite appUserTaskInvite) throws Exception
    {
        AppUserTaskAssign appAssignuser = new AppUserTaskAssign();
        try
        {
            appAssignuser = appUserTaskDao.createAssignee(appUserTaskAssign, appUserTaskInvite);
            LOG.info("createAssignee successfully");

        } catch (Exception e)
        {

            LOG.error("Error in AppUserTaskServiceImpl.createAssignee while savingAssigneeInformation", e);

            throw new Exception(e);

        }
        return appAssignuser;
    }

    /**
     * This method getAssignListData() get the AssignListData.
     * 
     * 
     */
    public ArrayList<AppUserTaskAssign> getAssignListData(int userId) throws Exception
    {
        ArrayList<AppUserTaskAssign> userTaskAssignList = new ArrayList<AppUserTaskAssign>();
        try
        {
            userTaskAssignList = appUserTaskDao.getAssignListData(userId);
            LOG.info("Getting information From Assign User successfully");

        } catch (Exception e)
        {

            LOG.error("Error in AppUserTaskServiceImpl.getAssignListData while getAssignListData", e);

            throw new Exception(e);

        }
        return userTaskAssignList;
    }

    /**
     * This method getExistSharingData() get the ExistSharingData.
     * 
     * 
     */
    public AppUserTaskSharing getExistSharingData(int userId, String taskId) throws Exception
    {
        AppUserTaskSharing appUserTaskSharing = new AppUserTaskSharing();
        try
        {
            appUserTaskSharing = appUserTaskDao.getExistSharingData(userId, taskId);
            LOG.info("getExistSharingData successfully");


        } catch (Exception e)
        {

            LOG.error("Error in AppUserTaskServiceImpl.createAssignee while savingAssigneeInformation", e);

            throw new Exception(e);

        }
        return appUserTaskSharing;
    }

    /**
     * This method updateAssignee() update the Assignee.
     * 
     * 
     */
    public AppUserTaskAssign updateAssignee(AppUserTaskAssign updateAssignee) throws Exception
    {
        AppUserTaskAssign upAssign = new AppUserTaskAssign();
        try
        {
            upAssign = appUserTaskDao.updateAssignee(updateAssignee);
            LOG.info("updateAssignee successfully");

        } catch (Exception e)
        {

            LOG.error("Error in UserTaskCreationServiceImpl.updateUserTask while updateUserTask", e);

            throw new Exception(e);

        }
        return upAssign;
    }

    /**
     * This method getCoinBalance() get the CoinBalance.
     * 
     * 
     */
    public AppUserCoinBalance getCoinBalance(int userId) throws Exception
    {
        AppUserCoinBalance usBalance = new AppUserCoinBalance();
        try
        {
            usBalance = appUserTaskDao.getCoinBalance(userId);
            LOG.info("getCoinBalance successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return usBalance;
    }

    /**
     * This method getTask() get the Task.
     * 
     * 
     */
    public AppUserTask getTask(String taskId) throws Exception
    {
        AppUserTask appTask = new AppUserTask();
        try
        {
            appTask = appUserTaskDao.getTask(taskId);
            LOG.info("getTask successfully");

        } catch (Exception e)
        {

            LOG.error("Error in VendorServiceImpl.saveVendor while savingVendorInformation", e);

            throw new Exception(e);

        }
        return appTask;
    }

}
