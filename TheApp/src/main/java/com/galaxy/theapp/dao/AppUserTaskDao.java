/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao;

import java.util.ArrayList;
import java.util.List;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTaskAssign;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskSharing;

/**
 * This <java>interface</java> AppUserTaskDao interface has all the abstract
 * methods related to userTask.
 * 
 * @author param Sheikh
 * @GWL
 */

public interface AppUserTaskDao
{
    /**
     * This method saveUserTask.
     * 
     * @paparam userTask
     *            <code>AppUserTask</code> to be saveUserTask from database
     * @return AppUserTask <code>AppUserTask</code> saveUserTask from database
     * @throws Exception
     *             error while saveUserTask information in database
     */
    public AppUserTask saveUserTask(AppUserTask userTask);

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
     * This method getTask.
     * 
     * @paparam userId
     *            <code>AppUserTask</code> to be getTask from database
     * @return AppUserTask <code>AppUserTask</code> getTask from database
     * @throws Exception
     *             error while getTask information in database
     */
    public AppUserTask getTask(String taskId);

    /**
     * This method getTaskList.
     * 
     * @paparam userId
     *            <code>AppUserTask</code> to be getTaskList from database
     * @return AppUserTask <code>AppUserTask</code> getTaskList from database
     * @throws Exception
     *             error while getTaskList information in database
     */

    public List<AppUserTask> getTaskList(int userId);

    /**
     * This method editUserTask.
     * 
     * @paparam editTask
     *            <code>AppUserTask</code> to be editUserTask from database
     * @return AppUserTask <code>AppUserTask</code> editUserTask from database
     * @throws Exception
     *             error while editUserTask information in database
     */

    public AppUserTask editUserTask(AppUserTask editTask);

    /**
     * This method appUserTaskInvite.
     * 
     * @paparam appUserTaskInvite
     *            <code>AppUserTaskInvite</code> to be appUserTaskInvite from
     *            database
     * @return AppUserTaskInvite <code>AppUserTaskInvite</code>
     *         appUserTaskInvite from database
     * @throws Exception
     *             error while appUserTaskInvite information in database
     */

    public AppUserTaskInvite appUserTaskInvite(AppUserTaskInvite appUserTaskInvite);

    /**
     * This method updateUserTask.
     * 
     * @paparam userTask
     *            ,newCoins <code>AppUserTask</code> to be updateUserTask from
     *            database
     * @return AppUserTask <code>AppUserTask</code> updateUserTask from database
     * @throws Exception
     *             error while updateUserTask information in database
     */

    public AppUserTask updateUserTask(AppUserTask userTask, int newCoins);

    /**
     * This method updateInviteUser.
     * 
     * @paparam appUserTaskInvite
     *            <code>AppUserTaskInvite</code> to be updateInviteUser from
     *            database
     * @return AppUserTaskInvite <code>AppUserTaskInvite</code> updateInviteUser
     *         from database
     * @throws Exception
     *             error while updateInviteUser information in database
     */

    public AppUserTaskInvite updateInviteUser(AppUserTaskInvite appUserTaskInvite);

    /**
     * This method sharingTask.
     * 
     * @paparam userSharingTask
     *            ,appUserTask <code>AppUserTaskSharing</code> to be sharingTask
     *            from database
     * @return AppUserTaskSharing <code>AppUserTaskSharing</code> sharingTask
     *         from database
     * @throws Exception
     *             error while sharingTask information in database
     */

    public AppUserTaskSharing sharingTask(AppUserTaskSharing userSharingTask, AppUserTask appUserTask);

    /**
     * This method getTaskListAccordingUser.
     * 
     * @paparam userId
     *            ,taskType <code>AppUserTask</code> to be
     *            getTaskListAccordingUser from database
     * @return AppUserTask <code>AppUserTask</code> getTaskListAccordingUser
     *         from database
     * @throws Exception
     *             error while getTaskListAccordingUser information in database
     */

    public ArrayList<AppUserTask> getTaskListAccordingUser(int userId, int taskType);

