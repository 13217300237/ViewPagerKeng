package study.hank.com.lazyloadingfragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment+ViewPager懒加载实现机制
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MyAdapter.Data> data = new ArrayList<>();
        data.add(new MyAdapter.Data("1111111111111"));
        data.add(new MyAdapter.Data("2222222222222"));
        data.add(new MyAdapter.Data("3333333333333"));
        data.add(new MyAdapter.Data("4444444444444"));

        vp = findViewById(R.id.vp);
        MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), data);
        vp.setAdapter(adapter);

    }
}
