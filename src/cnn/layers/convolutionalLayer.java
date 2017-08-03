package cnn.layers;

import cnn.utils.Padding;
import cnn.utils.WeightInitializeUtil;

public class convolutionalLayer {
	
	//��ǰ����������
	int [][][] currentLayerData;
	//��ǰ���������
	float[][][] currentLayerDelta;
	//��һ���������
	float[][][] nextLayerDelta;
	//���������
	float[][][][] convolutionalKernel;
	//ƫ����
	float[] bias;
	//ͨ����
	int layerChannel;
	//��һ��ͨ����
	int nextLayerChannel;
	//����߶�
	int layerHeight;
	//������
	int layerWidth;
	//������䷽ʽ
	Padding paddingType;
	//�����height����
	int hStride = 1;
	//�����width����
	int wStride = 1;
	//����˸߶�
	int windowHeight;
	//����˿��
	int windowWidth;

	
	public convolutionalLayer(int layerChannel, int layerHeight, int layerWidth, 
			int nextLayerChannel, Padding paddingType, int windowHeight, int windowWidth) {
		this.currentLayerData = new int[layerChannel][layerHeight][layerWidth];
		this.currentLayerDelta = new float[layerChannel][layerHeight][layerWidth];
		this.paddingType = paddingType;
		if (paddingType == Padding.same) {
			this.nextLayerDelta = new float[nextLayerChannel][layerHeight][layerWidth];
		}
		else {
			this.nextLayerDelta = new float[nextLayerChannel]
						[layerHeight-windowHeight+1]
								[layerWidth-windowWidth+1];
		}
		this.convolutionalKernel = new float[nextLayerChannel]
										[layerChannel]
												[windowHeight]
														[windowWidth];
		WeightInitializeUtil.xavier(convolutionalKernel, windowHeight * windowWidth, windowHeight * windowWidth);
		this.bias = new float[nextLayerChannel];
		
	}


