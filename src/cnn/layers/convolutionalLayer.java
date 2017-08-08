package cnn.layers;

import cnn.utils.Padding;
import cnn.utils.WeightInitializeUtil;

public class convolutionalLayer {
	
	/*
	 * ��ǰ����������
	 * int[sample][channel][height][width] currentLayerData;
	 */
	float[][][][] currentLayerData;
	//��ǰ���������
	float[][][][] currentLayerDelta;
	//��һ���������
	float[][][][] nextLayerDelta;
	/*
	 * ���������
	 * float[nextLayerChannel][layerChannel][height][width]  convolutionalKernel
	 */
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
	//������
	int sample;
	
//	float factor = 1/sample;
	
	public convolutionalLayer(int layerChannel, int layerHeight, int layerWidth, 
			int nextLayerChannel, Padding paddingType, int windowHeight, int windowWidth, 
			int sample,float[][][][] currentLayerData) {
		this.currentLayerData = currentLayerData;
		this.currentLayerDelta = new float[sample][layerChannel][layerHeight][layerWidth];
		this.paddingType = paddingType;
		if (paddingType == Padding.same) {
			this.nextLayerDelta = new float[sample][nextLayerChannel][layerHeight][layerWidth];
		}
		else {
			this.nextLayerDelta = new float[sample][nextLayerChannel]
						[layerHeight-windowHeight+1]
								[layerWidth-windowWidth+1];
		}
		this.convolutionalKernel = new float[nextLayerChannel]
										[layerChannel]
												[windowHeight]
														[windowWidth];
		WeightInitializeUtil.xavier(convolutionalKernel, windowHeight * windowWidth, windowHeight * windowWidth);
		this.bias = new float[nextLayerChannel];
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.nextLayerChannel = nextLayerChannel;
		this.layerChannel = layerChannel;
		this.layerHeight = layerHeight;
		this.layerWidth = layerWidth;
		this.sample = sample;
	}


	/*
	 * ������ǰ�򴫲�
	 * param��inData ��ǰ�����������
	 * param��outData ��һ�����������
	 */
	public float[][][][] forwardPropagation()
	{
		float[][][][] inData = currentLayerData;
		float[][][][] outData;
		outData = new float[sample][][][];
		if (Padding.same == paddingType) {
			outData = new float[sample][nextLayerChannel]
					[layerHeight]
							[layerWidth];
		}
		else {
			outData = new float[sample][nextLayerChannel]
					[layerHeight - windowHeight + 1]
							[layerWidth - windowWidth + 1]; 
		}
		float[][][][] inDataPadded = this.paddingInData(inData, paddingType);
		//������ʱ����Ϊ1
		for (int sp = 0; sp < sample; sp++) {
			for (int oc = 0; oc < nextLayerChannel; oc++) 
			{
				for(int h = 0; h < inDataPadded[sp][0].length-windowHeight+1; h ++)
				{
					for(int w = 0; w < inDataPadded[sp][0][h].length-windowWidth+1; w++)
					{
						for (int ic = 0; ic < layerChannel; ic++) {
							for (int wh = 0; wh < windowHeight; wh++) {
								for (int ww = 0; ww < windowWidth; ww++) {
									outData[sp][oc][h][w] += inDataPadded[sp][ic][h+wh][w+ww] * convolutionalKernel[oc][ic][wh][ww]; 
								}
							}
						}
						outData[sp][oc][h][w] += bias[oc];
					}
				}
			}
		}
		ActivationFunctions.relu(outData);
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
		float[][][][] pretData = currentLayerData; 
		float [][][][] currentDelta = nextLayerDelta;
		float[] b = bias;
		float[][][][] preDelta;
		float[][][][] currentDeltaPadded = this.paddingInData(currentDelta, paddingType);
//		preDelta = new float[sample][currentDeltaPadded.length]
//				[currentDeltaPadded[0].length - hStride + 1]
//						[currentDeltaPadded[0][0].length - wStride + 1];
		preDelta = currentLayerDelta;
		for (int sp = 0; sp < sample; sp++) 
		{
			for(int outc=0; outc < preDelta[sp].length; outc++)
			{
				//����ǰһ��delta
				for(int inc=0; inc < currentDeltaPadded[sp].length; inc++)
				{
					for (int iny = 0; iny < currentDeltaPadded[sp][inc].length; iny++) {
						for (int inx = 0; inx < currentDeltaPadded[sp][inc][iny].length; inx++) {
							float delta = currentDeltaPadded[sp][inc][iny][inx];
							for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
								for (int wx = 0; wx <  convolutionalKernel[inc][outc][wy].length; wx++) {
									preDelta[sp][outc][iny+wy][inx+wx] -= delta * convolutionalKernel[inc][outc][wy][wx]/(windowHeight*windowWidth);
								}
							}
							
						}
					}
					//�������˲���
					for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
						for (int wx = 0; wx < convolutionalKernel[inc][outc][wy].length; wx++) {
							for (int iny = 0; iny < currentDeltaPadded[sp][inc].length; iny++) {
								for (int inx = 0; inx < currentDeltaPadded[sp][inc][iny].length; inx++) {
									convolutionalKernel[inc][outc][wy][wx] -= currentDeltaPadded[sp][inc][iny][inx] * (float)pretData[sp][outc][iny+wy][inx+wx]
										 /(sample*layerHeight*layerWidth);
								}
							}
						}
					}
					//����ƫ����
					for (int iny = 0; iny < currentDelta[sp][inc].length; iny++) {
						for (int inx = 0; inx < currentDelta[sp][inc][iny].length; inx++) {
							b[inc] -= currentDelta[sp][inc][iny][inx] /(sample*layerHeight*layerWidth);
						}
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
	
	private int[][][][] paddingInData(int[][][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			int[][][][] buf = new int[sample][inData[0].length][inData[0][0].length+windowHeight-1][inData[0][0][0].length+windowWidth-1];
			for (int sp = 0; sp < sample; sp++) 
			{
				for(int c = 0; c < inData[sp].length; c++)
				{
					for(int h = 0; h < inData[sp][c].length; h++)
					{
						System.arraycopy(inData[sp][c][h], 0, buf[sp][c][h+windowHeight/2], windowWidth/2, inData[sp][c][h].length);
					}
				}
			}		
			return buf;
		}
		return null;
	}
	/*
	 * paddingInData�������أ��������������
	 */
	private float[][][][] paddingInData(float[][][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			float[][][][] buf = new float[sample][inData[0].length][inData[0][0].length+windowHeight-1][inData[0][0][0].length+windowWidth-1];
			for (int sp = 0; sp < sample; sp++) 
			{
				for(int c = 0; c < inData[sp].length; c++)
				{
					for(int h = 0; h < inData[sp][c].length; h++)
					{
						System.arraycopy(inData[sp][c][h], 0, buf[sp][c][h+windowHeight/2], windowWidth/2, inData[sp][c][h].length);
					}
				}
			}	
			return buf;
		}
		return null;
	}
	
	
	
	public float[][][][] getCurrentLayerData() {
		return currentLayerData;
	}


	public void setCurrentLayerData(float[][][][] currentLayerData) {
		this.currentLayerData = currentLayerData;
	}


	public float[][][][] getCurrentLayerDelta() {
		return currentLayerDelta;
	}


	public void setCurrentLayerDelta(float[][][][] currentLayerDelta) {
		this.currentLayerDelta = currentLayerDelta;
	}


	public float[][][][] getNextLayerDelta() {
		return nextLayerDelta;
	}


	public void setNextLayerDelta(float[][][][] nextLayerDelta) {
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





