package cnn.pojo;

public class MaxPoolParam {
	//池化层数据数组
	int [][][] layerData;
	//池化层误差数组
	float[][][] layerDelta;
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
	public int[][][] getLayerData() {
		return layerData;
	}
	public void setLayerData(int[][][] layerData) {
		this.layerData = layerData;
	}
	public float[][][] getLayerDelta() {
		return layerDelta;
	}
	public void setLayerDelta(float[][][] layerDelta) {
		this.layerDelta = layerDelta;
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
	public boolean[][][] getMaxLocation() {
		return maxLocation;
	}
	public void setMaxLocation(boolean[][][] maxLocation) {
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
	
	
}
