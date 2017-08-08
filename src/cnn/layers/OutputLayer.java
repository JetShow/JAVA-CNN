package cnn.layers;

import cnn.utils.LossFunctions;

public class OutputLayer {
	float[][] inputLayerData;
	
	float[][] outPutLayerData;
	
	float[][] targetData;
	
	float[][] layerDelta;
	
	int inputDim;
	
	int sample;
	
	public OutputLayer(int inputDim, int sample, float[][] inputLayerData) {
		this.inputDim = inputDim;
		this.sample = sample;
		outPutLayerData = new float[sample][inputDim];
		targetData = new float[sample][inputDim];
		layerDelta = new float[sample][inputDim];
		this.inputLayerData = inputLayerData;
	}
	public float forwardPropagation()
	{
		ActivationFunctions.softMax(inputLayerData, outPutLayerData);
		float loss = 0;
		for (int c = 0; c < outPutLayerData.length; c++) {
			loss += LossFunctions.ce(outPutLayerData[c], targetData[c]);
		}
		return loss;
	}
	public void backwardPropagation()
	{
		for (int c = 0; c < outPutLayerData.length; c++) {
			layerDelta[c] = LossFunctions.mseForRegression(outPutLayerData[c], targetData[c]);
		}
	}
	public float[][] getInputLayerData() {
		return inputLayerData;
	}
	public void setInputLayerData(float[][] inputLayerData) {
		this.inputLayerData = inputLayerData;
	}
	public float[][] getOutPutLayerData() {
		return outPutLayerData;
	}
	public void setOutPutLayerData(float[][] outPutLayerData) {
		this.outPutLayerData = outPutLayerData;
	}
	public float[][] getTargetData() {
		return targetData;
	}
	public void setTargetData(float[][] targetData) {
		this.targetData = targetData;
	}
	public float[][] getLayerDelta() {
		return layerDelta;
	}
	public void setLayerDelta(float[][] layerDelta) {
		this.layerDelta = layerDelta;
	}
	public int getInputDim() {
		return inputDim;
	}
	public void setInputDim(int inputDim) {
		this.inputDim = inputDim;
	}
	public int getSample() {
		return sample;
	}
	public void setSample(int sample) {
		this.sample = sample;
	}
	
	
	
}
