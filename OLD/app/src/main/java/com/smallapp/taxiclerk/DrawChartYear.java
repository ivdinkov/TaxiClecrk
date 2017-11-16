/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

class DrawChartYear extends View {

	private int mHeight;
	private String mText;
	private Paint mTextPaint;
	private float mTextLeft;
	private float mTextTopSq;
	private int mWidth;
	private float mStartX;
	private float mStartY;
	private float mStopX;
	private float mStopY;
	private Paint mLinePaint;
	private int mGridHeight;
	private int mGridWidth;
	private int mUnitWidth;
	private float origGridHeight = 473.0f;
	private float gridDif;
	private String[][] incomeResult;
	private String[] expResult;
	private String[] monthNames;
	private static final int WHITE = 0xFFFFFFFF;
	private static final int GREEN = 0x8000FF00;
	private static final int BLUE = 0x800099cc;
	private static final int RED = 0x80cc0000;

	public DrawChartYear(Context context, String[][] incomeResult, String[] expenseResult) {
		super(context);
		this.incomeResult = incomeResult;
		this.expResult = expenseResult;
		monthNames = getResources().getStringArray(R.array.month_names);
	}

	private void drawLine(Canvas canvas, int color, int t) {
		mLinePaint = new Paint();
		mLinePaint.setAntiAlias(true);
		mLinePaint.setColor(color);
		mLinePaint.setStrokeWidth(t);
		mLinePaint.setStyle(Paint.Style.STROKE);
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

		/**************** Draw grid ****************/

		mGridHeight = mHeight - mHeight / 7;
		mGridWidth = mWidth - mWidth / 22;
		mUnitWidth = mGridWidth / 48;
		gridDif = origGridHeight / mGridHeight;
		/*
		 * Draw Y
		 */
		mStartY = 0;
		mStartX = mUnitWidth;
		mStopY = mGridHeight;
		mStopX = mUnitWidth;
		drawLine(canvas, WHITE, 1);
		/*
		 * Draw X
		 */
		mStartY = mGridHeight;
		mStartX = mUnitWidth;
		mStopY = mGridHeight;
		mStopX = mGridWidth;
		drawLine(canvas, WHITE, 1);
		/*
		 * Draw Days Text
		 */
		int i = 0;
		for (int m = 0; m < 12; m++) {
			mText = monthNames[m];
			mTextLeft = mUnitWidth * (2 + i);
			mTextTopSq = mHeight - (15 / gridDif);
			drawText(canvas);
			i += 4;
		}
		/**************** Draw grid end ****************/
		/*
		 * 
		 * Draw monthly income/expense
		 */

		int p = 0;
		for (int m = 0; m < 11; m++) {

			mStartY = mGridHeight - (Float.valueOf(incomeResult[m][0]) / 17) / gridDif;
			mStartX = mUnitWidth * (2 + p);
			mStopY = mGridHeight - (Float.valueOf(incomeResult[m + 1][0]) / 17) / gridDif;
			mStopX = mUnitWidth * (6 + p);
			drawLine(canvas, GREEN, 4);
			mStartY = mGridHeight - (Float.valueOf(incomeResult[m][1]) / 17) / gridDif;
			mStartX = mUnitWidth * (2 + p);
			mStopY = mGridHeight - (Float.valueOf(incomeResult[m + 1][1]) / 17) / gridDif;
			mStopX = mUnitWidth * (6 + p);
			drawLine(canvas, BLUE, 4);
			mStartY = mGridHeight - (Float.valueOf(expResult[m]) / 17) / gridDif;
			mStartX = mUnitWidth * (2 + p);
			mStopY = mGridHeight - (Float.valueOf(expResult[m + 1]) / 17) / gridDif;
			mStopX = mUnitWidth * (6 + p);
			drawLine(canvas, RED, 4);
			p += 4;
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.mWidth = w;
		this.mHeight = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}