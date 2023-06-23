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
 * This <java>class</java> InAppPurchaseBean having DB table and column names.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
public class InAppPurchaseBean
{

    private Integer inAppId;
    private Integer userId;
    private String inAppProductId;
    private Integer pointPurchased;
    private String transactionId;
    private String transactionDate;

    public Integer getInAppId()
    {
        return inAppId;
    }

    public void setInAppId(Integer inAppId)
    {
        this.inAppId = inAppId;
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
    public String getInAppProductId()
    {
        return inAppProductId;
    }

    public void setInAppProductId(String inAppProductId)
    {
        this.inAppProductId = inAppProductId;
    }

    @XmlElement
    public String getTransactionId()
    {
        return transactionId;
    }

    @XmlElement
    public Integer getPointPurchased()
    {
        return pointPurchased;
    }

    public void setPointPurchased(Integer pointPurchased)
    {
        this.pointPurchased = pointPurchased;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    @XmlElement
    public String getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate)
    {
        this.transactionDate = transactionDate;
    }

}
