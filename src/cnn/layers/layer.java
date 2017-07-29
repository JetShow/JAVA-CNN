package cnn.layers;


public interface layer {
	public void forwardPropagation(int[][][] indata,int[][][] outdata);
	public void backwardPropagation(int[][][] pretData, float [][][] currentDelta, float[][][] preDelta, float[] b);
	
}
