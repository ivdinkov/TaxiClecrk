/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

class DrawChartWeek extends View {
	
	private int mHeight;
	private int mColor;
	private String mText;
	
	private Paint mTextPaint;
	private float mTextLeft;
	private float mTextTopSq;
	
	private int mWidth;
	private float mLeftX;
	private float mTopY;
	private float mStartX;
	private float mStartY;
	private float mStopX;
	private float mStopY;
	private float mRightX;
	private float mBottomY;
	private Paint mLinePaint;
	private Paint mRectPaint;
	private int mGridHeight;
	private int mGridWidth;
	private int mUnitWidth;
	private float origGridHeight = 473.0f;
	private float gridDif;
	private String[][] weekDays;
	private String[] expResult;	
	private String[] weekDaysLeters;
	private static final int WHITE = 0xFFFFFFFF;
	private static final int GREEN = 0x8000FF00;
	private static final int BLUE = 0x800099cc;
	private static final int RED = 0x80cc0000;

	public DrawChartWeek(Context context, String[][] incomeResult, String[] expenseResult) {
		super(context);
		this.weekDays = incomeResult;
		this.expResult = expenseResult;
		weekDaysLeters = getResources().getStringArray(R.array.week_days);
	}

	private void drawRect(Canvas canvas) {
		mRectPaint = new Paint();
		mRectPaint.setAntiAlias(true);
		mRectPaint.setColor(mColor);
		mRectPaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(mLeftX, mTopY, mRightX, mBottomY, mRectPaint);
	}

	private void drawLine(Canvas canvas) {
		mLinePaint = new Paint();
		mLinePaint.setAntiAlias(true);
		mLinePaint.setColor(WHITE);
		mLinePaint.setStyle(Paint.Style.FILL);
		canvas.drawLine(mStartX, mStartY, mStopX, mStopY, mLinePaint);
	}

	private void drawText(Canvas canvas) {
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(WHITE);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setTextSize(mHeight / 13);
		canvas.drawText(mText, mTextLeft, mTextTopSq, mTextPaint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		DecimalFormat dec = new DecimalFormat("00");

		/**************** Draw grid ****************/

		mGridHeight = mHeight - mHeight / 7;
		mGridWidth = mWidth - mWidth / 22;
		mUnitWidth = mGridWidth / 29;
		gridDif = origGridHeight/mGridHeight;
		
		/*
		 * Draw Y
		 */
		mStartY = 0;
		mStartX = mUnitWidth;
		mStopY = mGridHeight;
		mStopX = mUnitWidth;
		drawLine(canvas);
		/*
		 * Draw X
		 */
		mStartY = mGridHeight;
		mStartX = mUnitWidth;
		mStopY = mGridHeight;
		mStopX = mGridWidth;
		drawLine(canvas);
		/*
		 * Draw Days Text
		 */
		int i = 0;
		for (int m = 0; m < 7; m++) {
			mText = weekDaysLeters[m];
			mTextLeft = mUnitWidth * (3 + i);
			mTextTopSq = mHeight - (15/gridDif);
			drawText(canvas);
			i += 4;
		}

		/**************** Draw grid end ****************/
		/*
		 * 
		 * Draw daily income/expense
		 */
		int s = 0;
		for (int f = 0; f < 7; f++) {
			mLeftX = mUnitWidth * (2 + s);
			mTopY = mGridHeight - Float.valueOf(weekDays[f][0])/gridDif;
			mRightX = mUnitWidth * (3 + s);
			mBottomY = mGridHeight;
			mColor = GREEN;
			drawRect(canvas);
			mLeftX = mUnitWidth * (3 + s);
			mTopY = mGridHeight - Float.valueOf(weekDays[f][1])/gridDif;
			mRightX = mUnitWidth * (4 + s);
			mBottomY = mGridHeight;
			mColor = BLUE;
			drawRect(canvas);
			mLeftX = mUnitWidth * (4 + s);
			mTopY = mGridHeight - Float.valueOf(expResult[f])/gridDif;
			mRightX = mUnitWidth * (5 + s);
			mBottomY = mGridHeight;
			mColor = RED;
			drawRect(canvas);
			s += 4;
		}
		
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.mWidth = w;
		this.mHeight = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}