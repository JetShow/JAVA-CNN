package cnn.layers;

import javax.xml.transform.Templates;

import cnn.utils.WeightInitializeUtil;

public class FullConnectedLayer {
	//第L-1层的数据
	float[][] preLayerData;
	//第L层的数据
	float[][] currentLayerData;
	//第L-1层的误差数组
	float[][] preLayerDelta;
	//第L层的误差数组
	float[][] currentLayerDelta;
	//第L层的偏置数组
	float[] bias;
	//第L层的权重数组
	float[][] weight;
	//第L-1层的维度
	int preLayerDim;
	//第L层的维度
	int currentLayerDim;
	//样本数
	int sample;
	
//	float sampleFactor = 1/sample;
				
	public FullConnectedLayer(int preLayerDim, int currentLayerDim, int sample, float[][] preLayerData) {
		this.preLayerDim = preLayerDim;
		this.currentLayerDim = currentLayerDim;
		this.preLayerData = preLayerData;
		this.sample = sample;
		this.preLayerData = preLayerData;
		this.preLayerDelta = new float[sample][preLayerDim];
		this.currentLayerData = new float[sample][currentLayerDim];
		this.currentLayerDelta = new float[sample][currentLayerDim];
		this.bias = new float[currentLayerDim];
		this.weight = new float[preLayerDim][currentLayerDim];
		WeightInitializeUtil.xavier(weight, preLayerDim, currentLayerDim);
		
	}
	public float[][] forwordPropagation()
	{
		for (int sp = 0; sp < sample; sp++) {
			for (int outc = 0; outc < currentLayerDim; outc++) {
				float temp = 0;
				for (int inc = 0; inc < preLayerDim; inc++) {
					temp += preLayerData[sp][inc] * weight[inc][outc];
				}
				currentLayerData[sp][outc] = temp;
			}
		}
		ActivationFunctions.tanh(currentLayerData);
		return currentLayerData;
	}
	public void backwardPropagation() {
	
		float[][] preLayerDelta = new float[sample][preLayerDim];
		for (int sp = 0; sp < sample; sp++) {
			for (int i = 0; i < preLayerDim; i++) {
				for (int j = 0; j < currentLayerDim; j++) {
					preLayerDelta[sp][i] += currentLayerDelta[sp][j] * weight[i][j]; //* (1-preLayerData[sp][i]*preLayerData[sp][i]);
				}
			}
			for(int wh = 0; wh < weight.length; wh++)
			{
				for (int ww = 0; ww < weight[wh].length; ww++) {
					//防止权值爆炸 * 0.02
					weight[wh][ww] -= currentLayerDelta[sp][ww] * preLayerData[sp][wh] / sample;
				}
			}
			
			for(int i = 0; i < bias.length; i++ )
			{
				bias[i] -= currentLayerDelta[sp][i] / sample;
			}
		}
		this.preLayerDelta = preLayerDelta;
	}
	public float[][] getPreLayerData() {
		return preLayerData;
	}
	public void setPreLayerData(float[][] preLayerData) {
		this.preLayerData = preLayerData;
	}
	public float[][] getCurrentLayerData() {
		return currentLayerData;
	}
	public void setCurrentLayerData(float[][] currentLayerData) {
		this.currentLayerData = currentLayerData;
	}
	public float[][] getPreLayerDelta() {
		return preLayerDelta;
	}
	public void setPreLayerDelta(float[][] preLayerDelta) {
		this.preLayerDelta = preLayerDelta;
	}
	public float[][] getCurrentLayerDelta() {
		return currentLayerDelta;
	}
	public void setCurrentLayerDelta(float[][] currentLayerDelta) {
		this.currentLayerDelta = currentLayerDelta;
	}
	public float[] getBias() {
		return bias;
	}
	public void setBias(float[] bias) {
		this.bias = bias;
	}
	public float[][] getWeight() {
		return weight;
	}
	public void setWeight(float[][] weight) {
		this.weight = weight;
	}
	public int getPreLayerDim() {
		return preLayerDim;
	}
	public void setPreLayerDim(int preLayerDim) {
		this.preLayerDim = preLayerDim;
	}
	public int getCurrentLayerDim() {
		return currentLayerDim;
	}
	public void setCurrentLayerDim(int currentLayerDim) {
		this.currentLayerDim = currentLayerDim;
	}
	public int getSample() {
		return sample;
	}
	public void setSample(int sample) {
		this.sample = sample;
	}
	
}
