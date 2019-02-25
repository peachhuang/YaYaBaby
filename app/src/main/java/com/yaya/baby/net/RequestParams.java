package com.yaya.baby.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : Darcy
 * @package com.yaya.baby.net
 * @Date 2018-11-7 18:45
 * @Description
 */
public class RequestParams {

    public static String buildParams(JSONObject param) {
        JSONObject contentJson = new JSONObject();
        try {
            contentJson.put("params", param);
        } catch (JSONException e) {

        }
        String string = contentJson.toString();
        return string;
    }
}
