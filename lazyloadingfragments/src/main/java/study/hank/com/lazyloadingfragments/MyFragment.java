package study.hank.com.lazyloadingfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyFragment extends BaseLazyLoadingFragment {


    private Button tv;
    private String textArg;
    private CountDownTimer countDownTimer;

    public static MyFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getLogTag() {
        if (textArg == null) textArg = getArguments().getString("text");
        return "LazyLog:" + textArg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initView(View root) {
        tv = root.findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AnotherActivity.class);
                startActivity(i);
            }
        });
    }

    long totalTime = 1000L;
    long interval = 100L;

    @Override
    protected void onFragmentFirstVisible() {
        Log.d(getLogTag() + "_onFragment", "首次加载,初始必要全局参数:" + Thread.currentThread().getName());
    }

    @Override
    protected void onFragmentResume() {
        Log.d(getLogTag() + "_onFragment", "页面onResume,加载最新数据:");
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
        Log.d(getLogTag() + "_onFragment", "页面暂停,中断加载数据的所有操作，避免造成资源浪费，避免造成页面卡顿:"
                + "\n ======================================================================================================");
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
