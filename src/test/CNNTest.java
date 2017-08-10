package test;

import cnn.layers.ActivationFunctions;
import cnn.layers.FullConnectedLayer;
import cnn.layers.OutputLayer;
import cnn.layers.convolutionalLayer;
import cnn.layers.poolingLayer;
import cnn.utils.CifarData;
import cnn.utils.ImageUtil;
import cnn.utils.Padding;

public class CNNTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float loss = Float.MAX_VALUE;
		float[][] targetData = new float[50][10];
		CifarData cifarData = new CifarData();		
		cifarData = ImageUtil.cifarReadData("E:/cifar-10-batches-bin/trainningData", 0, 1);
		for (int i = 0; i < targetData.length; i++) {
			int j = cifarData.getDataLable()[i];
			targetData[i][j] = 1;
		};
		
		double tanh = Math.tanh(0.00001);
		
		convolutionalLayer conLayer = new convolutionalLayer(3, 32, 32, 6, Padding.valid, 5, 5, 10, cifarData.getNormalizationData());
		
		poolingLayer pLayer = new poolingLayer(6, 28, 28, 2, 2, 2, 2, 10,null);
		
		convolutionalLayer conLayer2 = new convolutionalLayer(6, 14, 14, 16, Padding.valid, 5, 5, 10, null);
		
		poolingLayer pLayer2 = new poolingLayer(16, 10, 10, 2, 2, 2, 2, 10, null);
		
		convolutionalLayer conLayer3 = new convolutionalLayer(16, 5, 5, 120, Padding.valid, 5, 5, 10, null);
		
		FullConnectedLayer fLayer = new FullConnectedLayer(120, 84, 10 ,null);
		
		FullConnectedLayer fLayer2 = new FullConnectedLayer(84, 10, 10 ,null);
		
		OutputLayer oLayer = new OutputLayer(10, 10, null);
		
		while (true)
		{									
			pLayer.setCurrentLayerData(conLayer.forwardPropagation());
			
			conLayer2.setCurrentLayerData(pLayer.forwardPropagation());
			
			pLayer2.setCurrentLayerData(conLayer2.forwardPropagation());
			
			conLayer3.setCurrentLayerData(pLayer2.forwardPropagation());			
			
			fLayer.setPreLayerData(ActivationFunctions.convertArray(conLayer3.forwardPropagation()));
			
			fLayer2.setPreLayerData(fLayer.forwordPropagation());
			
			oLayer.setInputLayerData(fLayer2.forwordPropagation());
			
			oLayer.setTargetData(targetData);
			
			loss = oLayer.forwardPropagation();
			
			if (loss < 0.001) {
				break;
			}
			
			oLayer.backwardPropagation();
			
			fLayer2.setCurrentLayerDelta(oLayer.getLayerDelta());
			
			fLayer2.backwardPropagation();
			
			fLayer.setCurrentLayerDelta(fLayer2.getPreLayerDelta());
			
			fLayer.backwardPropagation();
			
			conLayer3.setNextLayerDelta(ActivationFunctions.deConvertArray(fLayer.getPreLayerDelta()));
			
			conLayer3.backwardPropagation();
			
			pLayer2.setNextLayerDelta(conLayer3.getCurrentLayerDelta());
			
			pLayer2.backwardPropagation();
			
			conLayer2.setNextLayerDelta(pLayer2.getCurrentLayerDelta());
			
			conLayer2.backwardPropagation();
			
			pLayer.setNextLayerDelta(conLayer2.getCurrentLayerDelta());
			
			pLayer.backwardPropagation();
			
			conLayer.setNextLayerDelta(pLayer.getCurrentLayerDelta());
			
			conLayer.backwardPropagation();
			
		}
	}

}
