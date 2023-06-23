/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> AppUserTaskBean having all the properties setters and
 * getters methods.
 * 
 * @author Mayank Jain
 * @GWL
 */
@XmlRootElement
public class AppUserTaskBean
{
    private String userTaskId;
    private String taskTitle;
    private String description;
    private Integer coinValue;
    private float duration;
    private Integer taskStatus;
    private Integer userId;
    private String startDate;
    private String endDate;
    private String date;
    private String token;
    private String status;
    private Integer taskType;
    private Integer reSaleCoinValue;

    @XmlElement
    public Integer getReSaleCoinValue()
    {
        return reSaleCoinValue;
    }

    public void setReSaleCoinValue(Integer reSaleCoinValue)
    {
        this.reSaleCoinValue = reSaleCoinValue;
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
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

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
    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    @XmlElement
    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    @XmlElement
    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
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
    public String getUserTaskId()
    {
        return userTaskId;
    }

    @XmlElement
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    @XmlElement
    public float getDuration()
    {
        return duration;
    }

    public void setDuration(float duration)
    {
        this.duration = duration;
    }

    public void setUserTaskId(String userTaskId)
    {
        this.userTaskId = userTaskId;
    }

    @XmlElement
    public String getTaskTitle()
    {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle)
    {
        this.taskTitle = taskTitle;
    }

    @XmlElement
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @XmlElement
    public Integer getCoinValue()
    {
        return coinValue;
    }

    public void setCoinValue(Integer value)
    {
        this.coinValue = value;
    }

}
