/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao;

import com.galaxy.theapp.model.AppError;

/**
 * This <java>interface</java> AppErrorDao interface has all the abstract
 * methods related to errors.
 * 
 * @author param Sheikh
 * @GWL
 */

public abstract interface AppErrorDao
{
    /**
     * This method performed database operation related to errors.
     * 
     * @paparam paparamTA_ERROR
     *            <code>AppError</code> to save paparamTA_ERROR
     * 
     * @return AppError <code>AppError</code> save in DB
     * @throws Exception
     *             error while checking the error
     */
    public abstract AppError saveTaError(AppError paparamTA_ERROR);
}
