package cnn.layers;

import java.util.Arrays;

import javax.swing.text.Position.Bias;

import cnn.utils.Padding;

public class convolutionalLayer {
//	int inHeight;
//	int inWidth;
//	int windowHeight;
//	int windowWidth;
//	int inCannels;
//	int outChannels;
//	Padding paddingType;
//	boolean hasBias;
//	int h_stride;
//	int w_stride;
//	float[][][][] convolutionalKernel;
//	float[] bias;
	
	
	//当前层数据数组
	int [][][] currentLayerData;
	//当前层误差数组
	float[][][] currentLayerDelta;
	//下一层误差数组
	float[][][] nextLayerDelta;
	//卷积核数组
	float[][][][] convolutionalKernel;
	//偏置项
	float[] bias;
	//通道数
	int layerChannel;
	//下一层通道数
	int nextLayerChannel;
	//数组高度
	int layerHeight;
	//数组宽度
	int layerWidth;
	//数据填充方式
	Padding paddingType;
	//卷积的height步长
	int hStride = 1;
	//卷积的width步长
	int wStride = 1;
	//卷积核高度
	int windowHeight;
	//卷积核宽度
	int windowWidth;

	
	/*
	 * 方法：前向传播
	 * param：inData 当前层的数据数组
	 * param：outData 下一层的数据数组
	 */
	public int[][][] forwardPropagation()
	{
		int[][][] inData = currentLayerData;
		int[][][] outData;
		if (Padding.same == paddingType) {
			outData = new int[nextLayerChannel]
					[layerHeight]
							[layerWidth];
		}
		else {
			outData = new int[nextLayerChannel]
					[layerHeight - hStride + 1]
							[layerWidth - wStride + 1]; 
		}
		int[][][] inDataPadded = this.paddingInData(inData, paddingType);
		//步长暂时设置为1
		for (int oc = 0; oc < nextLayerChannel; oc++) {

				for(int h = 0; h < inDataPadded[0].length-windowHeight+1; h ++)
				{
					for(int w = 0; w < inDataPadded[0][h].length-windowWidth+1; w++)
					{
						for (int ic = 0; ic < layerChannel; ic++) {
							for (int wh = 0; wh < windowHeight; wh++) {
								for (int ww = 0; ww < windowWidth; ww++) {
									outData[oc][h][w] += inDataPadded[ic][h+wh][w+ww] * convolutionalKernel[oc][ic][wh][ww]; 
								}
							}
						}
						outData[oc][h][w] += bias[oc];
					}
				}
		}
		return outData;
	}
	/*
	 * 方法：反向传播
	 * param：pretData 前一层的数据数组
	 * param：currentDelta 当前层的误差数据数组
	 * param：preDelta 前一层的误差数据数组
	 * param：b 偏置项
	 */
	public void backwardPropagation()
	{
		int[][][] pretData = currentLayerData; 
		float [][][] currentDelta = nextLayerDelta;
		float[] b = bias;
		float[][][] preDelta;
		float[][][] currentDeltaPadded = this.paddingInData(currentDelta, paddingType);
		preDelta = new float[currentDeltaPadded.length]
				[currentDeltaPadded[0].length - hStride + 1]
						[currentDeltaPadded[0][0].length - wStride + 1];
	
		for(int outc=0; outc < preDelta.length; outc++)
		{
			//计算前一层delta
			for(int inc=0; inc < currentDeltaPadded.length; inc++)
			{
				for (int iny = 0; iny < currentDeltaPadded[inc].length; iny++) {
					for (int inx = 0; inx < currentDeltaPadded[inc][iny].length; inx++) {
						float delta = currentDeltaPadded[inc][iny][inx];
						for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
							for (int wx = 0; wx <  convolutionalKernel[inc][outc][wy].length; wx++) {
								preDelta[outc][iny+wy][inx+wx] += delta * convolutionalKernel[inc][outc][wy][wx];
							}
						}
						
					}
				}
				//计算卷积核参数
				for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
					for (int wx = 0; wx < convolutionalKernel[inc][outc][wy].length; wx++) {
						for (int iny = 0; iny < currentDeltaPadded[inc].length; iny++) {
							for (int inx = 0; inx < currentDeltaPadded[inc][iny].length; inx++) {
								convolutionalKernel[inc][outc][wy][wx] += currentDeltaPadded[inc][iny][inx] * (float)pretData[inc][iny+wy][inx+wx];
							}
						}
					}
				}
				//计算偏置项
				for (int iny = 0; iny < currentDelta[inc].length; iny++) {
					for (int inx = 0; inx < currentDelta[inc][iny].length; inx++) {
						b[inc] += currentDelta[inc][iny][inx];
					}
				}
			}
		}
	}

	/*
	 * 方法：数组填充
	 * 根据填充方式填充数组，包括前向传播数据填充和反向传播误差数据填充
	 * 使用System.arrayCopy方式
	 * param：inData 需要填充的数组
	 * param：paddingType 填充方式
	 */
	
	private int[][][] paddingInData(int[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			int[][][] buf = new int[inData.length][inData[0].length+windowHeight-1][inData[0][0].length+windowWidth-1];
			for(int c = 0; c < inData.length; c++)
			{
				for(int h = windowHeight/2; h < inData[c].length; h++)
				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
					System.arraycopy(inData[c][h], 0, buf[c][h], windowWidth/2, inData[c][h].length);
				}
			}
			return buf;
		}
		return null;
	}
	/*
	 * paddingInData方法重载，适用于误差数组
	 */
	private float[][][] paddingInData(float[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			float[][][] buf = new float[inData.length][inData[0].length+windowHeight-1][inData[0][0].length+windowWidth-1];
			for(int c = 0; c < inData.length; c++)
			{
				for(int h = windowHeight/2; h < inData[c].length; h++)
				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
					System.arraycopy(inData[c][h], 0, buf[c][h], windowWidth/2, inData[c][h].length);
				}				
			}
			return buf;
		}
		return null;
	}
}





