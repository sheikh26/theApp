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
 * This <java>class</java> AppUserForgetPasswordOtp having DB table and column
 * names.
 * 
 * @author Mayank Jain
 * @GWL
 */

@XmlRootElement
@Entity
@Table(name = "ta_otp")
public class AppUserForgetPasswordOtp
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OTP_ID")
    private Integer otpId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;

    @Column(name = "OTP")
    private String otp;

    @XmlElement
    public Integer getOtpId()
    {
        return otpId;
    }

    public void setOtpId(Integer otpId)
    {
        this.otpId = otpId;
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
    public String getOtp()
    {
        return otp;
    }

    public void setOtp(String otp)
    {
        this.otp = otp;
    }

}
