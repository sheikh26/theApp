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
import com.galaxy.theapp.bean.AppUserTaskAssignBean;
import com.galaxy.theapp.bean.AppUserTaskInviteBean;
import com.galaxy.theapp.bean.AppUserTaskBean;
import com.galaxy.theapp.bean.AppUserTaskSharingBean;
import com.galaxy.theapp.common.utility.CommonConstants;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.model.AppUserTaskAssign;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskSharing;

/**
 * This <java>class</java> CommonErrorUtility set all the beans related to
 * Error.
 * 
 * @author param Sheikh
 * @GWL
 */

public class CommonAppUserTaskUtility
{
    public static final Logger LOG = Logger.getLogger(CommonAppUserTaskUtility.class);
    static Properties prop = new Properties();
    static CommonUtility cu = new CommonUtility();

    public static boolean validateAddUserTaskRequest(AppUserTaskBean userTaskBean)
    {
        if (userTaskBean.getCoinValue() != null && userTaskBean.getDate() != null
                && userTaskBean.getDescription() != null && userTaskBean.getDuration() != 0
                && userTaskBean.getEndDate() != null && userTaskBean.getStartDate() != null
                && userTaskBean.getTaskStatus() != 0 && userTaskBean.getTaskTitle() != null
                && userTaskBean.getToken() != null && userTaskBean.getUserId() != 0)
        {
            return true;
        }
        return false;
    }

    public static AppUserTask prepareUserTaskModelFromUserTaskCreationBean(AppUserTaskBean userTaskBean)
    {
        AppUserTask appUserTask = new AppUserTask();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

        appUserTask.setCraetedBy(userTaskBean.getUserId());
        appUserTask.setCreatedOn(formatter.format(date));

        appUserTask.setCoinValue(userTaskBean.getCoinValue());
        appUserTask.setDate(userTaskBean.getDate());
        appUserTask.setDescription(userTaskBean.getDescription());
        appUserTask.setDuration(userTaskBean.getDuration());
        appUserTask.setEndDate(userTaskBean.getEndDate());
        appUserTask.setStartDate(userTaskBean.getStartDate());
        appUserTask.setTaskStatus(userTaskBean.getTaskStatus());
        appUserTask.setTaskTitle(userTaskBean.getTaskTitle());
        appUserTask.setUserId(userTaskBean.getUserId());
        appUserTask.setStatus(CommonConstants.DeleteTask.ACTIVE_TASK);
        return appUserTask;
    }

    public static AppUserTaskInvite prepareInviteUserModelFromInviteUserBean(
            AppUserTaskInviteBean appUserTaskInviteBean)
    {
        AppUserTaskInvite appUserTaskInvite = new AppUserTaskInvite();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

        appUserTaskInvite.setCraetedBy(appUserTaskInviteBean.getUserId());
        appUserTaskInvite.setCreatedOn(formatter.format(date));
        appUserTaskInvite.setInviteUserId(appUserTaskInviteBean.getInviteUserId());
        appUserTaskInvite.setTaskId(appUserTaskInviteBean.getTaskId());
        appUserTaskInvite.setUserId(appUserTaskInviteBean.getUserId());
        appUserTaskInvite.setTaskStatus(appUserTaskInviteBean.getTaskStatus());
        return appUserTaskInvite;
    }

    public static AppUserTask prepareUserTaskModelFromUserTaskCreationBeanForUpdate(
            AppUserTaskBean userTaskBean)
    {
        AppUserTask appUserTask = new AppUserTask();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

        appUserTask.setModifiedBy(userTaskBean.getUserId());
        appUserTask.setModifiedOn(formatter.format(date));
        appUserTask.setCoinValue(userTaskBean.getCoinValue());
        appUserTask.setDate(userTaskBean.getDate());
        appUserTask.setDescription(userTaskBean.getDescription());
        appUserTask.setDuration(userTaskBean.getDuration());
        appUserTask.setEndDate(userTaskBean.getEndDate());
        appUserTask.setStartDate(userTaskBean.getStartDate());
        appUserTask.setTaskStatus(userTaskBean.getTaskStatus());
        appUserTask.setTaskTitle(userTaskBean.getTaskTitle());
        appUserTask.setUserId(userTaskBean.getUserId());
        appUserTask.setUserTaskId(userTaskBean.getUserTaskId());
        return appUserTask;
    }

