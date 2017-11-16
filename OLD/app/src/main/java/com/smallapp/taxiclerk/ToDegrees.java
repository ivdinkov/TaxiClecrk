/*
 * Copyright (c) 2014. Small-app.com
 */

package com.smallapp.taxiclerk;

public class ToDegrees {
	public float ConvertDeg(Float a, Float b) {
		float res = 0;
		float deg = 360.0f / b;
		res = deg * a;
		return res;

	}
}
