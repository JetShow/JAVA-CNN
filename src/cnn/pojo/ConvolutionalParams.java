package cnn.pojo;

import cnn.utils.Padding;

public class ConvolutionalParams {
	//当前层数据数组
	int [][][] layerData;
	//当前层误差数组
	float[][][] layerDelta;
	//卷积核数组
	float[][][][] convolutionalKernel;
	//偏置项
	float[] bias;
	//通道数
	int layerChannel;
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
	
	
	
	public ConvolutionalParams() {
		this.layerData = layerData;
		this.layerDelta = layerDelta;
		this.convolutionalKernel = convolutionalKernel;
		this.bias = bias;
		this.layerChannel = layerChannel;
		this.layerHeight = layerHeight;
		this.layerWidth = layerWidth;
		this.paddingType = paddingType;
		this.hStride = hStride;
		this.wStride = wStride;
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
	}
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
	public float[][][][] getConvolutionalKernel() {
		return convolutionalKernel;
	}
	public void setConvolutionalKernel(float[][][][] convolutionalKernel) {
		this.convolutionalKernel = convolutionalKernel;
	}
	public float[] getBias() {
		return bias;
	}
	public void setBias(float[] bias) {
		this.bias = bias;
	}
	public Padding getPaddingType() {
		return paddingType;
	}
	public void setPaddingType(Padding paddingType) {
		this.paddingType = paddingType;
	}
	public int gethStride() {
		return hStride;
	}
	public void sethStride(int hStride) {
		this.hStride = hStride;
	}
	public int getwStride() {
		return wStride;
	}
	public void setwStride(int wStride) {
		this.wStride = wStride;
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
	public int getWindowHeight() {
		return windowHeight;
	}
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}
	public int getWindowWidth() {
		return windowWidth;
	}
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}
	
}
