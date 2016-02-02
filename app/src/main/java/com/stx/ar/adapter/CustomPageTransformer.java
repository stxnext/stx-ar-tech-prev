package com.stx.ar.adapter;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.stx.ar.R;

/**
 * Created by Łukasz Ciupa on 22.01.2016.
 */
public class CustomPageTransformer implements ViewPager.PageTransformer {

    private boolean isSliderAnimation = false;

    public CustomPageTransformer() {
        
    }

    public CustomPageTransformer(boolean isSliderAnimation) {
        this.isSliderAnimation = isSliderAnimation;
    }


    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        View imageView = view.findViewById(R.id.landing_img_slide);
        View contentView = view.findViewById(R.id.landing_txt_hint);
        View txt_title = view.findViewById(R.id.landing_txt_title);
        View finishTutorial = view.findViewById(R.id.finish_tutorial);

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left
        } else if (position <= 0) { // [-1,0]
            // This page is moving out to the left

            // Counteract the default swipe
            setTranslationX(view,pageWidth * -position);
            if (contentView != null) {
                // But swipe the contentView
                setTranslationX(contentView,pageWidth * position);
                setTranslationX(txt_title,pageWidth * position);
                setTranslationX(finishTutorial,pageWidth * position);

                setAlpha(contentView,1 + position);
                setAlpha(txt_title,1 + position);
                setAlpha(finishTutorial, 1 + position);
            }

            if (imageView != null) {
                // Fade the image in
                setAlpha(imageView,1 + position);
            }

        } else if (position <= 1) { // (0,1]
            // This page is moving in from the right

            // Counteract the default swipe
            setTranslationX(view, pageWidth * -position);
            if (contentView != null) {
                // But swipe the contentView
                setTranslationX(contentView,pageWidth * position);
                setTranslationX(txt_title,pageWidth * position);
                setTranslationX(finishTutorial, pageWidth * position);

                setAlpha(contentView, 1 - position);
                setAlpha(txt_title, 1 - position);
                setAlpha(finishTutorial, 1 - position);

            }
            if (imageView != null) {
                // Fade the image out
                setAlpha(imageView,1 - position);
            }

        }
    }

    /**
     * Sets the alpha for the view. The alpha will be applied only if the running android device OS is greater than honeycomb.
     * @param view - view to which alpha to be applied.
     * @param alpha - alpha value.
     */
    private void setAlpha(View view, float alpha) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ! isSliderAnimation) {
            view.setAlpha(alpha);
        }
    }

    /**
     * Sets the translationX for the view. The translation value will be applied only if the running android device OS is greater than honeycomb.
     * @param view - view to which alpha to be applied.
     * @param translationX - translationX value.
     */
    private void setTranslationX(View view, float translationX) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ! isSliderAnimation) {
            view.setTranslationX(translationX);
        }
    }

}