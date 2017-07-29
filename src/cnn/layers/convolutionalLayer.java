package cnn.layers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.text.Position.Bias;

import org.apache.commons.beanutils.BeanUtils;

import cnn.pojo.ConvolutionalParams;
import cnn.pojo.MaxPoolParam;
import cnn.utils.Padding;

public class convolutionalLayer {
	int inHeight;
	int inWidth;
	int windowHeight;
	int windowWidth;
	int inCannels;
	int outChannels;
	Padding paddingType;
	boolean hasBias;
	int h_stride;
	int w_stride;
	float[][][][] convolutionalKernel;
	float[] bias;
	
	public convolutionalLayer(int inHeight, 
							  int inWidth, 
							  int windowHeight,
							  int windowWidth,
							  int inChannels,
							  int outChannels,
							  Padding paddingType,
							  boolean hasBias,
							  int h_stride,
							  int w_stride
							  ) {
		// TODO Auto-generated constructor stub
		this.inHeight= inHeight;
		this.inWidth= inWidth;
		this.windowHeight= windowHeight;
		this.windowWidth= windowWidth;
		this.inCannels= inChannels;
		this.outChannels = outChannels;
		this.paddingType = paddingType;
		this.hasBias = hasBias;
		this.h_stride = h_stride;
		this.w_stride = w_stride;
		this.convolutionalKernel = new float[outChannels][inChannels][windowHeight][windowWidth];
		this.bias = new float[outChannels];
	}
	/*
	 * 方法：前向传播
	 * param：inData 当前层的数据数组
	 * param：outData 下一层的数据数组
	 */
	public ConvolutionalParams forwardPropagation(ConvolutionalParams convolutionalParam)
	{
		int[][][] inData = convolutionalParam.getLayerData();
		int[][][] outData;
		if (Padding.same == convolutionalParam.getPaddingType()) {
			outData = new int[convolutionalParam.getLayerChannel()]
					[convolutionalParam.getLayerHeight()]
							[convolutionalParam.getLayerWidth()];
		}
		else {
			outData = new int[convolutionalParam.getLayerChannel()]
					[convolutionalParam.getLayerHeight() - convolutionalParam.gethStride() + 1]
							[convolutionalParam.getLayerWidth() - convolutionalParam.getwStride() + 1]; 
		}
		float[] bias = convolutionalParam.getBias();
		int[][][] inDataPadded = this.paddingInData(inData, paddingType);
		//步长暂时设置为1
		for (int oc = 0; oc < outChannels; oc++) {

				for(int h = 0; h < inDataPadded[0].length-windowHeight+1; h ++)
				{
					for(int w = 0; w < inDataPadded[0][h].length-windowWidth+1; w++)
					{
						for (int ic = 0; ic < inCannels; ic++) {
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
		ConvolutionalParams outParam = new ConvolutionalParams();
		try {
			BeanUtils.copyProperties(outParam, convolutionalParam);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		outParam.setLayerData(outData);
		outParam.setLayerChannel(outData.length);
		outParam.setLayerHeight(outData[0].length);
		outParam.setLayerWidth(outData.length);
		return outParam;
	}
	/*
	 * 方法：反向传播
	 * param：pretData 前一层的数据数组
	 * param：currentDelta 当前层的误差数据数组
	 * param：preDelta 前一层的误差数据数组
	 * param：b 偏置项
	 */
	public ConvolutionalParams backwardPropagation(ConvolutionalParams currentParam,ConvolutionalParams preParam )
	{
		int[][][] pretData = preParam.getLayerData(); 
		float [][][] currentDelta = currentParam.getLayerDelta();
		float[] b = currentParam.getBias();
		float[][][] preDelta;
		float[][][] currentDeltaPadded = this.paddingInData(currentDelta, currentParam.getPaddingType());
		float[][][][] convolutionalKernel = currentParam.getConvolutionalKernel();
		preDelta = new float[currentDeltaPadded.length]
				[currentDeltaPadded[0].length - currentParam.gethStride() + 1]
						[currentDeltaPadded[0][0].length - currentParam.getwStride() + 1];
	
		for(int outc=0; outc < preDelta.length; outc++)
		{
			//计算前一层delta
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
				//计算卷积核参数
				for (int wy = 0; wy < convolutionalKernel[inc][outc].length; wy++) {
					for (int wx = 0; wx < convolutionalKernel[inc][outc][wy].length; wx++) {
						for (int iny = 0; iny < currentDeltaPadded[inc].length; iny++) {
							for (int inx = 0; inx < currentDeltaPadded[inc][iny].length; inx++) {
								convolutionalKernel[inc][outc][wy][wx] += currentDeltaPadded[inc][iny][inx] * (float)pretData[inc][iny+wy][inx+wx];
							}
						}
					}
				}
				//计算偏置项
				for (int iny = 0; iny < currentDelta[inc].length; iny++) {
					for (int inx = 0; inx < currentDelta[inc][iny].length; inx++) {
						b[inc] += currentDelta[inc][iny][inx];
					}
				}
			}
		}
		preParam.setLayerDelta(preDelta);
		preParam.setBias(b);
		preParam.setConvolutionalKernel(convolutionalKernel);
		return preParam;
	}
	
	/*
	 * 方法：数组填充
	 * 根据填充方式填充数组，包括前向传播数据填充和反向传播误差数据填充
	 * 使用System.arrayCopy方式
	 * param：inData 需要填充的数组
	 * param：paddingType 填充方式
	 */
	
	private int[][][] paddingInData(int[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			int[][][] buf = new int[inCannels][inHeight+windowHeight/2][inWidth+windowWidth/2];
			for(int c = 0; c < inCannels; c++)
			{
//				for(int h = 0; h < inHeight; h++)
//				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
//				}
				System.arraycopy(inData[c], 0, buf[c], windowWidth/2, inData[c].length);
			}
			return buf;
		}
		return null;
	}
	/*
	 * paddingInData方法重载，适用于误差数组
	 */
	private float[][][] paddingInData(float[][][] inData, Padding paddingType) {
		if(paddingType == Padding.valid)
		{
			return inData;
		}
		if(paddingType == Padding.same)
		{
			float[][][] buf = new float[inCannels][inHeight+windowHeight-1][inWidth+windowWidth-1];
			for(int c = 0; c < inCannels; c++)
			{
//				for(int h = 0; h < inHeight; h++)
//				{
//					for(int w = 0; w < inWidth; w++)
//					{
//						buf[c][h+windowHeight/2 + h][windowWidth + w]= 
//								inData[c][h][w];
//					}
//				}
				System.arraycopy(inData[c], 0, buf[c], windowWidth/2, inData[c].length);
			}
			return buf;
		}
		return null;
	}
}





