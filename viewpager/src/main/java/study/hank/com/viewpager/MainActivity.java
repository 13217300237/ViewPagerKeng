package study.hank.com.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.vp);

        List<Data> list = new ArrayList<>();
        list.add(new Data("1"));
        list.add(new Data("2"));
        list.add(new Data("3"));
        vp.setAdapter(new MyPagerAdapter(this, list));
    }

    class MyPagerAdapter extends PagerAdapter {

        private Context context;
        private List<Data> dataList;

        public MyPagerAdapter(Context context, List<Data> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View root = LayoutInflater.from(context).inflate(R.layout.vp_item, container, false);
            TextView tv = root.findViewById(R.id.tv);
            tv.setText(dataList.get(position).text);
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

    }

    private class Data {
        String text;

        public Data(String text) {
            this.text = text;
        }
    }
}
