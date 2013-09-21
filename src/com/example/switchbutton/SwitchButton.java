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
	private OnSwitchListener mOnSwitchListener;
	private String[] mStateHints;
	private View mFrameView;

	/**
	 * Listener of SwitchButton.
	 * 
	 */
	public interface OnSwitchListener {
		/**
		 * @param v the SwitchButton
		 * 
		 * @return if return true, don't forget to use setSwitch method 
		 * to set the Switch Point position yourself. 
		 */
		public boolean onSwitch(SwitchButton v, boolean isRight);
	}
	
	public SwitchButton(Context context) {
		this(context, null);
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mFrameView = LayoutInflater.from(context).inflate(
				R.layout.layout_switch_btn, null);

		mViewBg = mFrameView.findViewById(R.id.view_switch_bg);
		mViewSwitchPoint = (ImageView) mFrameView
				.findViewById(R.id.view_switch_image);
		mViewHint = (TextView) mFrameView.findViewById(R.id.view_switch_text);

		addView(mFrameView);
		ViewHelper.measureView(this);
		ViewHelper.measureView(mViewBg);
		ViewHelper.measureView(mViewSwitchPoint);

		mSwitchBtnWidth = mViewBg.getMeasuredWidth();
		mSwitchPointSize = mViewSwitchPoint.getMeasuredWidth();

		mFrameView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mOnSwitchListener != null && mOnSwitchListener.onSwitch(
						SwitchButton.this, !isRight)){
					return;
				}
				setSwitch(!isRight);
			}
		});
	}
	
	public void initSwitchPoint(boolean isRight, String[] stateHints) {
		mStateHints = stateHints;
		setSwitch(isRight, 0);
	}

	/**
	 * see also {@link #setSwitch(boolean, long)}
	 * 
	 */
	public void setSwitch(boolean toRight) {
		setSwitch(toRight, 200);
	}
	
	/**
	 * Switch the Position of the SwitchButton's indicate point.
	 * 
	 * @param toRight indicate the switch position.
	 * @param duration the switch animation duration
	 */
	public void setSwitch(boolean toRight, long duration) {
		this.isRight = toRight;
		Animation anim = getSwitchAnimation(toRight, duration);
		mViewSwitchPoint.startAnimation(anim);
		
		if (mStateHints == null || mStateHints.length == 0) {
			mViewHint.setText("");
		} else if (mStateHints.length == 1) {
			mViewHint.setText(mStateHints[0]);
		} else if (mStateHints.length >= 2) {
			mViewHint.setText(mStateHints[isRight ? 1 : 0]);
		}
	}
	
	public void setOnSwitchListener(OnSwitchListener l) {
		mOnSwitchListener = l;
	}
	
	public boolean isOnRight() {
		return isRight;
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
