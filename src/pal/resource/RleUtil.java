package pal.resource;

import java.util.Arrays;

import pal.game.Palette;
import pal.util.ArraysUtil;
import pal.util.Convert;

/**
 * ��ô�src ���ɽ�ѹ���ٸ�rleͼƬ.
 * 
 * ����ңͼƬ, 12��, ��һ��shortΪ0D,��13.  max = s1 - 1;
 * [0,max)
 * 
 * 
 * @author lhj
 * @time   2007-2-11 - ����02:48:58
 * 
 */
public class RleUtil {
	
	/**
	 * @param src
	 * @param pal �Ƿ������ɫ��, ��һ�������Ƿ��ǵ�ɫ����Ϣ. ��4���ֽ�.
	 * @return
	 */

	public static int[][][] decode(byte[][] src, boolean pal) {
		int[][][] result = new int[src.length][][];
		for (int i = 0; i < result.length; i++) {
			result[i] = decode(src[i], pal);
		}
		return result;
	}
	
	public static int[][] decode(byte[] src, boolean pal) {
		if (src == null) {
			return null;
		}
		int point = 0;
		int palId = 0;		//Ĭ��Ϊ0�ŵ�ɫ��.
		if(pal) {
			palId = Convert.getInt(src[0]);
			point += 4;
		}
		int[] palette = new Palette(palId).getPalette();
		int width = Convert.getInt(src[point++], src[point++]);
		int height = Convert.getInt(src[point++], src[point++]);
		
		int[] result = new int[height*width];
		int index = 0;
		while (index < width * height) {
			int n = src[point++] & 0xff;
			if (n > 0x80) {
				for (int i = 0; i < n - 0x80; i++) {
					result[index++] = 0x00FFFFFF;				//alphaλΪ0, ��͸��.
				}
			} else {
				for (int i = 0; i < n; i++) {
					result[index++] = palette[(src[point++] & 0xff)];
				}
			}
		}		
		return ArraysUtil.getArrays(result, width, height);
	}	
}

















