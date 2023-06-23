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
 * This <java>class</java> AppUserCoinBalanceBean having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh
 * @GWL
 */

@XmlRootElement
public class AppUserCoinBalanceBean
{

    private Integer coinBalanceId;
    private Integer userId;
    private Integer coin_balance;

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
    public Integer getCoinBalanceId()
    {
        return coinBalanceId;
    }

    public void setCoinBalanceId(Integer coinBalanceId)
    {
        this.coinBalanceId = coinBalanceId;
    }

    @XmlElement
    public Integer getCoin_balance()
    {
        return coin_balance;
    }

    public void setCoin_balance(Integer coin_balance)
    {
        this.coin_balance = coin_balance;
    }

}
