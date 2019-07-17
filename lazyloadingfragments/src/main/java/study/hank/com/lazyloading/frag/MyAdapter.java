package study.hank.com.lazyloading.frag;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ViewPager适配器 外层
 */
public class MyAdapter extends FragmentPagerAdapter {

    private boolean ifUseInnerViewPager = false;

    private List<Data> dataList;
    private Activity activity;

    public MyAdapter(Activity activity, FragmentManager fm, List<Data> dataList) {
        super(fm);
        this.activity = activity;
        this.dataList = dataList;
    }

    /**
     * 是否使用内嵌的ViewPager+Fragment
     *
     * @param ifUseInnerViewPager
     */
    public void setIfUseInnerViewPager(boolean ifUseInnerViewPager) {
        this.ifUseInnerViewPager = ifUseInnerViewPager;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return dataList.get(position).text;
    }

    @Override
    public Fragment getItem(int i) {
        if (ifUseInnerViewPager) {
            //把第二个变成带内嵌Viewpager的fragment
            if (i == 1) {
                return InnerViewPagerFragment.newInstance(activity, dataList.get(i).text);
            }
        }
        //其他的还是不带内嵌的
        return MyLazyLoadingFragment.newInstance(dataList.get(i).text, i);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public static class Data {
        String text;

        public Data(String text) {
            this.text = text;
        }
    }
}
