package cnn.layers;

import cnn.utils.Padding;

public class convolutionalLayer implements layer {
	int height;
	int width;
	int windowHeight;
	int windowWidth;
	int inCannels;
	int outChannels;
	Padding paddingType;
	boolean hasBias;
	int h_stride;
	int w_stride;
	public convolutionalLayer(int height, 
							  int width, 
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
		this.height= height;
		this.width= width;
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
			
		}
		return null;
	}
}
