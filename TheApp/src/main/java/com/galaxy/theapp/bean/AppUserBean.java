/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.bean;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.galaxy.theapp.model.AppUser;

/**
 * This <java>class</java> AppUserBean having DB table and column names.
 * 
 * @author Mayank Jain
 * @GWL
 */

@XmlRootElement
public class AppUserBean
{

    private Integer userId;
    private String email;
    private String password;
    private String profilePic;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String mobileNo;
    private ArrayList<AppUser> userList;
    private Long totalRecords;
    private Integer pageNo;
    private Integer isOtp;

    @XmlElement
    public Integer getIsOtp()
    {
        return isOtp;
    }

    public void setIsOtp(Integer isOtp)
    {
        this.isOtp = isOtp;
    }

    @XmlElement
    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    @XmlElement
    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    @XmlElement
    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    @XmlElement
    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    @XmlElement
    public Long getTotalRecords()
    {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords)
    {
        this.totalRecords = totalRecords;
    }

    @XmlElement
    public ArrayList<AppUser> getUserList()
    {
        return userList;
    }

    public void setUserList(ArrayList<AppUser> userList)
    {
        this.userList = userList;
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
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @XmlElement
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @XmlElement
    public String getProfilePic()
    {
        return profilePic;
    }

    public void setProfilePic(String profilePic)
    {
        this.profilePic = profilePic;
    }

    @XmlElement
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @XmlElement
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

}
