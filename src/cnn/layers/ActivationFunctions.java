package cnn.layers;

public class ActivationFunctions {
	//relu激活函数
	public static void relu(float[][][][] inData)
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
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				for (int h = 0; h < inData[sp][c].length; h++) {
					for (int w = 0; w < inData[sp][c][h].length; w++) {
							inData[sp][c][h][w] = (float) (Math.tanh(inData[sp][c][h][w])); 
					}
				}
			}
		}
   }
   public static void tanh(float[][] inData)
   {
				for (int h = 0; h < inData.length; h++) {
					for (int w = 0; w < inData[h].length; w++) {
							inData[h][w] = (float) (Math.tanh(inData[h][w])); 
					}
				}
   }
   /*
    * softMax 激活函数
    */
   public static void softMax(float[][] inData, float[][] outData)
   {
	   if(inData.length != outData.length)
	   {
		   System.out.println("ActivationFunctions.softMax().Data_length_error");
		   return;
	   }
	   float acc = 0;
	   for (int c = 0; c < inData.length; c++) 
	   {
		   acc = 0;
		   for (int i = 0; i < inData[c].length; i++) 
		   {
			   outData[c][i] = (float)Math.exp(inData[c][i]);
			   acc += outData[c][i];
		   }
		   for (int i = 0; i < outData[c].length; i++) {
			   outData[c][i] = outData[c][i]/acc;
		   }		
	   }
   }
   
   //卷积层到全连接层数组转化
   public static float[][] convertArray(float[][][][] inData)
   {
	   float[][] outData = new float[inData.length][inData[0].length];
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				outData[sp][c] = inData[sp][c][0][0];
			}
		}
		return outData;
   }
   
   public static float[][][][] deConvertArray(float[][] inData)
   {
	   float[][][][] outData = new float[inData.length][inData[0].length][1][1];
		for (int sp = 0; sp < inData.length; sp++) {
			for (int c = 0; c < inData[sp].length; c++) {
				outData[sp][c][0][0] = inData[sp][c];
			}
		}
		return outData;
   }
}
