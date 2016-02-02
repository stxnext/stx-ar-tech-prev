package com.stx.ar.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stx.ar.R;
import com.stx.ar.adapter.CustomPageTransformer;
import com.stx.ar.adapter.ViewPagerAdapter;
import com.stx.ar.util.ColorShades;
import com.stx.ar.view.CirclePageIndicator;

/**
 * Created by Łukasz Ciupa on 21.01.2016.
 */
public class TutorialActivity extends AppCompatActivity {

    private boolean isSliderAnimation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.layout_tutorial);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(this, R.array.icons, R.array.titles, R.array.hints));
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(viewPager);

        viewPager.setPageTransformer(true, new CustomPageTransformer(isSliderAnimation));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

                View landingBGView = findViewById(R.id.landing_backgrond);
                int colorBg[] = getResources().getIntArray(R.array.landing_bg);

                ColorShades shades = new ColorShades();
                shades.setFromColor(colorBg[position % colorBg.length])
                        .setToColor(colorBg[(position + 1) % colorBg.length])
                        .setShade(positionOffset);

                landingBGView.setBackgroundColor(shades.generate());
            }

            public void onPageSelected(int position) {

            }

            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void setResultOkAndFinish() {
        setResult(RESULT_OK);
        finish();
    }
}
