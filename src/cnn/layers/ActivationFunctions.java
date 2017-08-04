package cnn.layers;

public class ActivationFunctions {
	//relu激活函数
	public static void relu(int[][][][] inData)
	{
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				for (int h = 0; h < inData[sp][c].length; h++) {
					for (int w = 0; w < inData[sp][c][h].length; w++) {
						if(inData[sp][c][h][w] < 0)
						{
							inData[sp][c][h][w] = 0; 
						}
					}
				}
			}
		}
	}
	//sigmoid激活函数
   public static void sigmoid(float[][][][] inData)
   {
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				for (int h = 0; h < inData[sp][c].length; h++) {
					for (int w = 0; w < inData[sp][c][h].length; w++) {
							inData[sp][c][h][w] = (float) (1/(1+Math.exp(-inData[sp][c][h][w]))); 
					}
				}
			}
		}
   }
   //tanh激活函数
   public static void tanh(float[][][][] inData)
   {
	   float expn, expp;
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				for (int h = 0; h < inData[sp][c].length; h++) {
					for (int w = 0; w < inData[sp][c][h].length; w++) {
							expp = (float) Math.exp(inData[sp][c][h][w]);
							expn = (float) Math.exp(-inData[sp][c][h][w]);
							inData[sp][c][h][w] = (float) ((expp-expn)/(expn+expp)); 
					}
				}
			}
		}
   }
   //卷积层到全连接层数组转化
   public static int[][] convertArray(int[][][][] inData)
   {
	   int[][] outData = new int[inData.length][inData[0].length];
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				outData[sp][c] = inData[sp][c][0][0];
			}
		}
		return outData;
   }
}
