package study.hank.com.lazyloading.frag;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import study.hank.com.lazyloading.R;


/**
 * 内层Fragment，带有嵌套ViewPager
 */
public class InnerViewPagerFragment extends BaseLazyLoadingFragment {

    private String textArg;
    private ViewPager vpInner;
    private TabLayout tabLayoutInner;
    private Activity activity;
    private ArrayList<MyInnerAdapter.Data> data;
    private MyInnerAdapter adapter;

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
        initAdapter();
    }

    private void initAdapter() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new MyInnerAdapter.Data("inner " + i));
        }
        adapter = new MyInnerAdapter(getContext(), getChildFragmentManager(), data);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
    }

    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        vpInner.setAdapter(adapter);
        tabLayoutInner.setupWithViewPager(vpInner);
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
    }
}
