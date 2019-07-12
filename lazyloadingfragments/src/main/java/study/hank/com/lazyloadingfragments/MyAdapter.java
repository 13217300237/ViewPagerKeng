package study.hank.com.lazyloadingfragments;

import android.content.Context;
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

    public MyAdapter(Context context, FragmentManager fm, List<Data> dataList) {
        super(fm);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return Fragment001.newInstance(dataList.get(i).text);
            case 1:
                return Fragment002.newInstance(dataList.get(i).text);
            case 2:
                return Fragment003.newInstance(dataList.get(i).text);
            case 3:
                return Fragment004.newInstance(dataList.get(i).text);
            default:
                break;
        }
        return null;
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
