package study.hank.com.myapplication2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray();
    private int mLayoutId;

    public CustomViewHolder(View itemView) {
        super(itemView);
    }

    public static CustomViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            View holder2 = LayoutInflater.from(context).inflate(layoutId, parent, false);
            CustomViewHolder holder1 = new CustomViewHolder(holder2);
            holder1.mLayoutId = layoutId;
            return holder1;
        } else {
            CustomViewHolder holder = (CustomViewHolder) convertView.getTag();
            return holder;
        }
    }

    public int getLayoutId() {
        return this.mLayoutId;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) this.mViews.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            this.mViews.put(viewId, view);
        }

        return (T) view;
    }

    public CustomViewHolder setText(int viewId, CharSequence text) {
        TextView tv = (TextView) this.getView(viewId);
        tv.setText(text);
        return this;
    }

    public CustomViewHolder setSelected(int viewId, boolean selected) {
        View v = this.getView(viewId);
        v.setSelected(selected);
        return this;
    }

    public CustomViewHolder setImageResource(int viewId, int resId) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public CustomViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public CustomViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public CustomViewHolder setBackgroundColor(int viewId, int color) {
        View view = this.getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public CustomViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = this.getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public CustomViewHolder setTextColor(int viewId, int textColor) {
        TextView view = (TextView) this.getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public CustomViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = (TextView) this.getView(viewId);
        view.setTextColor(this.itemView.getContext().getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint({"NewApi"})
    public CustomViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0L);
            alpha.setFillAfter(true);
            this.getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public CustomViewHolder setVisible(int viewId, boolean visible) {
        View view = this.getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public CustomViewHolder linkify(int viewId) {
        TextView view = (TextView) this.getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public CustomViewHolder setTypeface(Typeface typeface, int... viewIds) {
        int[] var3 = viewIds;
        int var4 = viewIds.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            int viewId = var3[var5];
            TextView view = (TextView) this.getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | 128);
        }

        return this;
    }

    public CustomViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = (ProgressBar) this.getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public CustomViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = (ProgressBar) this.getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public CustomViewHolder setMax(int viewId, int max) {
        ProgressBar view = (ProgressBar) this.getView(viewId);
        view.setMax(max);
        return this;
    }

    public CustomViewHolder setRating(int viewId, float rating) {
        RatingBar view = (RatingBar) this.getView(viewId);
        view.setRating(rating);
        return this;
    }

    public CustomViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = (RatingBar) this.getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public CustomViewHolder setTag(int viewId, Object tag) {
        View view = this.getView(viewId);
        view.setTag(tag);
        return this;
    }

    public CustomViewHolder setTag(int viewId, int key, Object tag) {
        View view = this.getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public CustomViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) this.getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public CustomViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = this.getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public CustomViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = this.getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public CustomViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = this.getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public void setItemVisible(boolean visible) {
        if (null != this.itemView) {
            if (visible) {
                if (null != this.itemView.getLayoutParams()) {
                    this.itemView.getLayoutParams().width = -1;
                    this.itemView.getLayoutParams().height = -2;
                } else {
                    this.itemView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
                }

                this.itemView.setVisibility(View.VISIBLE);
            } else {
                if (null != this.itemView.getLayoutParams()) {
                    this.itemView.getLayoutParams().width = -1;
                    this.itemView.getLayoutParams().height = 1;
                } else {
                    this.itemView.setLayoutParams(new AbsListView.LayoutParams(-1, 1));
                }

                this.itemView.setVisibility(View.GONE);
            }
        }

    }

    public void setHItemVisible(boolean visible) {
        if (null != this.itemView) {
            if (visible) {
                if (null != this.itemView.getLayoutParams()) {
                    this.itemView.getLayoutParams().width = -2;
                    this.itemView.getLayoutParams().height = -2;
                } else {
                    this.itemView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
                }

                this.itemView.setVisibility(View.VISIBLE);
            } else {
                if (null != this.itemView.getLayoutParams()) {
                    this.itemView.getLayoutParams().width = -1;
                    this.itemView.getLayoutParams().height = 1;
                } else {
                    this.itemView.setLayoutParams(new AbsListView.LayoutParams(-1, 1));
                }

                this.itemView.setVisibility(View.GONE);
            }
        }

    }
}