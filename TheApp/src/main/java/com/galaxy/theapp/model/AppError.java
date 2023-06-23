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
 * This <java>class</java> TA_ERROR having DB table and columns names.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_error")
public class AppError implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ERROR_ID")
    private Integer errorId;

    @Column(name = "ERROR_DESCRIPTION")
    private String errorDescription;

    @Column(name = "EVENT")
    private String Event;

    @Column(name = "USER_ID")
    private String userID;

    @Column(name = "CREATED_ON")
    private String createdOn;

    @Column(name = "CREATED_BY")
    private Integer craetedBy;

    @Column(name = "MODIFIED_ON")
    private String modifiedOn;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

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

    @XmlElement
    public Integer getErrorId()
    {
        return this.errorId;
    }

    public void setErrorId(Integer errorId)
    {
        this.errorId = errorId;
    }

    @XmlElement
    public String getErrorDescription()
    {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription)
    {
        this.errorDescription = errorDescription;
    }

    @XmlElement
    public String getEvent()
    {
        return Event;
    }

    public void setEvent(String event)
    {
        Event = event;
    }

    @XmlElement
    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
