package study.hank.com.lazyloadingfragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 */
public class MyAdapter extends FragmentPagerAdapter {

    private List<Data> dataList;
    private Context context;

    MyAdapter(Context context, FragmentManager fm, List<Data> dataList) {
        super(fm);
        this.context = context;
        this.dataList = dataList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return dataList.get(position).text;
    }

    @Override
    public Fragment getItem(int i) {
        return MyFragment.newInstance(dataList.get(i).text);
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
