package pal.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import pal.util.Convert;

/**
 * 
 * @author lhj
 * @time   2007-2-4 - ����07:49:31
 * 
 */
public class MkfUtil {
	/**
	 * ��mkf�ļ��ж�����id���ļ�.			
	 * [0, src[0]-1];    [0,src[0])
	 * mkf �ļ��������ֽ�Ϊ��λ,���α�ʾ�����ļ�ƫ��.
	 * mkf�ļ�ֻ�Ǽ򵥵Ľ�һϵ���ļ���������һ��. 
	 * 
	 * @param filaName
	 * @param id
	 * @return
	 */
	public static byte[] decode(byte[] src, int id) {
		int max = Convert.getInt4(src, 0) / 4 - 1;
		int start = Convert.getInt4(src, id*4);
		int end = Convert.getInt4(src, id*4+4);
		if (start - end == 0 || id > max - 1) {
			//System.out.println("mkf id = " + id + " " + start + " " + end);
			//System.exit(-1);
		}
		return Arrays.copyOfRange(src, start, end);	
	}
	
	public static byte[][] decode(byte[] src) {
		int max = Convert.getInt4(src, 0) / 4 - 1;
		byte[][] result = new byte[max][];
		for (int i = 0; i < result.length; i++) {
			result[i] = decode(src, i);
		}
		return result;
	}

	/**
	 * �����SHORT�͵�ѹ��. ������ʽѹ����ͬ, ������ʾ��ַ, ����ʾ��ַ/2, �����һ����Ϊ��0, Ӧ��Ϊ�ļ�����.
	 * 
	 * 20111107   ע�� :  ��ʵ���ǳ���, ��ʾ�ж��ٸ�������
	 */
	public static byte[] decode2(byte[] src, int id) {
		int max = Convert.getInt2(src, 0) - 1;
		int start = 2 * Convert.getInt2(src, id*2);
		int end = 2 * Convert.getInt2(src, id*2+2);
		if (start == end || id > max - 1) {
			//System.out.println("mkf id = " + id + " " + start + " " + end);
			//System.exit(-1);
			return null;
		}
		if (id == max - 1) {
			end = src.length;
		}
		return Arrays.copyOfRange(src, start, end);			
	}
	
	public static byte[][] decode2(byte[] src) {
		int max = Convert.getInt2(src, 0) - 1;			//Ӧ��Ҫ��2�İ�??
		byte[][] result = new byte[max][];
		for (int i = 0; i < result.length; i++) {
			result[i] = decode2(src, i);			
		}
		return result;
	}
	
	

	
	
	
	
	//////////////////////////////////������ѹ�� ///////////////////////////
	
	/**
	 * ��mkf�ļ��ж�����id���ļ�.			
	 * [0, src[0]-1];    [0,src[0])
	 * mkf �ļ��������ֽ�Ϊ��λ,���α�ʾ�����ļ�ƫ��.
	 * mkf�ļ�ֻ�Ǽ򵥵Ľ�һϵ���ļ���������һ��. 
	 * 
	 * @param filaName
	 * @param id
	 * @return
	 */
	public static byte[] encode(byte[][] src) {
		try {
			ByteArrayOutputStream head = new ByteArrayOutputStream();
			ByteArrayOutputStream body = new ByteArrayOutputStream();
			
			int start = (src.length + 1) * 4;
			head.write(Convert.fromInt4(start));
			
			for (int i = 0; i < src.length; i++) {
				if (src[i] == null) {
					System.out.println("write null " + i);
					head.write(Convert.fromInt4(start));
				} else {
					start += src[i].length;
					head.write(Convert.fromInt4(start));
					body.write(src[i]);
				}
			}
			
			//merge
			head.write(body.toByteArray());
			return head.toByteArray();
		} catch (IOException e) {
			//ignore...
			return null;
		}
	}
}











