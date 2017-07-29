package cnn.layers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import cnn.pojo.FullConnectedParam;

public class FullConnectedLayer {
	public FullConnectedParam forwordPropagation(FullConnectedParam inParam) throws IllegalAccessException, InvocationTargetException
	{
		float[][] weight = inParam.getWeight();
		int[] inData = inParam.getLayerData();
		int[] outData = new int[inParam.getNextLayerDim()];
		for (int outc = 0; outc < inParam.getNextLayerDim(); outc++) {
			for (int inc = 0; inc < inParam.getNextLayerDim(); inc++) {
				outData[outc] += inData[inc] * weight[inc][outc];
			}
		}
		FullConnectedParam outParam = new FullConnectedParam();
		BeanUtils.copyProperties(outParam, inParam);
		outParam.setLayerData(outData);
		return outParam;
	}
	public FullConnectedParam backwardPropagation(FullConnectedParam inParam) {
		float[] outDelta = 
		
		
		return null;
	}
}