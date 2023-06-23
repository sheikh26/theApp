/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This <java>class</java> AppUserTask having DB table and columns names.
 * 
 * @author Mayank Jain
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_usertask")
public class AppUserTask implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USERTASK_ID")
    private String userTaskId;

    @Column(name = "TASK_TITLE")
    private String taskTitle;

    @Column(name = "TASK_DESCRIPTION")
    private String description;

    @Column(name = "TASK_COINVALUE")
    private Integer coinValue;

    @Column(name = "TASK_DURATION")
    private float duration;

    @Column(name = "TASK_STATUS")
    private Integer taskStatus;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TASK_START_DATE")
    private String startDate;

    @Column(name = "TASK_END_DATE")
    private String endDate;

    @Column(name = "TASK_CREATED_DATE")
    private String date;

    @Column(name = "TASK_RESALE_COINVALUE")
    private Integer reSaleCoinValue;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATED_BY")
    private Integer craetedBy;

    @Column(name = "MODIFIED_ON")
    private String modifiedOn;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    @Column(name = "CREATED_ON")
    private String createdOn;

    public Integer getReSaleCoinValue()
    {
        return reSaleCoinValue;
    }

    public void setReSaleCoinValue(Integer reSaleCoinValue)
    {
        this.reSaleCoinValue = reSaleCoinValue;
    }

    @XmlElement
    public Integer getCraetedBy()
    {
        return craetedBy;
    }

    @XmlElement
    public String getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(String createdOn)
    {
        this.createdOn = createdOn;
    }

    public void setCraetedBy(Integer craetedBy)
    {
        this.craetedBy = craetedBy;
    }

    @XmlElement
    public String getModifiedOn()
    {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn)
    {
        this.modifiedOn = modifiedOn;
    }

    @XmlElement
    public Integer getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy)
    {
        this.modifiedBy = modifiedBy;
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

    public void setCoinValue(Integer coinValue)
    {
        this.coinValue = coinValue;
    }

}
