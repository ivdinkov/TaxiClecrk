/*
 * Copyright (c) 2014. Small-app.com
 */

package com.smallapp.taxiclerk;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

class DrawPie extends View {
	private Paint mPaint;
	private RectF mOval;
	private float mStart;
	private float mSweep = 0;
	private float mTop = -90.0f;
	private boolean use_centre = true;
	private float mExp, mAcc, mCash;
	private int mHeight;
	private int mWidth;
	private int mColor;
	private float mTextLeft;
	private float mTextTopSq;
	private String mText;
	private float mPadding;
	private float mExpCent;
	private float mAccCent;
	private float mCashCent;
	private float mTotal;
	private Paint mTextPaint;
	private int mTextColor;
	private float mTextSize;
	private float origGridHeight = 473.0f;
	private float gridDif;
	private int mGridHeight;

	public DrawPie(Context context, float exp, float acc, float cash) {
		super(context);
		this.mExp = exp;
		this.mAcc = acc;
		this.mCash = cash;

	}

	private void drawArcs(Canvas canvas) {
		mOval = new RectF(0, 0, mHeight - mPadding, mHeight - mPadding);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(mColor);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawArc(mOval, mStart, mSweep, use_centre, mPaint);
	}

	private void drawText(Canvas canvas) {
		mTextPaint = new Paint();
		mTextPaint.setColor(mTextColor);
		mTextPaint.setTextSize(mTextSize);
		canvas.drawText(mText, mTextLeft, mTextTopSq, mTextPaint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		ToCent tc = new ToCent();
		ToDegrees td = new ToDegrees();
		DecimalFormat dec = new DecimalFormat("00");
		
		mGridHeight = mHeight - mHeight / 7;
		gridDif = origGridHeight / mGridHeight;
		mTotal = getTotal(mExp, mAcc, mCash);
		
		if (mExp == 0.000001f && mCash == 0.000001f && mAcc == 0.000001f) {

			// expense grey
			mStart = mTop;
			mSweep = 120;
			mColor = 0x90FFFFFF;
			mPadding = mHeight / 10;
			drawArcs(canvas);

			mTextSize = mHeight / 8;
			mText = "0 %";
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = mHeight / 10;
			mTextColor = 0x90FFFFFF;
			drawText(canvas);

			// acc grey
			mStart = mStart + 120;
			mSweep = 120;
			mColor = 0x80FFFFFF;
			drawArcs(canvas);

			mTextSize = mHeight / 8;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = (mHeight / 10) * 4;
			mText = "0 %";
			mTextColor = 0x80FFFFFF;
			drawText(canvas);

			// cash grey
			mStart = mStart + 120;
			mSweep = 120;
			mColor = 0x70FFFFFF;
			drawArcs(canvas);

			mTextSize = mHeight / 8;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = (mHeight / 10) * 7;
			mText = "0 %";
			mTextColor = 0x70FFFFFF;
			drawText(canvas);

		} else {

			// expenses arc
			mColor = 0x99cc0000;
			mStart = mTop;
			mSweep = td.ConvertDeg(mExp, mTotal);
			mPadding = mHeight / 10;
			mExpCent = tc.ConvertCent(mExp, mTotal);
			drawArcs(canvas);

			// acc arc
			mColor = 0x880099cc;
			mStart = mStart + mSweep;
			mSweep = td.ConvertDeg(mAcc, mTotal);
			mAccCent = tc.ConvertCent(mAcc, mTotal);
			drawArcs(canvas);

			// cash arc
			mColor = 0x8800FF00;
			mStart = mStart + mSweep;
			mSweep = td.ConvertDeg(mCash, mTotal);
			mCashCent = tc.ConvertCent(mCash, mTotal);
			drawArcs(canvas);

			// cash percent text
			mTextSize = mHeight / 8;
			mTextColor = 0xFF00FF00;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = (mHeight / 10);
			mText = String.valueOf(dec.format(mCashCent)) + " %";
			drawText(canvas);
			
			// cash text
			mTextSize = mHeight / 12;
			mTextColor = 0xFFFFFFFF;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = (mHeight / 10) + 42/gridDif;
			mText = "CASH";
			drawText(canvas);

			// acc percent text
			mTextSize = mHeight / 8;
			mTextColor = 0xFF0099cc;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = (mHeight / 10) * 4;
			mText = String.valueOf(dec.format(mAccCent)) + " %";
			drawText(canvas);
			// acc text
			mTextSize = mHeight / 12;
			mTextColor = 0xFFFFFFFF;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = (mHeight / 10) * 4 + 42/gridDif;
			mText = "ACCOUNT";
			drawText(canvas);

			// expenses percent text
			mTextSize = mHeight / 8;
			mTextColor = 0xFFcc0000;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = mHeight / 10 * 7;
			mText = String.valueOf(dec.format(mExpCent)) + " %";
			drawText(canvas);

			// expenses text
			mTextSize = mHeight / 12;
			mTextColor = 0xFFFFFFFF;
			mTextLeft = mWidth - mWidth * 0.39f;
			mTextTopSq = mHeight / 10 * 7 + 42/gridDif;
			mText = "EXPENSES";
			drawText(canvas);
		}

	}

	private float getTotal(float mExp2, float mAcc2, float mCash2) {

		float total = 0;
		if (mExp2 != 0.000001)
			total += mExp2;
		if (mAcc2 != 0.000001)
			total += mAcc2;
		if (mCash2 != 0.000001)
			total += mCash2;
		return total;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.mWidth = w;
		this.mHeight = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}