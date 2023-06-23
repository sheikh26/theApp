/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.service.impl;

import com.galaxy.theapp.dao.AppErrorDao;
import com.galaxy.theapp.model.AppError;
import com.galaxy.theapp.service.AppErrorService;
import org.apache.log4j.Logger;
import org.springfparamework.beans.factory.annotation.Autowired;

/**
 * This <java>class</java> AppErrorServiceImpl use to perform all our service
 * related logics for the Error.
 * 
 * @author param Sheikh
 * @GWL
 */

public class AppErrorServiceImpl implements AppErrorService
{
    public static final Logger LOG = Logger.getLogger(AppErrorServiceImpl.class);

    @Autowired
    AppErrorDao appErrorDao;

    /**
     * This method saveTaError() use to save new error on DB for the user.
     * 
     * @throws Exception
     * 
     */

    public AppError saveTaError(AppError ta_ERROR) throws Exception
    {
        AppError error = new AppError();
        try
        {
            return this.appErrorDao.saveTaError(ta_ERROR);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return error;
    }
}
