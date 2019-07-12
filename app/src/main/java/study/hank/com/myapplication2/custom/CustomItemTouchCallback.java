package study.hank.com.myapplication2.custom;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import study.hank.com.myapplication2.R;
import study.hank.com.myapplication2.adapter.CustomViewHolder;

/**
 * 统筹管理Item的各种点击拖拽效果
 */
public class CustomItemTouchCallback extends ItemTouchHelper.SimpleCallback {

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    List<DataBean> dataBeans;

    //还是先搞清楚怎么用，再考虑后续的
    public CustomItemTouchCallback(RecyclerView rv, RecyclerView.Adapter<?> adapter, List<DataBean> dataBeans) {
        super(0, 15);//二进制1111(表示上下左右拖动都支持)，换算成十进制，就是15
        this.rv = rv;
        this.adapter = adapter;
        this.dataBeans = dataBeans;
    }

    /**
     * Called when ItemTouchHelper wants to move the dragged item from its old position to the new position.
     * 英文注释的大概意思是：当ItemTouchHelper要将item从旧位置移动到新位置时，此方法会执行
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return True if the {@code viewHolder} has been moved to the adapter position of
     * {@code target}. 如果viewHolder被从adapter的目标位置被移除时，返回true，？？？暂时还不知道什么意思
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;//由于我这里不需要什么旧位置移动到新位置，我只需要让它从界面上滚出去，false就行了
    }

    /**
     * Called when a CustomViewHolder is swiped by the user.
     * 滑动逻辑核心:item滑出去之后执行
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        DataBean dataBean = dataBeans.remove(viewHolder.getLayoutPosition());//移除并且返回
        dataBeans.add(0, dataBean);
        adapter.notifyDataSetChanged();// 刷新adapter
    }

    /**
     * Called by ItemTouchHelper on RecyclerView's onDraw callback.
     * 在RecyclerView的onDraw回调时这个方法将被ItemTouchHelper执行,
     * ？？那RecyclerView的onDraw啥时候调用?应该是在 调用 invalidate()的时候，onDraw 会执行，也就是说当有外界出发了
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        //问题来了，为什么要重写这个方法呢？ 因为我们要在拖动的时候，让子view发生一些变化
        float maxDistanceX = recyclerView.getWidth() * 0.5f;
        float maxDistanceY = recyclerView.getHeight() * 0.5f;
        float maxDistance = (float) Math.sqrt(maxDistanceX * maxDistanceX + maxDistanceY * maxDistanceY);//计算对角线最大长度

        float distance = (float) Math.sqrt(dX * dX + dY * dY);//dx和dy是横竖方向的移动距离，现在算出直接的移动距离，勾股定理，开方
        float fraction = distance / maxDistance;//这是，当前移动的比例
        if (fraction > 1)//防止意外情况
            fraction = 1;

        int count = recyclerView.getChildCount();//这是啥？这是recyclerView这个容器里面的当前子view个数
        //我在滑动的时候，会触发invalidate，从而触发这个onChildDraw，引起子view的一些实时变化
        if (callback != null) {
            View child = recyclerView.getLayoutManager().getChildAt(RvAnimationConst.maxShownChildCount - 1);// 获得最上面的子view
            CustomViewHolder vh = (CustomViewHolder) recyclerView.getChildViewHolder(child);
            TextView textView = vh.getView(R.id.tv_name);
            String who = (String) textView.getText();
            callback.call(dX < 0, who);//向左负数，喜欢，向右正数不喜欢
        }
        //也要根据层级来进行不同程度的缩放，这个全看效果想如何设计了
        for (int i = 0; i < count; i++) {
            View child = recyclerView.getChildAt(i);

            int currentLevel = count - i - 1;//i越小，层级越大
            if (currentLevel > 0) {
                //让子进行缩放和位移(之前布局的时候位移过了，现在第一个item在移动，
                // 那就让做一些动画剩下的item发生一些变化，让整个UI显得自然一些)
                if (currentLevel < count - 1) {//当前拖动的整个子view就不要动了···你已经在被拖，还是别动了
                    //这是之前布局的时候调整的x，y缩放和y位移，这里要反向执行
                    child.setTranslationY(RvAnimationConst.baseYTransOffset * (currentLevel - fraction));//那么怎么去移动呢？？参数怎么设定呢？看老师怎么写吧
                    child.setScaleX(1 - RvAnimationConst.scaleOffset * (currentLevel - fraction));
                    child.setScaleY(1 - RvAnimationConst.scaleOffset * (currentLevel - fraction));
                    Log.d("levelTag_" + currentLevel, "" + (1 - RvAnimationConst.scaleOffset * (currentLevel - fraction)));
                }
            }
        }

    }

    //    @Override
//    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
//        return 200;
//    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }

    //给它加上一个回调，将向左向右的事件传递出去
    public interface LikeOrDislikeCallback {
        void call(boolean ifLike, String who);
    }

    LikeOrDislikeCallback callback;

    public void setCallback(LikeOrDislikeCallback callback) {
        this.callback = callback;
    }
}
