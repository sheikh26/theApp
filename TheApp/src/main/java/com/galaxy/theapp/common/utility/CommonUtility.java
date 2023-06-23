/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.common.utility;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import com.galaxy.theapp.model.AppUser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

/**
 * This <java>class</java> responds all the requests related to CommonUtility.
 * 
 * @author param Sheikh and Mayank Jain
 * @GWL
 */
@XmlAccessorType
@XmlRootElement
public class CommonUtility implements Serializable
{
    public static final Logger LOG = Logger.getLogger(CommonUtility.class);

    private static final long serialVersionUID = 1L;
    private String SUCCESS;
    private String FAILURE;
    private String status;
    private String token;
    private String errorMessage;
    private AppUser userData;
    private AppUser appUser;
    static Properties prop = new Properties();
    static CommonUtility cu = new CommonUtility();

    public AppUser getUserData()
    {
        return userData;
    }

    public void setUserData(AppUser userData)
    {
        this.userData = userData;
    }

    public AppUser getAppUser()
    {
        return appUser;
    }

    public void setAppUser(AppUser appUser)
    {
        this.appUser = appUser;
    }

    @XmlElement
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @XmlElement
    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @XmlElement
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @XmlElement
    public String getSUCCESS()
    {
        return SUCCESS;
    }

    public void setSUCCESS(String sUCCESS)
    {
        SUCCESS = sUCCESS;
    }

    @XmlElement
    public String getFAILURE()
    {
        return FAILURE;
    }

    public void setFAILURE(String fAILURE)
    {
        FAILURE = fAILURE;
    }

    /**
     * This method saveImageInDirectory save the image on local directory.
     * 
     */
    public static void saveImageInDirectory(String profilePic, String imagePath)
    {
        try
        {
            byte[] imageByteArray = decodeImage(profilePic);

            // Write a image byte array into file system
            FileOutputStream imageOutFile = new FileOutputStream(imagePath);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();

            LOG.info("Image Successfully Manipulated!");
        } catch (FileNotFoundException e)
        {
            LOG.error("Image not found" + e);
        } catch (IOException ioe)
        {
            LOG.error("Exception while reading the Image " + ioe);
        }
    }

    /**
     * This method decodeImage decode the image string into Base64.
     * 
     */
    public static byte[] decodeImage(String imageDataString)
    {
        return Base64.decodeBase64(imageDataString);
    }

    /**
     * This method uploaded File on local drive Location writeToFile().
     * 
     */

    public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation)
    {

        try
        {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e)
        {

            e.printStackTrace();
        }

    }

    /**
     * This method generatetToken randomly.
     * 
     */
    public static String generatetToken()
    {
        String token = null;
        try
        {
            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            char[] num = "1234567890".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 6; i++)
            {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }

            for (int i = 0; i < 9; i++)
            {
                char n = num[random.nextInt(num.length)];
                sb.append(n);
            }
            token = sb.toString();
            LOG.info("Generated Token"+token);
        } catch (Exception e)
        {

            e.printStackTrace();

        }
        return token;

    }

    /**
     * This method convertFromOneTimeZoneToOhter convert one time zone to UTC
     * timezone.
     * 
     */

    public static Date convertFromOneTimeZoneToOhter(String dateString1, String from, String to)
            throws ParseException
    {
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

        TimeZone fromTimezone = TimeZone.getTimeZone(from);// get Timezone
                                                           // object
        TimeZone toTimezone = TimeZone.getTimeZone(to);

        long fromOffset = fromTimezone.getOffset(formatter.parse(dateString1).getTime());// get
                                                                                         // offset
        long toOffset = toTimezone.getOffset(formatter.parse(dateString1).getTime());

        // calculate offset difference and calculate the actual time
        long convertedTime = formatter.parse(dateString1).getTime() - (fromOffset - toOffset);

        Date d2 = new Date(convertedTime);

        return d2;
    }

    /**
     * This method getDateAndTime get current date and time.
     * 
     */
    public static String getDateAndTime() throws ParseException
    {
        prop = cu.getFromProperty();
        DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate.image"));
        Date date = new Date();
        String dateTime = formatter.format(date);
        return dateTime;
    }

    /**
     * This method getFromProperty get Property from theApp.properties.
     * 
     */
    public Properties getFromProperty()
    {
        String resourceName = "theApp.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try
        {
            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            props.load(resourceStream);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return props;

    }

    /**
     * This method generatetOtp() generateOTP for the User.
     * 
     */
    public static String generatetOtp()
    {
        String otp = null;
        try
        {
            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            char[] num = "1234567890".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 1; i < 5; i++)
            {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }

            for (int i = 0; i < 4; i++)
            {
                char n = num[random.nextInt(num.length)];
                sb.append(n);
            }
            otp = sb.toString();
            LOG.info("Generated OTP"+otp);
        } catch (Exception e)
        {

            e.printStackTrace();

        }
        return otp;

    }
}