package com.xiteb.swclb.extention

import org.json.JSONObject
import retrofit2.HttpException


fun HttpException.getResponseError(text: String): String {

    return JSONObject(text).getString("message")

}