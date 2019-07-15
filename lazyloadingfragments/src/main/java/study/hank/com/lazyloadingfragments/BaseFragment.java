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
 * 现在你是一个不带懒加载机制的Fragment基类，要让你带有懒加载机制，如何处理
 */
@Deprecated
public abstract class BaseFragment extends Fragment {

    protected abstract String getLogTag();

    protected abstract int getLayoutId();

    protected abstract void init(View root);

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
        View root = inflater.inflate(getLayoutId(), null);
        Log.d(getLogTag(), "onCreateView");
        init(root);
        return root;
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
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getLogTag(), "onPause");
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
     *
     * 下面这一段乱七八糟的，只说明了一个结论：
     * setUserVisibleHint这个方法，是独立于Fragment生命周期之外的方法。
     * 所以不能确保它在生命周期的哪个阶段被调用。
     *
     * Set a hint to the system about whether this fragment's UI is currently visible
     * to the user. This hint defaults to true and is persistent across fragment instance
     * state save and restore.
     *  给系统设置一个关于当前fragment的UI是否显示给用户的暗示。
     *  这个暗示默认为true，并且它在fragment示例中是持久的，可以保存和恢复。
     * <p>An app may set this to false to indicate that the fragment's UI is
     * scrolled out of visibility or is otherwise not directly visible to the user.
     * This may be used by the system to prioritize operations such as fragment lifecycle updates
     * or loader ordering behavior.</p>
     *  一个app可能设置这个值为false，来表明 这个fragment的UI 滑动到了视线之外，或者是不直接对用户显示。
     *  它可能被系统用来将操作区分顺序。比如 一个fragment的生命周期更新，或者加载顺序。
     *
     * <p><strong>Note:</strong> This method may be called outside of the fragment lifecycle
     * and thus has no ordering guarantees with regard to fragment lifecycle method calls.</p>
     *  这个方法可能被fragment的生命周期的外部调用，这样的话，这个方法是独立于fragment生命周期之外的调用。
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
    }
}

