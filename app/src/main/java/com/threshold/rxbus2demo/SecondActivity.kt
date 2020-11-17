package com.threshold.rxbus2demo


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.threshold.rxbus2.RxBus
import com.threshold.rxbus2.annotation.RxSubscribe
import com.threshold.rxbus2.util.EventThread
import com.threshold.rxbus2demo.bean.event.TestEvent
import kotlinx.android.synthetic.main.activity_second_layout.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_layout)
        RxBus.getDefault().register(this)
    }


    //now we support private method.
    @RxSubscribe(observeOnThread = EventThread.MAIN, isSticky = true)
    @SuppressWarnings("unused")
    fun RxEvent(event: TestEvent) {
        msg.text=event.msg
        Log.e("测试",event.msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }
}