    /**
     * This method getInviteesData.
     * 
     * @paparam userId
     *            ,taskId <code>AppUserTaskInvite</code> to be getInviteesData
     *            from database
     * @return AppUserTaskInvite <code>AppUserTaskInvite</code> getInviteesData
     *         from database
     * @throws Exception
     *             error while getInviteesData information in database
     */

    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId);

    /**
     * This method getInviteUserInformation.
     * 
     * @paparam inviteUserId
     *            <code>AppUser</code> to be getInviteUserInformation from
     *            database
     * @return AppUser <code>AppUser</code> getInviteUserInformation from
     *         database
     * @throws Exception
     *             error while getInviteUserInformation information in database
     */

    public AppUser getInviteUserInformation(int inviteUserId);

    /**
     * This method getInviteeList.
     * 
     * @paparam userId
     *            <code>AppUserTaskInvite</code> to be getInviteeList from
     *            database
     * @return AppUserTaskInvite <code>AppUserTaskInvite</code> getInviteeList
     *         from database
     * @throws Exception
     *             error while getInviteeList information in database
     */

    public ArrayList<AppUserTaskInvite> getInviteeList(int userId);

    /**
     * This method getSharingTaskData.
     * 
     * @paparam inviteUserId
     *            ,taskType <code>AppUserTaskSharing</code> to be
     *            getSharingTaskData from database
     * @return AppUserTaskSharing <code>AppUserTaskSharing</code>
     *         getSharingTaskData from database
     * @throws Exception
     *             error while getSharingTaskData information in database
     */

    public ArrayList<AppUserTaskSharing> getSharingTaskData(int inviteUserId, int taskType);

    /**
     * This method getSharingTaskData.
     * 
     * @paparam userId
     *            ,taskId <code>AppUserTaskSharing</code> to be
     *            getSharingTaskData from database
     * @return AppUserTaskSharing <code>AppUserTaskSharing</code>
     *         getSharingTaskData from database
     * @throws Exception
     *             error while getSharingTaskData information in database
     */

    public ArrayList<AppUserTaskSharing> getSharingTaskData(int userId, String taskId);

    /**
     * This method getTaskListAccordingUserForInvitee.
     * 
     * @paparam userId
     *            ,taskId <code>AppUserTask</code> to be
     *            getTaskListAccordingUserForInvitee from database
     * @return AppUserTask <code>AppUserTask</code>
     *         getTaskListAccordingUserForInvitee from database
     * @throws Exception
     *             error while getTaskListAccordingUserForInvitee information in
     *             database
     */

    public ArrayList<AppUserTask> getTaskListAccordingUserForInvitee(int userId, String taskId);

    /**
     * This method getInviteesData.
     * 
     * @paparam userId
     *            ,taskId,reciverId <code>AppUserTaskInvite</code> to be
     *            getInviteesData from database
     * @return AppUserTaskInvite <code>AppUserTaskInvite</code> getInviteesData
     *         from database
     * @throws Exception
     *             error while getInviteesData information in database
     */

    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId, int reciverId);

    /**
     * This method getInviteesDataForSharing.
     * 
     * @paparam senderId
     *            ,taskId <code>AppUserTaskInvite</code> to be
     *            getInviteesDataForSharing from database
     * @return AppUserTaskInvite <code>AppUserTaskInvite</code>
     *         getInviteesDataForSharing from database
     * @throws Exception
     *             error while getInviteesDataForSharing information in database
     */

    public ArrayList<AppUserTaskInvite> getInviteesDataForSharing(int senderId, String taskId);

    /**
     * This method getTaskListAccordingUserForSharing.
     * 
     * @paparam inviteUserId
     *            ,taskId <code>AppUserTask</code> to be
     *            getTaskListAccordingUserForSharing from database
     * @return AppUserTask <code>AppUserTask</code>
     *         getTaskListAccordingUserForSharing from database
     * @throws Exception
     *             error while getTaskListAccordingUserForSharing information in
     *             database
     */

    public ArrayList<AppUserTask> getTaskListAccordingUserForSharing(int inviteUserId, String taskId);

    /**
     * This method getTaskInformation.
     * 
     * @paparam taskId
     *            ,taskType <code>AppUserTask</code> to be getTaskInformation
     *            from database
     * @return AppUserTask <code>AppUserTask</code> getTaskInformation from
     *         database
     * @throws Exception
     *             error while getTaskInformation information in database
     */

    public AppUserTask getTaskInformation(String taskId, int taskType);

    /**
     * This method updateTaskStatus.
     * 
     * @paparam updateTaskStatus
     *            <code>AppUserTask</code> to be updateTaskStatus from database
     * @return AppUserTask <code>AppUserTask</code> updateTaskStatus from
     *         database
     * @throws Exception
     *             error while updateTaskStatus information in database
     */

    public AppUserTask updateTaskStatus(AppUserTask updateTaskStatus);

    /**
     * This method updateAssignee.
     * 
     * @paparam updateAssignee
     *            <code>AppUserTaskAssign</code> to be updateAssignee from
     *            database
     * @return AppUserTaskAssign <code>AppUserTaskAssign</code> updateAssignee
     *         from database
     * @throws Exception
     *             error while updateAssignee information in database
     */

    public AppUserTaskAssign updateAssignee(AppUserTaskAssign updateAssignee);

    /**
     * This method createAssignee.
     * 
     * @paparam appUserTaskAssign
     *            ,appUserTaskInvite <code>AppUserTaskAssign</code> to be
     *            createAssignee from database
     * @return AppUserTaskAssign <code>AppUserTaskAssign</code> createAssignee
     *         from database
     * @throws Exception
     *             error while createAssignee information in database
     */

    public AppUserTaskAssign createAssignee(AppUserTaskAssign appUserTaskAssign,
            AppUserTaskInvite appUserTaskInvite);

    /**
     * This method getAssignListData.
     * 
     * @paparam userId
     *            <code>AppUserTaskAssign</code> to be getAssignListData from
     *            database
     * @return AppUserTaskAssign <code>AppUserTaskAssign</code>
     *         getAssignListData from database
     * @throws Exception
     *             error while createAssignee getAssignListData in database
     */

    public ArrayList<AppUserTaskAssign> getAssignListData(int userId);

    /**
     * This method getExistSharingData.
     * 
     * @paparam userId
     *            ,taskId <code>AppUserTaskSharing</code> to be
     *            getExistSharingData from database
     * @return AppUserTaskSharing <code>AppUserTaskSharing</code>
     *         getExistSharingData from database
     * @throws Exception
     *             error while getExistSharingData in database
     */

    public AppUserTaskSharing getExistSharingData(int userId, String taskId);

}
