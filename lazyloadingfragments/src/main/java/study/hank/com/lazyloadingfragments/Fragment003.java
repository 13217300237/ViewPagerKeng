package study.hank.com.lazyloadingfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Fragment003 extends BaseFragment {
    @Override
    protected String getFragmentTag() {
        return "Fragment003Tag";
    }

    public static Fragment003 newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        Fragment003 fragment = new Fragment003();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_003;
    }

    @Override
    protected void init(View root) {
        TextView tv = root.findViewById(R.id.tv);
        tv.setText(getArguments().getString("text"));
    }
}
