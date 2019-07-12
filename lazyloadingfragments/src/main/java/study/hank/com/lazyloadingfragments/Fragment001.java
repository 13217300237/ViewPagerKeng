package study.hank.com.lazyloadingfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Fragment001 extends BaseFragment {

    public static Fragment001 newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        Fragment001 fragment = new Fragment001();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_001;
    }

    @Override
    protected void init(View root) {
        TextView tv = root.findViewById(R.id.tv);
        String text = getArguments().getString("text");
        tv.setText(text);
    }
}
