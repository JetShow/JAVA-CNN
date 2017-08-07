package cnn.utils;

import java.time.Year;

public class LossFunctions {
	/*
	 * mse均方差损失函数
	 */
	public static float mse(float[] inData, float[] target)
	{
		float d = 0;
		if (inData.length != target.length) {
			System.out.println("LossFunctions.mse.Array_lenth_error");
			return -1;
		}
		for (int i = 0; i < target.length; i++) {
			d += (inData[i] - target[i]) * (inData[i] - target[i]);
		}
		return (d/(float)inData.length);
	}
	/*
	 * mse均方差损失函数的求导
	 */
	public static float[] mseForRegression(float[] inData, float[] target)
	{
		if (inData.length != target.length) {
			System.out.println("LossFunctions.mseForRegression.Array_lenth_error");
			return null;
		}
		float[] d = new float[inData.length];
		float factor = ((float)2 /(float)inData.length);
		for (int i = 0; i < target.length; i++) {
			d[i] = factor * (inData[i] - target[i]);
		}
		return d;
	}
	
	/*
	 * Cross-Entropy 交叉熵损失函数
	 */
	public static float ce(float[] inData, float[] target)
	{
		if (inData.length != target.length) {
			System.out.println("LossFunctions.ce.Array_lenth_error");
			return -1;
		}
		float d = 0;
		for (int i = 0; i < target.length; i++) {
			d += -target[i] * Math.log(inData[i]) - ((float)1-target[i]) * Math.log(inData[i]);
		}
		return d;
	}
	/*
	 * Cross-Entropy 交叉熵损失求导
	 */
	public static float[] ceForRegression(float[] inData, float[] target) {
		if (inData.length != target.length) {
			System.out.println("LossFunctions.ce.Array_lenth_error");
			return null;
		}
		float[] d = new float[inData.length];
		
		for (int i = 0; i < target.length; i++) {
			//factor是softmax函数的求导
			float factor = inData[i] * (1-inData[i]);
			d[i] = (inData[i] - target[i])/(inData[i] * (1-inData[i])) * factor;
		}
		return d;
	}
	
}
