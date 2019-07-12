package study.hank.com.lazyloadingfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Fragment002 extends BaseFragment {

    @Override
    protected String getFragmentTag() {
        return "Fragment002Tag";
    }

    public static Fragment002 newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        Fragment002 fragment = new Fragment002();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_002;
    }

    @Override
    protected void init(View root) {
        TextView tv = root.findViewById(R.id.tv);
        tv.setText(getArguments().getString("text"));
    }
}
