package com.example.switchbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwitchButton extends RelativeLayout {

	private View mViewBg;
	private ImageView mViewSwitchPoint;
	private TextView mViewHint;
	private int mSwitchPointSize;
	private int mSwitchBtnWidth;

	private boolean isRight = false;
	private OnClickListener mOnClickListener;
	private String[] mStateHints;
	private View mViewBgLeft;
	private int mSwitchBtnHeight;

	public SwitchButton(Context context) {
		this(context, null);
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_switch_btn, null);

		mViewBg = view.findViewById(R.id.view_switch_bg);
		mViewBgLeft = view.findViewById(R.id.view_switch_bg_left);
		mViewSwitchPoint = (ImageView) view
				.findViewById(R.id.view_switch_image);
		mViewHint = (TextView) view.findViewById(R.id.view_switch_text);

		addView(view);
		ViewHelper.measureView(this);
		ViewHelper.measureView(mViewSwitchPoint);
		mSwitchPointSize = mViewSwitchPoint.getMeasuredWidth();

		ViewHelper.measureView(mViewBg);
		ViewHelper.measureView(mViewBgLeft);
		mViewBgLeft.getLayoutParams().width = mSwitchPointSize;
		mViewBgLeft.setVisibility(View.GONE);
		mSwitchBtnWidth = mViewBg.getMeasuredWidth();
		mSwitchBtnHeight = mViewBg.getMeasuredHeight();

		getLayoutParams().height = mSwitchBtnHeight;
		getLayoutParams().width = mSwitchBtnWidth;
		setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isRight = !isRight;

				if (mOnClickListener != null) {
					mOnClickListener.onClick(SwitchButton.this, isRight);
				}

				Animation anim = getSwitchAnimation(isRight, 200);
				anim.setDuration(200);
				mViewSwitchPoint.startAnimation(anim);
			}
		});
	}

	public void initSwitchPoint(boolean isRight, String[] stateHints) {
		mStateHints = stateHints;
		setSwitchPoint(isRight);
	}

	public void setSwitchPoint(boolean isRight) {
		this.isRight = isRight;

		mViewSwitchPoint.startAnimation(getSwitchAnimation(isRight, 0));

		if (mStateHints == null || mStateHints.length == 0) {
			mViewHint.setText("");
		} else if (mStateHints.length == 1) {
			mViewHint.setText(mStateHints[0]);
		} else if (mStateHints.length >= 2) {
			mViewHint.setText(mStateHints[isRight ? 1 : 0]);
		}
	}

	public boolean isPointRight() {
		return isRight;
	}

	public void setOnClickListener(OnClickListener l) {
		mOnClickListener = l;
	}

	public interface OnClickListener {
		public void onClick(SwitchButton v, boolean isRight);
	}

	public Animation getSwitchAnimation(boolean toRight, long durationMillis) {

		TranslateAnimation anim = new TranslateAnimation(toRight ? 0
				: mSwitchBtnWidth - mSwitchPointSize, toRight ? mSwitchBtnWidth
				- mSwitchPointSize : 0, 0, 0);
		anim.setDuration(durationMillis);
		anim.setFillAfter(true);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if (mStateHints != null && mStateHints.length > 0) {
					mViewHint.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (mStateHints != null && mStateHints.length > 0) {
					mViewHint.setVisibility(View.VISIBLE);
				}
				mViewBg.setBackgroundResource(isRight ? R.drawable.switch_bg_grey
						: R.drawable.switch_bg_blue);
				if (mStateHints != null && mStateHints.length > 1) {
					mViewHint.setText(mStateHints[isRight ? 1 : 0]);
				}

			}
		});
		return anim;
	}

}
