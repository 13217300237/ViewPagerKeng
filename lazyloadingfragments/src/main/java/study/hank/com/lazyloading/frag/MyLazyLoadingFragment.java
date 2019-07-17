package study.hank.com.lazyloading.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import study.hank.com.lazyloading.R;
import study.hank.com.lazyloading.activity.AnotherActivity;


public class MyLazyLoadingFragment extends BaseLazyLoadingFragment {

    private Button tv;
    private String textArg;
    private int index;
    private CountDownTimer countDownTimer;

    public static MyLazyLoadingFragment newInstance(String text, int index) {
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putInt("index", index);
        MyLazyLoadingFragment fragment = new MyLazyLoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getLogTag() {
        if (textArg == null) textArg = getArguments().getString("text");
        return "LazyLog_" + textArg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initView(View root) {
        mRoot = root;
        index = getArguments().getInt("index");
        tv = root.findViewById(R.id.tv);
    }

    long totalTime = 1000L;
    long interval = 100L;

    private Map<Integer, Integer> colors;

    private void initColors() {
        colors = new HashMap<>();
        colors.put(0, getResources().getColor(android.R.color.holo_red_dark));
        colors.put(1, getResources().getColor(android.R.color.holo_green_dark));
        colors.put(2, getResources().getColor(android.R.color.holo_blue_light));
        colors.put(3, getResources().getColor(android.R.color.holo_orange_light));
        colors.put(4, getResources().getColor(android.R.color.holo_purple));
    }

    private void initTimer() {
        countDownTimer = new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(final long millisUntilFinished) {
                tv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("onFragmentResume \n执行中，剩余 :" + millisUntilFinished + " ms");
                    }
                }, 0);
            }

            @Override
            public void onFinish() {
                tv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("onFragmentResume \n执行完毕 " + textArg);
                    }
                }, 0);
            }
        };
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initColors();//对全局变量进行初始化，这里是颜色Map
        initTimer();//定时器初始化，这些东西都不会涉及到UI操作
    }

    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        //UI操作
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AnotherActivity.class);
                startActivity(i);
            }
        });

        mRoot.setBackgroundColor(colors.get(index));
        if (countDownTimer != null)
            countDownTimer.start();
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
        //耗时任务暂停
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
