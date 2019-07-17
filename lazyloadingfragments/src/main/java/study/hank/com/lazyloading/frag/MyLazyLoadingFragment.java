package study.hank.com.lazyloading.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onFragmentFirstVisible() {
        Log.d(getCustomMethodTag() + "_onFragment", "首次加载,初始必要全局参数:" + Thread.currentThread().getName());
    }

    @Override
    protected void onFragmentResume() {
        Log.d(getCustomMethodTag() + "_onFragment", "页面onResume,加载最新数据:");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AnotherActivity.class);
                startActivity(i);
            }
        });

        int color;
        //根据参数改变颜色
        switch (index) {
            case 0:
                color = getResources().getColor(android.R.color.holo_red_dark);
                break;
            case 1:
                color = getResources().getColor(android.R.color.holo_green_dark);
                break;
            case 2:
                color = getResources().getColor(android.R.color.holo_blue_light);
                break;
            case 3:
                color = getResources().getColor(android.R.color.holo_orange_light);
                break;
            case 4:
                color = getResources().getColor(android.R.color.holo_purple);
                break;
            default:
                color = getResources().getColor(android.R.color.white);
                break;
        }
        mRoot.setBackgroundColor(color);

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
        countDownTimer.start();
    }

    @Override
    protected void onFragmentPause() {
        Log.d(getCustomMethodTag() + "_onFragment", "页面暂停,中断加载数据的所有操作，避免造成资源浪费，避免造成页面卡顿:"
                + "\n ======================================================================================================");
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
