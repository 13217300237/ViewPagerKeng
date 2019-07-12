package study.hank.com.myapplication2.custom;

import android.content.Context;

import study.hank.com.myapplication2.util.DeviceUtil;

public class RvAnimationConst {
    public static int maxShownChildCount;//最多叠放4个,其他的不计算在内
    public static float scaleOffset;//每次缩放都以这个为偏移值
    public static float baseYTransOffset;//Y方向上基础偏移距离

    public static void init(Context context) {
        maxShownChildCount = 4;
        scaleOffset = 0.05f;
        baseYTransOffset = DeviceUtil.instance().dip2px(20, context);//Y方向上基础偏移距离
    }
}
