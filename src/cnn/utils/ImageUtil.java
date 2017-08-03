package cnn.utils;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.beanutils.IntrospectionContext;

public class ImageUtil {
	
	class MyCanvas extends Canvas{
		     private BufferedImage bi;
		     private Image im;
		     private int image_width;
		     private int image_height;
		     
		     public void setImage(BufferedImage bi){
		         this.bi=bi;
		         this.zoom();
		     }
		     
		     public void paint(Graphics g){
		         g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
		     }
		     
		     public void zoom(){
			image_width=bi.getWidth();
		        image_height=bi.getHeight();
		         im=bi.getScaledInstance(image_width,image_height,Image.SCALE_SMOOTH);
		    }
		 }
		
		
	public static BufferedImage readImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ImageUtil.readIamge\r\n" + e);
			return null;
		}
	}
	
	public static int[] getPixel(BufferedImage src, int x, int y) throws Exception
	{
		if (src.getType()==BufferedImage.TYPE_4BYTE_ABGR) {
			int[] argb = new int[4];
			if(x >= src.getWidth() || x < 0 || y >= src.getHeight() || y < 0)
			{
				throw new Exception("ImageUtil.getPixel\r\n");
			}
			int pixel = src.getRGB(x, y);
			argb[0] = (pixel & 0xff000000)>>24;//屏蔽低位，并移位到最低位  
		    argb[1] = (pixel & 0xff0000) >> 16;  
		    argb[2] = (pixel & 0xff00) >> 8;  
		    argb[3] = (pixel & 0xff);
		    return argb;
		}
		if (src.getType()==BufferedImage.TYPE_BYTE_GRAY) {
			int[] argb = new int[1];
			if(x >= src.getWidth() || x < 0 || y >= src.getHeight() || y < 0)
			{
				throw new Exception("ImageUtil.getPixel\r\n");
			}
			int pixel = src.getRGB(x, y);
			argb[0] = (pixel & 0xff);
		    return argb;
		}
		if (src.getType()==BufferedImage.TYPE_3BYTE_BGR) {
			int[] rgb = new int[3];
			if(x >= src.getWidth() || x < 0 || y >= src.getHeight() || y < 0)
			{
				throw new Exception("ImageUtil.getPixel\r\n");
			}
			int pixel = src.getRGB(x, y);
		    rgb[1] = (pixel & 0xff0000) >> 16;  
		    rgb[2] = (pixel & 0xff00) >> 8;  
		    rgb[3] = (pixel & 0xff);
		    return rgb;
		}
		throw new Exception("ImageUtil.getPixel.notsupportimagetype\r\n");
		
	}
		
	public static int[][][] image2Array(BufferedImage src) throws Exception {
		int height = src.getHeight();
		int width = src.getWidth();
		int[][][] array = null;
		if (src.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
			array = new int[4][height][width];
		}
		else if (src.getType() == BufferedImage.TYPE_BYTE_GRAY) {
			array = new int[1][height][width];
		}
		else if (src.getType() == BufferedImage.TYPE_3BYTE_BGR) {
			array = new int[3][height][width];
		}
		for(int y =0 ; y < height ; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int[] temp = getPixel(src, x, y);
				for (int channel = 0; channel < array.length; channel++) {
					array[channel][y][x] = temp[channel];
				}
			}
		}
		return array;
	}
	/*
	 * 遍历文件夹中所有文件，用于打开cifar所有二进制文件
	 */
	
	private static ArrayList<String> traversalFile(String filePath)
	{
		File file = new File(filePath);
		File[] files;
		ArrayList<String> fileNameList = new ArrayList<String>();
		if(file.exists())
		{
			files = file.listFiles();
			if(files.length == 0)
			{
				System.out.println("文件夹没有文件！");
			}
			else
			{
				for(File fileread: files)
				{
					if(fileread.isDirectory())
					{
						ArrayList<String> listTemp = traversalFile(fileread.getAbsolutePath());
						fileNameList.addAll(listTemp);
					}else {
						fileNameList.add(fileread.getAbsolutePath());
					}
				}
			}
		}
		return fileNameList;
	}
	
	
	public static CifarData cifarReadData(String binaryDataPath)
	{
		FileInputStream reader = null;
		BufferedInputStream bi = null;
//		FileReader reader = null;
		ArrayList<String> fileNameList = traversalFile(binaryDataPath);
		int[][][][] data = new int[50000][3][32][32];
		int[] dataLable = new int[50000];
		int index = 0;
		for (int i = 0; i < fileNameList.size(); i++) {
			try {
				reader = new FileInputStream(fileNameList.get(i));
				bi = new BufferedInputStream(reader);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("ImageUtil.cifarReadData.FileNotFound\r\n" + e);
			}
			int tempByte;
			try {
				while ((tempByte = bi.read()) != -1) {
					int subIndex = index%3073;
					int imageIndex = index/3073;
					int channels = (subIndex-1)/1024;
					int pixelIndex = (subIndex-1)%1024;
					int height = pixelIndex / 32;
					int width = pixelIndex % 32;
					if (subIndex == 0) {
						dataLable[imageIndex] = tempByte;
					}else {
						data[imageIndex][channels][height][width] = tempByte;
					}					
					index++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ImageUtil.cifarReadData.BufferedReader error!\r\n" + e);
			}
		}
		CifarData cifarData = new CifarData();
		cifarData.setData(data);
		cifarData.setDataLable(dataLable);
		return cifarData;
	}
	
	public static BufferedImage[] array2Image(int[][][] array) {
		BufferedImage[] outArray = null;
		outArray = new BufferedImage[array.length];
		
		for (int c = 0; c < array.length; c++) {
			BufferedImage bImage = new BufferedImage(array[c][0].length, array[c].length, BufferedImage.TYPE_BYTE_GRAY);
			for (int h = 0; h < array[c].length; h++) {
				for (int w = 0; w < array[c][h].length; w++) {
					bImage.setRGB(w, h, array[c][h][w]);
				}
			}
			outArray[c] = bImage;
		}
		return outArray;
	}
	
	public static void showImage(BufferedImage[] images) {
		JFrame f=new JFrame();
        MyCanvas mc=new ImageUtil.MyCanvas();
        f.add(mc);
	}
}
