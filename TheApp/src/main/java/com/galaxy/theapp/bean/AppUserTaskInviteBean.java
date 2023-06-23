/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.bean;

import javax.xml.bind.annotation.XmlElement;

/**
 * This <java>class</java> AppUserTaskInviteBean having all the properties
 * setters and getters methods.
 * 
 * @author Mayank Jain
 * @GWL
 */

public class AppUserTaskInviteBean
{
    private String inviteID;
    private int userId;
    private String token;
    private String taskId;
    private int inviteUserId;
    private Integer taskType;
    private Integer taskStatus;

    @XmlElement
    public Integer getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    @XmlElement
    public Integer getTaskType()
    {
        return taskType;
    }

    public void setTaskType(Integer taskType)
    {
        this.taskType = taskType;
    }

    @XmlElement
    public String getInviteID()
    {
        return inviteID;
    }

    public void setInviteID(String inviteID)
    {
        this.inviteID = inviteID;
    }

    @XmlElement
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    @XmlElement
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @XmlElement
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    @XmlElement
    public int getInviteUserId()
    {
        return inviteUserId;
    }

    public void setInviteUserId(int invitees)
    {
        this.inviteUserId = invitees;
    }

}
