package com.douzi.dd.demo.sqlite;

public class Info {
    String userId;
    int age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Info{" +
                "userId='" + userId + '\'' +
                ", age=" + age +
                '}';
    }
}
