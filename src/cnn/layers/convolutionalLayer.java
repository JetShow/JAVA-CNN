package cnn.layers;

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
	}
	
	public void forwardPropagation(int[][][] indata,int[][][] outdata)
	{
		
	}
	public void backwardPropagation(int[][][] inData, int[][][] outData)
	{
		
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
				for(int h = 0; h < inHeight; h++)
				{
					for(int w = 0; w < inWidth; w++)
					{
						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
								inData[c][h][w];
					}
				}
			}
			return buf;
		}
		return null;
	}
}
