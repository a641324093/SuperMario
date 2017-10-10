import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


class Menu 
{
	protected GameClient gc;
	protected boolean initialize=false;
	protected BackGroundMap bgm1 = null,bgm2 = null;
	protected GameButton b_start=null,b_exit=null,b_restart=null;
	protected Container c = null;
	protected JLabel label_t1 = null,label_gameover=null,label_bg=null,label_win=null;
	protected static Toolkit tk =Toolkit.getDefaultToolkit();
	protected static Image [] imgs = null;
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();
	protected Thread mp3thread1 = null,mp3thread2=null;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/title1.png")),
				};
		obj_imgs.put("T1", imgs[0]);
	}
	
	Menu(GameClient gc)
	{
		this.gc=gc;
		
	}
	
	public void draw(Graphics g)
	{
		if(initialize==true)
		{
			if(gc.player1.live==true)
			{
				//一开始进入游戏时的主菜单的背景
				bgm1.draw(g);
				
				//组件重画
				b_exit.repaint();
				b_start.repaint();
				label_t1.repaint();
			}
			else if(gc.player1.live==false)
			{
				//死亡后菜单进入的背景
				bgm2.draw(g);
				
				//音乐重新设置
				if(mp3thread2.isAlive())
				mp3thread2.stop(); //游戏背景音乐关闭
				
				//菜单背景音乐开始
				if(mp3thread1.isAlive()==false)
				{
					mp3thread1 = new Thread(new Mp3Thread("冒险岛1"));
					mp3thread1.start();
					System.out.println("新建了一个 Mp3thread1");
				}
				
				//组件重画
				b_exit.repaint();
				b_restart.repaint();
				if(gc.player1.finish==false)
				{
					label_gameover.repaint();
				}
				else
				{
					label_win.repaint();
				}
			}
		}
		if(initialize==true&&gc.d_game==false&&gc.d_menu==true&&gc.player1.live==false&&gc.player1.finish==false)//在人物死亡时的菜单
		{
			//背景音乐处理
			
			
			b_exit.setVisible(true);
			b_restart.setVisible(true);
			label_gameover.setVisible(true);
			label_win.setVisible(false);
		}
		else if(initialize==true&&gc.d_game==false&&gc.d_menu==true&&gc.player1.live==false&&gc.player1.finish==true)//在人物死亡时的菜单
		{
			//背景音乐处理
			
			
			b_exit.setVisible(true);
			b_restart.setVisible(true);
			label_win.setVisible(true);
			label_gameover.setVisible(false);
		}
		else if(initialize==false)//游戏开始前的菜单设置
		{	
			//初始设置菜单按钮
		
			//开始 退出 重新游戏按钮设置
			c=gc.getContentPane();
			
			label_bg = new JLabel();
			label_bg.setBounds(-5,-26,800,600);
			label_bg.setBackground(Color.blue);
			label_bg.setVisible(true);
			label_bg.setOpaque(false);
			c.add(label_bg);
			gc.label_bg=this.label_bg;
			
			gc.getContentPane().add(label_bg,null);
			b_start= new GameButton("START");
			b_start.setBounds(300,280,224,35);
			b_start.addActionListener(new ButtonListener(this));
			b_start.setContentAreaFilled(false);
			label_bg.add(b_start);
			gc.b_start=this.b_start;
			b_exit= new GameButton("EIXT");
			b_exit.setBounds(300,330,224,35);
			b_exit.addActionListener(new ButtonListener(this));
			b_exit.setContentAreaFilled(false);
			label_bg.add(b_exit);
			
			gc.b_exit=this.b_exit;
			b_restart = new GameButton("RESTART");
			b_restart.setBounds(300,280,224,35);
			b_restart.addActionListener(new ButtonListener(this));
			b_restart.setContentAreaFilled(false);
			label_bg.add(b_restart);
			gc.b_restart=this.b_restart;
			b_restart.setVisible(false);
			
			//游戏名字文本框
			label_t1 = new JLabel();
			label_t1.setIcon(new ImageIcon(obj_imgs.get("T1")));
			label_t1.setBounds(265,100,300,147);
			label_t1.setOpaque(false);
			
			label_bg.add(label_t1);
			gc.label_t1=this.label_t1;
			
			
			label_gameover= new JLabel("GAME OVER");
			label_gameover.setFont(new Font("GAME OVER",Font.BOLD,55));
			label_gameover.setForeground(Color.red);//设置字体颜色
			label_gameover.setBounds(255,100,400,200);
			label_gameover.setOpaque(false);
			label_gameover.setVisible(false);
			label_bg.add(label_gameover);
			gc.label_gameover=this.label_gameover;
			
			label_win= new JLabel("YOU WIN");
			label_win.setFont(new Font("YOU WIN",Font.BOLD,55));
			label_win.setForeground(Color.red);//设置字体颜色
			label_win.setBounds(285,130,300,150);
			label_win.setOpaque(false);
			label_win.setVisible(false);
			label_bg.add(label_win);
			gc.label_win=this.label_win;
			
			
			
			bgm1 = new BackGroundMap(2,gc);
			bgm2 = new BackGroundMap(3,gc);
			initialize=true;
			
			//背景音乐播放
			mp3thread1 = new Thread(new Mp3Thread("冒险岛1"));
			mp3thread2 = new Thread(new Mp3Thread("冒险岛2"));
			
			mp3thread1.start();
			
			
		}

		
	}
	private class ButtonListener implements ActionListener
	{

		GameClient gc =null;
 		Menu menu = null;
		ButtonListener(Menu menu)
		{
			this.menu=menu;
			this.gc=menu.gc;
		}
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==menu.b_start)
			{
				//菜单背景音乐停止播放
					mp3thread1.stop();
					
				
				//游戏背景音乐开始播放
					mp3thread2.start();
				
				
				gc.d_menu=false;
				gc.d_game=true;
	
				gc.setFocusable(true);//焦点设置回窗口
			}
			else if(e.getSource()==menu.b_exit)
			{
				System.exit(0);
			}
			else if(e.getSource()==menu.b_restart)
			{
				gc.player1.reset();
				gc.obj_map.reset(1);
				gc.img_map.reset(1);
				gc.d_menu=false;
				gc.d_game=true;
				gc.setFocusable(true);
				
				//重设背景音乐
				if(mp3thread1.isAlive())
				mp3thread1.stop();
				if(mp3thread2.isAlive()==false)
				{
					mp3thread2 = new Thread(new Mp3Thread("冒险岛2"));
					mp3thread2.start();
				}
			}
			new GameAudio("点击").start();
			System.out.println("点击！");
		}
		
	}
}
