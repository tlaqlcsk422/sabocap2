package com.example.registerloginexample;

import static android.hardware.SensorManager.SENSOR_DELAY_UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_age;
    private Button btn_register;
    private SensorManager mSensorManager = null;

    private SensorEventListener mGyroLis;
    private SensorEventListener mAccLis;
    private Sensor mGgyroSensor = null;
    private Sensor mAccelometerSensor = null;

    private double pitch;
    private double roll;
    private double yaw;

    private double timestamp;
    private double dt;

    private double RAD2DGR = 180/ Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;

    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);

        UserDB databaseUser = Room.databaseBuilder(getApplicationContext(), UserDB.class, "User_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        userDao = databaseUser.userDao();
        List<User> userList = userDao.getUserDBAll();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mGgyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mGyroLis = new GyroscopeListener();

        mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccLis = new AccelometerListener();

        findViewById(R.id.btn_gyro).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mSensorManager.registerListener(mGyroLis, mGgyroSensor,SensorManager.SENSOR_DELAY_UI);
                        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SENSOR_DELAY_UI);
                        break;

                    case MotionEvent.ACTION_UP:
                        mSensorManager.unregisterListener(mGyroLis);
                        mSensorManager.unregisterListener(mAccLis);
                        break;
                }
                return false;
            }
        });

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                int num = userList.size()+1;
                Log.d("num", String.valueOf(num));
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                Integer userAge = Integer.parseInt(et_age.getText().toString());

                User user = new User(userID, userPass, userAge, userName);
                boolean success = false;
                boolean empty = true;
                for(int i = 0; i<userList.size(); i++) {
                    if (userList.get(i).getEmail().equals(userID)) {
                        Toast.makeText(getApplicationContext(), "이미 존재하는 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        empty = false;
                    }
                }
                Log.d("empty", String.valueOf(empty));
//                if(!empty)
//                    userDao.setInsertUser(user);
                Log.d("num",String.valueOf(num));
                Log.d("userList", String.valueOf(userList.size()));
                if(empty){
                    userDao.setInsertUser(user);
                    Toast.makeText(getApplicationContext(),"회원 등록 성공",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "회원 등록 실패",Toast.LENGTH_SHORT).show();
                    return;
                }

//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        User user = new User(userID, userPass, userAge, userName);
//                        userDao.setInsertUser(user);
//                        boolean success = false;
//                        if(num == userList.size())
//                            success = true;
//                        System.out.println(success);
////                            JSONObject jsonObject = new JSONObject(response);
////                            boolean success = jsonObject.getBoolean("success");
//                        if (success) { // 회원등록에 성공한 경우
//                            Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        } else { // 회원등록에 실패한 경우
//                            Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                };
                // 서버로 Volley를 이용해서 요청을 함.
//                RegisterRequest registerRequest = new RegisterRequest(userID,userPass,userName, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
//                queue.add(registerRequest);

            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.e("LOG", "onPause()");
        mSensorManager.unregisterListener(mGyroLis);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("LOG", "onDestroy()");
        mSensorManager.unregisterListener(mGyroLis);
    }

    private class GyroscopeListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {

            /* 각 축의 각속도 성분을 받는다. */
            double gyroX = event.values[0];
            double gyroY = event.values[1];
            double gyroZ = event.values[2];

            /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
             * dt : 센서가 현재 상태를 감지하는 시간 간격
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
            if (dt - timestamp*NS2S != 0) {

                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
                pitch = pitch + gyroY*dt;
                roll = roll + gyroX*dt;
                yaw = yaw + gyroZ*dt;

                Log.e("LOG", "GYROSCOPE           [X]:" + String.format("%.4f", event.values[0])
                        + "           [Y]:" + String.format("%.4f", event.values[1])
                        + "           [Z]:" + String.format("%.4f", event.values[2])
                        + "           [Pitch]: " + String.format("%.1f", pitch*RAD2DGR)
                        + "           [Roll]: " + String.format("%.1f", roll*RAD2DGR)
                        + "           [Yaw]: " + String.format("%.1f", yaw*RAD2DGR)
                        + "           [dt]: " + String.format("%.4f", dt));

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private class AccelometerListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {

            double accX = event.values[0];
            double accY = event.values[1];
            double accZ = event.values[2];

            double angleXZ = Math.atan2(accX,  accZ) * 180/Math.PI;
            double angleYZ = Math.atan2(accY,  accZ) * 180/Math.PI;

            Log.e("LOG", "ACCELOMETER           [X]:" + String.format("%.4f", event.values[0])
                    + "           [Y]:" + String.format("%.4f", event.values[1])
                    + "           [Z]:" + String.format("%.4f", event.values[2])
                    + "           [angleXZ]: " + String.format("%.4f", angleXZ)
                    + "           [angleYZ]: " + String.format("%.4f", angleYZ));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}

