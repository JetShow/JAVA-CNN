package cnn.layers;

import cnn.utils.WeightInitializeUtil;

public class FullConnectedLayer {
	//��L-1�������
	int[][] preLayerData;
	//��L�������
	int[][] currentLayerData;
	//��L-1����������
	float[][] preLayerDelta;
	//��L����������
	float[][] currentLayerDelta;
	//��L���ƫ������
	float[] bias;
	//��L���Ȩ������
	float[][] weight;
	//��L-1���ά��
	int preLayerDim;
	//��L���ά��
	int currentLayerDim;
	//������
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
