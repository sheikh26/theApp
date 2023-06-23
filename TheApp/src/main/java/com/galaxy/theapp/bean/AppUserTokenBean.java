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
 * This <java>class</java> AppUserTokenBean having all the properties setters
 * and getters methods.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
public class AppUserTokenBean
{

    private Integer tokenId;
    private Integer userId;
    private String deviceToken;
    private String appToken;

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
