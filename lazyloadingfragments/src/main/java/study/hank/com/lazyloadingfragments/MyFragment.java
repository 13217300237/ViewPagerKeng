package study.hank.com.lazyloadingfragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyFragment extends BaseLazyLoadingFragment {


    private Button tv;
    private String textArg;

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

    @Override
    protected void onFragmentFirstVisible() {
        Log.d(getLogTag(), "更新UI:" + Thread.currentThread().getName());
    }

    @Override
    protected void onFragmentResume() {
        Log.d(getLogTag(), "页面恢复:");
        tv.setText(textArg);
    }

    @Override
    protected void onFragmentPause() {
        Log.d(getLogTag(), "页面暂停:");
    }
}
