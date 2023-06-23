/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This <java>class</java> OpenFireUser having DB table and columns names.
 * 
 * @author Mayank Jain
 * @GWL
 */
@XmlRootElement
@Entity
@Table(name = "ofuser")
public class OpenFireUser implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "plainPassword")
    private String plainPassword;

    @Column(name = "encryptedPassword")
    private String encryptedPassword;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "creationDate")
    private long creationDate;

    @Column(name = "modificationDate")
    private long modificationDate;

    @XmlElement
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @XmlElement
    public String getPlainPassword()
    {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword)
    {
        this.plainPassword = plainPassword;
    }

    @XmlElement
    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }

    @XmlElement
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
    public long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(long creationDate)
    {
        this.creationDate = creationDate;
    }

    @XmlElement
    public long getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate(long modificationDate)
    {
        this.modificationDate = modificationDate;
    }

}
