package cnn.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;

import javax.imageio.ImageIO;

public class ImageUtil {
	
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
		int[][][] array = new int[height][width][];
		for(int y =0 ; y < height ; y++)
		{
			for(int x = 0; x < width; x++)
			{
				array[y][x] = getPixel(src, x, y);
			}
		}
		return array;
	}

}
