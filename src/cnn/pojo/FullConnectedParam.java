package cnn.pojo;

public class FullConnectedParam {
	int[] preLayerData;
	
	int[] layerData;
	
	float[] layerDelta;
	
	float[][] weight;
	
	float[][] weightDelta;
	
	int prelayerDim;
	
	int layerDim;
	
	int nextLayerDim;
	
	float[] bias;

	public int[] getLayerData() {
		return layerData;
	}

	public void setLayerData(int[] layerData) {
		this.layerData = layerData;
	}

	public float[] getLayerDelta() {
		return layerDelta;
	}

	public void setLayerDelta(float[] layerDelta) {
		this.layerDelta = layerDelta;
	}

	public int getLayerDim() {
		return layerDim;
	}

	public void setLayerDim(int layerDim) {
		this.layerDim = layerDim;
	}

	public float[] getBias() {
		return bias;
	}

	public void setBias(float[] bias) {
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

	public int getPrelayerDim() {
		return prelayerDim;
	}

	public void setPrelayerDim(int prelayerDim) {
		this.prelayerDim = prelayerDim;
	}

	public int[] getPreLayerData() {
		return preLayerData;
	}

	public void setPreLayerData(int[] preLayerData) {
		this.preLayerData = preLayerData;
	}
	
}
