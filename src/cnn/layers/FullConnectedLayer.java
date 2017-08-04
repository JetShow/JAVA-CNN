package cnn.layers;

import cnn.utils.WeightInitializeUtil;

public class FullConnectedLayer {
	//第L-1层的数据
	int[][] preLayerData;
	//第L层的数据
	int[][] currentLayerData;
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
				
	public FullConnectedLayer(int preLayerDim, int currentLayerDim, int sample, int[][] preLayerData) {
		this.preLayerDim = preLayerDim;
		this.currentLayerDim = currentLayerDim;
		this.preLayerData = preLayerData;
		this.sample = sample;
		this.preLayerData = preLayerData;
		this.preLayerDelta = new float[sample][preLayerDim];
		this.currentLayerData = new int[sample][currentLayerDim];
		this.currentLayerDelta = new float[sample][currentLayerDim];
		this.bias = new float[currentLayerDim];
		this.weight = new float[preLayerDim][currentLayerDim];
		WeightInitializeUtil.xavier(weight, preLayerDim, currentLayerDim);
		
	}
	public int[][] forwordPropagation()
	{
		for (int sp = 0; sp < sample; sp++) {
			for (int outc = 0; outc < currentLayerDim; outc++) {
				for (int inc = 0; inc < preLayerDim; inc++) {
					currentLayerData[sp][outc] = (int) (preLayerData[sp][inc] * weight[inc][outc]);
				}
			}
		}
		return currentLayerData;
	}
	public void backwardPropagation() {
	
		for (int sp = 0; sp < sample; sp++) {
			for (int i = 0; i < preLayerDim; i++) {
				for (int j = 0; j < currentLayerDim; j++) {
					preLayerDelta[sp][i] += currentLayerDelta[sp][j] * weight[i][j];
				}
			}
			for(int wh = 0; wh < weight.length; wh++)
			{
				for (int ww = 0; ww < weight[wh].length; ww++) {
					weight[wh][ww] += currentLayerDelta[sp][ww] * preLayerData[sp][wh];
				}
			}
			
			for(int i = 0; i < bias.length; i++ )
			{
				bias[i] += currentLayerDelta[sp][i];
			}
		}
		
	}
}
