package study.hank.com.lazyloading.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import study.hank.com.lazyloading.R;
import study.hank.com.lazyloading.frag.MyAdapter;

/**
 * Fragment+ViewPager懒加载实现机制
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MyAdapter.Data> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new MyAdapter.Data(i + ""));
        }

        tabLayout = findViewById(R.id.tabLayout);
        vp = findViewById(R.id.vp);
        MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), data);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);
    }
}
