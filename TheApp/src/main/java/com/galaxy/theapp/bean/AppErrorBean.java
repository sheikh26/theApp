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
 * This <java>class</java> AppErrorBean having all the properties setters and
 * getters methods.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
public class AppErrorBean
{
    private Integer errorId;
    private String errorDescription;
    private String event;
    private String userID;

    @XmlElement
    public Integer getErrorId()
    {
        return errorId;
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
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
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
