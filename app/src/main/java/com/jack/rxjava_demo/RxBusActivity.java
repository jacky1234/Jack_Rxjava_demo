package com.jack.rxjava_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jack.rxjava_demo.tools.RxBus;

import rx.Observer;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacky on 2016/8/29.
 */
public class RxBusActivity extends AppCompatActivity {
    private final String TAG = RxBusActivity.class.getSimpleName();

    private CompositeSubscription allSubscription = new CompositeSubscription();
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new OneEvent("hello bus"));
            }
        });

        allSubscription.add(RxBus.getDefault()
                .toObserverable(OneEvent.class).subscribe(new Action1<OneEvent>() {
                    @Override
                    public void call(OneEvent oneEvent) {
                        Toast.makeText(RxBusActivity.this, oneEvent.msg, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (allSubscription != null && !allSubscription.isUnsubscribed())
            allSubscription.unsubscribe();
    }

    class OneEvent {
        // some data you need ...
        String msg;

        public OneEvent(String msg) {
            this.msg = msg;
        }
    }
}