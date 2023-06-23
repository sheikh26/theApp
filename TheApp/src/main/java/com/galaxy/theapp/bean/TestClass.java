package com.galaxy.theapp.bean;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class TestClass
{

    public static void main(String[] args)
    {
        JSONObject json = new JSONObject();
        JSONObject jsonObjData = new JSONObject();
        JSONObject jsonObjUser = new JSONObject();
        try
        {
            jsonObjUser.put("email", "adfdf@jjj.com");
            jsonObjUser.put("name", "ANKFKF");
            jsonObjData.put("userData", jsonObjUser);
            json.put("data", jsonObjData);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

}
