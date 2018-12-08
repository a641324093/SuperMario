import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameClient extends JFrame //JFrame重画不调用update 方法 双缓冲代码要写到paint中
{
	
	
	public final static int F_W=800,F_H=600;
	protected Image offscreen = null;
	//处于菜单、处于游戏状态
	protected boolean d_menu=true,d_game=false,initialize=false;
	
	Thread paint_thread=null;
	Hero player1 = new Mario1(100,100,this);
	BackGroundMap bgmap = new BackGroundMap(1,this);
	ObjectMap obj_map = new ObjectMap(1,this);
	ImageMap img_map = new ImageMap(1,this);
	Menu menu = new Menu(this);
	JButton b_start=null,b_exit=null,b_restart=null;
	JLabel label_t1=null,label_bg=null,label_gameover=null,label_win=null;
	Graphics offscreen_g=null;
	
	GameClient()
	{
		super();
		this.setBounds(0,0,F_W,F_H);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new MyWindowListener());
		this.setLayout(null);
		this.setBackground(Color.cyan);
		
	}
	
/*	public void update(Graphics g)
	{
		
		
	}*/
	
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}

	//绘制逻辑
	public void paint(Graphics g)
	{
		//双缓冲准备
		if(initialize==false)
		{
			if(offscreen == null)
			{
				offscreen = this.createImage(F_W,F_H);//后置缓存
			}
			offscreen_g = offscreen.getGraphics();
			initialize=true;
		}
		
		if(player1.live==false&&player1.y>=610)//人物已经死亡且落于屏幕外转换到菜单界面
		{
			d_menu=true;
			d_game=false;
		}
		if(d_menu==true)
		{
			menu.draw(offscreen_g);
			label_bg.setIcon(new ImageIcon(offscreen));//将背景图片放入背景Label中
		}
		else if(d_menu==false)
		{
			b_start.setVisible(false);
			b_exit.setVisible(false);
			b_restart.setVisible(false);
			label_t1.setVisible(false);
		}
		if(d_game==true)
		{
			bgmap.draw(offscreen_g);
			img_map.draw(offscreen_g);
			obj_map.draw(offscreen_g);
			player1.draw(offscreen_g);
		}
		else if(d_game==false)
		{
			
		}
		g.drawImage(offscreen,0,0,null);
	}
	
	public void launchFrame ()
	{
		this.setTitle("Super Mario");
		//图像刷新线程
		paint_thread = new Thread(new PaintThread());
		paint_thread.start();
		this.addKeyListener(new MyMonitor());
		this.addMouseListener(new MyMouseListener());
	}
	
	private class MyMonitor extends KeyAdapter
	{

		public void keyPressed(KeyEvent e) {
			player1.keyPressed(e);
			//System.out.println("ok1");
		}
		public void keyReleased(KeyEvent e) {
			player1.keyReleased(e);
			//System.out.println("ok2");
		}
	}
	
	private class MyMouseListener implements MouseListener
	{

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			new GameAudio("鼠标进入").start();
		}

		public void mouseExited(MouseEvent e) {
			new GameAudio("鼠标进入").start();
		}
		
	}
	
	private class PaintThread implements Runnable
	{
		public void run()
		{
			while(true)
			{
				repaint();//为什么错
				try {
					Thread.sleep(49);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String args[])
	{
		GameClient gc = new GameClient();
		gc.launchFrame();
	}
}
