/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> AppUserToken having DB table and columns names.
 * 
 * @author Mayank Jain
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_token")
public class AppUserToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TOKEN_ID")
    private Integer tokenId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;

    @Column(name = "APP_TOKEN")
    private String appToken;

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
    public Integer getTokenId()
    {
        return tokenId;
    }

    public void setTokenId(Integer tokenId)
    {
        this.tokenId = tokenId;
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
    public String getDeviceToken()
    {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    @XmlElement
    public String getAppToken()
    {
        return appToken;
    }

    public void setAppToken(String appToken)
    {
        this.appToken = appToken;
    }

}
