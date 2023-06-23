/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import com.galaxy.theapp.dao.OpenFireUserDao;
import com.galaxy.theapp.model.OpenFireUser;

/**
 * This <java>class</java> OpenFireUserDaoImpl use to perform all our DB related
 * logics for the OpenFire. users.
 * 
 * @author param Sheikh
 * @GWL
 */

public class OpenFireUserDaoImpl implements OpenFireUserDao
{

    public static final Logger LOG = Logger.getLogger(OpenFireUserDaoImpl.class);
    @Autowired
    SessionFactory sessionFactory;

    /**
     * This method createOpenFireUser save user on DB.
     * 
     */

    public OpenFireUser createOpenFireUser(OpenFireUser openFireUser)
    {
        try
        {
            sessionFactory.getCurrentSession().save(openFireUser);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return openFireUser;
    }

    /**
     * This method getOfUserName get the get Of User Name.
     * 
     */
    public OpenFireUser getOfUserName(String email)
    {
        OpenFireUser openFireUser = new OpenFireUser();
        try
        {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpenFireUser.class);
            criteria.add(Restrictions.eq("email", email));
            openFireUser = (OpenFireUser) criteria.uniqueResult();
            if (openFireUser == null)
            {
                return null;
            } else
            {
                return openFireUser;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return openFireUser;
    }

}
