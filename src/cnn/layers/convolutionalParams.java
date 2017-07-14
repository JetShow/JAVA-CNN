package cnn.layers;

import cnn.utils.Padding;

public class convolutionalParams {
	float[][][] weight;
	float[][][] delta;
	float[][][][] convolutionalKernel;
	float[] bias;
	Padding paddingType;
	int hStride;
	int wStride;
}
