package pal.game;

import java.util.Timer;
import java.util.TimerTask;

import pal.resource.Resource;
import pal.util.Convert;

public class Script {

	private byte[] data = Resource.getScript();
	private Game game;
	private int id;
	
	private int cmd;
	private int arg0;
	private int arg1;
	private int arg2;
	
	private boolean wait = false;
	
	public Script(Game game, int id) {
		this.game = game;
		this.id = id;
	}
	
	public boolean next() {
		return !wait && Convert.getInt2(data, id*8) != 0;
	}
	
	public void execute() {
		cmd = Convert.getInt2(data, id*8);
		arg0 = Convert.getInt2(data, id*8+2);
		arg1 = Convert.getInt2(data, id*8+4);
		arg2 = Convert.getInt2(data, id*8+6);
		//System.out.println("script : id = " + id + " " + cmd + " " + arg0 + " " + arg1 + " " + arg2);
		interpret();
		id++;
	}
	
	
	/*
	 * �ű�������
	 */
	public static final int TALK = 0xFFFF;					//TALK
	
	
	/**
	 * ����Ŀ�λ���ǵ�ͼ�Ŀ�,�����ɽ���ͼͼԪ��32x15�����Σ�����������ÿ�ж��Ǿ����״�ģ�
	 * ��MAP.MKF�е�XӦ�õ���(����1*2+����3),����2ΪY,����3ֻ��"0"��"1"���������˵���Ƿ��������Ǹ�����λ�á�
	 */
	public static final int SET_ROLE_POS = 0x46;		//���ö��������
	
	/**
	 *����1�ǽ�ɫ��ţ�"0"������ң��"1"�������..��
	 *����2��MGO.MKF�е������ţ��������ֵȥ���ض�Ӧ��MGO���ļ���ʵ���Ƕ��RLE��ʽλͼ��
	 *����3������˵���Ƿ��ݻ��������ģ�falseΪ�������¡���Ϊÿ��MGO�����ļ��а������λͼ,
	 *������λͼ��ָ����һ֡��ȥ�������ػ���������������ԸĴ�����true�󣬽��ݻ����£�ֱ����0x0015��ָ��(���ö�Ա����/֡)ʱ�Ÿ����� 
	 */
	public static final int SET_ROLE_TILE = 0x65;		//���ö�Ա����
	
	/**
	 * ;����1Ϊ֡������2Ϊ���򡣲���3Ϊ��ɫ���š�
	 */
	public static final int SET_ROLE_INDEX = 0x15;		//���ö�Ա�����֡
	
	/**
	 * �ɽ��пɿ��ƵĽ�ɫֻ��6������Ϸ��ʼ��Ӧ�ó�ʼ������6����ɫ�Ŀռ䡣������ʾʱֻ��ʾ��Ӻ�������
	 * Ҳ���������������壬���������ֻ��3����ɫ����������Ҳ����˵������ĳ�Ա�ˣ�"0"Ϊ��λ�á�
	 */
	public static final int SET_GROUP = 0x75;
	
	/**
	 * 
	 */
	public static final int UI_REPAINT = 0x05;
	
	public static final int TALK_POS_CENTER = 0x3B;
	public static final int TALK_POS_BOTTOM = 0x3D;
	public static final int SCENE_REFRESH = 0x09;
	public static final int SCENE_CHANGE = 0x59;
	public static final int ROLE_WALK = 0x70;
	public static final int SET_NPC_TILE = 0x16;
	public static final int SET_NPC_STATE = 0x49;
	public static final int EVENTOBJECT_WALK = 0x6C;
	/**
	 * �ű�����. 
	 * 
	 * @param cmd
	 * @param x
	 * @param y
	 * @param z
	 */
	private void interpret() {
		System.out.println("script: " + id + ' ' + cmd + ' ' + arg0 + ' ' + arg1 + ' ' + arg2);
		
		int command = cmd;
		int x = arg0;
		int y = arg1;
		int z = arg2;
		switch (command) {
		case SET_ROLE_POS :
			game.setPos(x, y, z==0?true:false);
			refresh(500);
			break;
		case SET_ROLE_TILE :
			game.getRole(x).setTile(y);
			break;
		case SET_ROLE_INDEX :
			game.getRole(z).setDirection(x);
			game.getRole(z).setIndex(y);
			break;
		case SET_GROUP :
			game.setGroup(x, y, z);
			break;
		case UI_REPAINT :
			//refresh(100);
			break;
		case TALK_POS_CENTER :
			game.getTalk().setPos(Talk.POS_CENTER);
			break;
		case TALK_POS_BOTTOM :
			game.getTalk().setPos(Talk.POS_BOTTOM);
			break;
		case TALK :
			game.showTalk(x);
			break;
		case SCENE_REFRESH :
			//game.repaintUI();						//ͬһ�߳�, �޷�ˢ����~~~
			refresh(z*200+200);
			break;
		case SCENE_CHANGE :
			game.changeScene(x);
			break;
		case ROLE_WALK :
			game.setPos(x, y, z==0?true:false);
			break;
		case SET_NPC_TILE :
			game.getEventObject(x).setFrame(z);			//y������roleId;
			break;
		case SET_NPC_STATE :
			game.getEventObject(x).setState(y);
			break;
		case EVENTOBJECT_WALK :
			EventObject e = game.getEventObject(x);
			y = (short)(y & 0xffff);
			z = (short)(z & 0xffff);
			e.setX(e.getX()+y);
			e.setY(e.getY()+z);			
		}
	}

	private void refresh(int delay) {
		game.refresh();
		new Timer().schedule(new TimerTask() {
		
			@Override
			public void run() {
				game.clearState();
			}
		
		}, delay);
	}
	
	
	
	
	
	
	

}
