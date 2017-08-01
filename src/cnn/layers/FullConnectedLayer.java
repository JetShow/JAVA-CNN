package cnn.layers;

public class FullConnectedLayer {
	//��L-1�������
	int[] preLayerData;
	//��L�������
	int[] currentLayerData;
	//��L-1����������
	float[] preLayerDelta;
	//��L����������
	float[] currentLayerDelta;
	//��L���ƫ������
	float[] bias;
	//��L���Ȩ������
	float[][] weight;
	//��L-1���ά��
	int preLayerDim;
	//��L���ά��
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
