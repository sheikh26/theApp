/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.service;

import com.galaxy.theapp.model.AppError;

/**
 * This <java>interface</java> TaErrorService defines all abstract methods of
 * error operations.
 * 
 * @author param Sheikh
 * @GWL
 */

public interface AppErrorService
{
    /**
     * This method saveTaError
     * 
     * @paparam ta_ERROR
     *            : <code>AppError</code> information to be saved in database
     * @return AppError
     * @throws Exception
     *             error while saving ta_ERROR information.
     */
    public AppError saveTaError(AppError ta_ERROR) throws Exception;
}
