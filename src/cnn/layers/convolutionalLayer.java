package cnn.layers;

import java.util.Arrays;

import javax.swing.text.Position.Bias;

import cnn.utils.Padding;

public class convolutionalLayer {
//	int inHeight;
//	int inWidth;
//	int windowHeight;
//	int windowWidth;
//	int inCannels;
//	int outChannels;
//	Padding paddingType;
//	boolean hasBias;
//	int h_stride;
//	int w_stride;
//	float[][][][] convolutionalKernel;
//	float[] bias;
	
	
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
				for(int h = windowHeight/2; h < inData[c].length; h++)
				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
					System.arraycopy(inData[c][h], 0, buf[c][h], windowWidth/2, inData[c][h].length);
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
				for(int h = windowHeight/2; h < inData[c].length; h++)
				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
					System.arraycopy(inData[c][h], 0, buf[c][h], windowWidth/2, inData[c][h].length);
				}				
			}
			return buf;
		}
		return null;
	}
}