    public static boolean validateUpdateUserTaskRequest(AppUserTaskBean userTaskBean)
    {

        if (userTaskBean.getCoinValue() != null && userTaskBean.getDate() != null
                && userTaskBean.getDescription() != null && userTaskBean.getDuration() != 0
                && userTaskBean.getEndDate() != null && userTaskBean.getStartDate() != null
                //&& userTaskBean.getTaskStatus() != 0 && userTaskBean.getTaskTitle() != null
                && userTaskBean.getToken() != null && userTaskBean.getUserId() != 0
                && userTaskBean.getUserTaskId() != null)
        {
            return true;
        }
        return false;
    }

    public static AppUserTaskInvite prepareInviteUserModelFromInviteUserBeanForUpdate(
            AppUserTaskInviteBean appUserTaskInviteBean)
    {
        AppUserTaskInvite appUserTaskInvite = new AppUserTaskInvite();
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
        appUserTaskInvite.setUserId(appUserTaskInviteBean.getUserId());
        appUserTaskInvite.setModifiedBy(appUserTaskInviteBean.getUserId());
        appUserTaskInvite.setModifiedOn(formatter.format(date));
        appUserTaskInvite.setInviteUserId(appUserTaskInviteBean.getInviteUserId());
        appUserTaskInvite.setTaskId(appUserTaskInviteBean.getTaskId());
        return appUserTaskInvite;
    }

    public static AppUserTaskSharing prepareTaskSharingModelFromTaskSharingUserBean(
            AppUserTaskSharingBean appUserTaskSharingBean)
    {
        AppUserTaskSharing usertask = new AppUserTaskSharing();
        prop = cu.getFromProperty();
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

        usertask.setCraetedBy(appUserTaskSharingBean.getSenderId());
        usertask.setCreatedOn(formatter.format(date));
        usertask.setSenderId(appUserTaskSharingBean.getSenderId());
        usertask.setRecieverId(appUserTaskSharingBean.getRecieverId());
        usertask.setTaskId(appUserTaskSharingBean.getTaskId());
        return usertask;
    }

    public static boolean validateUserTaskStatusRequest(AppUserTaskBean userTaskBean, String token)
    {
        if (userTaskBean.getUserId() != 0 && userTaskBean.getUserTaskId() != null
                && userTaskBean.getTaskStatus() != 0 && token != null)
        {
            return true;
        }
        return false;
    }

    public static AppUserTask prepareUpdateTaskStatusModelFromUserTaskBean(AppUserTaskBean userTaskBean)
    {
        AppUserTask appUserTask = new AppUserTask();
        appUserTask.setUserId(userTaskBean.getUserId());
        appUserTask.setUserTaskId(userTaskBean.getUserTaskId());
        appUserTask.setTaskStatus(userTaskBean.getTaskStatus());
        return appUserTask;
    }

    public static AppUserTaskAssign prepareAppUserTaskAssignModelFromAppUserTaskAssignBean(
            AppUserTaskAssignBean appUserTaskAssignBean)
    {
        Date date = new Date();
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));
        AppUserTaskAssign appUserTaskAssign = new AppUserTaskAssign();
        appUserTaskAssign.setCraetedBy(appUserTaskAssignBean.getRecieverId());
        appUserTaskAssign.setRecieverId(appUserTaskAssignBean.getRecieverId());
        appUserTaskAssign.setSenderId(appUserTaskAssignBean.getSenderId());
        appUserTaskAssign.setTaskId(appUserTaskAssignBean.getTaskId());
        appUserTaskAssign.setCreatedOn(formatter.format(date));

        return appUserTaskAssign;
    }
}
