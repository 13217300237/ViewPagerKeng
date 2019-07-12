package study.hank.com.myapplication2.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView的子view排布，全都在这里
 */
public class CustomRvLayoutManager extends RecyclerView.LayoutManager {
    private Context mContext;

    public CustomRvLayoutManager(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 自定义LayoutManager最重要的布局方法
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        //第一步，让所有子View参与回收复用机制
        detachAndScrapAttachedViews(recycler);
        // 第二步：制定子view的排布规则
        final int itemCount = getItemCount();//原来这个getItemCount，是Rv的adapter的那个getItemCount
        //对比 getChildCount- 它是当前附加在Rv上可见的子view个数,在你布局之前，附加在Rv上的子view个数一定是0，所以这里不要用getChildCount
        int currentLevel;//层级，影响子view的缩放倍率和Y偏移量
        //那就以最后4个为准了
        int startIndex = itemCount - RvAnimationConst.maxShownChildCount;
        for (int i = startIndex; i < itemCount; i++) {

            View child = recycler.getViewForPosition(i);//从recycler中获取子view，因为子view已经被Recycler统筹管理
            addView(child);//加入到LayoutManager中

            //对子view进行测量（带margin）
            measureChildWithMargins(child, 0, 0);//既然 要layoutChild，那么在layout之前要先measure
            //拿到子View的宽高
            int childWidth = getDecoratedMeasuredWidth(child);//因为Rv自带了ItemDecoration（比如item之间的分隔符）,所以计算宽高的时候要使用这个方法
            int childHeight = getDecoratedMeasuredHeight(child);
            //因为我想让所有子View横向居中，所以我要事先计算出子左右两侧应该留出的缝隙大小
            int widthSpace = (getWidth() - childWidth) / 2;
            int heightSpace = (getHeight() - childHeight) / 2;
            //现在对子view进行layout, 设定它的左上右下坐标值
            layoutDecoratedWithMargins(child,
                    widthSpace,
                    heightSpace,
                    widthSpace + childWidth,
                    heightSpace + childHeight);
            //然后让子view进行适当缩小
            //计算偏移值，越往上，越大，也就是说，最底下的是层级最低的
            //打印出每个level的缩放倍率和y平移距离
            currentLevel = itemCount - i - 1;
            if (currentLevel > 0) {
                if (currentLevel >= RvAnimationConst.maxShownChildCount - 1)//如果是最后一个item，则让它和倒数第二个重叠（缩放倍率和Y平移量一样）
                    currentLevel--;
                float currentScaleOffset = RvAnimationConst.scaleOffset * currentLevel;
                child.setScaleX(1 - currentScaleOffset);
                child.setScaleY(1 - currentScaleOffset);
                child.setTranslationY(RvAnimationConst.baseYTransOffset * currentLevel);
            } // 但是我不想所有的数据都叠起来，因为最多看见那么几个，没必要全都叠放，那就最多放4个
        }

    }
}
