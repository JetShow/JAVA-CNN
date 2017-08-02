package cnn.layers;

public class poolingLayer{
	//当前层数据数组
	int [][][] currentLayerData;
	//当前层误差数组
	float[][][] currentLayerDelta;
	//下一层误差数组
	float[][][] nextLayerDelta;
	//记录最大位置数组
	boolean[][][] maxLocation;
	//数据数组通道数
	int layerChannel;
	//数据数组高度
	int layerHeight;
	//数据数组宽度
	int layerWidth;	
	//X方向池化尺寸
	int poolX;
	//Y方向池化尺寸
	int poolY;
	//池化X方向移动步长
	int strideX;
	//池化Y方向移动步长
	int strideY;
	
	public poolingLayer(int layerChannel, int layerHeight, int layerWidth, 
			int poolX, int poolY, int strideX, int strideY) {
		this.layerChannel = layerChannel;
		this.layerHeight = layerHeight;
		this.layerWidth = layerWidth;
		this.poolX = poolX;
		this.poolY = poolY;
		this.strideX = strideX;
		this.strideY = strideY;
		currentLayerData = new int[layerChannel][layerHeight][layerWidth];
		currentLayerDelta = new float[layerChannel][layerHeight][layerWidth];
		nextLayerDelta = new float[layerChannel][layerHeight/strideY][layerWidth/strideX];
		maxLocation = new boolean[layerChannel][layerHeight][layerWidth];
	}

	public int[][][] forwardPropagation() {
		// TODO Auto-generated method stub
		int[][][] indata = currentLayerData;
		int[][][] outdata = 
				new int[layerChannel][layerHeight/strideY][layerWidth/strideX];
		if(!checkStride(layerHeight, 
						layerWidth, 
						strideX,
						strideY))
		{
			System.err.println("invalidated boolSize!");
		}
		int inHeight = layerHeight;
		int inWidth = layerWidth;
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
		return outdata;
	}

	public void backwardPropagation() {
		// TODO Auto-generated method stub
		float[][][] currentDelta = nextLayerDelta;
//		float[][][] preDelta = new 
//				float[layerChannel]
//						[layerHeight*strideY]
//								[layerWidth*strideX];
		for (int outc = 0; outc < currentDelta.length; outc++) {
			for (int outy = 0, iny = 0; outy < currentDelta[outc].length; outy+=strideY, iny++) {
				for (int outx = 0, inx = 0; outx < currentLayerDelta[outc][iny].length; outx+=strideX, inx++) {
					for (int py = 0; py < strideY; py++) {
						for (int px = 0; px < strideX; px++) {
							if(maxLocation[outc][outy + py][outx + px])
							{
								currentLayerDelta[outc][outy + py][outx + px] = currentDelta[outc][iny][inx];
							}
						}
					}
					 
				}
			}
		}		
	}
	private boolean checkStride(int inHeight, int inWidth, int strideX, int strideY)
	{
		return (0==inHeight%strideY)&&(0==inWidth%strideX)? true: false;
	}

}
