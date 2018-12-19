import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;


class GameCreature extends GameObject
{
	protected int XSPE=10,YSPE=10;
	protected int xspe,yspe,xadd,yadd=3;
	protected List<GameObject> objs=null;
	
	public boolean draw = false;
	
	GameCreature(int x,int y,GameClient gc)
	{
		super(x,y,gc);
	}
	
	public void draw(Graphics g)
	{
		setObjs(gc.obj_map.objs);
		super.draw(g);
//		setAvailable();
//		getHasrun();
//		touchWithHero(gc.player1);
//		move();
//		action();
	}

	/**
	 * 设置物体还是否有效（是否绘制，是否检测碰撞）
	 */
	protected void checkAvailable()
	{
		if(available==false) return;
		if(getRectangle().intersects(new Rectangle(0,0,GameClient.F_W,GameClient.F_H)))
		{
			this.draw=true;
		}
		else if(x<=-GameClient.F_W/2)
		{
			draw=false;
			available=false;
		}
	}
	
	public void move()
	{
		super.move();
	}
	
	protected void xMove()
	{
		
	}
	
	protected void yMove()
	{
		
	}
	
	protected void disappear()
	{
		if(draw==true)
		{
			draw=false;
			available=false;
		}
		else return;
		
	}
	
	public void touchWithHero(Hero hero)
	{
		super.touchWithHero(hero);
	}
	
	protected void action()
	{
		super.action();
	}
	
	public void setObjs(List<GameObject> objs) {
		this.objs = objs;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,all_w,all_h);
	}
	public Rectangle getARectangle(int x,int y,int w,int h)
	{
		return new Rectangle(x,y,w,h);
	}
	public Rectangle getNextRectangle()
	{
		return new Rectangle(x+xspe,y+yspe,all_w,all_h);
	}
	
	/*protected void touchWithObjs() 
	{
		if(draw==false||available==false) return;
		GameObject obj1=null;
		GameObject obj2=null;
		for(int i=0;i<objs.size();i++)
		{
			GameObject obj=null;
			obj = objs.get(i);
			if((x+xspe>obj.x&&x+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h)||(x+all_w+xspe>obj.x&&x+all_w+xspe<obj.x+obj.all_w&&y>obj.y&&y<obj.y+obj.all_h))//穿越物体检测1
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
			}
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
					System.out.println("GC穿越物体检测1");
				}
				if(obj.throughCheck(this))//穿越物体检测2
				{
					if(obj.y>=y) return;
					x-=xspe;
					y-=yspe;
					System.out.println("GC穿越物体检测2");
				}
				if(obj1==null)
				{
					obj1=obj;
					System.out.println("obj1  "+obj.getRectangle());
				}
				else if(obj1!=null)
				{
					obj2=obj;
					System.out.println("obj2  "+obj.getRectangle());
				}
				if(obj1!=null&&obj2!=null)
				{
					System.out.println("这里需要一个新的obj了   "+obj.getRectangle()+" obj1 "+obj1.getRectangle()+" obj2 "+obj2.getRectangle());
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
			if(touch!=Action.UNTOUCH)
			System.out.println("GC有一个物体即将碰撞 "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y);
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
			if(touch!=Action.UNTOUCH)
			System.out.println("GC有两个个物体即将碰撞     "+act+" "+touch+" xspe "+xspe+" yspe "+yspe);
		 
		}
		else if(obj1==null&obj2==null)
		{
			act=Action.UNSTAND;
			touch=Action.UNTOUCH;
			//System.out.println("GC没有物体即将碰撞 "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y+" yspe "+yspe);
		}
	}*/
	
}
