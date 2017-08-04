package test;

import java.awt.image.BufferedImage;

import cnn.layers.ActivationFunctions;
import cnn.layers.FullConnectedLayer;
import cnn.layers.convolutionalLayer;
import cnn.layers.poolingLayer;
import cnn.utils.CifarData;
import cnn.utils.ImageUtil;
import cnn.utils.Padding;

public class ConvolutionalLayerTest {
	
	public static void main(String[] arvgs) {
		CifarData cifarData = new CifarData();
		cifarData = ImageUtil.cifarReadData("E:/cifar-10-batches-bin/trainningData");
		convolutionalLayer conLayer = new convolutionalLayer(3, 32, 32, 6, Padding.valid, 5, 5, 50, cifarData.getData());
		
		poolingLayer pLayer = new poolingLayer(6, 28, 28, 2, 2, 2, 2, 50,conLayer.forwardPropagation());
		
		convolutionalLayer conLayer2 = new convolutionalLayer(6, 14, 14, 16, Padding.valid, 5, 5, 50, pLayer.forwardPropagation());
		
		poolingLayer pLayer2 = new poolingLayer(16, 10, 10, 2, 2, 2, 2, 50, conLayer2.forwardPropagation());
		
		convolutionalLayer conLayer3 = new convolutionalLayer(16, 5, 5, 120, Padding.valid, 5, 5, 50, pLayer2.forwardPropagation());
		
		FullConnectedLayer fLayer = new FullConnectedLayer(120, 84, 50 ,ActivationFunctions.convertArray(conLayer3.forwardPropagation()));
		
		FullConnectedLayer fLayer2 = new FullConnectedLayer(84, 10, 50 ,fLayer.forwordPropagation());
		
		int[][] out = fLayer2.forwordPropagation();
		while (true) {
			System.out.println("lived!");
		}
	}
}
