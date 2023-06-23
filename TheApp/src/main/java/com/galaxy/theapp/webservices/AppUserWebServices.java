/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.webservices;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;
import org.springfparamework.transaction.annotation.Transactional;
import com.galaxy.theapp.bean.AppUserForgetPasswordOtpBean;
import com.galaxy.theapp.bean.AppUserTokenBean;
import com.galaxy.theapp.bean.InAppPurchaseBean;
import com.galaxy.theapp.bean.OpenFireUserBean;
import com.galaxy.theapp.bean.AppUserBean;
import com.galaxy.theapp.common.utility.CommonConstants;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.common.utility.MailApi;
import com.galaxy.theapp.model.AppUserForgetPasswordOtp;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.model.InAppPurchase;
import com.galaxy.theapp.model.OpenFireUser;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.service.OpenFireUserService;
import com.galaxy.theapp.service.AppUserService;
import com.galaxy.theapp.utils.CommonAppUserUtility;
import com.google.gson.Gson;

/**
 * This <java>class</java> AppUserWebServices is use to provide
 * login/registration/updateProfile and getProfile and getUserList operations.
 * 
 * @author param Sheikh
 * @GWL
 */

@Path("ta_user")
@Component
public class AppUserWebServices
{
    public static final Logger LOG = Logger.getLogger(AppUserWebServices.class);

    @Autowired
    public AppUserService appUserService;

    @Autowired
    public OpenFireUserService fireUserService;

    @Autowired
    OpenFireUserService openFireService;

    AppUserBean appUserBean = new AppUserBean();
    AppUserTokenBean tokenBean = new AppUserTokenBean();
    OpenFireUserBean openFireUserBean = new OpenFireUserBean();
    Properties prop = new Properties();
    CommonUtility cu = new CommonUtility();

