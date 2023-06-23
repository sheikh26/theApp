/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao;

import com.galaxy.theapp.model.OpenFireUser;

/**
 * This <java>interface</java> OpenFireUserDao interface has all the abstract
 * methods related to OpenFireUser.
 * 
 * @author param Sheikh
 * @GWL
 */
public interface OpenFireUserDao
{
    /**
     * This method performed database operation related to createOpenFireUser.
     * 
     * @paparam openFireUser
     *            <code>OpenFireUser</code> to be create in database
     * @return OpenFireUser <code>OpenFireUser</code> saved in database
     * @throws Exception
     *             error while saving information in database
     */
    public OpenFireUser createOpenFireUser(OpenFireUser openFireUser);

    /**
     * This method performed database operation related to getOfUserName.
     * 
     * @paparam email
     *            <code>OpenFireUser</code> to be get from database
     * @return OpenFireUser <code>OpenFireUser</code> saved in database
     * @throws Exception
     *             error while getting information in database
     */

    public OpenFireUser getOfUserName(String email);
}
