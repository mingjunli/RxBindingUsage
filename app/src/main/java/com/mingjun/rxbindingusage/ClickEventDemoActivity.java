package com.mingjun.rxbindingusage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class ClickEventDemoActivity extends AppCompatActivity {

    private static final String TAG = "ClickEventDemo";

    private Button mPreventMultipleClicksBtn;
    private Button mPreventMultipleClicksBtn2;
    private Button mPreventMultipleClicksBtn3;
    private Button mDelayResponseBtn;
    private Button mClickCountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_event_demo);

        initViews();
    }

    private void initViews() {
        mPreventMultipleClicksBtn = (Button) findViewById(R.id.prevent_multiple_clicks_btn);
        // 1s内的点击只响应第一次
        RxView.clicks(mPreventMultipleClicksBtn).throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Log.d(TAG, "Just response the first click event during 1s.");
                    }
                });

        mPreventMultipleClicksBtn2 = (Button) findViewById(R.id.prevent_multiple_clicks_btn2);
        // 1s内的点击只响应最后一次
        RxView.clicks(mPreventMultipleClicksBtn2).throttleLast(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Log.d(TAG, "Just response the last click event during 1s.");
                    }
                });

        mPreventMultipleClicksBtn3 = (Button) findViewById(R.id.prevent_multiple_clicks_btn3);
        // Debounce (防抖动), 对于连续点击A和B, 只有其中间隔超过1s时, 才响应A的事件.
        RxView.clicks(mPreventMultipleClicksBtn3).debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Log.d(TAG, "Response while clicks interval more than 1s");
                    }
                });

        mDelayResponseBtn = (Button) findViewById(R.id.delay_response_btn);
        // 延迟1s响应
        RxView.clicks(mDelayResponseBtn)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Log.d(TAG, "Response the click after 1s. response time = " + System.currentTimeMillis());
                    }
                });

        mClickCountBtn = (Button) findViewById(R.id.click_count_btn);
        RxView.clicks(mClickCountBtn).count()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "Click count = " + integer);
                    }
                });
    }
}
