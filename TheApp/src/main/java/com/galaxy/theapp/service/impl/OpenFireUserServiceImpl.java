/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.service.impl;

import org.apache.log4j.Logger;
import org.springfparamework.beans.factory.annotation.Autowired;
import com.galaxy.theapp.dao.OpenFireUserDao;
import com.galaxy.theapp.model.OpenFireUser;
import com.galaxy.theapp.service.OpenFireUserService;

/**
 * This <java>class</java> OpenFireUserServiceImpl use to perform all our
 * service related logics for the OpenFireUser.
 * 
 * @author param Sheikh
 * @GWL
 */
public class OpenFireUserServiceImpl implements OpenFireUserService
{
    public static final Logger LOG = Logger.getLogger(OpenFireUserServiceImpl.class);

    @Autowired
    OpenFireUserDao openFireUserDao;

    /**
     * This method createOpenFireUser() use to save new openFireUser on DB.
     * 
     * @throws Exception
     * 
     */
    public OpenFireUser createOpenFireUser(OpenFireUser openFireUser) throws Exception
    {

        OpenFireUser opUser = new OpenFireUser();
        try
        {
            opUser = openFireUserDao.createOpenFireUser(openFireUser);
            return opUser;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return opUser;
    }

    /**
     * This method getOfUserName() get the OfUserName.
     * 
     * @throws Exception
     * 
     */
    public OpenFireUser getOfUserName(String email) throws Exception
    {
        OpenFireUser opUser = new OpenFireUser();
        try
        {
            opUser = openFireUserDao.getOfUserName(email);
            return opUser;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return opUser;
    }
}
