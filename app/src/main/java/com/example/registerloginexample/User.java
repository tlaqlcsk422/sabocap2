package com.example.registerloginexample;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int key = 0;

    private String email;

    private String password;

    private int age;

    private String name;


    private double gyro_x1=0,gyro_y1=0,gyro_z1=0,gyro_x2=0,gyro_y2=0,gyro_z2=0,gyro_x3=0,gyro_y3=0,gyro_z3=0;

    private double acc_x1=0,acc_y1=0,acc_z1=0,acc_x2=0,acc_y2=0,acc_z2=0,acc_x3=0,acc_y3=0,acc_z3=0;

    @Ignore
    User(String userID, String userPass, Integer userAge, String userName, android.hardware.Sensor sensor){

    }

    User(String email, String password, int age, String name, double gyro_x1, double gyro_x2, double gyro_x3, double gyro_y1, double gyro_y2, double gyro_y3, double gyro_z1, double gyro_z2, double gyro_z3, double acc_x1, double acc_x2, double acc_x3, double acc_y1, double acc_y2, double acc_y3, double acc_z1, double acc_z2, double acc_z3){
        this.email = email;
        this.password = password;
        this.age = age;
        this.name = name;
        this.gyro_x1 = gyro_x1;
        this.gyro_x2 = gyro_x2;
        this.gyro_x3 = gyro_x3;
        this.gyro_y1 = gyro_y1;
        this.gyro_y2 = gyro_y2;
        this.gyro_y3 = gyro_y3;
        this.gyro_z1 = gyro_z1;
        this.gyro_z2 = gyro_z2;
        this.gyro_z3 = gyro_z3;
        this.acc_x1 = acc_x1;
        this.acc_x2 = acc_x2;
        this.acc_x3 = acc_x3;
        this.acc_y1 = acc_y1;
        this.acc_y2 = acc_y2;
        this.acc_y3 = acc_y3;
        this.acc_z1 = acc_z1;
        this.acc_z2 = acc_z2;
        this.acc_z3 = acc_z3;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


        public double getGyro_x1() {
        return gyro_x1;
    }

    public void setGyro_x1(double gyro_x1) {
        this.gyro_x1 = gyro_x1;
    }

    public double getGyro_y1() {
        return gyro_y1;
    }

    public void setGyro_y1(double gyro_y1) {
        this.gyro_y1 = gyro_y1;
    }

    public double getGyro_z1() {
        return gyro_z1;
    }

    public void setGyro_z1(double gyro_z1) {
        this.gyro_z1 = gyro_z1;
    }

    public double getGyro_x2() {
        return gyro_x2;
    }

    public void setGyro_x2(double gyro_x2) {
        this.gyro_x2 = gyro_x2;
    }

    public double getGyro_y2() {
        return gyro_y2;
    }

    public void setGyro_y2(double gyro_y2) {
        this.gyro_y2 = gyro_y2;
    }

    public double getGyro_z2() {
        return gyro_z2;
    }

    public void setGyro_z2(double gyro_z2) {
        this.gyro_z2 = gyro_z2;
    }

    public double getGyro_x3() {
        return gyro_x3;
    }

    public void setGyro_x3(double gyro_x3) {
        this.gyro_x3 = gyro_x3;
    }

    public double getGyro_y3() {
        return gyro_y3;
    }

    public void setGyro_y3(double gyro_y3) {
        this.gyro_y3 = gyro_y3;
    }

    public double getGyro_z3() {
        return gyro_z3;
    }

    public void setGyro_z3(double gyro_z3) {
        this.gyro_z3 = gyro_z3;
    }

    public double getAcc_x1() {
        return acc_x1;
    }

    public void setAcc_x1(double acc_x1) {
        this.acc_x1 = acc_x1;
    }

    public double getAcc_y1() {
        return acc_y1;
    }

    public void setAcc_y1(double acc_y1) {
        this.acc_y1 = acc_y1;
    }

    public double getAcc_z1() {
        return acc_z1;
    }

    public void setAcc_z1(double acc_z1) {
        this.acc_z1 = acc_z1;
    }

    public double getAcc_x2() {
        return acc_x2;
    }

    public void setAcc_x2(double acc_x2) {
        this.acc_x2 = acc_x2;
    }

    public double getAcc_y2() {
        return acc_y2;
    }

    public void setAcc_y2(double acc_y2) {
        this.acc_y2 = acc_y2;
    }

    public double getAcc_z2() {
        return acc_z2;
    }

    public void setAcc_z2(double acc_z2) {
        this.acc_z2 = acc_z2;
    }

    public double getAcc_x3() {
        return acc_x3;
    }

    public void setAcc_x3(double acc_x3) {
        this.acc_x3 = acc_x3;
    }

    public double getAcc_y3() {
        return acc_y3;
    }

    public void setAcc_y3(double acc_y3) {
        this.acc_y3 = acc_y3;
    }

    public double getAcc_z3() {
        return acc_z3;
    }

    public void setAcc_z3(double acc_z3) {
        this.acc_z3 = acc_z3;
    }
}
