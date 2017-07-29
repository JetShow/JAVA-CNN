package cnn.layers;

import cnn.utils.Padding;

public class convolutionalParams {
	int [][][] inData;
	float[][][] indelta;
	int[][][] inDataPadded;
	int[][][] outData;
	float[][][] outDelta;
	float[][][][] convolutionalKernel;
	float[] bias;
	Padding paddingType;
	int hStride;
	int wStride;
}
