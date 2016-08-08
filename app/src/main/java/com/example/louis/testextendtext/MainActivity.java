package com.example.louis.testextendtext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final static int mDefaultLineCount = 3;
    private final static int mDurationMillis = 350;//动画显示的时间
    private TextView mContent;
    private Button mConfirm;
    private ImageView mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = (TextView) findViewById(R.id.content);
        mConfirm = (Button) findViewById(R.id.confirm);
        mIcon = (ImageView) findViewById(R.id.icon);
        mContent.setHeight(mContent.getLineHeight() * mDefaultLineCount);
        //post方法会在TextView显示完后执行
        mContent.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = mContent.getLineCount();
                if(lineCount > mDefaultLineCount){
                    mConfirm.setVisibility(View.VISIBLE);
                }
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                mContent.clearAnimation();
                final int deltaValue;
                final int startValue = mContent.getHeight();

                //箭头icon动画 start
                if (isExpand) {
                    deltaValue = mContent.getLineHeight() * mContent.getLineCount() - startValue;
                    RotateAnimation animation = new RotateAnimation(360, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(mDurationMillis);
                    animation.setFillAfter(true);
                    mIcon.startAnimation(animation);
                } else {
                    deltaValue = mContent.getLineHeight() * mDefaultLineCount - startValue;
                    RotateAnimation animation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(mDurationMillis);
                    animation.setFillAfter(true);
                    mIcon.startAnimation(animation);
                }
                //箭头icon动画 end
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        mContent.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }

                };
                animation.setDuration(mDurationMillis);
                mContent.startAnimation(animation);
            }
        });
    }
}
