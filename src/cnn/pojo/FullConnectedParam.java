package cnn.pojo;

public class FullConnectedParam {
	int[] layerData;
	
	int[] layerDelta;
	
	float[][] weight;
	
	float[][] weightDelta;
	
	int layerDim;
	
	int nextLayerDim;
	
	int[] bias;

	public int[] getLayerData() {
		return layerData;
	}

	public void setLayerData(int[] layerData) {
		this.layerData = layerData;
	}

	public int[] getLayerDelta() {
		return layerDelta;
	}

	public void setLayerDelta(int[] layerDelta) {
		this.layerDelta = layerDelta;
	}

	public int getLayerDim() {
		return layerDim;
	}

	public void setLayerDim(int layerDim) {
		this.layerDim = layerDim;
	}

	public int[] getBias() {
		return bias;
	}

	public void setBias(int[] bias) {
		this.bias = bias;
	}

	public int getNextLayerDim() {
		return nextLayerDim;
	}

	public void setNextLayerDim(int nextLayerDim) {
		this.nextLayerDim = nextLayerDim;
	}

	public float[][] getWeight() {
		return weight;
	}

	public void setWeight(float[][] weight) {
		this.weight = weight;
	}

	public float[][] getWeightDelta() {
		return weightDelta;
	}

	public void setWeightDelta(float[][] weightDelta) {
		this.weightDelta = weightDelta;
	}
	
}
