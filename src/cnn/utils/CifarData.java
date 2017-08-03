package cnn.utils;

public class CifarData {
	int[][][][] data;
	int[] dataLable;
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
	
}

