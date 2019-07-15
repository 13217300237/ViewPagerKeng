package study.hank.com.lazyloadingfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 源码从BaseFragment中复制而来，造就一个带有 懒加载机制的Fragment基类
 * <p>
 * 所谓懒加载，就是看得到页面才去更新UI，看不到的，就不要浪费资源了
 *
 * <p>
 * 代码版权来自：想学课堂VIP课老师 Alvin老师;
 * <p>
 * 2019年7月13日 22:25:46 剽窃完毕，下一步，解析具体细节,讲解在什么情况下会触发程序分支
 * <p>
 * 2019年7月14日 17:27:40 开始优化demo，更直观展示懒加载特性
 * <p>
 * 下面统筹本次ViewPager+Fragment懒加载实现之后，能够得到优化的几个问题：
 * <p>
 * ViewPager+Fragment 有一个特别大的坑。就是 一旦一个Fragment在ViewPager中缓存了，那么当ViewPager所在的Activity执行生命周期函数
 * （onCreate-onStart-onResume-onPause-onStop-onDestroy），那么fragment也会跟着执行(onCreate-onCreateView-onViewCreated-onStart-onResume
 * -onPause-onStop-onDestroyView-onDestroy),无论它是不是可见，这就很尴尬了，明明这个Fragment并不可见，你还去调用生命周期函数去浪费UI资源，
 * 很不合理
 * <p>
 * 1、滑动过程中显示那个页面，就去加载哪个页面的数据
 * 2、比如从Fragment的一个按钮跳转到另一个Activity，再回来时，也只有当前这个可见的Fragment会执行UI更新
 * 3、当其中一个Fragment的内容，也是ViewPager+Fragment，结果形成了嵌套结构时，跳转到另外的Activity再回来，这个内部的多个Fragment，生命周期也会出现不必要的执行
 */
public abstract class BaseLazyLoadingFragment extends Fragment {

    private View mRoot;//Fragment在ViewPager中滑动的时候，可能会发生多次 onDestroyView/onCreateView 的情况，将它提取出来，可以优化内存

    protected abstract String getLogTag();

    protected abstract int getLayoutId();

    /**
     * 只是initView，并不涉及到数据
     * <p>
     * 比如，做一些findViewById
     *
     * @param root
     */
    protected abstract void initView(View root);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(getLogTag(), "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(getLayoutId(), container, false);
        Log.d(getLogTag(), "onCreateView");
        initView(mRoot);
        //在View创建完毕之后，isViewCreate 要变为true
        isViewCreated = true;
        if (!isHidden() && getUserVisibleHint())
            dispatchVisibleState(true);
        return mRoot;
    }

