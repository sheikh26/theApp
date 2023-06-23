/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */

package com.galaxy.theapp.common.utility;

/**
 * This <java>class</java> CommonConstants is use to set some generic
 * CommonConstants.
 * 
 * @author param Sheikh
 * @GWL
 */

public class CommonConstants
{
    public static final String ERROR = "error";

    public static final String SUCCESS = "success";

    public static final String LOGIN = "login";

    public static final String INVALID_REQUEST = "Invalid Request";

    public static class UserLogin
    {
        public static final String ERROR_MESSAGE = "invalid Email-Id and Password";

        public static final String UPDATE_PROFILE = "invalid Email-Id and Password";

        public static final String SUCCESS = "success";

        public static final String ERROR = "error";

        public static final String NO_ERROR = "NO Error";
    }

    public static class UserRegistration
    {

        public static final String ERROR_MESSAGE = "Invalid email-Id or password";
        public static final String ERROR = "error";

        public static final String EXIST_EMAIL_ID = "Email Id Already Exist";

        public static final String NO_ERROR = "NO Error";

        public static final String SUCCESS = "success";

        public static final String FAILURE = "error";
    }

    public static class UpdateProfile
    {
        public static final String SUCCESS = "success";

        public static final String ERROR_MESSAGE = "Invalid token";

        public static final String ERROR_MESSAGE_EMAIL = "This email id is already exist";

        public static final String ERROR = "error";
    }

    public static class GetUserList
    {
        public static final String SUCCESS = "success";

        public static final String ERROR_MESSAGE = "Invalid token";

        public static final String ERROR_MESSAGE_NULL = "There is no records";

        public static final String ERROR = "error";

    }

    public static class getProfile
    {
        public static final String SUCCESS = "success";

        public static final String ERROR_MESSAGE = "Invalid token";

        public static final String ERROR = "error";
    }

    public static class getTaskList
    {
        public static final String SUCCESS = "success";

        public static final String ERROR_MESSAGE = "No Record Found";

        public static final String ERROR = "error";
    }

    public static class UserTaskCreation
    {
        public static final String SUCCESS = "success";

        public static final String ERROR = "error";

        public static final String ERROR_MESSAGE = "Invalid token";

        public static final String ERROR_MESSAGE_COIN = "Sorry, you don't have sufficient coin balance!";

        public static final Integer Task_Status_Open = 0;

        public static final String ERROR_MESSAGE_STATUS = "Invalid task Status for delete";

        public static final String Exist_Invitee = "You have already invited for this user, Please change invitee!";

    }

    public static class changePassword
    {
        public static final String SUCCESS = "success";

        public static final String ERROR = "error";

        public static final String ERROR_MESSAGE = "Invalid token";

        public static final String oldPassword = "Old PassWord does not match,plz Try again";

        public static final String samePassword = "Old PassWord and new password do not same";

        public static final String newPassWord = "New PassWord and confirm password does not match,plz Try again";

    }

    public static class ForgetPassword
    {
        public static final String SUCCESS = "success";

        public static final String ERROR = "error";

        public static final String ERROR_MESSAGE = "Invalid Email-Id";

        public static final Integer IS_OTP_ZERO = 0;

        public static final Integer IS_OTP_ONE = 1;

    }

    public static class UpdateTaskStatus
    {

        public static final Integer Open_Task = 0;

        public static final Integer Accepted_By_Invitee = 1;

        public static final Integer Rejected_By_Invitee = 2;

        public static final Integer Accepted_By_Share = 3;

        public static final Integer Rejected_By_Share = 4;

        public static final Integer Completed_By_Invitee = 5;

        public static final Integer Completed_By_Share = 6;

        public static final Integer Pending_Share_Invitee = 7;

    }

    public static class UserConstants
    {
        public static final Integer ACTIVE_USER = 1;

        public static final Integer INACTIVE_USER = 0;
    }

    public static class DeleteTask
    {
        public static final Integer ACTIVE_TASK = 1;

        public static final Integer INACTIVE_TASK = 0;
    }

    public static class TransactionAmountType
    {
        public static final Integer CREDITED_AMOUNT = 1;

        public static final Integer DEBITED_AMOUNT = 0;
    }

}
