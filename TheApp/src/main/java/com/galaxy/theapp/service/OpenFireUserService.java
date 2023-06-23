/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.service;

import com.galaxy.theapp.model.OpenFireUser;

/**
 * This <java>interface</java> OpenFireService defines all abstract methods of
 * OpenFire.
 * 
 * @author param Sheikh
 * @GWL
 */

public interface OpenFireUserService
{
    /**
     * This method createOpenFireUser
     * 
     * @paparam openFireUser
     *            : <code>OpenFireUser</code> information to be saved in
     *            database
     * @return OpenFireUser
     * @throws Exception
     *             error while saving openFireUser information.
     */
    public OpenFireUser createOpenFireUser(OpenFireUser openFireUser) throws Exception;

    /**
     * This method getOfUserName
     * 
     * @paparam email
     *            : <code>OpenFireUser</code> information to be get from
     *            database
     * @return OpenFireUser
     * @throws Exception
     *             error while fetching getOfUserName information.
     */
    public OpenFireUser getOfUserName(String email) throws Exception;

}