    /**
     * This method registration() use to create new user registration.
     * 
     * @throws Exception
     * 
     * 
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/registration")
    @Transactional
    public Response registration(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        try
        {
            String output = formpaparam.getString("paparam");

            JSONObject jObject = new JSONObject(output);

            JSONObject userData = jObject.getJSONObject("userRegistrationData");
            
            String email = userData.getString("email");

            String firstName = userData.getString("firstName");

            String lastName = userData.getString("lastName");
            String password = userData.getString("password");

            String deviceToken = userData.getString("deviceToken");

            boolean existEmailId = appUserService.checkEmailId(email);
            LOG.info("existEmailId :" + existEmailId);
            if (existEmailId)
            {
                json.putOpt("status", CommonConstants.UserRegistration.ERROR);
                json.putOpt("errorMessage", CommonConstants.UserRegistration.EXIST_EMAIL_ID);
            } else
            {
                LOG.info("Registration Request call");
                String token = CommonUtility.generatetToken();
                LOG.info("Token from generateToken()" + token);
                AppUserToken userToken = appUserService.checkNewGenaratedToken(token);
                if (userToken != null)
                {
                    token = CommonUtility.generatetToken();
                    tokenBean.setAppToken(token);
                } else
                {
                    tokenBean.setAppToken(token);
                }
                tokenBean.setDeviceToken(deviceToken);
                appUserBean.setIsOtp(CommonConstants.ForgetPassword.IS_OTP_ZERO);
                appUserBean.setEmail(email);
                appUserBean.setPassword(password);
                appUserBean.setFirstName(firstName);
                appUserBean.setLastName(lastName);

                boolean isValidRequest = CommonAppUserUtility.validateUserRegistrationRequest(appUserBean);
                if (!isValidRequest)
                {
                    LOG.error("Invalid Registration Request");
                    return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
                }

                AppUser taUserModel = CommonAppUserUtility.prepareTaUserFromTaUserBean(appUserBean);

                taUserModel = appUserService.createNewTaUser(taUserModel);
                LOG.info("taUserModel.userId" + taUserModel.getUserId());
                tokenBean.setUserId(taUserModel.getUserId());
                AppUserToken taUserToken = CommonAppUserUtility
                        .prepareTaUserTokenFromTaUserTokenBean(tokenBean);
                taUserToken = appUserService.createNewTaUserToken(taUserToken);

                long dateTimeInMilliSeconds = System.currentTimeMillis();
                String name = taUserModel.getFirstName() + "_" + taUserModel.getLastName();
                openFireUserBean.setUsername(email.replace('@', '_'));
                openFireUserBean.setCreationDate(dateTimeInMilliSeconds);
                openFireUserBean.setModificationDate(dateTimeInMilliSeconds);
                openFireUserBean.setPlainPassword(password);
                openFireUserBean.setEmail(email);
                openFireUserBean.setName(name);
                OpenFireUser openFireUser = CommonAppUserUtility
                        .prepareOpenFireUserFromOpenFireUserBean(openFireUserBean);
                openFireUser = openFireService.createOpenFireUser(openFireUser);
                if (taUserModel != null)
                {
                    jsonObjUser.put("ofUserName", openFireUser.getUsername());
                    jsonObjUser.put("userId", taUserModel.getUserId());
                    jsonObjUser.put("email", taUserModel.getEmail());

                    jsonObjUser.put("firstName", taUserModel.getFirstName());
                    jsonObjUser.put("lastName", taUserModel.getLastName());

                }
                jsonObjData.put("userData", jsonObjUser);
                json.put("data", jsonObjData);
                if (taUserModel != null && openFireUser != null)
                {

                    json.putOpt("token", taUserToken.getAppToken());
                    json.putOpt("status", CommonConstants.UserRegistration.SUCCESS);

                } else
                {

                    json.putOpt("errorMessage", CommonConstants.UserRegistration.ERROR_MESSAGE);
                    json.putOpt("status", CommonConstants.UserRegistration.ERROR);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method updateProfile() use to update the user information.
     * 
     * @throws Exception
     * 
     * 
     */

    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/updateProfile")
    @Transactional
    public Response updateProfile(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        CommonUtility cu = new CommonUtility();
        AppUserToken appUserToken = new AppUserToken();
        try
        {
            Properties prop = new Properties();

            String output = formpaparam.getString("paparam");
            JSONObject jObject = new JSONObject(output);

            JSONObject userData = jObject.getJSONObject("userData"); // get
                                                                     // userData
                                                                     // object
            String email = userData.getString("email"); // get the name from
                                                        // data.
            LOG.info("email :" + email);
            int userId = userData.getInt("userId");
            String firstName = userData.getString("firstName");
            String lastName = userData.getString("lastName");
            String profilePic = userData.getString("profilePic");
            LOG.info("profilePic :" + profilePic);
            String mobileNo = userData.getString("mobileNo");

            JSONObject addressData = jObject.getJSONObject("addressData"); // get
                                                                           // addressData
            String address1 = addressData.getString("address1");
            String address2 = addressData.getString("address2");
            LOG.info("addressPart2 :" + address2);
            prop = cu.getFromProperty();
            String token = (String) formpaparam.getString("token");
            LOG.info("token====" + token);

            String baseName = prop.getProperty("app.image.name");
            String extension = prop.getProperty("app.image.extension");

            String imageName = baseName + CommonUtility.getDateAndTime() + extension;

            String responsePath = null;
            prop = cu.getFromProperty();
            responsePath = prop.getProperty("app.image.respons.path");

            String imagePath = responsePath + imageName;

            LOG.info("imagePath ::" + imagePath);
            AppUserToken appUser = appUserService.getUserId(token);
            if (appUser == null || appUser.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UpdateProfile.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UpdateProfile.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            appUserBean.setAddress1(address1);
            appUserBean.setAddress2(address2);
            appUserBean.setFirstName(firstName);
            appUserBean.setLastName(lastName);
            tokenBean.setAppToken(token);
            appUserBean.setProfilePic("http://192.168.1.99:8080/TheApp/profilePic/" + imageName);
            appUserBean.setMobileNo(mobileNo);
            appUserBean.setUserId(userId);

            boolean isValidRequest = CommonAppUserUtility.validateupdateProfileRequest(appUserBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid updateProfile Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }

            AppUser taUserModel = CommonAppUserUtility
                    .prepareTaUserModelFromTaUserBeanForUpdateProfile(appUserBean);
            taUserModel = appUserService.updateProfile(taUserModel);

            OpenFireUser fireUser = new OpenFireUser();
            fireUser = fireUserService.getOfUserName(email);
            LOG.info("fireUser.getUsername();" + fireUser.getUsername());


            if (taUserModel != null)
            {
                jsonObjUser.put("userId", taUserModel.getUserId());
                jsonObjUser.put("email", taUserModel.getEmail());
                jsonObjUser.put("mobileNo", taUserModel.getMobileNo());
                jsonObjUser.put("profilePic", taUserModel.getProfilePic());
                jsonObjUser.put("firstName", taUserModel.getFirstName());
                jsonObjUser.put("lastName", taUserModel.getLastName());
                jsonObjUser.put("address1", taUserModel.getAddress1());
                jsonObjUser.put("address2", taUserModel.getAddress2());
                if (fireUser == null)
                {
                    fireUser.setUsername("null");
                }
                jsonObjUser.put("ofUserName", fireUser.getUsername());
            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
            File file = new File(prop.getProperty("app.image.path"));
            if (taUserModel != null)
            {
                if (!file.exists())
                {
                    file.mkdir();
                    json.putOpt("status", CommonConstants.UpdateProfile.SUCCESS);
                    json.putOpt("token", appUserToken.getAppToken());
                    CommonUtility.saveImageInDirectory(profilePic, imagePath);

                } else
                {
                    json.putOpt("token", appUserToken.getAppToken());
                    json.putOpt("status", CommonConstants.UpdateProfile.SUCCESS);
                    CommonUtility.saveImageInDirectory(profilePic, imagePath);

                }
            } else
            {
                json.putOpt("errorMessage", CommonConstants.UpdateProfile.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UpdateProfile.ERROR);
            }

        } catch (Exception e)
        {

        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method login() use to check login credential from the DB.
     * 
     * @throws Exception
     * 
     * 
     */

    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/login")
    @Transactional
    public Response login(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonerror = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        CommonUtility result = new CommonUtility();
        AppUserToken appUserToken = new AppUserToken();
        try
        {
            String output = formpaparam.getString("paparam");
            LOG.info("output===" + output);
            JSONObject jObject = new JSONObject(output);

            JSONObject userData = jObject.getJSONObject("userLoginData"); // get
                                                                          // userData
                                                                          // object
            String email = userData.getString("email"); // get the name from
                                                        // data.
            LOG.info("email :" + email);
            String password = userData.getString("password");
            LOG.info("password :" + password);
            String deviceToken = userData.getString("deviceToken");

            appUserBean.setEmail(email);
            appUserBean.setPassword(password);
            boolean isValidRequest = CommonAppUserUtility.checkValidRequestForUserLogin(appUserBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid UserLogin Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            String token = CommonUtility.generatetToken();
            AppUserToken userToken = appUserService.checkNewGenaratedToken(token);
            if (userToken != null)
            {
                token = CommonUtility.generatetToken();
                tokenBean.setAppToken(token);
            } else
            {
                tokenBean.setAppToken(token);
            }
            AppUser userDetail = appUserService.getUserDetail(email);
            AppUser taUserModel = CommonAppUserUtility
                    .prepareTaUserModelFromTaUserBeanForUserLogin(appUserBean);
            AppUserForgetPasswordOtp appUserForgetPasswordOtp = new AppUserForgetPasswordOtp();
            if (userDetail == null)
            {
                json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserLogin.ERROR);
                return Response.status(200).entity(json.toString()).build();
            } else
            {
                if (userDetail.getIsOtp() == 1)
                {

                    appUserForgetPasswordOtp = appUserService.checkValidRequestForUserLoginUsingOtp(
                            userDetail.getUserId(), taUserModel.getPassword());
                    if (appUserForgetPasswordOtp != null)
                    {
                        taUserModel = userDetail;
                        jsonObjUser.put("isOtp", CommonConstants.ForgetPassword.IS_OTP_ONE);
                        appUserForgetPasswordOtp = appUserService.removeOtp(appUserForgetPasswordOtp);
                    } else
                    {
                        json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                        json.putOpt("status", CommonConstants.UserLogin.ERROR);
                        return Response.status(200).entity(json.toString()).build();
                    }

                } else if (userDetail.getIsOtp() == 0)
                {
                    if (taUserModel == null)
                    {
                        json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                        json.putOpt("status", CommonConstants.UserLogin.ERROR);
                        return Response.status(200).entity(json.toString()).build();
                    } else
                    {
                        taUserModel = appUserService.checkValidRequestForUserLogin(taUserModel);
                        if (taUserModel == null)
                        {
                            json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                            json.putOpt("status", CommonConstants.UserLogin.ERROR);
                            return Response.status(200).entity(json.toString()).build();
                        }
                        jsonObjUser.put("isOtp", CommonConstants.ForgetPassword.IS_OTP_ZERO);
                    }

                }
            }

            if (taUserModel == null && appUserForgetPasswordOtp == null)
            {
                json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserLogin.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            OpenFireUser fireUser = new OpenFireUser();
            fireUser = fireUserService.getOfUserName(taUserModel.getEmail());
            LOG.info("fireUser.getUsername();" + fireUser.getUsername());
            ArrayList<AppUserToken> userTokenList = new ArrayList<AppUserToken>();
            userTokenList = appUserService.getUserTokenList(taUserModel.getUserId(), deviceToken);
            if (userTokenList.size() == 0)
            {
                appUserToken = appUserService.saveNewDeviceToken(taUserModel.getUserId(), token, deviceToken);
                jsonObjUser.put("userId", taUserModel.getUserId());
                jsonObjUser.put("email", taUserModel.getEmail());
                jsonObjUser.put("mobileNo", taUserModel.getMobileNo());
                jsonObjUser.put("profilePic", taUserModel.getProfilePic());
                jsonObjUser.put("firstName", taUserModel.getFirstName());
                jsonObjUser.put("lastName", taUserModel.getLastName());
                jsonObjUser.put("address1", taUserModel.getAddress1());
                jsonObjUser.put("address2", taUserModel.getAddress2());
                jsonObjUser.put("ofUserName", fireUser.getUsername());
            } else
            {
                Iterator<AppUserToken> appIterator = userTokenList.iterator();
                while (appIterator.hasNext())
                {
                    appUserToken = appIterator.next();
                    int status = appUserService.updateAppToken(tokenBean.getAppToken(),
                            taUserModel.getUserId(), deviceToken);
                    if (status == 1)
                    {
                        jsonObjUser.put("userId", taUserModel.getUserId());
                        jsonObjUser.put("email", taUserModel.getEmail());
                        jsonObjUser.put("mobileNo", taUserModel.getMobileNo());
                        jsonObjUser.put("profilePic", taUserModel.getProfilePic());
                        jsonObjUser.put("firstName", taUserModel.getFirstName());
                        jsonObjUser.put("lastName", taUserModel.getLastName());
                        jsonObjUser.put("address1", taUserModel.getAddress1());
                        jsonObjUser.put("address2", taUserModel.getAddress2());
                        jsonObjUser.put("ofUserName", fireUser.getUsername());
                    }
                }

            }

            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);

            if (appUserToken != null)
            {
                json.putOpt("token", tokenBean.getAppToken());

                json.putOpt("status", CommonConstants.UserLogin.SUCCESS);

            } else
            {

                json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserLogin.ERROR);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method getProfile() use to get userProfile according to their
     * userId.
     * 
     * @throws Exception
     * 
     * 
     */

    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/getProfile")
    @Transactional
    public Response getProfile(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();

        String jsonOutPut = formpaparam.getString("paparam");
        LOG.info("output===" + jsonOutPut);
        JSONObject jObject = new JSONObject(jsonOutPut);

        JSONObject userData = jObject.getJSONObject("userGetProfile"); // get
                                                                       // userData
                                                                       // object
        int userId = userData.getInt("userId");
        String token = formpaparam.getString("token");
        LOG.info("token " + token);
        try
        {
            boolean isValidRequest = CommonAppUserUtility.checkValidategetProfile(token);
            if (!isValidRequest)
            {
                LOG.error("Invalid getProfile Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            AppUserToken appUserToken = appUserService.getUserId(token);
            if (appUserToken == null || appUserToken.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            AppUser taUserModel = appUserService.getUserProfile(userId);
            AppUserCoinBalance appCoinBalance = appUserService.getCoinBalance(userId);
            InAppPurchase inAppPurchase = appUserService.getInAppCoinBalance(userId);
            BigDecimal creditAmount = appUserService.getcreditAmountCoinBalance(userId);
            BigDecimal debitAmount = appUserService.getdebitAmountCoinBalance(userId);
            LOG.info("creditAmount----" + creditAmount);
            LOG.info("debitAmount----" + debitAmount);
            LOG.info("userId " + taUserModel.getUserId());
            OpenFireUser fireUser = new OpenFireUser();
            fireUser = fireUserService.getOfUserName(taUserModel.getEmail());
            LOG.info("fireUser.getUsername();" + fireUser.getUsername());
            if (taUserModel != null)
            {
                if (inAppPurchase != null)
                {
                    jsonObjUser.put("pointPurchased", inAppPurchase.getPointPurchased());
                } else
                {
                    jsonObjUser.put("pointPurchased", "null");
                }
                if (appCoinBalance != null)
                {
                    jsonObjUser.put("coin_balance", appCoinBalance.getCoin_balance());
                } else
                {
                    jsonObjUser.put("coin_balance", "null");
                }
                if (creditAmount != null)
                {
                    jsonObjUser.put("forCreditAmount", creditAmount);
                } else
                {
                    jsonObjUser.put("forCreditAmount", "null");
                }
                if (debitAmount != null)
                {
                    jsonObjUser.put("forDebitAmount", debitAmount);
                } else
                {
                    jsonObjUser.put("forDebitAmount", "null");
                }
                jsonObjUser.put("userId", taUserModel.getUserId());
                jsonObjUser.put("email", taUserModel.getEmail());
                jsonObjUser.put("mobileNo", taUserModel.getMobileNo());
                jsonObjUser.put("profilePic", taUserModel.getProfilePic());
                jsonObjUser.put("firstName", taUserModel.getFirstName());
                jsonObjUser.put("lastName", taUserModel.getLastName());
                jsonObjUser.put("address1", taUserModel.getAddress1());
                jsonObjUser.put("address2", taUserModel.getAddress2());
                jsonObjUser.put("ofUserName", fireUser.getUsername());
            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
            if (taUserModel != null)
            {
                json.putOpt("status", CommonConstants.getProfile.SUCCESS);
            } else
            {
                json.putOpt("errorMessage", CommonConstants.getProfile.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.getProfile.ERROR);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method getUserList() use to get userProfile according to their
     * userId.
     * 
     * @throws Exception
     * 
     * 
     */
    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/getUserList")
    @Transactional
    public Response getUserList(JSONObject formpaparam) throws Exception
    {
        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        String output = formpaparam.getString("paparam");
        JSONObject jObject = new JSONObject(output);
        JSONObject userListData = jObject.getJSONObject("userListData");
        int pageNo = userListData.getInt("pageNo");
        int recordPerPage = userListData.getInt("recordPerPage");
        Integer userId = userListData.getInt("userId");
        String searchByAlphabet = userListData.getString("searchByAlphabet");
        String taskId = userListData.getString("taskId");
        String token = formpaparam.getString("token");
        String data = null;
        String s2 = null;
        String demo = "";
        String senderType = userListData.getString("senderType");
        try
        {
            boolean isValidRequest = CommonAppUserUtility.validategetUserList(token, pageNo, recordPerPage);
            if (!isValidRequest)
            {
                LOG.error("Invalid Registration Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            AppUserToken appUserToken = appUserService.getUserId(token);
            if (appUserToken == null || appUserToken.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            ArrayList<AppUser> userList = null;
            Long totalRecords;

            AppUser taUserModel = appUserService.getUserProfile(userId);

            if (taUserModel == null)
            {
                json.putOpt("errorMessage", CommonConstants.GetUserList.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.GetUserList.ERROR);
                return Response.status(200).entity(json.toString()).build();
            } else if (userId.equals(taUserModel.getUserId()))
            {
                userList = appUserService.getUserList(userId, pageNo, recordPerPage, searchByAlphabet,
                        taskId, senderType);

                Iterator<AppUser> userListIterator = userList.iterator();
                while (userListIterator.hasNext())
                {
                    AppUser appUserData = new AppUser();
                    appUserData = userListIterator.next();
                    OpenFireUser fireUser = new OpenFireUser();
                    fireUser = openFireService.getOfUserName(appUserData.getEmail());
                    demo += "{\"firstName\":\"" + appUserData.getFirstName() + "\",\"lastName\":\""
                            + appUserData.getLastName() + "\",\"address1\":\"" + appUserData.getAddress1()
                            + "\",\"address2\":\"" + appUserData.getAddress2() + "\",\"profilePic\":\""
                            + appUserData.getProfilePic() + "\",\"email\":\"" + appUserData.getEmail()
                            + "\",\"mobileNo\":\"" + appUserData.getMobileNo() + "\",\"userId\":\""
                            + appUserData.getUserId() + "\",\"ofUserName\":\"" + fireUser.getUsername()
                            + "\"},";

                }

                if (userList.size() == 0)
                {
                    json.putOpt("errorMessage", CommonConstants.GetUserList.ERROR_MESSAGE_NULL);
                    json.putOpt("status", CommonConstants.GetUserList.ERROR);
                    return Response.status(200).entity(json.toString()).build();
                } else
                {
                    demo = demo.substring(0, demo.lastIndexOf(","));
                    s2 = " \"status\":\"success\",";
                    data = "{" + s2 + " \"data\": {" + "\"userData\": [" + demo + "]}}";

                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(data).build();
    }

    /**
     * This method logout() use to logout the device.
     * 
     * @throws Exception
     * 
     * 
     */
    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/logout")
    @Transactional
    public Response logout(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonerror = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        CommonUtility result = new CommonUtility();
        AppUserTokenBean appUserTokenBean = new AppUserTokenBean();
        AppUserToken appUserToken = new AppUserToken();
        boolean appLogoutUser = false;
        try
        {
            String output = formpaparam.getString("paparam");
            String token = formpaparam.getString("token");
            JSONObject jObject = new JSONObject(output);

            JSONObject userData = jObject.getJSONObject("userLogOut"); // get
                                                                       // userData
                                                                       // object
            int userId = userData.getInt("userId"); // get the name from
                                                    // data.
            String deviceToken = userData.getString("deviceToken");
            LOG.info("userId " + userId);
            appUserTokenBean.setUserId(userId);
            appUserTokenBean.setAppToken(token);
            appUserTokenBean.setDeviceToken(deviceToken);

            boolean isValidRequest = CommonAppUserUtility.checkValidRequestForUserLogout(appUserTokenBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid UserLogout Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            AppUserToken appUserToken2 = appUserService.getUserId(token);
            if (appUserToken2 == null || appUserToken2.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            appUserToken = CommonAppUserUtility
                    .prepareTaUserModelFromTaUserBeanForUserLogout(appUserTokenBean);

            if (appUserToken != null)
            {
                appLogoutUser = appUserService.appUserLogout(appUserToken);
            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);

            if (appUserToken != null)
            {

                json.putOpt("token", appUserToken.getAppToken());
                json.putOpt("status", CommonConstants.UserLogin.SUCCESS);

            } else
            {

                json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserLogin.ERROR);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method inAppPurchase() use to purchase the coins from inAppPurchase.
     * 
     * @throws Exception
     * 
     * 
     */
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/inAppPurchase")
    @Transactional
    public Response inAppPurchase(JSONObject formpaparam) throws Exception
    {
        InAppPurchaseBean inAppPurchaseBean = new InAppPurchaseBean();
        InAppPurchase inPurchase = new InAppPurchase();
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();

        try
        {
            String output = formpaparam.getString("paparam");
            LOG.info("output==="+output);
            JSONObject jObject = new JSONObject(output);

            JSONObject inAppPurchaseData = jObject.getJSONObject("transactionData");

            String inAppProductId = inAppPurchaseData.getString("inAppProductId");

            Integer pointPurchased = inAppPurchaseData.getInt("pointPurchased");

            String transactionId = inAppPurchaseData.getString("transactionId");

            String transactionDate = inAppPurchaseData.getString("transactionDate");

            Integer userId = inAppPurchaseData.getInt("userId");

            String token = formpaparam.getString("token");

            AppUserToken taUserModel = appUserService.getUserId(token);
            if (taUserModel == null || taUserModel.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            inAppPurchaseBean.setInAppProductId(inAppProductId);
            inAppPurchaseBean.setPointPurchased(pointPurchased);
            inAppPurchaseBean.setTransactionDate(transactionDate);
            inAppPurchaseBean.setTransactionId(transactionId);
            inAppPurchaseBean.setUserId(userId);
            boolean isValidRequest = CommonAppUserUtility
                    .checkValidRequestForInAppPurchase(inAppPurchaseBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid UserLogout Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            inPurchase = CommonAppUserUtility
                    .prepareInAppModelFromInAppBeanForInAppPurchase(inAppPurchaseBean);

            if (inPurchase != null)
            {
                inPurchase = appUserService.inAppPurchased(inPurchase);
            }

            if (inPurchase != null)
            {
                jsonObjUser.put("inAppId", inPurchase.getInAppId());
                jsonObjUser.put("inAppProductId", inPurchase.getInAppProductId());
                jsonObjUser.put("pointPurchased", inPurchase.getPointPurchased());
                jsonObjUser.put("transactionId", inPurchase.getTransactionId());
                jsonObjUser.put("transactionDate", inPurchase.getTransactionDate());
                jsonObjUser.put("userId", inPurchase.getUserId());

            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
            if (inPurchase != null)
            {
                json.putOpt("status", CommonConstants.UserRegistration.SUCCESS);

            } else
            {

                json.putOpt("errorMessage", CommonConstants.UserRegistration.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserRegistration.ERROR);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method changePassword() use to change the Password.
     * 
     * @throws Exception
     * 
     * 
     */
    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/changePassword")
    @Transactional
    public Response changePassword(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonerror = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        CommonUtility result = new CommonUtility();
        AppUserTokenBean appUserTokenBean = new AppUserTokenBean();
        AppUserToken appUserToken = new AppUserToken();
        try
        {
            String output = formpaparam.getString("paparam");
            LOG.info("output==="+output);
            String token = formpaparam.getString("token");
            JSONObject jObject = new JSONObject(output);

            JSONObject userData = jObject.getJSONObject("changePasswordData"); // get
                                                                               // userData
                                                                               // object
            int userId = userData.getInt("userId"); // get the name from
                                                    // data.
            String newPassword = userData.getString("newPassword");
            LOG.info("userId " + userId);

            appUserTokenBean.setUserId(userId);
            appUserTokenBean.setAppToken(token);

            boolean isValidRequest = CommonAppUserUtility
                    .checkValidRequestForUserChangePassword(appUserTokenBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid change password Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            AppUserToken appUserToken2 = appUserService.getUserId(token);
            if (appUserToken2 == null || appUserToken2.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.changePassword.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.changePassword.ERROR);
                jsonObjData.put("userData", jsonObjUser);
                json.put("data", jsonObjData);
                return Response.status(200).entity(json.toString()).build();
            }
            AppUser appUser = new AppUser();

            boolean cpStatus = appUserService.updateNewPassword(userId, newPassword);
            if (cpStatus)
            {
                jsonObjUser.put("userId", userId);
                jsonObjUser.put("newPassword", newPassword);
            }

            else
            {
                json.putOpt("errorMessage", CommonConstants.changePassword.oldPassword);
                json.putOpt("status", CommonConstants.changePassword.ERROR);
                jsonObjData.put("userData", jsonObjUser);
                json.put("data", jsonObjData);
                return Response.status(200).entity(json.toString()).build();
            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);

            if (appUserToken2 != null)
            {

                json.putOpt("token", token);
                json.putOpt("status", CommonConstants.changePassword.SUCCESS);

            } else
            {

                json.putOpt("errorMessage", CommonConstants.changePassword.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserLogin.ERROR);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method forgetPassword() use to get forget Password.
     * 
     * @throws Exception
     * 
     * 
     */
    @SuppressWarnings("unused")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @Path("/forgetPassword")
    @Transactional
    public Response forgetPassword(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonerror = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        CommonUtility result = new CommonUtility();
        AppUserTokenBean appUserTokenBean = new AppUserTokenBean();
        AppUserToken appUserToken = new AppUserToken();

        try
        {
            String output = formpaparam.getString("paparam");

            JSONObject jObject = new JSONObject(output);

            JSONObject userData = jObject.getJSONObject("forgetPasswordData"); // get
                                                                               // userData
                                                                               // object
            String email = userData.getString("email"); // get the name from
                                                        // data.
            String deviceToken = userData.getString("deviceToken");
            AppUser appUser = new AppUser();
            boolean checkValidEmailId = appUserService.checkEmailId(email);
            if (checkValidEmailId)
            {
                appUser = appUserService.getUserDetail(email);
                if (appUser != null)
                {
                    String otp = CommonUtility.generatetOtp();
                    AppUserForgetPasswordOtpBean appUserForgetPasswordOtpBean = new AppUserForgetPasswordOtpBean();
                    appUserForgetPasswordOtpBean.setDeviceToken(deviceToken);
                    appUserForgetPasswordOtpBean.setOtp(otp);
                    appUserForgetPasswordOtpBean.setUserId(appUser.getUserId());
                    AppUserForgetPasswordOtp appUserForgetPasswordOtp = new AppUserForgetPasswordOtp();

                    appUserForgetPasswordOtp = CommonAppUserUtility
                            .prepareAppUserForgetPasswordOtpModelFromAppUserForgetPasswordOtpBean(appUserForgetPasswordOtpBean);
                    appUserForgetPasswordOtp = appUserService.SaveOtp(appUserForgetPasswordOtp);
                    MailApi.SendMail(email, otp);
                    jsonObjUser.put("otp", otp);
                    json.putOpt("status", CommonConstants.ForgetPassword.SUCCESS);
                    jsonObjUser.put("emailId", email);
                    jsonObjData.put("userdata", jsonObjUser);
                    json.put("data", jsonObjData);
                    json.putOpt("errorMessage", "");
                }

            } else
            {
                json.putOpt("status", CommonConstants.ForgetPassword.ERROR);
                json.putOpt("errorMessage", CommonConstants.ForgetPassword.ERROR_MESSAGE);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

}
