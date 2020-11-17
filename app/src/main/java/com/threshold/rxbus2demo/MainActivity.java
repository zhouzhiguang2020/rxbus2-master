package com.threshold.rxbus2demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.threshold.rxbus2.RxBus;
import com.threshold.rxbus2demo.bean.event.TestEvent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRxBus:
                startActivity(RxBusActivity.class);
                break;
            case R.id.btnBehaviorBus:
                startActivity(BehaviorBusActivity.class);
                break;
            case R.id.btnReplayBus:
                startActivity(ReplayBusActivity.class);
                break;
            case R.id.mytest:
                TestEvent event = new TestEvent();
                event.setMsg("我要跳转了");
                RxBus.getDefault().postSticky(event);
                startActivity(SecondActivity.class);
                break;
            default:
                break;

        }
    }

    private void startActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}
