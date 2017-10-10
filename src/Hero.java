import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Hero 
{
	protected static final int XSPE=12,YSPE=27;
	public int spe_add=2,xspe=0,yspe=0,spe1=0,rub_add=1,g_add=3,y_add=17,j_time=0,j_lim=2,die_t=0;
	public int hero_w=25,hero_h=40;
	public int x,y,x1,y1,x2,y2,hasrun_x=0;
	public final static int LIM_X1=0,LIM_X2=550;
	protected boolean march,can_j=true,finish=false;
	protected boolean b_l,b_u,b_r,b_d;
	public boolean live = true;
	public Action act = Action.UNSTAND;
	public Action touch = Action.STAND;
	public Action last_t = Action.UNSTAND;
	protected Dirction add_dir=Dirction.STOP;
	protected Dirction move_dir=Dirction.STOP;
	protected Dirction face_dir=Dirction.R;
	private List<GameObject> objs=null;
	protected static Map<String,Image> hero_img = new HashMap<String,Image>();
	public boolean big=false;
	protected GameClient gc=null;
	
	public Hero(int x, int y,GameClient gc) 
	{
		this.x = x;
		this.y = y;
		this.gc = gc;
	}
	
	public void draw(Graphics g)
	{
		move();
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x,(y+hero_h),5,5);
		g.fillOval(x+hero_w,y,5,5);
		g.setColor(c);
		if(this.live==true)
		{
			this.setMarch();
			setObjs(gc.obj_map.objs);
			touchWithObjs();
		}
	}
	
	private void setHasrun() 
	{
		if(x+xspe>=LIM_X2)
		{
		hasrun_x+=xspe;
		}
	}

	public void setObjs(List<GameObject> objs) {
		this.objs = objs;
	}
	
	public void keyPressed(KeyEvent e) 
	{
		if(live==false||finish==true) return;
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_LEFT) b_l=true;
		else if (key==KeyEvent.VK_UP) b_u=true;
		else if (key==KeyEvent.VK_RIGHT) b_r=true;
		else if (key==KeyEvent.VK_DOWN) b_d=true;
	}

	public void keyReleased(KeyEvent e) 
	{
		if(finish==true) return;
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_LEFT) b_l=false;
		else if (key==KeyEvent.VK_UP) b_u=false;
		else if (key==KeyEvent.VK_RIGHT) b_r=false;
		else if (key==KeyEvent.VK_DOWN) b_d=false;
		//else if (key==KeyEvent.VK_F1) relive();
	}
	
	public void relive() 
	{
		if(live==false)
		{
			big=false;
			live=true;
			finish=false;
			x=50;
			y=100;
			xspe=0;
			yspe=0;
			die_t=0;
		}
	}
	
	public void reset()
	{
		hasrun_x=0;
		relive();
		//System.out.println("big "+big+" march "+march);
	}

	protected void setDir()
	{
		if(xspe>0) move_dir=Dirction.R;
		if(xspe<0) move_dir=Dirction.L;
		if(xspe==0) move_dir=Dirction.STOP;
	}
	
	protected void setMarch()
	{
		if(move_dir==add_dir) march=true;
		else march=false;
		if(finish==true) march=true;//完成任务时是走的状态
	}
	
	protected void setFacedir()
	{
		if(add_dir!=Dirction.STOP)
		face_dir=this.add_dir;
	}
	
	protected int getXAdd()
	{
		if(finish==true) return 0;
		int add = 0;
		if(b_l==false&&b_r==false) 
		{
			add=0;
			add_dir=Dirction.STOP;
		}
		else if(b_l==true) 
		{
			add=(-spe_add);
			add_dir=Dirction.L;
		}
		else if(b_r==true) 
		{
			add=spe_add;
			add_dir=Dirction.R;
		}
		return add;
	}

	protected int get_rub_Add()
	{
		if(live==false||finish==true) return 0;
		int rub = 0;
		if(xspe!=0) 
		{
			if(move_dir==Dirction.R) rub=(-rub_add);
			else if(move_dir==Dirction.L) rub=(rub_add);
		}
		if(touch==Action.LTOUCH||touch==Action.RTOUCH)
		{
			rub=0;
		}
		return rub;
	}
	
	protected void jump()
	{
		if(can_j&&act==Action.STAND)
		{
			yspe-=y_add*1.3;
			b_u=false;
		}
		else if(can_j==true&&act==Action.UNSTAND)
		{	
			yspe=-y_add*1;
			b_u=false;
		}
		if(yspe<=-YSPE)
		{
			yspe=-YSPE;
			can_j=false;
		}
		j_time++;
		act=Action.UNSTAND;
		
		//跳跃声音
		new GameAudio("水泡").start();
	}
	
	public void move()
	{
		if(y>gc.F_H+100) return;
		xMove();
		yMove();
		setHasrun();
		if(y>600)
		{
			if(die_t==0)
			die();
		}
		if(x>gc.F_W&&finish==true)
		{
			xspe=0;
			yspe=0;
			live=false;
			gc.d_game=false;
			gc.d_menu=true;
		}
	}
	
	protected void die()
	{
		//人物属性设置
		live=false;
		die_t++;
		act=Action.UNSTAND;
		touch=Action.UNTOUCH;		
		xspe=0;
		yspe=-YSPE;
		hasrun_x=0;
		System.out.println("die");
		
		//失败音效
		new GameAudio("失败").start();
	}
	
	public void finish()
	{
		if(finish==false)
		{
			finish=true;
			xspe=XSPE;
			
			//过关音效
			new GameAudio("庆祝").start();
		}
	}
	
	private void yMove() {
		if(b_u==true&&can_j==true&&j_time<j_lim)
		{
			jump();
			b_u=false;
		}
		else if (act==Action.STAND&&yspe>0)
		{
			can_j=true;
			j_time=0;
			yspe=1;
		}
		else if (act==Action.UNSTAND)
		{
			yspe+=g_add;
		}
		int g_add1=g_add;
		if(touch==Action.UNTOUCH)
		{
			g_add=g_add1;
		}
		else if(touch==Action.LTOUCH)
		{
			yspe=0;
			//g_add=0;
		}
		else if(touch==Action.RTOUCH)
		{
			yspe=0;
			//g_add=0;
		}
		else if(touch==Action.BUNT)
		{
			yspe=0;
		}
		
		y+=yspe;
	}


	private void xMove() {
		this.setFacedir();
		this.setDir();
		int xadd=this.getXAdd();
		int radd=this.get_rub_Add();
		spe1+=(xadd+radd);

		if(touch==Action.UNTOUCH)
		{
			rub_add=1;
		}
		//碰撞后弹回
		else if(touch==Action.LTOUCH)
		{
			//xspe=-xspe/2;
			x+=hero_w/2;
			rub_add=3;
			b_l=false;
			if(b_l==false)
			{
				xspe=0;
			}
		}
		else if(touch==Action.RTOUCH)
		{
			//xspe=-xspe/2;
			x-=hero_w/2;
			rub_add=3;
			b_r=false;
			if(b_r==false)
			{
				xspe=0;
			}
		}
		if(-XSPE<spe1&&spe1<XSPE)
		{
			xspe=spe1;
		}
		else
		{
			spe1=xspe;
		}
		if((x+xspe>LIM_X2||x+xspe<LIM_X1)&&finish==false)//未到终点且越过边界时
		{
			x=x1;
		}
		else
		{
			x+=xspe;
			x1=x;
		}
	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,hero_w,hero_h);
	}
	public Rectangle getARectangle(int x,int y,int w,int h)
	{
		return new Rectangle(x,y,w,h);
	}
	public Rectangle getNextRectangle()
	{
		return new Rectangle(x+xspe,y+yspe,hero_w,hero_h);
	}
	protected void touchWithObjs() 
	{
		if(live==false) return;
		GameObject obj1=null;
		GameObject obj2=null;
		for(int i=0;i<objs.size();i++)
		{
			GameObject obj=null;
			obj = objs.get(i);
			
			if((x>obj.x&&x<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)
				||(x+hero_w>obj.x&&x+hero_w<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h))//穿越物体检测1
			{
				if(obj.y>=y-hero_h) 
					{
						y=obj.y-hero_h;
						yspe=1;
						System.out.println("Hero穿越物体检测1退出");
						return;
					}
				if(xspe>=0)
				{
					x=obj.x-hero_w;
				}
				else
				{
					x=obj.x+obj.all_w;
				}
				act=Action.UNSTAND;
				System.out.println("Hero穿越物体检测1");
			}
			if((obj.draw==true&&getNextRectangle().intersects(obj.getRectangle())==true&&(obj!=obj1&&obj!=obj2))||obj.throughCheck(this))
			{
				if(obj.throughCheck(this))//穿越物体检测2
				{
					if(obj.y>=y) return;
					x-=xspe;
					y-=yspe;
					System.out.println("Hero穿越物体检测2");
				}
				if(obj1==null)
				{
					obj1=obj;
					//System.out.println("obj1  "+obj.getRectangle());
				}
				else if(obj1!=null)
				{
					obj2=obj;
					//System.out.println("obj2  "+obj.getRectangle());
				}
				if(obj1!=null&&obj2!=null)
				{
					//System.out.println("这里需要一个新的obj了   "+obj.getRectangle()+" obj1 "+obj1.getRectangle()+" obj2 "+obj2.getRectangle());
				}
			}
		}
		if(obj1!=null&&obj2==null)//只有一个物体与mario接触时
		{
			if(y<=obj1.y)
			{
				y=obj1.y-hero_h;
				act=Action.STAND;
			}
			else
			{
				act=Action.UNSTAND;
			}
			
			if(x>=obj1.x+obj1.all_w&&xspe<0)
			{
				touch=Action.LTOUCH;
			}
			else if(x+hero_w<=obj1.x&&xspe>0)
			{
				touch=Action.RTOUCH;
			}
			else if(y>=obj1.y+obj1.all_h&&yspe<0)
			{
				touch=Action.BUNT;
				y=obj1.y+obj1.all_h;
			}
			else
			{
				touch=Action.UNTOUCH;
			}
/*			if(touch!=Action.UNTOUCH)
			System.out.println("有一个物体即将碰撞 "+act+" "+touch+" x "+x+" y "+y);*/
		}
		else if(obj1!=null&&obj2!=null)//有两个物体与mario接触时
		{
			int ground=0;//找出作为地面的物体和作为墙的物体
			if(x+hero_w>=obj1.x&&x<=obj1.x+obj1.all_w&&obj1.y>=y)
			{
				ground=1;
			}
			else if(x+hero_w>=obj2.x&&x<=obj2.x+obj2.all_w&&obj2.y>=y)
			{
				ground=2;
			}
			else
			{
				System.out.println("hero与两物体碰撞但无物体在mario下");
				return ;
			}
			//对于墙物体的处理
			GameObject obj = null;
			if(ground==1)
			{
				obj=obj2;
			}
			else if(ground==2)
			{
				obj=obj1;
			}
			if(x+hero_w>=obj.x+obj.all_w&&xspe<=0)
			{
				touch=Action.LTOUCH;
			}
			else if(x<=obj.x&&xspe>=0)
			{
				touch=Action.RTOUCH;
			}
			//if(touch!=Action.UNTOUCH)
			//System.out.println("有两个个物体即将碰撞     "+act+" "+touch+" xspe "+xspe+" yspe "+yspe);
		 
		}
		else if(obj1==null&obj2==null)
		{
			act=Action.UNSTAND;
			touch=Action.UNTOUCH;
			//System.out.println("没有物体即将碰撞 "+act+" "+touch+" x "+x+" y "+y);
		}

	}
}
