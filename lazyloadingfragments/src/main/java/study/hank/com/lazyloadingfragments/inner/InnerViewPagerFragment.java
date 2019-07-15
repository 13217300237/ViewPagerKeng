package study.hank.com.lazyloadingfragments.inner;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import study.hank.com.lazyloadingfragments.R;
import study.hank.com.lazyloadingfragments.base.BaseLazyLoadingFragment;

/**
 *  内层Fragment，带有嵌套ViewPager
 */
public class InnerViewPagerFragment extends BaseLazyLoadingFragment {

    private String textArg;
    private ViewPager vpInner;
    private TabLayout tabLayoutInner;
    private Activity activity;

    public static InnerViewPagerFragment newInstance(Activity activity, String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        InnerViewPagerFragment fragment = new InnerViewPagerFragment();
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
        return R.layout.fragment_item_inner_viewpager;
    }

    @Override
    protected void initView(View root) {
        vpInner = root.findViewById(R.id.vpInner);
        tabLayoutInner = root.findViewById(R.id.tabLayoutInner);
        List<MyAdapterInner.Data> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new MyAdapterInner.Data("inner " + i));
        }
        MyAdapterInner adapter = new MyAdapterInner(getContext(), getChildFragmentManager(), data);
        vpInner.setAdapter(adapter);
        tabLayoutInner.setupWithViewPager(vpInner);


    }

    @Override
    protected void onFragmentFirstVisible() {
        Log.d(getLogTag() + "_onFragment", "首次加载,初始必要全局参数:" + Thread.currentThread().getName());
    }

    @Override
    protected void onFragmentResume() {
        Log.d(getLogTag() + "_onFragment", "页面onResume,加载最新数据:");

    }

    @Override
    protected void onFragmentPause() {
        Log.d(getLogTag() + "_onFragment", "页面暂停,中断加载数据的所有操作，避免造成资源浪费，避免造成页面卡顿:"
                + "\n ======================================================================================================");
    }
}