	/*
	 * ������ǰ�򴫲�
	 * param��inData ��ǰ�����������
	 * param��outData ��һ�����������
	 */
	public int[][][] forwardPropagation()
	{
		int[][][] inData = currentLayerData;
		int[][][] outData;
		if (Padding.same == paddingType) {
			outData = new int[nextLayerChannel]
					[layerHeight]
							[layerWidth];
		}
		else {
			outData = new int[nextLayerChannel]
					[layerHeight - hStride + 1]
							[layerWidth - wStride + 1]; 
		}
		int[][][] inDataPadded = this.paddingInData(inData, paddingType);
		//������ʱ����Ϊ1
		for (int oc = 0; oc < nextLayerChannel; oc++) {

				for(int h = 0; h < inDataPadded[0].length-windowHeight+1; h ++)
				{
					for(int w = 0; w < inDataPadded[0][h].length-windowWidth+1; w++)
					{
						for (int ic = 0; ic < layerChannel; ic++) {
							for (int wh = 0; wh < windowHeight; wh++) {
								for (int ww = 0; ww < windowWidth; ww++) {
									outData[oc][h][w] += inDataPadded[ic][h+wh][w+ww] * convolutionalKernel[oc][ic][wh][ww]; 
								}
							}
						}
						outData[oc][h][w] += bias[oc];
					}
				}
		}
		return outData;
	}
	/*
	 * ���������򴫲�
	 * param��pretData ǰһ�����������
	 * param��currentDelta ��ǰ��������������
	 * param��preDelta ǰһ��������������
	 * param��b ƫ����
	 */
	public void backwardPropagation()
	{
		int[][][] pretData = currentLayerData; 
		float [][][] currentDelta = nextLayerDelta;
		float[] b = bias;
		float[][][] preDelta;
		float[][][] currentDeltaPadded = this.paddingInData(currentDelta, paddingType);
		preDelta = new float[currentDeltaPadded.length]
				[currentDeltaPadded[0].length - hStride + 1]
						[currentDeltaPadded[0][0].length - wStride + 1];
	
		for(int outc=0; outc < preDelta.length; outc++)
		{
			//����ǰһ��delta
			for(int inc=0; inc < currentDeltaPadded.length; inc++)
			{
				for (int iny = 0; iny < currentDeltaPadded[inc].length; iny++) {
					for (int inx = 0; inx < currentDeltaPadded[inc][iny].length; inx++) {
						float delta = currentDeltaPadded[inc][iny][inx];
						for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
							for (int wx = 0; wx <  convolutionalKernel[inc][outc][wy].length; wx++) {
								preDelta[outc][iny+wy][inx+wx] += delta * convolutionalKernel[inc][outc][wy][wx];
							}
						}
						
					}
				}
				//�������˲���
				for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
					for (int wx = 0; wx < convolutionalKernel[inc][outc][wy].length; wx++) {
						for (int iny = 0; iny < currentDeltaPadded[inc].length; iny++) {
							for (int inx = 0; inx < currentDeltaPadded[inc][iny].length; inx++) {
								convolutionalKernel[inc][outc][wy][wx] += currentDeltaPadded[inc][iny][inx] * (float)pretData[inc][iny+wy][inx+wx];
							}
						}
					}
				}
				//����ƫ����
				for (int iny = 0; iny < currentDelta[inc].length; iny++) {
					for (int inx = 0; inx < currentDelta[inc][iny].length; inx++) {
						b[inc] += currentDelta[inc][iny][inx];
					}
				}
			}
		}
	}

	/*
	 * �������������
	 * ������䷽ʽ������飬����ǰ�򴫲��������ͷ��򴫲�����������
	 * ʹ��System.arrayCopy��ʽ
	 * param��inData ��Ҫ��������
	 * param��paddingType ��䷽ʽ
	 */
	
	private int[][][] paddingInData(int[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			int[][][] buf = new int[inData.length][inData[0].length+windowHeight-1][inData[0][0].length+windowWidth-1];
			for(int c = 0; c < inData.length; c++)
			{
				for(int h = 0; h < inData[c].length; h++)
				{
					System.arraycopy(inData[c][h], 0, buf[c][h+windowHeight/2], windowWidth/2, inData[c][h].length);
				}
			}
			return buf;
		}
		return null;
	}
	/*
	 * paddingInData�������أ��������������
	 */
	private float[][][] paddingInData(float[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			float[][][] buf = new float[inData.length][inData[0].length+windowHeight-1][inData[0][0].length+windowWidth-1];
			for(int c = 0; c < inData.length; c++)
			{
				for(int h = 0; h < inData[c].length; h++)
				{
					System.arraycopy(inData[c][h], 0, buf[c][h+windowHeight/2], windowWidth/2, inData[c][h].length);
				}				
			}
			return buf;
		}
		return null;
	}
	
	
	
	public int[][][] getCurrentLayerData() {
		return currentLayerData;
	}


	public void setCurrentLayerData(int[][][] currentLayerData) {
		this.currentLayerData = currentLayerData;
	}


	public float[][][] getCurrentLayerDelta() {
		return currentLayerDelta;
	}


	public void setCurrentLayerDelta(float[][][] currentLayerDelta) {
		this.currentLayerDelta = currentLayerDelta;
	}


	public float[][][] getNextLayerDelta() {
		return nextLayerDelta;
	}


	public void setNextLayerDelta(float[][][] nextLayerDelta) {
		this.nextLayerDelta = nextLayerDelta;
	}


	public float[][][][] getConvolutionalKernel() {
		return convolutionalKernel;
	}


	public void setConvolutionalKernel(float[][][][] convolutionalKernel) {
		this.convolutionalKernel = convolutionalKernel;
	}


	public float[] getBias() {
		return bias;
	}


	public void setBias(float[] bias) {
		this.bias = bias;
	}


	public int getLayerChannel() {
		return layerChannel;
	}


	public void setLayerChannel(int layerChannel) {
		this.layerChannel = layerChannel;
	}


	public int getNextLayerChannel() {
		return nextLayerChannel;
	}


	public void setNextLayerChannel(int nextLayerChannel) {
		this.nextLayerChannel = nextLayerChannel;
	}


	public int getLayerHeight() {
		return layerHeight;
	}


	public void setLayerHeight(int layerHeight) {
		this.layerHeight = layerHeight;
	}


	public int getLayerWidth() {
		return layerWidth;
	}


	public void setLayerWidth(int layerWidth) {
		this.layerWidth = layerWidth;
	}


	public Padding getPaddingType() {
		return paddingType;
	}


	public void setPaddingType(Padding paddingType) {
		this.paddingType = paddingType;
	}


	public int gethStride() {
		return hStride;
	}


	public void sethStride(int hStride) {
		this.hStride = hStride;
	}


	public int getwStride() {
		return wStride;
	}


	public void setwStride(int wStride) {
		this.wStride = wStride;
	}


	public int getWindowHeight() {
		return windowHeight;
	}


	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}


	public int getWindowWidth() {
		return windowWidth;
	}


	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

}





