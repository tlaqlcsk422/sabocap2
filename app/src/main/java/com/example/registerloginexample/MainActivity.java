package com.example.registerloginexample;

import static android.hardware.SensorManager.SENSOR_DELAY_UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.Volley;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView gyro_x11,gyro_x22,gyro_x33,gyro_y11,gyro_y22,gyro_y33,gyro_z11,gyro_z22,gyro_z33,acc_x11,acc_x22,acc_x33,acc_y11,acc_y22,acc_y33,acc_z11,acc_z22,acc_z33;
    private Button btn_blue, btn_gyro2;
    private SensorManager mSensorManager = null;

    private SensorEventListener mGyroLis;
    private SensorEventListener mAccLis;
    private Sensor mGgyroSensor = null;
    private Sensor mAccelometerSensor = null;
    double start = System.currentTimeMillis(); // start = 시작시간 - 1970년

    private double pitch;
    private double roll;
    private double yaw;


    private double gyro_x1=0;
    private double gyro_y1=0;
    private double gyro_z1=0;

    private double gyro_x2=0;
    private double gyro_y2=0;
    private double gyro_z2=0;

    private double gyro_x3=0;
    private double gyro_y3=0;
    private double gyro_z3=0;

    private double acc_x1=0;
    private double acc_y1=0;
    private double acc_z1=0;

    private double acc_x2=0;
    private double acc_y2=0;
    private double acc_z2=0;

    private double acc_x3=0;
    private double acc_y3=0;
    private double acc_z3=0;



    private int num=0;
    private int num2 =0;

    private double timestamp;
    private double dt;

    private double RAD2DGR = 180/ Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;

    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyro_x11 = findViewById(R.id.gyro_x1);
        gyro_x22 = findViewById(R.id.gyro_x2);
        gyro_x33 = findViewById(R.id.gyro_x3);

        gyro_y11 = findViewById(R.id.gyro_y1);
        gyro_y22 = findViewById(R.id.gyro_y2);
        gyro_y33 = findViewById(R.id.gyro_y3);

        gyro_z11 = findViewById(R.id.gyro_z1);
        gyro_z22 = findViewById(R.id.gyro_z2);
        gyro_z33 = findViewById(R.id.gyro_z3);

        acc_x11 = findViewById(R.id.acc_x1);
        acc_x22 = findViewById(R.id.acc_x2);
        acc_x33 = findViewById(R.id.acc_x3);

        acc_y11 = findViewById(R.id.acc_y1);
        acc_y22 = findViewById(R.id.acc_y2);
        acc_y33 = findViewById(R.id.acc_y3);

        acc_z11 = findViewById(R.id.acc_z1);
        acc_z22 = findViewById(R.id.acc_z2);
        acc_z33 = findViewById(R.id.acc_z3);


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

        btn_gyro2 = (Button) findViewById(R.id.btn_gyro2);
        btn_gyro2.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        num = 0;
                        num2 = 0;
                        start = System.currentTimeMillis();
                        mSensorManager.registerListener(mGyroLis, mGgyroSensor, SENSOR_DELAY_UI);
                        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SENSOR_DELAY_UI);
                        gyro_x11.setText(String.format("%.4f",gyro_x1));
                        gyro_x22.setText(String.format("%.4f",gyro_x2));
                        gyro_x33.setText(String.format("%.4f",gyro_x3));

                        gyro_y11.setText(String.format("%.4f",gyro_y1));
                        gyro_y22.setText(String.format("%.4f",gyro_y2));
                        gyro_y33.setText(String.format("%.4f",gyro_y3));

                        gyro_z11.setText(String.format("%.4f",gyro_z1));
                        gyro_z22.setText(String.format("%.4f",gyro_z2));
                        gyro_z33.setText(String.format("%.4f",gyro_z3));

                        acc_x11.setText(String.format("%.4f",acc_x1));
                        acc_x22.setText(String.format("%.4f",acc_x2));
                        acc_x33.setText(String.format("%.4f",acc_x3));

                        acc_y11.setText(String.format("%.4f",acc_y1));
                        acc_y22.setText(String.format("%.4f",acc_y2));
                        acc_y33.setText(String.format("%.4f",acc_y3));

                        acc_z11.setText(String.format("%.4f",acc_z1));
                        acc_z22.setText(String.format("%.4f",acc_z2));
                        acc_z33.setText(String.format("%.4f",acc_z3));

                        break;

                    case MotionEvent.ACTION_UP:
                        mSensorManager.unregisterListener(mGyroLis);
                        mSensorManager.unregisterListener(mAccLis);

                        break;
                }
                return false;
            }
        });
//        btn_gyro2.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event){
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        num = 0;
//                        num2 = 0;
//                        start = System.currentTimeMillis(); // start = 시작시간 - 1970년
//                        mSensorManager.registerListener(mGyroLis, mGgyroSensor,SensorManager.SENSOR_DELAY_UI);
//                        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SENSOR_DELAY_UI);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        mSensorManager.unregisterListener(mGyroLis);
//                        mSensorManager.unregisterListener(mAccLis);
//                        break;
//                }
//                return false;
//            }
//        });

        // 회원가입 버튼 클릭 시 수행
        btn_blue = (Button) findViewById(R.id.btn_blue);
        btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"ggg",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);

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


            double end; // end = 종료시간 - 1970년
            double current;

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
                end = System.currentTimeMillis(); // end = 종료시간 - 1970년
                current = end-start;
                if(num2==2 && current> 2000 && current < 3000){
                    gyro_x3 = gyroX;
                    gyro_y3 = gyroY;
                    gyro_z3 = gyroZ;
                    num2++;
                }
                else if(num2==1 && current > 1000 && current < 2000){
                    gyro_x2 = gyroX;
                    gyro_y2 = gyroY;
                    gyro_z2 = gyroZ;
                    num2++;
                }
                else if(num2 == 0 && current > 0 && current < 1000){
                    gyro_x1 = gyroX;
                    gyro_y1 = gyroY;
                    gyro_z1 = gyroZ;
                    num2++;
                }
                Toast.makeText(getApplicationContext(), current+" 1:"+String.format("%.4f",gyro_x1) +" 2:"+ String.format("%.4f",gyro_x2) +" 3:"+ String.format("%.4f",gyro_x3)+"",Toast.LENGTH_SHORT).show();



//                Toast.makeText(getApplicationContext(), timestamp+"X:"+String.format("%.4f",gyroX)+" Y:"+String.format("%.4f",gyroY)+" Z:"+String.format("%.4f",gyroZ),Toast.LENGTH_SHORT).show();
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

            double end; // end = 종료시간 - 1970년
            double current;


            double angleXZ = Math.atan2(accX,  accZ) * 180/Math.PI;
            double angleYZ = Math.atan2(accY,  accZ) * 180/Math.PI;

            end = System.currentTimeMillis(); // end = 종료시간 - 1970년
            current = end-start;

            if(num==2 && current> 2000 && current < 3000){
                acc_x3 = accX;
                acc_y3 = accY;
                acc_z3 = accZ;
                num++;
            }
            else if(num==1 && current > 1000 && current < 2000){
                acc_x2 = accX;
                acc_y2 = accY;
                acc_z2 = accZ;
                num++;
            }
            else if(num == 0 && current > 0 && current < 1000){
                acc_x1 = accX;
                acc_y1 = accY;
                acc_z1 = accZ;
                num++;
            }

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

