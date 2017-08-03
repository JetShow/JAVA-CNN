package test;

import cnn.layers.convolutionalLayer;
import cnn.utils.CifarData;
import cnn.utils.ImageUtil;
import cnn.utils.Padding;

public class ConvolutionalLayerTest {
	
	public static void main(String[] arvgs) {
		CifarData cifarData = new CifarData();
		cifarData = ImageUtil.cifarReadData("F:/cifar-10-batches-bin/trainningData");
		
		convolutionalLayer conLayer = new convolutionalLayer(3, 32, 32, 6, Padding.same, 3, 3);
		conLayer.forwardPropagation();
	}
}
