package study.hank.com.lazyloadingfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Fragment004 extends BaseFragment {

    public static Fragment004 newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        Fragment004 fragment = new Fragment004();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_004;
    }

    @Override
    protected void init(View root) {
        TextView tv = root.findViewById(R.id.tv);
        tv.setText(getArguments().getString("text"));
    }
}
