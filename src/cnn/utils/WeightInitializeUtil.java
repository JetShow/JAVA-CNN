package cnn.utils;

import java.util.Random;

public class WeightInitializeUtil {
	//Xavier权值初始化方法
	public static void xavier(float[][][][] weight, int fanIn, int fanOut)
	{
		Random random = new Random();
		double basicNum = Math.sqrt((double)6/(double)(fanIn+fanOut));
		double area = 2 * basicNum;
		for (int outc = 0; outc < weight.length; outc++) {
			for (int inc = 0; inc < weight[outc].length; inc++) {
				for (int wh = 0; wh < weight[outc][inc].length; wh++) {
					for (int ww = 0; ww < weight[outc][inc][wh].length; ww++) {
						//[-sqrt(6/(fanIn+fanOut)),sqrt(6/(fanIn+fanOut))]的均匀分布
						weight[outc][inc][wh][ww] =(float) (-basicNum + random.nextDouble() * area);
					}
				}
			}
		}		
	}
}
