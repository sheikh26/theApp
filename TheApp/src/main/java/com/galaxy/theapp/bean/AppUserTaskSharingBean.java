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
 * This <java>class</java> AppUserTaskSharingBean having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh
 * @GWL
 */

public class AppUserTaskSharingBean
{

    private int shareId;
    private int recieverId;
    private int senderId;
    private String taskId;
    private String createdOn;
    private Integer craetedBy;
    private String modifiedOn;
    private Integer modifiedBy;
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
    public int getRecieverId()
    {
        return recieverId;
    }

    public void setRecieverId(int recieverId)
    {
        this.recieverId = recieverId;
    }

    @XmlElement
    public int getSenderId()
    {
        return senderId;
    }

    public void setSenderId(int senderId)
    {
        this.senderId = senderId;
    }

    @XmlElement
    public int getShareId()
    {
        return shareId;
    }

    public void setShareId(int shareId)
    {
        this.shareId = shareId;
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
    public String getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(String createdOn)
    {
        this.createdOn = createdOn;
    }

    @XmlElement
    public Integer getCraetedBy()
    {
        return craetedBy;
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

}
