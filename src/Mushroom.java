import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

class Mushroom extends GameCreature
{
	
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();
	protected int d_time=1;
	protected Action touch = Action.UNTOUCH;
	protected Box box=null;
	protected int XSPE=3,YSPE=15,yadd=3,xspe=-3;
//	protected boolean draw=false;
	
	Mushroom(int x, int y, GameClient gc) 
	{
		super(x, y, gc);
		obj_w=23;
		obj_h=25;
		all_w=23;
		all_h=25;
	}
	Mushroom(int x, int y,Box box ,GameClient gc) 
	{
		super(x, y, gc);
		obj_w=23;
		obj_h=25;
		all_w=23;
		all_h=25;
		this.box=box;
	}
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/mushroom1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/mushroom2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/mushroom3.png"))
				};
		obj_imgs.put("M1", imgs[0]);
		obj_imgs.put("M2", imgs[1]);
		obj_imgs.put("M3", imgs[2]);
	}
	
	public void draw(Graphics g) {
		//System.out.println(" "+x+" "+y);
		super.draw(g);
		if(draw==false||available==false) return;
		Image img = null;
		if(d_time<=15)
		{
			img=obj_imgs.get("M1");
			d_time++;
		}
		else if(d_time>15&&d_time<=30)
		{
			img=obj_imgs.get("M2");
			d_time++;
		}
		else if(d_time>30&&d_time<=45)
		{
			img=obj_imgs.get("M3");
			if(d_time==45)
			d_time=1;
			else d_time++;
		}
		
		Color c = g.getColor();
		g.drawImage(img, x, y, null);
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+obj_w,y,5,5);
		g.setColor(c);
		
		touchWithObjs();
//		touchWithHero(gc.player1);
//		action();
//		move();
	}

	public void move() {
		super.move();
		if(draw==false) return;
/*		if(box!=null)
		{
			if(box.touch==Action.BUNT)
				this.draw=true;
		}*/
		xMove();
		yMove();
	}

	protected void xMove()
	{
		x+=xspe;
	}
	
	protected void yMove()
	{
		if(act==Action.UNSTAND)
		{
			yspe+=yadd;
		}
		else if(act==Action.STAND)
		{
			yspe=-YSPE;
		}
		y+=yspe;
	}
	
	protected void action() {
		super.action();
		if(touch==Action.BUNT)
		{
			disappear();
		}
	}

	protected void disappear() 
	{
		
		if(draw==false) return;
		gc.player1.big=true;
		
		//嚼东西音效
		new GameAudio("嚼东西").start();
		
		super.disappear();
	}

	public void touchWithHero(Hero hero)
	{
		super.touchWithHero(hero);
		if(draw==false) return;
		if(hero.getNextRectangle().intersects(this.getRectangle()))
		{
			touch=Action.BUNT;
		}
		else
		{
			touch=Action.UNTOUCH;
		}
		action();
	}
	
	protected void checkAvailable() 
	{
		if(available==false) return;
		if((in_x+all_w>=hasrun_x-GameClient.F_W/2))
		{
			if(box.touch==Action.BUNT)
			{
				this.draw=true;
			}
		}
		else available=false;
	}

	protected void touchWithObjs() 
	{
		if(draw==false||available==false) return;
		GameObject obj1=null;
		GameObject obj2=null;
		for(int i=0;i<objs.size();i++)
		{
			GameObject obj=null;
			obj = objs.get(i);
/*			if((x+xspe>obj.x&&x+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)||(x+all_w+xspe>obj.x&&x+all_w+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h))//穿越物体检测1
			{
				if(obj.y>=y) return;
				if(xspe>=0)
				{
					x=obj.x-all_w;
				}
				else
				{
					x=obj.x+obj.all_w;
				}
				System.out.println("GC穿越物体检测1");
				//System.out.println(getNextRectangle()+" "+obj.getRectangle()+" "+getNextRectangle().intersects(obj.getRectangle()));
			}*/
			if((obj.draw==true&&getNextRectangle().intersects(obj.getRectangle())==true
					&&(obj!=obj1&&obj!=obj2))||obj.throughCheck(this)
					||((x+xspe>obj.x&&x+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)||(x+all_w+xspe>obj.x&&x+all_w+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)))
			{
				if((x+xspe>obj.x&&x+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)||(x+all_w+xspe>obj.x&&x+all_w+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h))
				{//穿越护体检测2
					if(obj.y>=y) return;
					if(xspe>=0)
					{
						x=obj.x-all_w;
					}
					else
					{
						x=obj.x+obj.all_w;
					}
					System.out.println("MU穿越物体检测1");
				}
				if(obj.throughCheck(this))//穿越物体检测2
				{
					if(obj.y>=y) return;
					x-=xspe;
					y-=yspe;
					System.out.println("MU穿越物体检测2");
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
				y=obj1.y-all_h;
				act=Action.STAND;
			}
			else
			{
				act=Action.UNSTAND;
			}
			
			if(x>=obj1.x+obj1.all_w&&xspe<0)
			{
				touch=Action.LTOUCH;
				x=obj1.x+obj1.all_w+XSPE;	
				xspe=XSPE;
			}
			else if(x+all_w<=obj1.x&&xspe>0)
			{
				touch=Action.RTOUCH;
				x=obj1.x-all_w-XSPE;
				xspe=-XSPE;
			}
			else if(y>=obj1.y+obj1.all_h&&yspe<0)
			{
				touch=Action.BUNT;
			}
			else
			{
				touch=Action.UNTOUCH;
			}
//			if(touch!=Action.UNTOUCH)
//			System.out.println("GC有一个物体即将碰撞 "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y);
		}
		else if(obj1!=null&&obj2!=null)//有两个物体与mario接触时
		{
			int ground=0;//找出作为地面的物体和作为墙的物体
			if(x+all_w>=obj1.x&&x<=obj1.x+obj1.all_w&&obj1.y>=y)
			{
				ground=1;
			}
			else if(x+all_w>=obj2.x&&x<=obj2.x+obj2.all_w&&obj2.y>=y)
			{
				ground=2;
			}
			else
			{
				//System.out.println("错误！无物体在mario下");
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
			if(x+all_w>=obj.x+obj.all_w&&xspe<=0)
			{
				touch=Action.LTOUCH;
				xspe=XSPE;
			}
			else if(x<=obj.x&&xspe>=0)
			{
				touch=Action.RTOUCH;
				xspe=-XSPE;
			}
//			if(touch!=Action.UNTOUCH)
//			System.out.println("GC有两个个物体即将碰撞     "+act+" "+touch+" xspe "+xspe+" yspe "+yspe);
		 
		}
		else if(obj1==null&obj2==null)
		{
			act=Action.UNSTAND;
			touch=Action.UNTOUCH;
			//System.out.println("GC没有物体即将碰撞 "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y+" yspe "+yspe);
		}
	}
	
}
