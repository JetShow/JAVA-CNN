package cnn.layers;

public class FullConnectedLayer {
	//第L-1层的数据
	int[] preLayerData;
	//第L层的数据
	int[] currentLayerData;
	//第L-1层的误差数组
	float[] preLayerDelta;
	//第L层的误差数组
	float[] currentLayerDelta;
	//第L层的偏置数组
	float[] bias;
	//第L层的权重数组
	float[][] weight;
	//第L-1层的维度
	int preLayerDim;
	//第L层的维度
	int currentLayerDim;
				
	public FullConnectedLayer(int preLayerDim, int currentLayerDim) {
		this.preLayerDim = preLayerDim;
		this.currentLayerDim = currentLayerDim;
		this.preLayerData = new int[preLayerDim];
		this.preLayerDelta = new float[preLayerDim];
		this.currentLayerData = new int[currentLayerDim];
		this.currentLayerDelta = new float[currentLayerDim];
		this.bias = new float[currentLayerDim];
		this.weight = new float[preLayerDim][currentLayerDim];
	}
	public void initialWeight()
	{
		
	}
	public void forwordPropagation()
	{
		for (int outc = 0; outc < currentLayerDim; outc++) {
			for (int inc = 0; inc < preLayerDim; inc++) {
				currentLayerData[outc] = (int) (preLayerData[inc] * weight[inc][outc]);
			}
		}
	}
	public void backwardPropagation() {
		
		for (int i = 0; i < preLayerDim; i++) {
			for (int j = 0; j < currentLayerDim; j++) {
				preLayerDelta[i] += currentLayerDelta[j] * weight[i][j];
			}
		}
		for(int wh = 0; wh < weight.length; wh++)
		{
			for (int ww = 0; ww < weight[wh].length; ww++) {
				weight[wh][ww] += currentLayerDelta[ww] * preLayerData[wh];
			}
		}
		
		for(int i = 0; i < bias.length; i++ )
		{
			bias[i] += currentLayerDelta[i];
		}
	}
}