    @Override
    public void onDestroyView() {//相对应的，当View被销毁的时候，isViewCreated要变为false
        super.onDestroyView();
        isViewCreated = false;
        isFirstVisible = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(getLogTag(), "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getLogTag(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getLogTag(), "onResume");

        //因为onResume可能会在跳转Activity的时候反复执行，但是不是每一次都需要执行true分发
        //存在一个情况。ViewPager+Fragment ，当tab1 滑到 tab2 时， tab3 会执行完整的生命周期 onCreate-onCreateView-onViewCreated-onStart-onResume 但是此时tab3并不是可见的，
        // 没有必要执行true分发
        if (!isFirstVisible) {//只有在不是第一次可见的时候，才进入逻辑,由于isFirstVisible默认是true，所以，第一次进入onResume不会执行true分发
            if (!isHidden() && !currentVisibleState && getUserVisibleHint())//没有隐藏，当前状态为不可见，系统的可见hint为true 同时满足
                // 这个会发生在 Activity1 中 是ViewPager+Fragment时，如果从某个Fragment跳转到activity2，再跳回Activity1，那么 Activity1中的多个Fragment会同时执行onResume，
                //但是不会所有的fragment都是可见的，所以我只需要对可见的Fragment进行true分发
                dispatchVisibleState(true);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getLogTag(), "onPause");
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchVisibleState(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getLogTag(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getLogTag(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 下面这一段原版注解。乱七八糟的，只说明了一个结论：
     * setUserVisibleHint这个方法，是独立于Fragment生命周期之外的方法。
     * 所以不能确保它在生命周期的哪个阶段被调用。
     * <p>
     * Set a hint to the system about whether this fragment's UI is currently visible
     * to the user. This hint defaults to true and is persistent across fragment instance
     * state save and restore.
     * 给系统设置一个关于当前fragment的UI是否显示给用户的暗示。
     * 这个暗示默认为true，并且它在fragment示例中是持久的，可以保存和恢复。
     * <p>An app may set this to false to indicate that the fragment's UI is
     * scrolled out of visibility or is otherwise not directly visible to the user.
     * This may be used by the system to prioritize operations such as fragment lifecycle updates
     * or loader ordering behavior.</p>
     * 一个app可能设置这个值为false，来表明 这个fragment的UI 滑动到了视线之外，或者是不直接对用户显示。
     * 它可能被系统用来将操作区分顺序。比如 一个fragment的生命周期更新，或者加载顺序。
     *
     * <p><strong>Note:</strong> This method may be called outside of the fragment lifecycle
     * and thus has no ordering guarantees with regard to fragment lifecycle method calls.</p>
     * 这个方法可能被fragment的生命周期的外部调用，这样的话，这个方法是独立于fragment生命周期之外的调用。
     *
     *
     * <p><strong>Note:</strong> Prior to Android N there was a platform bug that could cause
     * <code>setUserVisibleHint</code> to bring a fragment up to the started state before its
     * <code>FragmentTransaction</code> had been committed. As some apps relied on this behavior,
     * it is preserved for apps that declare a <code>targetSdkVersion</code> of 23 or lower.</p>
     *
     * @param isVisibleToUser true if this fragment's UI is currently visible to the user (default),
     *                        false if it is not.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(getLogTag(), "setUserVisibleHint:" + isVisibleToUser);

        //因为只有在Fragment的View已经被创建的前提下，UI处理才有意义，所以
        if (isViewCreated) {
            //为了逻辑严谨，必须当目前状态值和目标相异的时候，才去执行UI可见分发
            if (currentVisibleState && !isVisibleToUser) {
                dispatchVisibleState(false);
            } else if (!currentVisibleState && isVisibleToUser) {
                dispatchVisibleState(true);
            }
        }

    }

    /**
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {// 这个，是在Fragment被hide/show的时候被调用
        super.onHiddenChanged(hidden);
        if (hidden) {
            dispatchVisibleState(false);
        } else {
            dispatchVisibleState(true);
        }
    }

    // 前方高能
    // ************************* 懒加载机制核心标志位 *****************************
    private boolean isViewCreated = false;//View是否已经被创建出来， View的创建是在onCreateView中进行，
    // ViewPager+Fragment很可能会发生多次destroy.create view的情况，所以这个标志位，表示当前fragment的view是否存在

    private boolean isFirstVisible = true;//当前Fragment是否是首次可见；
    // 设计这个Flag是 因为 根据懒加载的需求，我们往往需要在fragment首次可见的时候，整个页面全部初始化包括数据请求，
    // 而非第一次可见，UI内容的调整力度会小很多，所以还是区分出来比较好

    private boolean currentVisibleState = false;//当前可见状态
    // 它表示，当前UI是否可见。为什么设计这个标志位？
    // 因为，fragment本身api提供的的可见状态，并不完全可信。
    // 到目前为止，fragment可见状态的相关api有   setUserVisibleHint（可由外部调用，这个方法和生命周期无关）
    // onHiddenChange（它是在使用Activity+Fragment+FragmentManager时，由FragmentManager改变标志位mHidden）
    // onResume onPause 这两个是生命周期函数
    // 因为情况太多，我们单独上述使用任何一个方法作为当前fragment可见的判定，都不合适，所以还是综合所有情况，自己设定一个标志位


    //OK，标志位设计完毕了，现在把标志位用起来
    //设计一个方法，统一分发Ui的可见状态, 当UI可见状态发生变化时，都通过这个方法去处理
    //至于，UI可见状态什么时候发生变化，就要分多种情况
    // 比如，ViewPager+Fragment，滑动的时候
    // 比如，ViewPager+Fragment，从其中一个Fragment跳转到另外的Activity，然后又回来

    /**
     * @param isVisible 目标，true变为可见，false。变为不可见
     */
    private void dispatchVisibleState(boolean isVisible) {
        if (isVisible == currentVisibleState) return;//如果目标值，和当前值相同，那就别费劲了
        currentVisibleState = isVisible;//更新状态值
        if (isVisible) {//如果可见
            //那就区分是第一次可见，还是非第一次可见
            if (isFirstVisible) {
                isFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();
        } else {
            onFragmentPause();
        }

    }

    /**
     * 当第一次可见的时候(此方法，在View的一次生命周期中只执行一次)
     */
    protected void onFragmentFirstVisible() {
        Log.d(getLogTag(), "第一次可见,进行当前Fragment必要的，和UI无关的初始化操作");
    }

    /**
     * 当fragment变成可见的时候(可能会多次)
     * <p>
     * 设计这么一对方法 onFragmentResume / onFragmentPause 可以在快速划过页面的时候，保证资源不浪费
     * <p>
     * 比如：一共有4个Fragment，我从1，快速滑到4，中途会经过2,3
     * 2 3 都会经历 onFragmentResume/onFragmentPause,而 onFragmentResume的时候，会去加载数据，然后调用UI线程刷新界面，
     * 如果此时，停留时间太短，onFragmentResume请求的网络数据尚未返回，若没有onFragmentResume中断操作，那么，在2 不可见的时候，它也去刷新了UI，这是显然的浪费。
     * 所以，设计这一对方法，可以放置在某个Fragment不可见的时候，浪费UI资源。
     * 这样做还可以优化滑动卡顿
     */
    protected void onFragmentResume() {
        Log.d(getLogTag(), "onFragmentResume 执行网络请求以及，UI操作");
    }

    /**
     * 当fragment变成不可见的时候(可能会多次)
     */
    protected void onFragmentPause() {
        Log.d(getLogTag(), "onFragmentPause 中断网络请求，UI操作");
    }
}

