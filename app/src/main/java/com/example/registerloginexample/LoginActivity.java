package com.example.registerloginexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

//import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login;
    private TextView btn_register;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        UserDB databaseUser = Room.databaseBuilder(getApplicationContext(), UserDB.class, "User_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        userDao = databaseUser.userDao();
        List<User> userList = userDao.getUserDBAll();


        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                boolean success = false;
                for (int i = 0; i < userList.size(); i++) {
                    if (userID.equals(userList.get(i).getEmail()) && userPass.equals(userList.get(i).getPassword()))
                        success = true;
                }
                if (success) {
                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("userPass", userPass);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            boolean success = jsonObject.getBoolean("success");
//                            if (success) { // 로그인에 성공한 경우
//                                String userID = jsonObject.getString("userID");
//                                String userPass = jsonObject.getString("userPassword");
//
//                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                intent.putExtra("userID", userID);
//                                intent.putExtra("userPass", userPass);
//                                startActivity(intent);
//                            } else { // 로그인에 실패한 경우
//                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
            }
        });


    }
}