package cnn.layers;

import java.util.Arrays;

import javax.swing.text.Position.Bias;

import cnn.utils.Padding;

public class convolutionalLayer implements layer {
	int inHeight;
	int inWidth;
	int windowHeight;
	int windowWidth;
	int inCannels;
	int outChannels;
	Padding paddingType;
	boolean hasBias;
	int h_stride;
	int w_stride;
	float[][][][] convolutionalKernel;
	float[] bias;
	
	public convolutionalLayer(int inHeight, 
							  int inWidth, 
							  int windowHeight,
							  int windowWidth,
							  int inChannels,
							  int outChannels,
							  Padding paddingType,
							  boolean hasBias,
							  int h_stride,
							  int w_stride
							  ) {
		// TODO Auto-generated constructor stub
		this.inHeight= inHeight;
		this.inWidth= inWidth;
		this.windowHeight= windowHeight;
		this.windowWidth= windowWidth;
		this.inCannels= inChannels;
		this.outChannels = outChannels;
		this.paddingType = paddingType;
		this.hasBias = hasBias;
		this.h_stride = h_stride;
		this.w_stride = w_stride;
		this.convolutionalKernel = new float[outChannels][inChannels][windowHeight][windowWidth];
		this.bias = new float[outChannels];
	}
	
	public void forwardPropagation(int[][][] inData,int[][][] outData)
	{
		int[][][] inDataPadded = this.paddingInData(inData, paddingType);
		for (int oc = 0; oc < outChannels; oc++) {

				for(int h = 0; h < inDataPadded[0].length-windowHeight+1; h ++)
				{
					for(int w = 0; w < inDataPadded[0][h].length-windowWidth+1; w++)
					{
						for (int ic = 0; ic < inCannels; ic++) {
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
		
	}
	public void backwardPropagation(int[][][] inData, int[][][] outData)
	{
		int[][][] inDataPadded = this.paddingInData(inData, paddingType);
		for(int c=0; c < outChannels; c++)
		{
			
		}
		
	}
	private int[][][] paddingInData(int[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			int[][][] buf = new int[inCannels][inHeight+windowHeight/2][inWidth+windowWidth/2];
			for(int c = 0; c < inCannels; c++)
			{
//				for(int h = 0; h < inHeight; h++)
//				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
//				}
				System.arraycopy(inData, inData[c][0][0], buf, buf[c][windowHeight/2][windowWidth/2], inData[c].length);
			}
			return buf;
		}
		return null;
	}
}
