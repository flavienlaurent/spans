package com.flavienlaurent.spanimated;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerActivity extends Activity {

    private static final String TAG = "ViewPagerActivity";
    private static final boolean DEBUG = true;
    private static int[] sPics = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5};
    private static String[] sPicNames = {"It's green!", "DarkRed this one", "Vioolet", "Dark aqua blue", "BLUE"};
    private static int[] sPicColors;

    private ViewPager mViewPager;
    private SpannableString[] mSpannableTitles;
    private MutableForegroundColorSpan mMutableSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources resources = getResources();
        sPicColors = new int[] {resources.getColor(R.color.color1), resources.getColor(R.color.color2), resources.getColor(R.color.color3), resources.getColor(R.color.color4), resources.getColor(R.color.color5)};

        setContentView(R.layout.activity_view_pager);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ImagePagerAdapter());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int mCurrentSelected;
            private int mNextSelected;

            @Override
            public void onPageSelected(int position) {
                if(DEBUG) Log.d(TAG, "onPageSelected:" + position);
                mCurrentSelected = position;
                mNextSelected = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(DEBUG) Log.d(TAG, "positionOffset:" + positionOffset);
                if(DEBUG) Log.d(TAG, "positionOffsetPixels:" + positionOffsetPixels);

                //http://stackoverflow.com/a/14111712/1785133
                if (positionOffset > 0.5) {
                    // Closer to next screen than to current
                    if (position + 1 != mNextSelected) {
                        mNextSelected = position + 1;
                    }
                } else {
                    // Closer to current screen than to next
                    if (position != mNextSelected) {
                        mNextSelected = position;
                    }
                }

                if(DEBUG) Log.d(TAG, "mCurrentSelected:" + mCurrentSelected);
                if(DEBUG) Log.d(TAG, "mNextSelected:" + mNextSelected);

                float positionOffsetCalc = Math.round(positionOffset * 100.0f) / 100.0f;
                if(positionOffsetCalc < 0.50) {
                    positionOffsetCalc = 1 - positionOffsetCalc;
                } else if(positionOffsetCalc == 0.50) {
                    positionOffsetCalc = 0;
                }

                if(DEBUG) Log.d(TAG, "positionOffsetCalc:" + positionOffsetCalc);
                mMutableSpan.setAlpha((int) (510 * positionOffsetCalc)); // 255 * 2
                mMutableSpan.setForegroundColor(sPicColors[mNextSelected]);

                updateTitle(mNextSelected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mMutableSpan = new MutableForegroundColorSpan(255, sPicColors[0]);

        initSpannableTitles();

        updateTitle(mViewPager.getCurrentItem());
    }

    private void initSpannableTitles() {
        mSpannableTitles = new SpannableString[sPicNames.length];
        int index = 0;
        for(String picName : sPicNames) {
            mSpannableTitles[index++] = new SpannableString(picName);
        }
    }

    private void updateTitle(int position) {
        SpannableString mSpannableTitle = mSpannableTitles[position];
        mSpannableTitle.setSpan(mMutableSpan, 0, mSpannableTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(mSpannableTitle);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return sPics.length;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            ImageView view = new ImageView(ViewPagerActivity.this);
            view.setImageResource(sPics[position]);
            viewGroup.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            ((ViewPager) collection).removeView((ImageView) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

    }
}
