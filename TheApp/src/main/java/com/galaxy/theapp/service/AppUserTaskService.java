/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.service;

import java.util.ArrayList;
import java.util.List;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTaskAssign;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskSharing;

/**
 * This <java>interface</java> AppUserTaskService defines all abstract methods
 * of userTask. operations.
 * 
 * @author param Sheikh
 * @GWL
 */
public interface AppUserTaskService
{
    /**
     * This method saveUserTask
     * 
     * @paparam userTask
     *            : <code>AppUserTask</code> information to be saved in database
     * @return AppUserTask
     * @throws Exception
     *             error while saving the saveUserTask information.
     */
    public AppUserTask saveUserTask(AppUserTask userTask) throws Exception;

    /**
     * This method getCoinBalance
     * 
     * @paparam userId
     *            : <code>AppUserCoinBalance</code> information to be get from
     *            database
     * @return AppUserCoinBalance
     * @throws Exception
     *             error while fetching getCoinBalance information.
     */
    public AppUserCoinBalance getCoinBalance(int userId) throws Exception;

    /**
     * This method getTaskList
     * 
     * @paparam userId
     *            : <code>AppUserTask</code> information to be get from database
     * @return AppUserTask
     * @throws Exception
     *             error while fetching getTaskList information.
     */

    public List<AppUserTask> getTaskList(int userId);

    /**
     * This method editUserTask
     * 
     * @paparam userTask
     *            : <code>AppUserTask</code> information to be edit in database
     * @return AppUserTask
     * @throws Exception
     *             error while editUserTask information.
     */
    public AppUserTask editUserTask(AppUserTask userTask) throws Exception;

    /**
     * This method appUserTaskInvite
     * 
     * @paparam appUserTaskInvite
     *            : <code>AppUserTaskInvite</code> information to be
     *            appUserTaskInvite in database
     * @return AppUserTaskInvite
     * @throws Exception
     *             error while appUserTaskInvite information.
     */

    public AppUserTaskInvite appUserTaskInvite(AppUserTaskInvite appUserTaskInvite) throws Exception;

    /**
     * This method updateUserTask
     * 
     * @paparam userTask
     *            ,newCoins : <code>AppUserTask</code> information to be saved
     *            in database
     * @return AppUserTask
     * @throws Exception
     *             error while updateUserTask information.
     */

    public AppUserTask updateUserTask(AppUserTask userTask, int newCoins) throws Exception;

    /**
     * This method updateUserTask
     * 
     * @paparam taskId
     *            : <code>AppUserTask</code> information to be getTask in
     *            database
     * @return AppUserTask
     * @throws Exception
     *             error while getTask information.
     */

    public AppUserTask getTask(String taskId) throws Exception;

    /**
     * This method UpdateinviteUser
     * 
     * @paparam appUserTaskInvite
     *            : <code>AppUserTaskInvite</code> information to be
     *            UpdateinviteUser in database
     * @return AppUserTaskInvite
     * @throws Exception
     *             error while UpdateinviteUser information.
     */

    public AppUserTaskInvite UpdateinviteUser(AppUserTaskInvite appUserTaskInvite) throws Exception;

    /**
     * This method sharingTask
     * 
     * @paparam userTask
     *            ,appUserTask : <code>AppUserTaskSharing</code> information to
     *            be AppUserTaskSharing in database
     * @return AppUserTaskSharing
     * @throws Exception
     *             error while AppUserTaskSharing information.
     */

    public AppUserTaskSharing sharingTask(AppUserTaskSharing userTask, AppUserTask appUserTask)
            throws Exception;

    /**
     * This method getTaskListAccordingUser
     * 
     * @paparam userId
     *            ,taskType : <code>AppUserTask</code> information to be
     *            getTaskListAccordingUser in database
     * @return AppUserTask
     * @throws Exception
     *             error while getTaskListAccordingUser information.
     */
    public ArrayList<AppUserTask> getTaskListAccordingUser(int userId, int taskType) throws Exception;

