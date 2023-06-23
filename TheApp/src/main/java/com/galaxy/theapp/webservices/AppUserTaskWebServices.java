/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.webservices;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;
import org.springfparamework.transaction.annotation.Transactional;
import com.galaxy.theapp.bean.AppUserTaskAssignBean;
import com.galaxy.theapp.bean.AppUserTaskBean;
import com.galaxy.theapp.bean.AppUserTaskInviteBean;
import com.galaxy.theapp.bean.AppUserTaskSharingBean;
import com.galaxy.theapp.common.utility.CommonConstants;
import com.galaxy.theapp.common.utility.CommonUtility;
import com.galaxy.theapp.model.AppUserCoinBalance;
import com.galaxy.theapp.model.AppUserTaskAssign;
import com.galaxy.theapp.model.AppUserTaskInvite;
import com.galaxy.theapp.model.AppUser;
import com.galaxy.theapp.model.AppUserTask;
import com.galaxy.theapp.model.AppUserTaskSharing;
import com.galaxy.theapp.model.AppUserToken;
import com.galaxy.theapp.service.AppUserService;
import com.galaxy.theapp.service.AppUserTaskService;
import com.galaxy.theapp.utils.CommonAppUserTaskUtility;
import com.galaxy.theapp.utils.CommonAppUserUtility;

/**
 * This <java>class</java> AppUserTaskWebServices is use to perform all the
 * operation related to UserTask.
 * 
 * @author param Sheikh/Mayank Jain
 * @GWL
 */

@Path("ta_task")
@Component
public class AppUserTaskWebServices
{

    public static final Logger LOG = Logger.getLogger(AppUserTaskWebServices.class);

    @Autowired
    public AppUserService appUserService;
    @Autowired
    public AppUserTaskService appUserTaskService;

    AppUserTaskBean userTaskBean = new AppUserTaskBean();

    AppUserTaskInviteBean appTaskInviteUserBean = new AppUserTaskInviteBean();

    AppUserTaskSharingBean appUserTaskSharingBean = new AppUserTaskSharingBean();

    Properties prop = new Properties();
    CommonUtility cu = new CommonUtility();

