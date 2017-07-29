package cnn.pojo;

public class MaxPoolParam {
	//�ػ�����������
	int [][][] layerData;
	//�ػ����������
	float[][][] layerDelta;
	//��¼���λ������
	boolean[][][] maxLocation;
	//��������ͨ����
	int layerChannel;
	//��������߶�
	int layerHeight;
	//����������
	int layerWidth;	
	//X����ػ��ߴ�
	int poolX;
	//Y����ػ��ߴ�
	int poolY;
	//�ػ�X�����ƶ�����
	int strideX;
	//�ػ�Y�����ƶ�����
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