    /**
     * This method getInviteesData
     * 
     * @paparam userId
     *            ,taskId : <code>AppUserTaskInvite</code> information to be
     *            getInviteesData in database
     * @return AppUserTaskInvite
     * @throws Exception
     *             error while getInviteesData information.
     */

    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId) throws Exception;

    /**
     * This method getInviteUserInformation
     * 
     * @paparam inviteUserId
     *            : <code>AppUser</code> information to be
     *            getInviteUserInformation in database
     * @return AppUser
     * @throws Exception
     *             error while getInviteUserInformation information.
     */

    public AppUser getInviteUserInformation(int inviteUserId) throws Exception;

    /**
     * This method getInviteeList
     * 
     * @paparam userId
     *            : <code>AppUserTaskInvite</code> information to be
     *            getInviteeList in database
     * @return AppUserTaskInvite
     * @throws Exception
     *             error while getInviteeList information.
     */

    public ArrayList<AppUserTaskInvite> getInviteeList(int userId) throws Exception;

    /**
     * This method getSharingTaskData
     * 
     * @paparam inviteUserId
     *            ,taskType : <code>AppUserTaskSharing</code> information to be
     *            getSharingTaskData in database
     * @return AppUserTaskSharing
     * @throws Exception
     *             error while getSharingTaskData information.
     */

    public ArrayList<AppUserTaskSharing> getSharingTaskData(int inviteUserId, int taskType) throws Exception;

    /**
     * This method getSharingData
     * 
     * @paparam userId
     *            ,taskId : <code>AppUserTaskSharing</code> information to be
     *            getSharingData in database
     * @return AppUserTaskSharing
     * @throws Exception
     *             error while getSharingData information.
     */

    public ArrayList<AppUserTaskSharing> getSharingData(int userId, String taskId) throws Exception;

    /**
     * This method getTaskListAccordingUserForInvitee
     * 
     * @paparam userId
     *            ,taskId : <code>AppUserTask</code> information to be
     *            getTaskListAccordingUserForInvitee in database
     * @return AppUserTask
     * @throws Exception
     *             error while getTaskListAccordingUserForInvitee information.
     */

    public ArrayList<AppUserTask> getTaskListAccordingUserForInvitee(int userId, String taskId)
            throws Exception;

    /**
     * This method getInviteesData
     * 
     * @paparam userId
     *            ,taskId,reciverId : <code>AppUserTaskInvite</code> information
     *            to be getInviteesData in database
     * @return AppUserTaskInvite
     * @throws Exception
     *             error while getInviteesData information.
     */

    public ArrayList<AppUserTaskInvite> getInviteesData(int userId, String taskId, int reciverId)
            throws Exception;

    /**
     * This method getInviteesDataForSharing
     * 
     * @paparam senderId
     *            ,taskId : <code>AppUserTaskInvite</code> information to be
     *            getInviteesDataForSharing in database
     * @return AppUserTaskInvite
     * @throws Exception
     *             error while getInviteesDataForSharing information.
     */
    public ArrayList<AppUserTaskInvite> getInviteesDataForSharing(int senderId, String taskId)
            throws Exception;

    /**
     * This method getTaskListAccordingUserForSharing
     * 
     * @paparam inviteUserId
     *            ,taskId : <code>AppUserTask</code> information to be
     *            getTaskListAccordingUserForSharing in database
     * @return AppUserTask
     * @throws Exception
     *             error while getTaskListAccordingUserForSharing information.
     */
    public ArrayList<AppUserTask> getTaskListAccordingUserForSharing(int inviteUserId, String taskId)
            throws Exception;

    /**
     * This method getTaskInformation
     * 
     * @paparam taskType
     *            ,taskId : <code>AppUserTask</code> information to be
     *            getTaskInformation in database
     * @return AppUserTask
     * @throws Exception
     *             error while getTaskListAccordingUserForSharing information.
     */
    public AppUserTask getTaskInformation(String taskId, int taskType) throws Exception;

    /**
     * This method updateTaskStatus
     * 
     * @paparam updateTaskStatus
     *            : <code>AppUserTask</code> information to be updateTaskStatus
     *            in database
     * @return AppUserTask
     * @throws Exception
     *             error while updateTaskStatus information.
     */

    public AppUserTask updateTaskStatus(AppUserTask updateTaskStatus) throws Exception;

    /**
     * This method createAssignee
     * 
     * @paparam appUserTaskAssign
     *            ,appUserTaskInvite : <code>AppUserTaskAssign</code>
     *            information to be createAssignee in database
     * @return AppUserTaskAssign
     * @throws Exception
     *             error while createAssignee information.
     */

    public AppUserTaskAssign createAssignee(AppUserTaskAssign appUserTaskAssign,
            AppUserTaskInvite appUserTaskInvite) throws Exception;

    /**
     * This method updateAssignee
     * 
     * @paparam updateAssignee
     *            : <code>AppUserTaskAssign</code> information to be
     *            updateAssignee in database
     * @return AppUserTaskAssign
     * @throws Exception
     *             error while updateAssignee information.
     */
    public AppUserTaskAssign updateAssignee(AppUserTaskAssign updateAssignee) throws Exception;

    /**
     * This method getAssignListData
     * 
     * @paparam userId
     *            : <code>AppUserTaskAssign</code> information to be
     *            getAssignListData in database
     * @return AppUserTaskAssign
     * @throws Exception
     *             error while getAssignListData information.
     */
    public ArrayList<AppUserTaskAssign> getAssignListData(int userId) throws Exception;

    /**
     * This method getExistSharingData
     * 
     * @paparam userId
     *            ,taskId : <code>AppUserTaskAssign</code> information to be
     *            getExistSharingData in database
     * @return AppUserTaskSharing
     * @throws Exception
     *             error while getExistSharingData information.
     */
    public AppUserTaskSharing getExistSharingData(int userId, String taskId) throws Exception;
}
