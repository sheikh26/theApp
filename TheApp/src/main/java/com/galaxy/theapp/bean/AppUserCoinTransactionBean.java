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
 * This <java>class</java> AppUserCoinTransactionBean having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
public class AppUserCoinTransactionBean
{

    private Integer transactionId;
    private Integer userId;
    private String transactionType;
    private Integer foparamount;
    private Integer amountType;

    @XmlElement
    public Integer getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId)
    {
        this.transactionId = transactionId;
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
    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    @XmlElement
    public Integer getFoparamount()
    {
        return foparamount;
    }

    public void setFoparamount(Integer foparamount)
    {
        this.foparamount = foparamount;
    }

    @XmlElement
    public Integer getAmountType()
    {
        return amountType;
    }

    public void setAmountType(Integer amountType)
    {
        this.amountType = amountType;
    }

}
