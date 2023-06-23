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
 * This <java>class</java> AppUserCoinTransaction having DB table and column
 * names.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_user_coin_transaction")
public class AppUserCoinTransaction implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRANSACTION_ID")
    private Integer transactionId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Column(name = "FOR_AMOUNT")
    private Integer foparamount;

    @Column(name = "AMOUNT_TYPE")
    private Integer amountType;

    @Column(name = "CREATED_ON")
    private String createdOn;

    @Column(name = "CREATED_BY")
    private Integer craetedBy;

    @Column(name = "MODIFIED_ON")
    private String modifiedOn;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

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
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

}
