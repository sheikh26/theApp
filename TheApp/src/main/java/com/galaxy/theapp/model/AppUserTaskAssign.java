/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> AppUserTaskAssign having DB table and columns names.
 * 
 * @author Mayank Jain
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_taskassign")
public class AppUserTaskAssign implements Serializable

{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ASSIGN_ID")
    private int assignId;

    @Column(name = "SENDER_ID")
    private int senderId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "RECEIVER_ID")
    private int recieverId;

    @Column(name = "CREATED_ON")
    private String createdOn;

    @Column(name = "CREATED_BY")
    private Integer craetedBy;

    @Column(name = "MODIFIED_ON")
    private String modifiedOn;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

    @XmlElement
    public int getAssignId()
    {
        return assignId;
    }

    public void setAssignId(int assignId)
    {
        this.assignId = assignId;
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
    public int getSenderId()
    {
        return senderId;
    }

    public void setSenderId(int senderId)
    {
        this.senderId = senderId;
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
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

}
