package cnn.utils;

public class CifarData {
	int[][][][] data;
	int[] dataLable;
	float[][][][] normalizationData;
	public int[][][][] getData() {
		return data;
	}
	public void setData(int[][][][] data) {
		this.data = data;
	}
	public int[] getDataLable() {
		return dataLable;
	}
	public void setDataLable(int[] dataLable) {
		this.dataLable = dataLable;
	}
	public float[][][][] getNormalizationData() {
		return normalizationData;
	}
	public void setNormalizationData(float[][][][] normalizationData) {
		this.normalizationData = normalizationData;
	}
	
	
}

