package study.hank.com.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hSpec, wSpec;
        int finalH = 0, finalW = 0;
        final int count = getChildCount();
        View child;

        //我原本想直接使用子view测量之后的宽高，然后发现，尼玛，ViewPager根本就没有针对考虑子View的WrapContent
//        for (int i = 0; i < count; i++) {
//            //算出所有子view的最大宽度
//            child = getChildAt(i);
//            int thisH = child.getMeasuredHeight();
//            int thisW = child.getMeasuredWidth();
//            finalH = finalH > thisH ? finalH : thisH;
//            finalW = finalW > thisW ? finalW : thisW;
//        }
//
//        hSpec = MeasureSpec.makeMeasureSpec(finalH, MeasureSpec.EXACTLY);
//        wSpec = MeasureSpec.makeMeasureSpec(finalW, MeasureSpec.EXACTLY);

        // 那没办法了，那就再对子view进行一次常规测量
        // 坑并不只是 ViewPager没有对子进行测量，而是，它对于子的测量，也是使用的默认matchParent··使得子view充满父容器
        // 那我自己来考虑子view的LayoutParam来测量一次
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int thisH = child.getMeasuredHeight();
            int thisW = child.getMeasuredWidth();
            finalH = finalH > thisH ? finalH : thisH;
            finalW = finalW > thisW ? finalW : thisW;
            Log.d("MeasureSpec ", "finalH:" + finalH + "/finalW:" + finalW);
        }

        hSpec = MeasureSpec.makeMeasureSpec(finalH, MeasureSpec.EXACTLY);//这里的模式。。emmm
        wSpec = MeasureSpec.makeMeasureSpec(finalW, MeasureSpec.EXACTLY);

        setMeasuredDimension(wSpec, hSpec);
    }
}
