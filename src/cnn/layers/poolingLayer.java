package cnn.layers;

public class poolingLayer{
	//当前层数据数组
	int [][][][] currentLayerData;
	//当前层误差数组
	float[][][][] currentLayerDelta;
	//下一层误差数组
	float[][][][] nextLayerDelta;
	//记录最大位置数组
	boolean[][][][] maxLocation;
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
	//样本数
	int sample;
	
	public poolingLayer(int layerChannel, int layerHeight, int layerWidth, 
			int poolX, int poolY, int strideX, int strideY, int sample ,int[][][][] inData) {
		this.layerChannel = layerChannel;
		this.layerHeight = layerHeight;
		this.layerWidth = layerWidth;
		this.poolX = poolX;
		this.poolY = poolY;
		this.strideX = strideX;
		this.strideY = strideY;
		this.sample = sample;
		currentLayerData = inData;
		currentLayerDelta = new float[sample][layerChannel][layerHeight][layerWidth];
		nextLayerDelta = new float[sample][layerChannel][layerHeight/strideY][layerWidth/strideX];
		maxLocation = new boolean[sample][layerChannel][layerHeight][layerWidth];
	}

	public int[][][][] forwardPropagation() {
		// TODO Auto-generated method stub
		int[][][][] indata = currentLayerData;
		int[][][][] outdata = 
				new int[sample][layerChannel][layerHeight/strideY][layerWidth/strideX];
		if(!checkStride(layerHeight, 
						layerWidth, 
						strideX,
						strideY))
		{
			System.err.println("invalidated boolSize!");
		}
		for (int sp = 0; sp < sample; sp++) {
			for (int inc = 0; inc < indata[sp].length; inc++) {
				for (int iny = 0,outy=0; iny < indata[sp][inc].length; iny += strideY, outy++) {
					for (int inx = 0,outx=0; inx < indata[sp][inc][iny].length; inx += strideY, outx++) {
						int max = Integer.MIN_VALUE;
						int maxY=0,maxX=0;
						for (int py = 0; py < poolY; py++) {
							for (int px = 0; px < poolX; px++) {
								//记录最大值位置
								if(max < indata[sp][inc][iny+py][inx+px])
								{
									max = indata[sp][inc][iny+py][inx+px];
									maxY = py;
									maxX = px;
								}
							}
						}
						maxLocation[sp][inc][iny+maxY][inx+maxX] = true;
						outdata[sp][inc][outy][outx] = max;
					}
				}
			}
		}
		
		return outdata;
	}

	public void backwardPropagation() {
		// TODO Auto-generated method stub
		float[][][][] currentDelta = nextLayerDelta;
//		float[][][] preDelta = new 
//				float[layerChannel]
//						[layerHeight*strideY]
//								[layerWidth*strideX];
		for (int sp = 0; sp < sample; sp++) {
			for (int outc = 0; outc < currentDelta[sp].length; outc++) {
				for (int outy = 0, iny = 0; outy < currentDelta[sp][outc].length; outy+=strideY, iny++) {
					for (int outx = 0, inx = 0; outx < currentLayerDelta[sp][outc][iny].length; outx+=strideX, inx++) {
						for (int py = 0; py < strideY; py++) {
							for (int px = 0; px < strideX; px++) {
								if(maxLocation[sp][outc][outy + py][outx + px])
								{
									currentLayerDelta[sp][outc][outy + py][outx + px] = currentDelta[sp][outc][iny][inx];
								}
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

	public int[][][][] getCurrentLayerData() {
		return currentLayerData;
	}

	public void setCurrentLayerData(int[][][][] currentLayerData) {
		this.currentLayerData = currentLayerData;
	}

	public float[][][][] getCurrentLayerDelta() {
		return currentLayerDelta;
	}

	public void setCurrentLayerDelta(float[][][][] currentLayerDelta) {
		this.currentLayerDelta = currentLayerDelta;
	}

	public float[][][][] getNextLayerDelta() {
		return nextLayerDelta;
	}

	public void setNextLayerDelta(float[][][][] nextLayerDelta) {
		this.nextLayerDelta = nextLayerDelta;
	}

	public boolean[][][][] getMaxLocation() {
		return maxLocation;
	}

	public void setMaxLocation(boolean[][][][] maxLocation) {
		this.maxLocation = maxLocation;
	}

	public int getLayerChannel() {
		return layerChannel;
	}

	public void setLayerChannel(int layerChannel) {
		this.layerChannel = layerChannel;
	}

	public int getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(int layerHeight) {
		this.layerHeight = layerHeight;
	}

	public int getLayerWidth() {
		return layerWidth;
	}

	public void setLayerWidth(int layerWidth) {
		this.layerWidth = layerWidth;
	}

	public int getPoolX() {
		return poolX;
	}

	public void setPoolX(int poolX) {
		this.poolX = poolX;
	}

	public int getPoolY() {
		return poolY;
	}

	public void setPoolY(int poolY) {
		this.poolY = poolY;
	}

	public int getStrideX() {
		return strideX;
	}

	public void setStrideX(int strideX) {
		this.strideX = strideX;
	}

	public int getStrideY() {
		return strideY;
	}

	public void setStrideY(int strideY) {
		this.strideY = strideY;
	}

	public int getSample() {
		return sample;
	}

	public void setSample(int sample) {
		this.sample = sample;
	}
	
	
	
}
