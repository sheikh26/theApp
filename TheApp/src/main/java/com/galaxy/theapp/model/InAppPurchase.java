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
 * This <java>class</java> InAppPurchase having DB table and column names.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_in_app_purchase")
public class InAppPurchase implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IN_APP_ID")
    private Integer inAppId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "IN_APP_PRODUCT_ID")
    private String inAppProductId;

    @Column(name = "POINT_PURCHASED")
    private Integer pointPurchased;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "TRANSACTION_DATE")
    private String transactionDate;

    @Column(name = "CREATED_ON")
    private String createdOn;

    @Column(name = "CREATED_BY")
    private Integer craetedBy;

    @Column(name = "MODIFIED_ON")
    private String modifiedOn;

    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;

    @XmlElement
    public Integer getInAppId()
    {
        return inAppId;
    }

    public void setInAppId(Integer inAppId)
    {
        this.inAppId = inAppId;
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
    public Integer getPointPurchased()
    {
        return pointPurchased;
    }

    public void setPointPurchased(Integer pointPurchased)
    {
        this.pointPurchased = pointPurchased;
    }

    @XmlElement
    public String getTransactionId()
    {
        return transactionId;
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
