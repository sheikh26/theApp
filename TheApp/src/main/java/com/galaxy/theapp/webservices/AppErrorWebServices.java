/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.webservices;

import com.galaxy.theapp.bean.AppErrorBean;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.model.AppError;
import com.galaxy.theapp.service.AppErrorService;
import com.galaxy.theapp.utils.CommonAppErrorUtility;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;
import org.springfparamework.transaction.annotation.Transactional;

/**
 * This <java>class</java> AppErrorWebServices is use to save error from device.
 * 
 * @author param Sheikh
 * @GWL
 */

@Path("ta_user_error")
@Component
public class AppErrorWebServices
{
    public static final Logger LOG = Logger.getLogger(AppErrorWebServices.class);

    @Autowired
    public AppErrorService appErrorService;
    @Context
    private ServletContext context;

    AppErrorBean appErrorBean = new AppErrorBean();

    /**
     * This method error() use to save error on DB.
     * 
     * @throws Exception
     * 
     * 
     */

    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)  
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/error")
    @Transactional
    public Response error(JSONObject formpaparam) throws Exception
    {
        System.out.println("hi");
        CommonUtility result = new CommonUtility();

        String output = formpaparam.getString("paparam");

        JSONObject jObject = new JSONObject(output);

        JSONObject userData = jObject.getJSONObject("errorFromDevice");
        String userId = userData.getString("userId");
        String errorDescription = userData.getString("errorDescription");
        String event = userData.getString("event");

        this.appErrorBean.setErrorDescription(errorDescription);
        this.appErrorBean.setEvent(event);
        this.appErrorBean.setUserID(userId);

        boolean isValidRequest = CommonAppErrorUtility.validateErrorRequest(this.appErrorBean);
        if (!isValidRequest)
        {
            LOG.error("Invalid Error Request");
            return Response.status(200).entity("Invalid Request").build();
        }
        AppError ta_ERROR = CommonAppErrorUtility.prepareTaUserFromTaUserBean(this.appErrorBean);
        try
        {
            ta_ERROR = this.appErrorService.saveTaError(ta_ERROR);
            LOG.info("taUserModel.getErrorDescription" + ta_ERROR.getErrorDescription());

            if (ta_ERROR != null)
            {
                result.setStatus("success");
            } else
            {
                result.setErrorMessage("error");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(result).build();
    }
}
