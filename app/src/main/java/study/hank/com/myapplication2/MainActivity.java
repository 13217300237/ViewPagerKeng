package study.hank.com.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import study.hank.com.myapplication2.adapter.CustomViewHolder;
import study.hank.com.myapplication2.adapter.RecyclerViewUniversalAdapter;
import study.hank.com.myapplication2.custom.CustomItemTouchCallback;
import study.hank.com.myapplication2.custom.CustomRvLayoutManager;
import study.hank.com.myapplication2.custom.DataBean;
import study.hank.com.myapplication2.custom.RvAnimationConst;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewUniversalAdapter adapter;
    private TextView tvRes;
    private RecyclerView rv;
    private List<DataBean> dataBeans;
    private CustomItemTouchCallback customItemTouchCallback;
    private ItemTouchHelper touchHelper;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRes = findViewById(R.id.tv_res);
        rv = findViewById(R.id.rv);

        RvAnimationConst.init(this);//初始化相关参数,我纯粹自定义的

        initData();//Rv的数据
        initAdapter();//Rv的适配器
        initItemTouchCallback();//Rv的item触摸反馈事件
        initLayoutManager();//Rv的布局管理器

        // 4个准备工作都做好了
        initRv();//rv初始化
    }

    private void initRv() {
        if (layoutManager == null) throw new RuntimeException("ERROR：layoutManager is null!");
        if (adapter == null) throw new RuntimeException("ERROR：adapter is null!");
        if (touchHelper == null) throw new RuntimeException("ERROR：touchHelper is null!");

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        touchHelper.attachToRecyclerView(rv);//给rv加上滑动事件
    }

    private void initLayoutManager() {
        //子view的排布的实现全都在LayoutManager的实现方法 onChildLayout里
        layoutManager = new CustomRvLayoutManager(this);
    }

    private void initItemTouchCallback() {
        //子view的触摸反馈事件，都在CustomItemTouchCallback中
        customItemTouchCallback = new CustomItemTouchCallback(rv, adapter, dataBeans);
        customItemTouchCallback.setCallback(new CustomItemTouchCallback.LikeOrDislikeCallback() {
            @Override
            public void call(boolean ifLike, String who) {
                tvRes.setText(ifLike ? "喜欢  " + who : "不喜欢  " + who);
            }
        });
        touchHelper = new ItemTouchHelper(customItemTouchCallback);
    }

    private void initData() {
        dataBeans = new ArrayList<>();
        DataBean temp;
        for (int i = 0; i < DataManager.dataCount; i++) {//制造模拟数据
            temp = new DataBean();
            temp.name = DataManager.getGirlName(i);
            temp.index = i;
            dataBeans.add(temp);
        }
    }

    /**
     * 使用万能RvAdapter，来节省代码量
     */
    private void initAdapter() {
        adapter = new RecyclerViewUniversalAdapter<DataBean>(this, dataBeans, R.layout.item_data) {
            @Override
            public void convert(CustomViewHolder viewHolder, DataBean data) {
                viewHolder.setText(R.id.tv_name, data.name);
                viewHolder.setText(R.id.tv_index, data.index + "");
                viewHolder.setImageDrawable(R.id.iv_girl, getResources().getDrawable(DataManager.getGirlImg(data.index)));
                CardView cv = viewHolder.itemView.findViewById(R.id.cv_main);
                cv.setCardBackgroundColor(getResources().getColor(DataManager.getBgColorByIndex(data.index)));
            }
        };
    }


    /**
     * 学学RecyclerView的写法，弄个内部类
     */
    private static class DataManager {

        private static final int dataCount = 10;

        private static int getBgColorByIndex(int index) {
            int s = index % 3;
            switch (s) {
                case 0:
                    return R.color.color_0;
                case 1:
                    return R.color.color_1;
                case 2:
                    return R.color.color_2;
                default:
                    return R.color.color_default;
            }
        }

        private static int getGirlImg(int index) {
            int s = index;
            switch (s) {
                case 0:
                    return R.mipmap.girl1;
                case 1:
                    return R.mipmap.girl2;
                case 2:
                    return R.mipmap.girl3;
                case 3:
                    return R.mipmap.girl4;
                case 4:
                    return R.mipmap.girl5;
                case 5:
                    return R.mipmap.girl6;
                case 6:
                    return R.mipmap.girl7;
                case 7:
                    return R.mipmap.girl8;
                case 8:
                    return R.mipmap.girl9;
                case 9:
                    return R.mipmap.girl10;
                default:
                    return R.mipmap.girl1;
            }
        }

        private static String getGirlName(int index) {
            int s = index;
            switch (s) {
                case 0:
                    return "江袭月";
                case 1:
                    return "苗蕊";
                case 2:
                    return "雪未央";
                case 3:
                    return "玉芙";
                case 4:
                    return "苏妙弋";
                case 5:
                    return "花溪";
                case 6:
                    return "顾红妆";
                case 7:
                    return "月采屏";
                case 8:
                    return "夏艺容";
                case 9:
                    return "玉流霞";
                default:
                    return "柳诗妍";
            }
        }
    }
}
