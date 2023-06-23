/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao.impl;

import com.galaxy.theapp.dao.AppErrorDao;
import com.galaxy.theapp.model.AppError;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springfparamework.beans.factory.annotation.Autowired;

/**
 * This <java>class</java> AppErrorDaoImpl use to perform all our DB related
 * logics for the errors.
 * 
 * @author param Sheikh
 * @GWL
 */

public class AppErrorDaoImpl implements AppErrorDao
{
    public static final Logger LOG = Logger.getLogger(AppErrorDaoImpl.class);
    @Autowired
    SessionFactory sessionFactory;

    /**
     * This method saveTaError save error on DB.
     * 
     */

    public AppError saveTaError(AppError ta_ERROR)
    {
        try
        {
            this.sessionFactory.getCurrentSession().save(ta_ERROR);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ta_ERROR;
    }
}
