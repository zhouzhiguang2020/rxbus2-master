package com.threshold.rxbus2demo.bean.event;

/**
 * Created by .
 * User: ASUS
 * Date: 2020/11/17
 * Time: 11:52
 */
public class TestEvent1 {
    private String msg;

    public TestEvent1(String msg) {
        this.msg = msg;
    }

    public TestEvent1() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
