package com.flavienlaurent.spanimated;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LocaleSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends Activity {

    private static String ANIMATED_WORD = "Im gonna be Animated!";

    private TextView mText;
    private Button mBtnAnimateColor;
    private Button mBtnReset;
    private Button mBtnDraw1;
    private Button mBtnDraw2;
    private Button mBtnDraw3;
    private Button mBtnDrawCornMark;
    private Button mBtnAnimateTypeWriter;
    private Button mBtnViewPager;
    private Button mBtnAnimateAb1;
    private Button mBtnAnimateAb2;
    private Button mBtnSpanIt;
    private Spinner mSpinner;

    private int mTextColor;

    private String mBaconIpsum;
    private CharSequence mActionBarTitle;

    private SpannableString mBaconIpsumSpannableString;
    private SpannableString mActionBarTitleSpannableString;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;
    private LinearInterpolator mTypeWriterInterpolator;

    private HashSet<Object> mSpans = new HashSet<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.text);
        mBtnAnimateColor = (Button) findViewById(R.id.btn_animate_color);
        mBtnDraw1 = (Button) findViewById(R.id.btn_draw1);
        mBtnDraw2 = (Button) findViewById(R.id.btn_draw2);
        mBtnDraw3 = (Button) findViewById(R.id.btn_draw3);
        mBtnDrawCornMark = (Button) findViewById(R.id.btn_corner_mark);
        mBtnAnimateTypeWriter = (Button) findViewById(R.id.btn_animate_typewriter);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mBtnViewPager = (Button) findViewById(R.id.btn_viewpager);
        mBtnAnimateAb1 = (Button) findViewById(R.id.btn_animate_ab_1);
        mBtnAnimateAb2 = (Button) findViewById(R.id.btn_animate_ab_2);
        mBtnSpanIt = (Button) findViewById(R.id.btn_do_span);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        mBaconIpsum = getResources().getString(R.string.bacon_ipsum);
        mTextColor = getResources().getColor(R.color.text_color);

        mBaconIpsumSpannableString = new SpannableString(mBaconIpsum);
        mActionBarTitle = getTitle();
        mActionBarTitleSpannableString = new SpannableString(mActionBarTitle);

        mSmoothInterpolator = new AccelerateDecelerateInterpolator();
        mTypeWriterInterpolator = new LinearInterpolator();

        mBtnAnimateColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                animateColorSpan();
            }
        });

        mBtnDraw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                drawingSpan1();
            }
        });

        mBtnDrawCornMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                drawingCornerMarkSpan();
            }
        });
        mBtnDraw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                drawingSpan2();
            }
        });


        mBtnDraw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                drawingSpan3();
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        mBtnAnimateTypeWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                animateTypeWriter();
            }
        });

        mBtnViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
            }
        });

        mBtnAnimateAb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                animateActionBarFireworks();
            }
        });

        mBtnAnimateAb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                animateActionBarBlur();
            }
        });

        mBtnSpanIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                spanIt();
            }
        });

        mSpinner.setAdapter(new ArrayAdapter<SpanType>(this, android.R.layout.simple_spinner_item, SpanType.values()));
    }

    private void reset() {
        for(Object span : mSpans) {
            mBaconIpsumSpannableString.removeSpan(span);
            mActionBarTitleSpannableString.removeSpan(span);
        }
        mSpans.clear();
        //refresh
        mText.setText(mBaconIpsumSpannableString);
        setTitle(mActionBarTitleSpannableString);
    }

    enum SpanType {BULLET, QUOTE, UNDERLINE, STRIKETHROUGH, BGCOLOR, FGCOLOR, MASKFILTER_EMBOSS, SUBSCRIPT, STYLE, ABSOLUTE_SIZE_SPAN, RELATIVE_SIZE_SPAN, TEXTAPPEARANCE_SPAN, SUPERSCRIPT, LOCALE_SPAN, SCALEX_SPAN, TYPEFACE_SPAN, IMAGE_SPAN, MASKFILTER_BLUR, ALIGNMENT_STANDARD};

    private void spanIt() {
        float density = getResources().getDisplayMetrics().density;
        WordPosition wordPosition = getWordPosition(mBaconIpsum);
        Object span = null;
        int allTextStart = 0;
        int allTextEnd = mBaconIpsum.length() - 1;
        switch((SpanType)mSpinner.getSelectedItem()) {
            case BULLET:
                span = new BulletSpan(15, Color.BLACK);
                mBaconIpsumSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case QUOTE:
                span = new QuoteSpan(Color.RED);
                mBaconIpsumSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ALIGNMENT_STANDARD:
                span = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
                mBaconIpsumSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case UNDERLINE:
                span = new UnderlineSpan();
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case STRIKETHROUGH:
                span = new StrikethroughSpan();
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case BGCOLOR:
                span = new BackgroundColorSpan(Color.GREEN);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case FGCOLOR:
                span = new ForegroundColorSpan(Color.RED);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case MASKFILTER_BLUR:
                span = new MaskFilterSpan(new BlurMaskFilter(density*2, BlurMaskFilter.Blur.NORMAL));
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case MASKFILTER_EMBOSS:
                span = new MaskFilterSpan(new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f));
                ForegroundColorSpan fg = new ForegroundColorSpan(Color.BLUE);
                StyleSpan style = new StyleSpan(Typeface.BOLD);

                mSpans.add(fg);
                mSpans.add(style);

                mBaconIpsumSpannableString.setSpan(fg, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mBaconIpsumSpannableString.setSpan(style, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case SUBSCRIPT:
                span = new SubscriptSpan();
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case SUPERSCRIPT:
                span = new SuperscriptSpan();
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case STYLE:
                span = new StyleSpan(Typeface.BOLD | Typeface.ITALIC);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ABSOLUTE_SIZE_SPAN:
                span = new AbsoluteSizeSpan(24, true);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case RELATIVE_SIZE_SPAN:
                span = new RelativeSizeSpan(2.0f);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case TEXTAPPEARANCE_SPAN:
                span = new TextAppearanceSpan(this, R.style.SpecialTextAppearance);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case LOCALE_SPAN:
                span = new LocaleSpan(Locale.CHINESE);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case SCALEX_SPAN:
                span = new ScaleXSpan(3.0f);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case TYPEFACE_SPAN:
                span = new TypefaceSpan("serif");
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case IMAGE_SPAN:
                span = new ImageSpan(this, R.drawable.pic1_small);
                mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        if(span == null) {
            return;
        }
        mSpans.add(span);
        mText.setText(mBaconIpsumSpannableString);
    }

    private void animateColorSpan() {
        MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, mTextColor);
        mSpans.add(span);

        WordPosition wordPosition = getWordPosition(mBaconIpsum);
        mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY, Color.BLACK, Color.RED);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                mText.setText(mBaconIpsumSpannableString);
            }
        });
        objectAnimator.setInterpolator(mSmoothInterpolator);
        objectAnimator.setDuration(600);
        objectAnimator.start();
    }

    private static final Property<MutableForegroundColorSpan, Integer> MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY =
            new Property<MutableForegroundColorSpan, Integer>(Integer.class, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {

                @Override
                public void set(MutableForegroundColorSpan alphaForegroundColorSpanGroup, Integer value) {
                    alphaForegroundColorSpanGroup.setForegroundColor(value);
                }

                @Override
                public Integer get(MutableForegroundColorSpan span) {
                    return span.getForegroundColor();
                }
            };


    private void drawingSpan2() {
        BubbleSpan drawingSpan = new BubbleSpan();
        mSpans.add(drawingSpan);

        WordPosition wordPosition = getWordPosition(mBaconIpsum);
        mBaconIpsumSpannableString.setSpan(drawingSpan, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //refresh
        mText.setText(mBaconIpsumSpannableString);
    }

    private void drawingSpan3() {
        LetterLineBackgroundSpan drawingSpan = new LetterLineBackgroundSpan();
        mSpans.add(drawingSpan);

        int allTextStart = 0;
        int allTextEnd = mBaconIpsum.length() - 1;
        mBaconIpsumSpannableString.setSpan(drawingSpan, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //refresh
        mText.setText(mBaconIpsumSpannableString);
    }

    private void drawingSpan1() {
        FrameSpan drawingSpan = new FrameSpan();
        mSpans.add(drawingSpan);

        WordPosition wordPosition = getWordPosition(mBaconIpsum);
        mBaconIpsumSpannableString.setSpan(drawingSpan, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //refresh
        mText.setText(mBaconIpsumSpannableString);
    }

    private void drawingCornerMarkSpan() {
        CornerMarkSpan cornerMarkSpan = new CornerMarkSpan(this, mText.getPaint());
        mSpans.add(cornerMarkSpan);

        WordPosition wordPosition = getWordPosition(mBaconIpsum);
        mBaconIpsumSpannableString.setSpan(cornerMarkSpan, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //refresh
        mText.setText(mBaconIpsumSpannableString);
    }

    private void animateTypeWriter() {
        TypeWriterSpanGroup spanGroup = buildTypeWriterSpanGroup(0, mBaconIpsum.length() - 1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(spanGroup, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0.0f, 1.0f);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                mText.setText(mBaconIpsumSpannableString);
            }
        });
        objectAnimator.setInterpolator(mTypeWriterInterpolator);
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }

    private void animateActionBarFireworks() {
        FireworksSpanGroup spanGroup = buildFireworksSpanGroup(0, mActionBarTitle.length() - 1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(spanGroup, FIREWORKS_GROUP_PROGRESS_PROPERTY, 0.0f, 1.0f);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                setTitle(mActionBarTitleSpannableString);
            }
        });
        objectAnimator.setInterpolator(mSmoothInterpolator);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    private void animateActionBarBlur() {
        float density = getResources().getDisplayMetrics().density;
        float maxRadius = density*8;
        MutableBlurMaskFilterSpan span = new MutableBlurMaskFilterSpan(maxRadius);
        mSpans.add(span);

        WordPosition wordPosition = getWordPosition(mBaconIpsum);
        mBaconIpsumSpannableString.setSpan(span, wordPosition.start, wordPosition.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(span, BLUR_RADIUS_PROPERTY, maxRadius, 0.1f);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                mText.setText(mBaconIpsumSpannableString);
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset();
            }
        });
        objectAnimator.setDuration(1500);
        objectAnimator.start();
    }

    private static final Property<MutableBlurMaskFilterSpan, Float> BLUR_RADIUS_PROPERTY =
            new Property<MutableBlurMaskFilterSpan, Float>(Float.class, "BLUR_RADIUS_PROPERTY") {

                @Override
                public void set(MutableBlurMaskFilterSpan span, Float value) {
                    span.setRadius(value);
                }

                @Override
                public Float get(MutableBlurMaskFilterSpan span) {
                    return span.getRadius();
                }
            };


    private TypeWriterSpanGroup buildTypeWriterSpanGroup(int start, int end) {
        final TypeWriterSpanGroup group = new TypeWriterSpanGroup(0);
        for(int index = start ; index <= end ; index++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan(0, Color.BLACK);
            mSpans.add(span);
            group.addSpan(span);
            mBaconIpsumSpannableString.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return group;
    }

    private FireworksSpanGroup buildFireworksSpanGroup(int start, int end) {
        final FireworksSpanGroup group = new FireworksSpanGroup();
        for(int index = start ; index <= end ; index++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan(0, Color.WHITE);
            mSpans.add(span);
            group.addSpan(span);
            mActionBarTitleSpannableString.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        group.init();
        return group;
    }

    private static final class FireworksSpanGroup {

        private static final boolean DEBUG = false;
        private static final String TAG = "FireworksSpanGroup";

        private final float mProgress;
        private final ArrayList<MutableForegroundColorSpan> mSpans;
        private final ArrayList<Integer> mSpanIndexes;

        private FireworksSpanGroup() {
            mProgress = 0;
            mSpans = new ArrayList<MutableForegroundColorSpan>();
            mSpanIndexes = new ArrayList<Integer>();
        }

        public void addSpan(MutableForegroundColorSpan span) {
            span.setAlpha(0);
            mSpanIndexes.add(mSpans.size());
            mSpans.add(span);
        }

        public void init() {
            Collections.shuffle(mSpans);
        }

        public void setProgress(float progress) {
            int size = mSpans.size();
            float total = 1.0f * size * progress;

            if(DEBUG) Log.d(TAG, "progress " + progress + " * 1.0f * size => " + total);

            for(int index = 0 ; index < size; index++) {
                MutableForegroundColorSpan span = mSpans.get(index);

                if(total >= 1.0f) {
                    span.setAlpha(255);
                    total -= 1.0f;
                } else {
                    span.setAlpha((int) (total * 255));
                    total = 0.0f;
                }
            }
        }

        public float getProgress() {
            return mProgress;
        }
    }

    private static final Property<FireworksSpanGroup, Float> FIREWORKS_GROUP_PROGRESS_PROPERTY =
            new Property<FireworksSpanGroup, Float>(Float.class, "FIREWORKS_GROUP_PROGRESS_PROPERTY") {

                @Override
                public void set(FireworksSpanGroup spanGroup, Float value) {
                    spanGroup.setProgress(value);
                }

                @Override
                public Float get(FireworksSpanGroup spanGroup) {
                    return spanGroup.getProgress();
                }
            };


    private static final class TypeWriterSpanGroup {

        private static final boolean DEBUG = false;
        private static final String TAG = "TypeWriterSpanGroup";

        private final float mAlpha;
        private final ArrayList<MutableForegroundColorSpan> mSpans;

        private TypeWriterSpanGroup(float alpha) {
            mAlpha = alpha;
            mSpans = new ArrayList<MutableForegroundColorSpan>();
        }

        public void addSpan(MutableForegroundColorSpan span) {
            span.setAlpha((int) (mAlpha * 255));
            mSpans.add(span);
        }

        public void setAlpha(float alpha) {
            int size = mSpans.size();
            float total = 1.0f * size * alpha;

            if(DEBUG) Log.d(TAG, "alpha " + alpha + " * 1.0f * size => " + total);

            for(int index = 0 ; index < size; index++) {
                MutableForegroundColorSpan span = mSpans.get(index);

                if(total >= 1.0f) {
                    span.setAlpha(255);
                    total -= 1.0f;
                } else {
                    span.setAlpha((int) (total * 255));
                    total = 0.0f;
                }

                if(DEBUG) Log.d(TAG, "alpha span(" + index + ") => " + alpha);
            }
        }

        public float getAlpha() {
            return mAlpha;
        }
    }

    private static final Property<TypeWriterSpanGroup, Float> TYPE_WRITER_GROUP_ALPHA_PROPERTY =
            new Property<TypeWriterSpanGroup, Float>(Float.class, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {

                @Override
                public void set(TypeWriterSpanGroup spanGroup, Float value) {
                    spanGroup.setAlpha(value);
                }

                @Override
                public Float get(TypeWriterSpanGroup spanGroup) {
                    return spanGroup.getAlpha();
                }
            };

    private WordPosition getWordPosition(String text) {
        int start = text.indexOf(ANIMATED_WORD);
        int end = start + ANIMATED_WORD.length();
        return new WordPosition(start, end);
    }

    private static final class WordPosition {
        int start;
        int end;

        private WordPosition(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "WordPosition{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }
}
