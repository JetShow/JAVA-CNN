package cnn.layers;

import cnn.pojo.MaxPoolParam;

public class poolingLayer{
		
	public MaxPoolParam forwardPropagation(MaxPoolParam maxPoolParam) {
		// TODO Auto-generated method stub
		int[][][] indata = maxPoolParam.getLayerData();
		int[][][] outdata = 
				new int[maxPoolParam.getLayerChannel()][maxPoolParam.getLayerHeight()/maxPoolParam.getStrideY()][maxPoolParam.getLayerWidth()/maxPoolParam.getStrideX()];
		if(!checkStride(maxPoolParam.getLayerHeight(), 
				maxPoolParam.getLayerWidth(), 
				maxPoolParam.getStrideX(),
				maxPoolParam.getStrideY()))
		{
			System.err.println("invalidated boolSize!");
		}
		int inHeight = maxPoolParam.getLayerHeight();
		int inWidth = maxPoolParam.getLayerWidth();
		int poolX = maxPoolParam.getPoolX();
		int poolY = maxPoolParam.getPoolY();
		boolean[][][] maxLocation = maxPoolParam.getMaxLocation();
		for (int inc = 0; inc < indata.length; inc++) {
			for (int iny = 0,outy=0; iny < indata[inc].length; iny += inHeight, outy++) {
				for (int inx = 0,outx=0; inx < indata[inc][iny].length; inx += inWidth, outx++) {
					int max = Integer.MIN_VALUE;
					int maxY=0,maxX=0;
					for (int py = 0; py < poolY; py++) {
						for (int px = 0; px < poolX; px++) {
							//记录最大值位置
							if(max < indata[inc][iny+poolY][inx+poolX])
							{
								max = indata[inc][iny+poolY][inx+poolX];
								maxY = poolY;
								maxX = poolX;
							}
						}
					}
					maxLocation[inc][iny+maxY][inx+maxX] = true;
					outdata[inc][outy][outx] = max;
				}
			}
		}
		MaxPoolParam outParam = new MaxPoolParam();
		outParam.setLayerData(outdata);
		outParam.setMaxLocation(maxLocation);
		outParam.setLayerChannel(maxPoolParam.getLayerChannel());
		outParam.setLayerHeight(maxPoolParam.getLayerHeight()/maxPoolParam.getStrideY());
		outParam.setLayerWidth(maxPoolParam.getLayerWidth()/maxPoolParam.getStrideX());
		outParam.setPoolX(maxPoolParam.getPoolX());
		outParam.setPoolY(maxPoolParam.getPoolY());
		outParam.setStrideX(maxPoolParam.getStrideX());
		outParam.setStrideY(maxPoolParam.getStrideY());
		return outParam;
	}

	public MaxPoolParam backwardPropagation(MaxPoolParam maxPoolParam) {
		// TODO Auto-generated method stub
		float[][][] currentDelta = maxPoolParam.getLayerDelta();
		float[][][] preDelta = new 
				float[maxPoolParam.getLayerChannel()]
						[maxPoolParam.getLayerHeight()*maxPoolParam.getStrideY()]
								[maxPoolParam.getLayerWidth()*maxPoolParam.getStrideX()];
		int strideX = maxPoolParam.getStrideX();
		int strideY = maxPoolParam.getStrideY();
		boolean[][][] maxLocation = maxPoolParam.getMaxLocation();
		for (int outc = 0; outc < currentDelta.length; outc++) {
			for (int outy = 0, iny = 0; outy < currentDelta[outc].length; outy+=strideY, iny++) {
				for (int outx = 0, inx = 0; outx < preDelta[outc][iny].length; outx+=strideX, inx++) {
					for (int py = 0; py < strideY; py++) {
						for (int px = 0; px < strideX; px++) {
							if(maxLocation[outc][outy + py][outx + px])
							{
								preDelta[outc][outy + py][outx + px] = currentDelta[outc][iny][inx];
							}
						}
					}
					 
				}
			}
		}
		MaxPoolParam outParam = new MaxPoolParam();
		outParam.setLayerDelta(preDelta);
		outParam.setMaxLocation(maxLocation);
		outParam.setLayerChannel(maxPoolParam.getLayerChannel());
		outParam.setLayerHeight(maxPoolParam.getLayerHeight()*maxPoolParam.getStrideY());
		outParam.setLayerWidth(maxPoolParam.getLayerWidth()*maxPoolParam.getStrideX());
		outParam.setPoolX(maxPoolParam.getPoolX());
		outParam.setPoolY(maxPoolParam.getPoolY());
		outParam.setStrideX(maxPoolParam.getStrideX());
		outParam.setStrideY(maxPoolParam.getStrideY());
		return outParam;
		
	}
	private boolean checkStride(int inHeight, int inWidth, int strideX, int strideY)
	{
		return (0==inHeight%strideY)&&(0==inWidth%strideX)? true: false;
	}

}
