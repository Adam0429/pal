package pal.game;

import pal.resource.Resource;
import pal.util.Convert;

public class Scene {
	private static byte[] data = Resource.getScene();
	public static int getMax() {
		return data.length / 8 - 2;
	}
	//private Map map;
	//private EventObject[] events;	
	private int mapId;
	private int enterScriptId;
	private int exitScriptId;
	private int eventBeginId;
	private int eventEndId;
	
	public Scene(int id) {
		//id--;		//ԭʼ�ļ�����ID��1��ʼ��  //����Game���������.
		mapId = Convert.getInt2(data, id*8+0);
		enterScriptId = Convert.getInt2(data, id*8+2);
		exitScriptId = Convert.getInt2(data, id*8+4);		
		eventBeginId = Convert.getInt2(data, id*8+6);
		eventEndId = Convert.getInt2(data, id*8+14);		//���һ�����ļ�βΪβ.
	}

	public int getEnterScriptId() {
		return enterScriptId;
	}

	public void setEnterScriptId(int enterScriptId) {
		this.enterScriptId = enterScriptId;
	}

	public int getExitScriptId() {
		return exitScriptId;
	}

	public void setExitScriptId(int exitScriptId) {
		this.exitScriptId = exitScriptId;
	}

	public int getEventBeginId() {
		return eventBeginId;
	}

	public int getEventEndId() {
		return eventEndId;
	}

	public int getMapId() {
		return mapId;
	}
	
	
	
	

}
