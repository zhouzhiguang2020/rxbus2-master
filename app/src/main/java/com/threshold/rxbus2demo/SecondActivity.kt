package com.threshold.rxbus2demo


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.threshold.rxbus3.RxBus
import com.threshold.rxbus3.annotation.RxSubscribe
import com.threshold.rxbus3.util.EventThread
import com.threshold.rxbus2demo.bean.event.TestEvent
import com.threshold.rxbus2demo.bean.event.TestEvent1
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
        msg.text = event.msg
        Log.e("测试", event.msg)
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN, isSticky = false)
    @SuppressWarnings("unused")
    fun RxEvent(event: TestEvent1) {
        msg.text = event.msg
        Log.e("测试==", event.msg)
    }


    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }
}