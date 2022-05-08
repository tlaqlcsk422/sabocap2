package com.example.registerloginexample;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.165/Register.php";
    private Map<String, String> map;
    int num = 1000;


    public RegisterRequest(String userID, String userPassword, String userName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("sensor_a1",num+"");
        map.put("sensor_a2", num+"");
        map.put("sensor_a3", num+"");
        map.put("sensor_g1",num+"");
        map.put("sensor_g2", num+"");
        map.put("sensor_g3", num+"");
        map.put("sensor_z1",num+"");
        map.put("sensor_z2", num+"");
        map.put("sensor_z3", num+"");
//        map.put("userAge", userAge + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
