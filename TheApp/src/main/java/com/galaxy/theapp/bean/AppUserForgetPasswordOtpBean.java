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
 * This <java>class</java> AppUserForgetPasswordOtpBean having all the
 * properties setters and getters methods.
 * 
 * @author Mayank Jain
 * @GWL
 */

@XmlRootElement
public class AppUserForgetPasswordOtpBean
{

    private Integer otpId;
    private Integer userId;
    private String deviceToken;
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
