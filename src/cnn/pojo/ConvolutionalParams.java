package cnn.pojo;

import cnn.utils.Padding;

public class ConvolutionalParams {
	//��ǰ����������
	int [][][] layerData;
	//��ǰ���������
	float[][][] layerDelta;
	//���������
	float[][][][] convolutionalKernel;
	//ƫ����
	float[] bias;
	//ͨ����
	int layerChannel;
	//����߶�
	int layerHeight;
	//������
	int layerWidth;
	//������䷽ʽ
	Padding paddingType;
	//�����height����
	int hStride = 1;
	//�����width����
	int wStride = 1;
	//����˸߶�
	int windowHeight;
	//����˿��
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
