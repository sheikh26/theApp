/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.bean;

/**
 * This <java>class</java> OpenFireUserBean having all the properties setters
 * and getters methods.
 * 
 * @author param Sheikh
 * @GWL
 */

public class OpenFireUserBean
{

    private String username;
    private String plainPassword;
    private String encryptedPassword;
    private String name;
    private String email;
    private long creationDate;
    private long modificationDate;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPlainPassword()
    {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword)
    {
        this.plainPassword = plainPassword;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(long creationDate)
    {
        this.creationDate = creationDate;
    }

    public long getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate(long modificationDate)
    {
        this.modificationDate = modificationDate;
    }

}
