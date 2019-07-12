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

public abstract class BaseFragment extends Fragment {

    protected abstract String getFragmentTag();

    protected abstract int getLayoutId();

    protected abstract void init(View root);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getFragmentTag(), "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getFragmentTag(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(getFragmentTag(), "onCreateView");
        View root = inflater.inflate(getLayoutId(), null);
        init(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(getFragmentTag(), "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getFragmentTag(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getFragmentTag(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getFragmentTag(), "onDetach");
    }
}

