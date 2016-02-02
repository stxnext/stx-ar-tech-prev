package com.stx.ar.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stx.ar.R;
import com.stx.ar.activity.TutorialActivity;
import com.stx.ar.util.Preferences;

/**
 * Created by Łukasz Ciupa on 22.01.2016.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private int iconResId, titleArrayResId, hintArrayResId;
    private Context context;

    private static int FINISH_TUTORIAL_POSITION = 3;

    public ViewPagerAdapter(Context context, int iconResId, int titleArrayResId, int hintArrayResId) {

        this.context = context;
        this.iconResId = iconResId;
        this.titleArrayResId = titleArrayResId;
        this.hintArrayResId = hintArrayResId;
    }

    @Override
    public int getCount() {
        return context.getResources().getIntArray(iconResId).length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Drawable icon = context.getResources().obtainTypedArray(iconResId).getDrawable(position);
        String title = context.getResources().getStringArray(titleArrayResId)[position];
        String hint = context.getResources().getStringArray(hintArrayResId)[position];
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        ImageView iconView = (ImageView) itemView.findViewById(R.id.landing_img_slide);
        TextView titleView = (TextView)itemView.findViewById(R.id.landing_txt_title);
        TextView hintView = (TextView)itemView.findViewById(R.id.landing_txt_hint);
        Button finishTutorial = (Button)itemView.findViewById(R.id.finish_tutorial);
        if (position == FINISH_TUTORIAL_POSITION) {
            finishTutorial.setVisibility(View.VISIBLE);
            finishTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        TutorialActivity parentActivity = (TutorialActivity) context;
                        parentActivity.setResultOkAndFinish();
                    } catch (ClassCastException e) {
                        Log.e(this.getClass().getName(), context.toString() + " must be a TutorialActivity");
                    }
                }
            });
        }
        hintView.setMovementMethod(LinkMovementMethod.getInstance());
        iconView.setImageDrawable(icon);
        titleView.setText(title);
        hintView.setText(Html.fromHtml(hint));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
