package test;

import java.awt.image.BufferedImage;

import cnn.layers.convolutionalLayer;
import cnn.utils.CifarData;
import cnn.utils.ImageUtil;
import cnn.utils.Padding;

public class ConvolutionalLayerTest {
	
	public static void main(String[] arvgs) {
		CifarData cifarData = new CifarData();
		cifarData = ImageUtil.cifarReadData("E:/cifar-10-batches-bin/trainingData");
		convolutionalLayer conLayer = new convolutionalLayer(3, 32, 32, 6, Padding.same, 3, 3,50000);
		conLayer.setCurrentLayerData(cifarData.getData());
		conLayer.forwardPropagation();
	}
}
