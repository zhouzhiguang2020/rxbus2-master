package com.threshold.rxbus2demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.threshold.rxbus2.ReplayBus;
import com.threshold.rxbus2demo.util.RandomUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;




/**
 * Demo for showing {@link ReplayBus} usage.
 * Created by threshold on 2017/1/19.
 */

public class ReplayBusActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ReplayBus");
        setContentView(R.layout.activity_replay_bus);
        textView = findViewById(R.id.text);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFirstSubscription:
                listenStringEvent("First Subscription");
                v.setEnabled(false);
                break;
            case R.id.btnAddSecondSubscription:
                listenStringEvent("Second Subscription");
                v.setEnabled(false);
                break;
            case R.id.btnFireEvent:

                ReplayBus.getDefault().post("ReplayBus"+ RandomUtil.random(100));
                break;
        }
    }

    private void listenStringEvent(final String tag) {
        Disposable subscribe = ReplayBus.getDefault()
                .ofType(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        String text = "{ [" + tag + "]:" + s+" }";
                        textView.append(text);
                        textView.append("\n");
                        Logger.d(text);
                    }
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
