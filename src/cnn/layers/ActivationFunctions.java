package cnn.layers;

public class ActivationFunctions {
	//relu�����
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
	//sigmoid�����
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
   //tanh�����
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
   /*
    * softMax �����
    */
   public static void softMax(int[][] inData, float[][] outData)
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
   
   //�����㵽ȫ���Ӳ�����ת��
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