    /**
     * This method createTask create the user Task.
     * 
     */

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createTask")
    @Transactional
    public Response taskCreation(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        try
        {
            String output = formpaparam.getString("paparam");
            
            JSONObject jObject = new JSONObject(output);

            JSONObject updateTaskData = jObject.getJSONObject("createTaskData"); // get
                                                                                 // userData
                                                                                 // object
            String title = updateTaskData.getString("taskTitle"); // get the
                                                                  // name
                                                                  // from data.
            String description = updateTaskData.getString("description");
            String date = updateTaskData.getString("date");
            String startdate = updateTaskData.getString("startDate");
            String enddate = updateTaskData.getString("endDate");
            String fromTimezone = updateTaskData.getString("fromTimezone");
            Integer userId = updateTaskData.getInt("userId");
            String token = formpaparam.getString("token");
            Integer value = updateTaskData.getInt("coinValue");

            String invitees = updateTaskData.getString("inviteUserId");
            JSONArray inviteesArray = new JSONArray(invitees);
            AppUserTask userTask = new AppUserTask();
            AppUserCoinBalance appUserCoinBalance = new AppUserCoinBalance();

            AppUserToken taUserModel = appUserService.getUserId(token);
            if (taUserModel == null || taUserModel.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            appUserCoinBalance = appUserTaskService.getCoinBalance(userId);
            if (appUserCoinBalance == null)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE_COIN);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            if (value > appUserCoinBalance.getCoin_balance())
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE_COIN);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            prop = cu.getFromProperty();
            DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

            double duration = 0.0;
            float durationNew = 0.0f;
            int hoursInMins = 0;

            try
            {
                double diff = formatter.parse(enddate).getTime() - formatter.parse(startdate).getTime();
                duration = (double) (diff / (60 * 60 * 1000));
                durationNew = (float) Math.round(duration * 100) / 100;
                hoursInMins = (int) (durationNew * 60);
            } catch (ParseException e)
            {

                e.printStackTrace();
            }

            userTaskBean.setTaskTitle(title);
            userTaskBean.setDescription(description);
            userTaskBean.setCoinValue(value);
            userTaskBean.setTaskStatus(CommonConstants.UserTaskCreation.Task_Status_Open);
            userTaskBean.setUserId(userId);
            userTaskBean.setDuration(durationNew);
            userTaskBean.setStartDate(formatter.format(CommonUtility.convertFromOneTimeZoneToOhter(startdate,
                    fromTimezone, "UTC")));
            userTaskBean.setEndDate(formatter.format(CommonUtility.convertFromOneTimeZoneToOhter(enddate,
                    fromTimezone, "UTC")));
            userTaskBean.setToken(token);
            userTaskBean.setDate(formatter.format(CommonUtility.convertFromOneTimeZoneToOhter(date,
                    fromTimezone, "UTC")));

            boolean isValidRequest = CommonAppUserTaskUtility.validateAddUserTaskRequest(userTaskBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid addUserTask Request");
            }

            AppUserTaskInvite appTaskInviteUser = new AppUserTaskInvite();

            userTask = CommonAppUserTaskUtility.prepareUserTaskModelFromUserTaskCreationBean(userTaskBean);

            userTask = appUserTaskService.saveUserTask(userTask);

            if (userTask != null)
            {
                jsonObjUser.put("userTaskId", userTask.getUserTaskId());
                jsonObjUser.put("taskTitle", userTask.getTaskTitle());
                jsonObjUser.put("description", userTask.getDescription());
                jsonObjUser.put("duration", hoursInMins);
                jsonObjUser.put("coinValue", userTask.getCoinValue());
                jsonObjUser.put("reSaleCoinValue", userTask.getReSaleCoinValue());
                jsonObjUser.put("taskStatus", userTask.getTaskStatus());
                jsonObjUser.put("userId", userTask.getUserId());
                jsonObjUser.put("startDate", startdate);
                jsonObjUser.put("endDate", enddate);
                jsonObjUser.put("date", date);
            }
            LOG.info("taskId" + userTask.getUserTaskId());
            for (int i = 0; i < inviteesArray.length(); i++)
            {
                appTaskInviteUserBean.setUserId(userTask.getUserId());
                appTaskInviteUserBean.setTaskId(userTask.getUserTaskId());
                appTaskInviteUserBean.setTaskStatus(userTask.getTaskStatus());
                appTaskInviteUserBean.setInviteUserId(inviteesArray.getInt(i)); 

                appTaskInviteUser = CommonAppUserTaskUtility
                        .prepareInviteUserModelFromInviteUserBean(appTaskInviteUserBean);
                appTaskInviteUser = appUserTaskService.appUserTaskInvite(appTaskInviteUser);
                if (appTaskInviteUser != null)
                {
                    jsonObjUser.put("inviteID", appTaskInviteUser.getInviteID());
                    jsonObjUser.put("userId", appTaskInviteUser.getUserId());
                    jsonObjUser.put("inviteUserId", appTaskInviteUser.getInviteUserId());
                    jsonObjUser.put("taskId", appTaskInviteUser.getTaskId());
                    jsonObjUser.put("taskStatus", appTaskInviteUser.getTaskStatus());

                }
                jsonObjData.put("userData", jsonObjUser);
                json.put("data", jsonObjData);
            }

            if (userTask != null && appTaskInviteUser != null)
            {
                json.putOpt("status", CommonConstants.UserTaskCreation.SUCCESS);
            } else
            {
                json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                json.putOpt("error", CommonConstants.UserLogin.ERROR);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();

    }

    /**
     * This method editTask editTask the user Task.
     * 
     */

    @SuppressWarnings("unused")
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/editTask")
    @Transactional
    public Response taskCreationUpdate(JSONObject formpaparam) throws Exception
    {
        CommonUtility result = new CommonUtility();
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        try
        {

            String output = formpaparam.getString("paparam");
            
            JSONObject jObject = new JSONObject(output);

            JSONObject updateTaskData = jObject.getJSONObject("updateTaskData"); 
            
            String title = updateTaskData.getString("taskTitle"); 
            String description = updateTaskData.getString("description");
            String date = updateTaskData.getString("date");
            String startdate = updateTaskData.getString("startDate");
            String enddate = updateTaskData.getString("endDate");
            int userId = updateTaskData.getInt("userId");
            String fromTimezone = updateTaskData.getString("fromTimezone");
            LOG.info("fromTimezone :" + fromTimezone);
            String taskId = updateTaskData.getString("taskId");
            String token = formpaparam.getString("token");
            int value = updateTaskData.getInt("coinValue");
            String invitees = updateTaskData.getString("inviteUserId");
            JSONArray inviteesArray = new JSONArray(invitees);

            AppUserTask appTask = appUserTaskService.getTask(taskId);
            int newCoins = (value) - (appTask.getCoinValue());
            AppUserCoinBalance appCoinBalance = appUserService.getCoinBalance(userId);
            if (appCoinBalance.getCoin_balance() < newCoins)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE_COIN);
                json.putOpt("error", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            AppUserToken appUserToken = appUserService.getUserId(token);
            if (appUserToken == null || appUserToken.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            Date date1 = new Date();
            prop = cu.getFromProperty();
            DateFormat formatter = new SimpleDateFormat(prop.getProperty("app.sample.date.formate"));

            double duration = 0.0;
            float durationNew = 0.0f;
            int hoursInMins = 0;
            try
            {
                double diff = formatter.parse(enddate).getTime() - formatter.parse(startdate).getTime();
                duration = (double) (diff / (60 * 60 * 1000));
                durationNew = (float) Math.round(duration * 100) / 100;
                hoursInMins = (int) (durationNew * 60);
                LOG.info("diffHours :" + hoursInMins);
            } catch (ParseException e)
            {

                e.printStackTrace();
            }
            userTaskBean.setTaskTitle(title);
            userTaskBean.setDescription(description);
            userTaskBean.setCoinValue(value);
            userTaskBean.setTaskStatus(CommonConstants.UserTaskCreation.Task_Status_Open);
            userTaskBean.setUserId(appUserToken.getUserId());
            userTaskBean.setDuration(durationNew);
            userTaskBean.setStartDate(formatter.format(CommonUtility.convertFromOneTimeZoneToOhter(startdate,
                    fromTimezone, "UTC")));
            userTaskBean.setEndDate(formatter.format(CommonUtility.convertFromOneTimeZoneToOhter(enddate,
                    fromTimezone, "UTC")));
            userTaskBean.setToken(token);
            userTaskBean.setDate(formatter.format(CommonUtility.convertFromOneTimeZoneToOhter(date,
                    fromTimezone, "UTC")));
            userTaskBean.setUserTaskId(taskId);

            LOG.info("userTaskBean.getToken " + userTaskBean.getToken());
            boolean isValidRequest = CommonAppUserTaskUtility.validateUpdateUserTaskRequest(userTaskBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid addUserTask Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }

            AppUserTask userTask = CommonAppUserTaskUtility
                    .prepareUserTaskModelFromUserTaskCreationBeanForUpdate(userTaskBean);

            LOG.info("userTask :" + userTask.getDescription());
            userTask = appUserTaskService.updateUserTask(userTask, newCoins);
            LOG.info("duration :" + userTask.getDuration());
            if (userTask != null)
            {
                jsonObjUser.put("userTaskId", userTask.getUserTaskId());
                jsonObjUser.put("taskTitle", userTask.getTaskTitle());
                jsonObjUser.put("description", userTask.getDescription());
                jsonObjUser.put("duration", hoursInMins);
                jsonObjUser.put("coinValue", userTask.getCoinValue());
                jsonObjUser.put("reSaleCoinValue", userTask.getReSaleCoinValue());
                jsonObjUser.put("taskStatus", userTask.getTaskStatus());
                jsonObjUser.put("userId", userTask.getUserId());
                jsonObjUser.put("startDate", startdate);
                jsonObjUser.put("endDate", enddate);
                jsonObjUser.put("date", date);
            }
            LOG.info("taskId" + userTask.getUserTaskId());
            AppUserTaskInviteBean inviteUserBean = new AppUserTaskInviteBean();
            inviteUserBean.setUserId(userTask.getUserId());
            inviteUserBean.setTaskId(taskId);
            inviteUserBean.setToken(token);
            for (int i = 0; i < inviteesArray.length(); i++)
            {
                inviteUserBean.setInviteUserId(Integer.parseInt(inviteesArray.getString(i)));
                AppUserTaskInvite inviteUser = new AppUserTaskInvite();
                inviteUser = CommonAppUserTaskUtility
                        .prepareInviteUserModelFromInviteUserBeanForUpdate(inviteUserBean);
                LOG.info("invitees " + inviteUser.getInviteUserId());
                inviteUser = appUserTaskService.UpdateinviteUser(inviteUser);
                if (inviteUser == null)
                {
                    json.putOpt("errorMessage", CommonConstants.UserTaskCreation.Exist_Invitee);
                    json.putOpt("error", CommonConstants.UserTaskCreation.ERROR);
                    return Response.status(200).entity(json.toString()).build();
                } else
                {
                    jsonObjUser.put("invitee", inviteesArray.getString(i));
                }
            }

            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
            if (userTask != null)
            {
                json.putOpt("status", CommonConstants.UserTaskCreation.SUCCESS);

            } else
            {
                json.putOpt("errorMessage", CommonConstants.UserLogin.ERROR_MESSAGE);
                json.putOpt("error", CommonConstants.UserLogin.ERROR);

            }

            LOG.info("date" + date);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();

    }

    /**
     * This method shareTask shareTask the user Task.
     * 
     */

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/shareTask")
    @Transactional
    public Response sharingTask(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        try
        {
            String output = formpaparam.getString("paparam");
            LOG.info("output==="+output);
            JSONObject jObject = new JSONObject(output);
            JSONObject updateTaskData = jObject.getJSONObject("shareTaskData");
            String token = formpaparam.getString("token");
            int userId = updateTaskData.getInt("userId");
            AppUserToken taUserModel = appUserService.getUserId(token);
            if (taUserModel == null || taUserModel.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            String taskId = updateTaskData.getString("taskId");

            int senderId = updateTaskData.getInt("senderId");

            String sharingId = updateTaskData.getString("recieverId");
            int reSaleCoinValue = updateTaskData.getInt("reSaleCoinValue");

            JSONArray sharingIdArray = new JSONArray(sharingId);
            AppUserTaskSharing userTask = new AppUserTaskSharing();
            AppUserTask appUserTask = new AppUserTask();
            userTaskBean.setReSaleCoinValue(reSaleCoinValue);
            appUserTask.setReSaleCoinValue(userTaskBean.getReSaleCoinValue());
            LOG.info("appUserTask------" + appUserTask.getReSaleCoinValue());
            for (int i = 0; i < sharingIdArray.length(); i++)
            {

                appUserTaskSharingBean.setSenderId(senderId);
                appUserTaskSharingBean.setTaskId(taskId);
                appUserTaskSharingBean.setRecieverId(sharingIdArray.getInt(i));
                userTask = CommonAppUserTaskUtility
                        .prepareTaskSharingModelFromTaskSharingUserBean(appUserTaskSharingBean);

                userTask = appUserTaskService.sharingTask(userTask, appUserTask);
            }
            if (userTask != null)
            {
                jsonObjUser.put("shareId", userTask.getShareId());
                jsonObjUser.put("senderId", userTask.getSenderId());
                jsonObjUser.put("recieverId", userTask.getRecieverId());
                jsonObjUser.put("taskId", userTask.getTaskId());

            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
            if (userTask != null)
            {
                json.putOpt("status", CommonConstants.UserTaskCreation.SUCCESS);

            } else
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("error", CommonConstants.UserTaskCreation.ERROR);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();

    }

    /**
     * This method getTaskList get the TaskList of user Task.
     * 
     */

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getTaskList")
    @Transactional
    public Response getTaskList(JSONObject formpaparam) throws Exception
    {
        JSONObject json = new JSONObject();
        try
        {
            String output = formpaparam.getString("paparam");
            JSONObject jObject = new JSONObject(output);
            JSONObject updateTaskData = jObject.getJSONObject("taskListData");
            String token = formpaparam.getString("token");
            int userId = updateTaskData.getInt("userId");
            int taskType = updateTaskData.getInt("taskType");
            ArrayList<AppUserTask> taskList = new ArrayList<AppUserTask>();
            ArrayList<AppUserTaskInvite> InviteList = new ArrayList<AppUserTaskInvite>();
            ArrayList<AppUserTaskSharing> sharingInviteList = new ArrayList<AppUserTaskSharing>();
            ArrayList<AppUser> inviteUserInfoList = new ArrayList<AppUser>();
            ArrayList<AppUserTaskAssign> userTaskAssignList = new ArrayList<AppUserTaskAssign>();
            AppUserToken appUserToken = appUserService.getUserId(token);
            if (appUserToken == null || appUserToken.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            String demo = "";
            if (taskType == 2)
            {
                taskList = appUserTaskService.getTaskListAccordingUser(userId, taskType);
                LOG.info("taskList" + taskList.size());
                if (taskList.size() == 0)
                {

                    demo += "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                            + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                            + "\",\"data\":{" + " \"userData\":[]}}";
                    return Response.status(200).entity(demo).build();
                } else
                {

                    demo += "{  " + " \"status\":\"" + CommonConstants.getTaskList.SUCCESS + "\",\"data\":{"
                            + " \"userData\":[";
                    Iterator<AppUserTask> it = taskList.iterator();
                    int t = 1;
                    while (it.hasNext())
                    {

                        AppUserTask appUserTask = new AppUserTask();
                        appUserTask = it.next();
                        String taskId = appUserTask.getUserTaskId();
                        LOG.info("taskId " + taskId);

                        ArrayList<AppUserTaskInvite> invitedUserList = new ArrayList<AppUserTaskInvite>();
                        invitedUserList = appUserTaskService.getInviteesData(userId, taskId);

                        prop = cu.getFromProperty();
                        DateFormat formatter = new SimpleDateFormat(
                                prop.getProperty("app.sample.date.formate"));
                        double duration = 0.0;
                        float durationNew = 0.0f;
                        float hoursInMins = 0.0f;

                        try
                        {
                            double diff = formatter.parse(appUserTask.getEndDate()).getTime()
                                    - formatter.parse(appUserTask.getStartDate()).getTime();
                            LOG.info("diff-----" + diff);
                            duration = (double) (diff / (60 * 60 * 1000));
                            durationNew = (float) Math.round(duration * 100) / 100;
                            hoursInMins = (durationNew * 60);
                            LOG.info("diffHours :" + hoursInMins);
                        } catch (ParseException e)
                        {

                            e.printStackTrace();
                        }
                        demo += "{" + "\"taskTitle\":\"" + appUserTask.getTaskTitle()
                                + "\",\"description\":\"" + appUserTask.getDescription() + "\""
                                + ",\"coinValue\":\"" + appUserTask.getCoinValue()
                                + "\",\"reSaleCoinValue\":\"" + appUserTask.getReSaleCoinValue()
                                + "\",\"duration\":\"" + hoursInMins + "\",\"taskId\":\""
                                + appUserTask.getUserTaskId() + "\",\"taskStatus\":\""
                                + appUserTask.getTaskStatus() + "\",\"startDate\":\""
                                + appUserTask.getStartDate() + "\",\"endDate\":\"" + appUserTask.getEndDate()
                                + "\",\"sharedInvitees\":\"" + sharingInviteList + "\",\"invitees\":[";


                        for (int i = 0; i < invitedUserList.size(); i++)
                        {
                            AppUserTaskInvite invitedUser = new AppUserTaskInvite();
                            invitedUser = invitedUserList.get(i);
                            AppUser appUserData = new AppUser();
                            LOG.info("invitedUser :" + invitedUser.getInviteUserId());
                            appUserData = appUserTaskService.getInviteUserInformation(invitedUser
                                    .getInviteUserId()); // According ReciverId
                            LOG.info("appUserTaskData :" + appUserData.getFirstName());
                            inviteUserInfoList.add(appUserData);
                            if (invitedUserList.size() == 1)
                            {
                                demo += "{\"firstName\":\"" + appUserData.getFirstName()
                                        + "\",\"lastName\":\"" + appUserData.getLastName()
                                        + "\",\"address1\":\"" + appUserData.getAddress1()
                                        + "\",\"address2\":\"" + appUserData.getAddress2()
                                        + "\",\"profilePic\":\"" + appUserData.getProfilePic()
                                        + "\",\"email\":\"" + appUserData.getEmail() + "\",\"mobileNo\":\""
                                        + appUserData.getMobileNo() + "\",\"userId\":\""
                                        + appUserData.getUserId() + "\"}";

                            } else if (invitedUserList.size() > 1 && i < (invitedUserList.size() - 1))
                            {
                                demo += "{\"firstName\":\"" + appUserData.getFirstName()
                                        + "\",\"lastName\":\"" + appUserData.getLastName()
                                        + "\",\"address1\":\"" + appUserData.getAddress1()
                                        + "\",\"address2\":\"" + appUserData.getAddress2()
                                        + "\",\"profilePic\":\"" + appUserData.getProfilePic()
                                        + "\",\"email\":\"" + appUserData.getEmail() + "\",\"mobileNo\":\""
                                        + appUserData.getMobileNo() + "\",\"userId\":\""
                                        + appUserData.getUserId() + "\"},";
                            } else if (i == (invitedUserList.size() - 1))
                            {
                                demo += "{\"firstName\":\"" + appUserData.getFirstName()
                                        + "\",\"lastName\":\"" + appUserData.getLastName()
                                        + "\",\"address1\":\"" + appUserData.getAddress1()
                                        + "\",\"address2\":\"" + appUserData.getAddress2()
                                        + "\",\"profilePic\":\"" + appUserData.getProfilePic()
                                        + "\",\"email\":\"" + appUserData.getEmail() + "\",\"mobileNo\":\""
                                        + appUserData.getMobileNo() + "\",\"userId\":\""
                                        + appUserData.getUserId() + "\"}";
                            }

                        }
                        if (t == taskList.size())
                        {
                            demo += "]}";
                        } else if (taskList.size() > 1 && t < taskList.size())
                        {
                            demo += "]},";
                        }
                        t++;
                    }
                    demo += "]}}";

                    return Response.status(200).entity(demo).build();
                }
            } else if (taskType == 1)
            {

                InviteList = appUserTaskService.getInviteeList(userId); // reciverId

                demo += "{  " + " \"status\":\"" + CommonConstants.getTaskList.SUCCESS + "\",\"data\":{"
                        + " \"userData\":[";

                LOG.info("size of InviteList:" + InviteList.size());
                Iterator<AppUserTaskInvite> inviteListIterator = InviteList.iterator();
                while (inviteListIterator.hasNext())
                {
                    AppUserTaskInvite appUserTaskInvite = new AppUserTaskInvite();
                    appUserTaskInvite = inviteListIterator.next();
                    LOG.info("recieverId:" + appUserTaskInvite.getInviteUserId());
                    LOG.info("senderId :" + appUserTaskInvite.getUserId());
                    LOG.info("taskId :" + appUserTaskInvite.getTaskId());
                    taskList = appUserTaskService.getTaskListAccordingUserForInvitee(
                            appUserTaskInvite.getUserId(), appUserTaskInvite.getTaskId());// according
                                                                                          // senderId
                    for (int t = 0; t < taskList.size(); t++)
                    {
                        AppUserTask appUserTask = new AppUserTask();
                        appUserTask = taskList.get(t);
                        String taskId = appUserTask.getUserTaskId();
                        LOG.info("taskId " + taskId);

                        prop = cu.getFromProperty();
                        DateFormat formatter = new SimpleDateFormat(
                                prop.getProperty("app.sample.date.formate"));
                        double duration = 0.0;
                        float durationNew = 0.0f;
                        int hoursInMins = 0;

                        try
                        {
                            double diff = formatter.parse(appUserTask.getEndDate()).getTime()
                                    - formatter.parse(appUserTask.getStartDate()).getTime();
                            LOG.info("diff-----" + diff);
                            duration = (double) (diff / (60 * 60 * 1000));
                            durationNew = (float) Math.round(duration * 100) / 100;
                            hoursInMins = (int) (durationNew * 60);
                            LOG.info("diffHours :" + hoursInMins);
                        } catch (ParseException e)
                        {

                            e.printStackTrace();
                        }

                        demo += "{" + "\"taskTitle\":\"" + appUserTask.getTaskTitle()
                                + "\",\"description\":\"" + appUserTask.getDescription() + "\""
                                + ",\"coinValue\":\"" + appUserTask.getCoinValue()
                                + "\",\"reSaleCoinValue\":\"" + appUserTask.getReSaleCoinValue()
                                + "\",\"duration\":\"" + hoursInMins + "\",\"taskId\":\""
                                + appUserTask.getUserTaskId() + "\",\"taskStatus\":\""
                                + appUserTask.getTaskStatus() + "\",\"startDate\":\""
                                + appUserTask.getStartDate() + "\",\"endDate\":\"" + appUserTask.getEndDate()
                                + "\",\"owner\":[";

                        ArrayList<AppUserTaskInvite> invitedUserList = new ArrayList<AppUserTaskInvite>();
                        invitedUserList = appUserTaskService.getInviteesData(appUserTask.getUserId(), taskId,
                                userId);
                        LOG.info("size of invitedUserList: " + invitedUserList.size());

                        Iterator<AppUserTaskInvite> inviteeIterator = invitedUserList.iterator();

                        for (int i = 0; i < invitedUserList.size(); i++)
                        {

                            AppUserTaskInvite invitedUser = new AppUserTaskInvite();
                            invitedUser = inviteeIterator.next();
                            AppUser appUserData = new AppUser();
                            LOG.info("invitedUser :" + invitedUser.getInviteUserId());
                            appUserData = appUserTaskService
                                    .getInviteUserInformation(invitedUser.getUserId()); // According
                                                                                        // ReciverId
                            LOG.info("appUserTaskData :" + appUserData.getFirstName());
                            inviteUserInfoList.add(appUserData);
                            if (invitedUserList.size() == 1)
                            {
                                demo += "{\"firstName\":\"" + appUserData.getFirstName()
                                        + "\",\"lastName\":\"" + appUserData.getLastName()
                                        + "\",\"address1\":\"" + appUserData.getAddress1()
                                        + "\",\"address2\":\"" + appUserData.getAddress2()
                                        + "\",\"profilePic\":\"" + appUserData.getProfilePic()
                                        + "\",\"email\":\"" + appUserData.getEmail() + "\",\"mobileNo\":\""
                                        + appUserData.getMobileNo() + "\",\"userId\":\""
                                        + appUserData.getUserId() + "\"}";

                            } else if (invitedUserList.size() > 1 && i < (invitedUserList.size() - 1))
                            {
                                demo += "{\"firstName\":\"" + appUserData.getFirstName()
                                        + "\",\"lastName\":\"" + appUserData.getLastName()
                                        + "\",\"address1\":\"" + appUserData.getAddress1()
                                        + "\",\"address2\":\"" + appUserData.getAddress2()
                                        + "\",\"profilePic\":\"" + appUserData.getProfilePic()
                                        + "\",\"email\":\"" + appUserData.getEmail() + "\",\"mobileNo\":\""
                                        + appUserData.getMobileNo() + "\",\"userId\":\""
                                        + appUserData.getUserId() + "\"},";
                            } else if (i == (invitedUserList.size() - 1))
                            {
                                demo += "{\"firstName\":\"" + appUserData.getFirstName()
                                        + "\",\"lastName\":\"" + appUserData.getLastName()
                                        + "\",\"address1\":\"" + appUserData.getAddress1()
                                        + "\",\"address2\":\"" + appUserData.getAddress2()
                                        + "\",\"profilePic\":\"" + appUserData.getProfilePic()
                                        + "\",\"email\":\"" + appUserData.getEmail() + "\",\"mobileNo\":\""
                                        + appUserData.getMobileNo() + "\",\"userId\":\""
                                        + appUserData.getUserId() + "\"}";
                            }

                        }

                        if (t == taskList.size() || t == 1)
                        {
                            demo += "],\"sharedInvitees\":[";
                        } else if (taskList.size() > 0 && t < taskList.size())
                        {
                            
                            demo += "],\"sharedInvitees\":[";
                        }
                        sharingInviteList = appUserTaskService.getSharingData(userId,
                                appUserTask.getUserTaskId());
                        for (int a = 0; a < sharingInviteList.size(); a++)
                        {
                            AppUserTaskSharing appUserTaskSharing = new AppUserTaskSharing();
                            appUserTaskSharing = sharingInviteList.get(a);
                            AppUser appUserData = new AppUser();
                            LOG.info("sharedUser :" + appUserTaskSharing.getRecieverId());
                            appUserData = appUserTaskService.getInviteUserInformation(appUserTaskSharing
                                    .getRecieverId());

                            demo += "{\"firstName\":\"" + appUserData.getFirstName() + "\",\"lastName\":\""
                                    + appUserData.getLastName() + "\",\"address1\":\""
                                    + appUserData.getAddress1() + "\",\"address2\":\""
                                    + appUserData.getAddress2() + "\",\"profilePic\":\""
                                    + appUserData.getProfilePic() + "\",\"email\":\""
                                    + appUserData.getEmail() + "\",\"mobileNo\":\""
                                    + appUserData.getMobileNo() + "\",\"userId\":\""
                                    + appUserData.getUserId() + "\"},";

                        }

                        if (taskList.size() > 0 && t < taskList.size())
                        {
                            if (demo.endsWith(","))
                            {
                                demo = demo.substring(0, demo.length() - 1) + " ";
                            }

                            demo += "]},";
                        }

                        t++;
                    }

                }
                sharingInviteList = appUserTaskService.getSharingTaskData(userId, taskType);

                Iterator<AppUserTaskSharing> sharingInviteiterator = sharingInviteList.iterator();
                while (sharingInviteiterator.hasNext())
                {
                    AppUserTaskSharing appUserTaskSharing = new AppUserTaskSharing();
                    appUserTaskSharing = sharingInviteiterator.next();
                    AppUserTask appUserTask = new AppUserTask();
                    appUserTask = appUserTaskService.getTaskInformation(appUserTaskSharing.getTaskId(),
                            taskType);
                    if (appUserTask == null)
                    {
                        continue;
                    } else
                    {
                        AppUser appUserData = new AppUser();
                        appUserData = appUserTaskService.getInviteUserInformation(appUserTaskSharing
                                .getSenderId());
                        if (appUserTask.getTaskStatus() == 7)
                        {

                            prop = cu.getFromProperty();
                            DateFormat formatter = new SimpleDateFormat(
                                    prop.getProperty("app.sample.date.formate"));
                            double duration = 0.0;
                            float durationNew = 0.0f;
                            int hoursInMins = 0;

                            try
                            {
                                double diff = formatter.parse(appUserTask.getEndDate()).getTime()
                                        - formatter.parse(appUserTask.getStartDate()).getTime();
                                LOG.info("diff-----" + diff);
                                duration = (double) (diff / (60 * 60 * 1000));
                                durationNew = (float) Math.round(duration * 100) / 100;
                                hoursInMins = (int) (durationNew * 60);
                                LOG.info("diffHours :" + hoursInMins);
                            } catch (ParseException e)
                            {

                                e.printStackTrace();
                            }

                            demo += "{" + "\"taskTitle\":\"" + appUserTask.getTaskTitle()
                                    + "\",\"description\":\"" + appUserTask.getDescription() + "\""
                                    + ",\"coinValue\":\"" + appUserTask.getCoinValue()
                                    + "\",\"reSaleCoinValue\":\"" + appUserTask.getReSaleCoinValue()
                                    + "\",\"duration\":\"" + hoursInMins + "\",\"taskId\":\""
                                    + appUserTask.getUserTaskId() + "\",\"taskStatus\":\""
                                    + appUserTask.getTaskStatus() + "\",\"startDate\":\""
                                    + appUserTask.getStartDate() + "\",\"endDate\":\""
                                    + appUserTask.getEndDate() + "\",\"owner\":[";
                            demo += "{\"firstName\":\"" + appUserData.getFirstName() + "\",\"lastName\":\""
                                    + appUserData.getLastName() + "\",\"address1\":\""
                                    + appUserData.getAddress1() + "\",\"address2\":\""
                                    + appUserData.getAddress2() + "\",\"profilePic\":\""
                                    + appUserData.getProfilePic() + "\",\"email\":\""
                                    + appUserData.getEmail() + "\",\"mobileNo\":\""
                                    + appUserData.getMobileNo() + "\",\"userId\":\""
                                    + appUserData.getUserId() + "\"}]},";

                        } else
                        {
                            continue;
                        }
                    }

                }

                LOG.info("inviteList :" + InviteList.size());
                LOG.info("shareList :" + sharingInviteList.size());

                if (InviteList.size() == 0 && sharingInviteList.size() == 0)
                {
                    demo = "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                            + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                            + "\",\"data\":{" + " \"userData\":[]}}";
                    return Response.status(200).entity(demo).build();

                }

                else
                {
                    if (demo.length() == 44)
                    {
                        demo = "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                                + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                                + "\",\"data\":{" + " \"userData\":[]}}";
                        return Response.status(200).entity(demo).build();
                    } else
                    {
                        demo = demo.substring(0, demo.lastIndexOf(","));
                        demo += "]}}";
                       
                        return Response.status(200).entity(demo).build();
                    }
                }
            }

            else if (taskType == 3)
            {
                sharingInviteList = appUserTaskService.getSharingTaskData(userId, taskType);

                demo += "{  " + " \"status\":\"" + CommonConstants.getTaskList.SUCCESS + "\",\"data\":{"
                        + " \"userData\":[";
                Iterator<AppUserTaskSharing> sharingIterator = sharingInviteList.iterator();
                int i = 1;
                AppUserTask appUserTask = null;
                while (sharingIterator.hasNext())
                {
                    appUserTask = new AppUserTask();
                    AppUserTaskSharing appUserTaskSharing = new AppUserTaskSharing();
                    appUserTaskSharing = sharingIterator.next();

                    appUserTask = appUserTaskService.getTaskInformation(appUserTaskSharing.getTaskId(),
                            taskType);
                    if (appUserTask == null)
                    {
                        continue;

                    } else
                    {

                        prop = cu.getFromProperty();
                        DateFormat formatter = new SimpleDateFormat(
                                prop.getProperty("app.sample.date.formate"));
                        double duration = 0.0;
                        float durationNew = 0.0f;
                        int hoursInMins = 0;

                        try
                        {
                            double diff = formatter.parse(appUserTask.getEndDate()).getTime()
                                    - formatter.parse(appUserTask.getStartDate()).getTime();
                            LOG.info("diff-----" + diff);
                            duration = (double) (diff / (60 * 60 * 1000));
                            durationNew = (float) Math.round(duration * 100) / 100;
                            hoursInMins = (int) (durationNew * 60);
                            LOG.info("diffHours :" + hoursInMins);
                        } catch (ParseException e)
                        {

                            e.printStackTrace();
                        }

                        demo += "{" + "\"taskTitle\":\"" + appUserTask.getTaskTitle()
                                + "\",\"description\":\"" + appUserTask.getDescription() + "\""
                                + ",\"coinValue\":\"" + appUserTask.getCoinValue()
                                + "\",\"reSaleCoinValue\":\"" + appUserTask.getReSaleCoinValue()
                                + "\",\"duration\":\"" + hoursInMins + "\",\"taskId\":\""
                                + appUserTask.getUserTaskId() + "\",\"taskStatus\":\""
                                + appUserTask.getTaskStatus() + "\",\"startDate\":\""
                                + appUserTask.getStartDate() + "\",\"endDate\":\"" + appUserTask.getEndDate()
                                + "\",\"owner\":[";
                        AppUser appUserData1 = new AppUser();
                        appUserData1 = appUserTaskService.getInviteUserInformation(appUserTask.getUserId());
                        demo += "{\"firstName\":\"" + appUserData1.getFirstName() + "\",\"lastName\":\""
                                + appUserData1.getLastName() + "\",\"address1\":\""
                                + appUserData1.getAddress1() + "\",\"address2\":\""
                                + appUserData1.getAddress2() + "\",\"profilePic\":\""
                                + appUserData1.getProfilePic() + "\",\"email\":\"" + appUserData1.getEmail()
                                + "\",\"mobileNo\":\"" + appUserData1.getMobileNo() + "\",\"userId\":\""
                                + appUserData1.getUserId() + "\"}],";

                        AppUser appUserData = new AppUser();
                        LOG.info("sharingRecieverId :"+appUserTaskSharing.getRecieverId());
                        appUserData = appUserTaskService.getInviteUserInformation(appUserTaskSharing
                                .getRecieverId()); // According ReciverId
                        LOG.info("appUserTaskData :" + appUserData.getFirstName());
                        inviteUserInfoList.add(appUserData);

                        demo += "" + "\"sharedInvitees\":[{\"firstName\":\"" + appUserData.getFirstName()
                                + "\",\"lastName\":\"" + appUserData.getLastName() + "\",\"address1\":\""
                                + appUserData.getAddress1() + "\",\"address2\":\""
                                + appUserData.getAddress2() + "\",\"profilePic\":\""
                                + appUserData.getProfilePic() + "\",\"email\":\"" + appUserData.getEmail()
                                + "\",\"mobileNo\":\"" + appUserData.getMobileNo() + "\",\"userId\":\""
                                + appUserData.getUserId() + "\"}]";
                        i++;

                        demo += "},";
                    }

                }
                if (appUserTask == null)
                {
                    demo = "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                            + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                            + "\",\"data\":{" + " \"userData\":[]}}";
                    return Response.status(200).entity(demo).build();
                } else
                {
                    demo = demo.substring(0, demo.lastIndexOf(","));
                    demo += "]}}";
                    LOG.info("demo :" + demo);
                    return Response.status(200).entity(demo).build();
                }
            } else if (taskType == 4)
            {
                LOG.info("userId" + userId);
                userTaskAssignList = appUserTaskService.getAssignListData(userId);
                LOG.info("size of list " + userTaskAssignList.size());
                if (userTaskAssignList.size() == 0)
                {

                    demo += "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                            + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                            + "\",\"data\":{" + " \"userData\":[]}}";
                    return Response.status(200).entity(demo).build();
                } else
                {

                    demo += "{  " + " \"status\":\"" + CommonConstants.getTaskList.SUCCESS + "\",\"data\":{"
                            + " \"userData\":[";
                    Iterator<AppUserTaskAssign> userTaskAssignIterator = userTaskAssignList.iterator();
                    int i = 1;
                    while (userTaskAssignIterator.hasNext())
                    {
                        AppUserTaskAssign appUserTaskAssign = new AppUserTaskAssign();
                        appUserTaskAssign = userTaskAssignIterator.next();
                        AppUserTask appUserTask = new AppUserTask();
                        appUserTask = appUserTaskService.getTaskInformation(appUserTaskAssign.getTaskId(),
                                taskType);
                        if (appUserTask == null)
                        {
                            continue;
                        } else
                        {
                            AppUserTaskInvite appUserTaskInvite = new AppUserTaskInvite();
                            appUserTaskInvite = null;

                            prop = cu.getFromProperty();
                            DateFormat formatter = new SimpleDateFormat(
                                    prop.getProperty("app.sample.date.formate"));
                            double duration = 0.0;
                            float durationNew = 0.0f;
                            int hoursInMins = 0;

                            try
                            {
                                double diff = formatter.parse(appUserTask.getEndDate()).getTime()
                                        - formatter.parse(appUserTask.getStartDate()).getTime();
                                LOG.info("diff-----" + diff);
                                duration = (double) (diff / (60 * 60 * 1000));
                                durationNew = (float) Math.round(duration * 100) / 100;
                                hoursInMins = (int) (durationNew * 60);
                                LOG.info("diffHours :" + hoursInMins);
                            } catch (ParseException e)
                            {

                                e.printStackTrace();
                            }

                            demo += "{" + "\"taskTitle\":\"" + appUserTask.getTaskTitle()
                                    + "\",\"description\":\"" + appUserTask.getDescription() + "\""
                                    + ",\"coinValue\":\"" + appUserTask.getCoinValue()
                                    + "\",\"reSaleCoinValue\":\"" + appUserTask.getReSaleCoinValue()
                                    + "\",\"duration\":\"" + hoursInMins + "\",\"taskId\":\""
                                    + appUserTask.getUserTaskId() + "\",\"taskStatus\":\""
                                    + appUserTask.getTaskStatus() + "\",\"startDate\":\""
                                    + appUserTask.getStartDate() + "\",\"endDate\":\""
                                    + appUserTask.getEndDate() + "\",\"invitees\":\"" + appUserTaskInvite
                                    + "\",";

                            AppUser appUserData = new AppUser();
                            appUserData = appUserTaskService.getInviteUserInformation(appUserTaskAssign
                                    .getSenderId()); // According ReciverId
                            LOG.info("appUserTaskData :" + appUserData.getFirstName());
                            inviteUserInfoList.add(appUserData);

                            demo += "" + "\"acceptedUser\":[{\"firstName\":\"" + appUserData.getFirstName()
                                    + "\",\"lastName\":\"" + appUserData.getLastName() + "\",\"address1\":\""
                                    + appUserData.getAddress1() + "\",\"address2\":\""
                                    + appUserData.getAddress2() + "\",\"profilePic\":\""
                                    + appUserData.getProfilePic() + "\",\"email\":\""
                                    + appUserData.getEmail() + "\",\"mobileNo\":\""
                                    + appUserData.getMobileNo() + "\",\"userId\":\""
                                    + appUserData.getUserId() + "\"}]";
                            i++;

                            demo += "},";
                        }

                    }
                    if (userTaskAssignList.size() == 0)
                    {
                        demo = "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                                + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                                + "\",\"data\":{" + " \"userData\":[]}}";

                        return Response.status(200).entity(demo).build();
                    } else
                    {
                        if (demo.length() == 44)
                        {
                            demo = "{  " + " \"status\":\"" + CommonConstants.getTaskList.ERROR
                                    + "\",\"errorMessage\":\"" + CommonConstants.getTaskList.ERROR_MESSAGE
                                    + "\",\"data\":{" + " \"userData\":[]}}";

                            return Response.status(200).entity(demo).build();
                        } else
                        {
                            demo = demo.substring(0, demo.lastIndexOf(","));
                            demo += "]}}";
                            
                            return Response.status(200).entity(demo).build();
                        }
                    }
                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Response.status(200).entity("hi").build();
    }

    /**
     * This method updateTaskStatus update the TaskStatus of user Task.
     * 
     */

    @SuppressWarnings("unused")
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateTaskStatus")
    @Transactional
    public Response updateTaskStatus(JSONObject formpaparam) throws Exception
    {
        CommonUtility result = new CommonUtility();
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        try
        {

            String output = formpaparam.getString("paparam");
            JSONObject jObject = new JSONObject(output);

            JSONObject updateTaskData = jObject.getJSONObject("updateTaskStatusData"); // get
                                                                                       // userData
                                                                                       // object
            String token = formpaparam.getString("token");

            Integer recieverId = updateTaskData.getInt("userId");

            String taskId = updateTaskData.getString("taskId");

            Integer taskStatus = updateTaskData.getInt("taskStatus");

            Integer senderId = updateTaskData.getInt("senderId");

            AppUserTaskBean userTaskBean = new AppUserTaskBean();
            AppUserToken appUserToken = appUserService.getUserId(token);
            if (appUserToken == null || appUserToken.getUserId() != recieverId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            userTaskBean.setUserId(recieverId);
            userTaskBean.setUserTaskId(taskId);
            userTaskBean.setTaskStatus(taskStatus);

            boolean isValidRequest = CommonAppUserTaskUtility.validateUserTaskStatusRequest(userTaskBean,
                    token);
            if (!isValidRequest)
            {
                LOG.error("Invalid addUserTask Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }

            AppUserTask updateTaskStatus = CommonAppUserTaskUtility
                    .prepareUpdateTaskStatusModelFromUserTaskBean(userTaskBean);

            LOG.info("updateTaskStatus.getTaskStatus()  :" + updateTaskStatus.getTaskStatus());
            updateTaskStatus = appUserTaskService.updateTaskStatus(updateTaskStatus);
            if (updateTaskStatus == null)
            {
                jsonObjUser.put("updateTaskStatus", "false");
                jsonObjData.put("userData", jsonObjUser);
                json.put("data", jsonObjData);
                json.put("status", CommonConstants.ERROR);
                json.put("errorMessage", "Request Failed");

            } else
            {
                if (taskStatus == 2 || taskStatus == 4)
                {
                    AppUserTaskInvite appUserTaskInvite = new AppUserTaskInvite();

                    AppUserTaskAssignBean appUserTaskAssignBean = new AppUserTaskAssignBean();

                    appUserTaskAssignBean.setRecieverId(recieverId);
                    appUserTaskAssignBean.setSenderId(senderId);
                    appUserTaskAssignBean.setTaskId(taskId);

                    appUserTaskInvite.setTaskId(taskId);
                    appUserTaskInvite.setTaskStatus(taskStatus);
                    appUserTaskInvite.setUserId(recieverId);

                    AppUserTaskAssign appUserTaskAssign = new AppUserTaskAssign();

                    appUserTaskAssign = CommonAppUserTaskUtility
                            .prepareAppUserTaskAssignModelFromAppUserTaskAssignBean(appUserTaskAssignBean);
                    appUserTaskAssign = appUserTaskService.createAssignee(appUserTaskAssign,
                            appUserTaskInvite);

                    jsonObjUser.put("updateTaskStatus", "true");
                    jsonObjUser.put("taskStatus", updateTaskStatus.getTaskStatus());
                    jsonObjData.put("userData", jsonObjUser);
                    json.put("data", jsonObjData);
                    json.put("status", CommonConstants.SUCCESS);
                    json.put("errorMessage", "null");
                } else if (taskStatus == 1 || taskStatus == 3)
                {
                    AppUserTaskInvite appUserTaskInvite = new AppUserTaskInvite();

                    AppUserTaskAssignBean appUserTaskAssignBean = new AppUserTaskAssignBean();

                    appUserTaskAssignBean.setRecieverId(recieverId);
                    appUserTaskAssignBean.setSenderId(senderId);
                    appUserTaskAssignBean.setTaskId(taskId);

                    appUserTaskInvite.setTaskId(taskId);
                    appUserTaskInvite.setTaskStatus(taskStatus);
                    appUserTaskInvite.setUserId(recieverId);

                    AppUserTaskAssign appUserTaskAssign = new AppUserTaskAssign();

                    appUserTaskAssign = CommonAppUserTaskUtility
                            .prepareAppUserTaskAssignModelFromAppUserTaskAssignBean(appUserTaskAssignBean);
                    appUserTaskAssign = appUserTaskService.createAssignee(appUserTaskAssign,
                            appUserTaskInvite);

                    jsonObjUser.put("updateTaskStatus", "true");
                    jsonObjUser.put("taskStatus", updateTaskStatus.getTaskStatus());
                    jsonObjData.put("userData", jsonObjUser);
                    json.put("data", jsonObjData);
                    json.put("status", CommonConstants.SUCCESS);
                    json.put("errorMessage", "null");
                }

                else
                {
                    jsonObjUser.put("updateTaskStatus", "true");
                    jsonObjUser.put("taskStatus", updateTaskStatus.getTaskStatus());
                    jsonObjData.put("userData", jsonObjUser);
                    json.put("data", jsonObjData);
                    json.put("status", CommonConstants.SUCCESS);
                    json.put("errorMessage", "null");
                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * This method deleteTask delete the Task of user.
     * 
     */
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deleteTask")
    @Transactional
    public Response deleteTask(JSONObject formpaparam) throws Exception
    {
        AppUserTaskBean appUserTaskBean = new AppUserTaskBean();
        AppUserTask appUserTask = new AppUserTask();
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();

        try
        {
            String output = formpaparam.getString("paparam");
            JSONObject jObject = new JSONObject(output);

            JSONObject delJsonObject = jObject.getJSONObject("deleteTask");
            Integer userId = delJsonObject.getInt("userId");
            String userTaskId = delJsonObject.getString("taskId");
            Integer taskStatus = delJsonObject.getInt("taskStatus");

            String token = formpaparam.getString("token");

            AppUserToken taUserModel = appUserService.getUserId(token);
            if (taUserModel == null || taUserModel.getUserId() != userId)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }
            appUserTaskBean.setUserId(userId);
            appUserTaskBean.setUserTaskId(userTaskId);
            appUserTaskBean.setTaskStatus(taskStatus);

            boolean isValidRequest = CommonAppUserUtility.checkValidRequestForDeleteTask(appUserTaskBean);
            if (!isValidRequest)
            {
                LOG.error("Invalid DeleteTask Request");
                return Response.status(200).entity(CommonConstants.INVALID_REQUEST).build();
            }
            appUserTask = CommonAppUserUtility.prepareInAppModelFromInAppBeanForDeleteTask(appUserTaskBean);
            if (appUserTask.getTaskStatus() != 0 && appUserTask.getTaskStatus() != 2)
            {
                json.putOpt("errorMessage", CommonConstants.UserTaskCreation.ERROR_MESSAGE_STATUS);
                json.putOpt("status", CommonConstants.UserTaskCreation.ERROR);
                return Response.status(200).entity(json.toString()).build();
            }

            if (appUserTask != null)
            {
                appUserTask = appUserService.deleteTask(appUserTask);
            }

            if (appUserTask != null)
            {
                jsonObjUser.put("userId", appUserTask.getUserId());
                jsonObjUser.put("userTaskId", appUserTask.getUserTaskId());

            }
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
            if (appUserTask != null)
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

}