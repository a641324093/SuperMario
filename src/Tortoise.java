import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

class Tortoise extends GameCreature 
{
	
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();//这个不能放到父类中
	protected Dirction dir= Dirction.L;
	protected boolean hit=false;
	protected int XSPE=2,YSPE,xspe,yspe,d_time=1,yadd=3,hit_time=0;
	protected boolean initialize=false;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/Ltortoise1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/Ltortoise2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/shell1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/shell2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/shell3.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/shell4.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/Rtortoise1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/Rtortoise2.png")),
				};
		obj_imgs.put("LT1", imgs[0]);
		obj_imgs.put("LT2", imgs[1]);
		obj_imgs.put("S1", imgs[2]);
		obj_imgs.put("S2", imgs[3]);
		obj_imgs.put("S3", imgs[4]);
		obj_imgs.put("S4", imgs[5]);
		obj_imgs.put("RT1", imgs[6]);
		obj_imgs.put("RT2", imgs[7]);
	}

	Tortoise(int x, int y,Dirction move_dir, GameClient gc) 
	{
		super(x, y, gc);
		if(move_dir==Dirction.L) xspe=-XSPE;
		else xspe=XSPE;
		obj_w=28;
		obj_h=37;
		all_w=28;
		all_h=37;
	}
	
	public void draw(Graphics g) {
		if(initialize==false)
		{
			for(int i=0;i<imgs.length;i++)
			{
				g.drawImage(imgs[i],-200,-200,null);
			}
			initialize=true;
		}
		super.draw(g);
		setDir();
		Image img = null;
		if(hit==false)
		{
			if(dir==Dirction.L)
			{
				if(d_time<=3)
				{
					img=obj_imgs.get("LT1");
					d_time++;
				}
				else if(d_time>3&&d_time<=6)
				{
					img=obj_imgs.get("LT2");
					if(d_time==6)
					d_time=1;
					else 
					d_time++;
				}
			}
			else if(dir==Dirction.R)
			{
				if(d_time<=3)
				{
					img=obj_imgs.get("RT1");
					d_time++;
				}
				else if(d_time>3&&d_time<=6)
				{
					img=obj_imgs.get("RT2");
					if(d_time==6)
					d_time=1;
					else 
					d_time++;
				}
			}
		}
		else if(hit==true)
		{
			if(hit_time<=1)
			{
				img=obj_imgs.get("S3");
			}
			else
			{
				if(d_time<=2)
				{
					img=obj_imgs.get("S1");
					d_time++;
				}
				else if(d_time>2&&d_time<=4)
				{
					img=obj_imgs.get("S2");
					d_time++;
				}
				else if(d_time>4&&d_time<=6)
				{
					img=obj_imgs.get("S3");
					d_time++;
				}
				else if(d_time>6&&d_time<=8)
				{
					img=obj_imgs.get("S4");
					if(d_time==8)
						d_time=1;
						else 
						d_time++;
				}
			}
			g.drawImage(img,x,y,null);
		}
		
		Color c = g.getColor();
		g.drawImage(img, x, y, null);
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+all_w,y,5,5);
		g.fillOval(x,y+all_h,5,5);
		g.setColor(c);
		
//		touchWithHero(gc.player1);
		touchWithObjs();
//		move();

	}

	public void move() {
		super.move();
		xMove();
		yMove();
	}
	
	protected void setDir()
	{
		if(xspe>=0) dir=Dirction.R;
		else dir=Dirction.L;
	}

	protected void xMove() {
		x+=xspe;
	}

	protected void yMove() {
		if(act==Action.UNSTAND)
		{
			yspe+=yadd;
		}
		else if (act==Action.STAND)
		{
			yspe=1;
		}
		else
		{
			yspe=1;
		}
		y+=yspe;
	}

	
	public void touchWithHero(Hero hero) {
		super.touchWithHero(hero);
		if(draw==false||hero.live==false) return;
//		System.out.println(hero.getNextRectangle()+" "+this.getRectangle());
		if(hero.getNextRectangle().intersects(getRectangle())
			||hero.getARectangle(hero.x+1, hero.y,hero.hero_w,hero.hero_h).intersects(getNextRectangle())
			||hero.getARectangle(hero.x-1,hero.y,hero.hero_w,hero.hero_h).intersects(getNextRectangle())
				)
		{
			if(hero.y+hero.hero_h<=y)
			{
				touchhero=Action.BUNT;
			}
			else if(hero.x<=x)
			{
				touchhero=Action.LTOUCH;
			}
			else if(hero.x+hero.hero_w>=x+all_w)
			{
				touchhero=Action.RTOUCH;
			}
		}
		else
		{
			touchhero=Action.UNTOUCH;
		}
//		System.out.println("TO touchhero "+touchhero);
		action(gc.player1); 
	}

	protected void action(Hero hero) {
		super.action();
		if(touchhero==Action.BUNT)
		{
			hit_time++;
			hero.yspe=-hero.Y_ADD;
			hit=true;
			if(hit==true)
			{
				all_h=25;
				all_w=25;
				if(hit_time==1)
				{
					xspe=0;
					XSPE=0;
				}
				else if (hit_time==2)
				{
					XSPE=10;
					if(hero.x>=x+all_w/2)
					{
						xspe=-XSPE;
					}
					else 
					{
						xspe=XSPE;
					}
				}
				else if (hit_time==3)
				{
					disappear();
				}
			}
			
			//打击音效
			new GameAudio("打击").start();
			
		}
		else if(touchhero==Action.LTOUCH)
		{
			if(hit_time!=1)
			hero.die();
		}
		else if(touchhero==Action.RTOUCH)
		{
			if(hit_time!=1)
			hero.die();
		}
		
		if(touch==Action.LTOUCH)
		{
			xspe=XSPE;
		}
		else if(touch==Action.RTOUCH)
		{
			
			xspe=-XSPE;
		}
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
			if((obj.draw==true&&getNextRectangle().intersects(obj.getRectangle())==true
					&&(obj!=obj1&&obj!=obj2&&obj!=this))||obj.throughCheck(this)
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
					System.out.println("TO穿越物体检测1");
				}
				if(obj.throughCheck(this))//穿越物体检测2
				{
					if(obj.y>=y) return;
					x-=xspe;
					y-=yspe;
					System.out.println("TO穿越物体检测2");
				}
				if(obj1==null)
				{
					obj1=obj;
					//System.out.println("TO obj1  "+obj.getRectangle());
				}
				else if(obj1!=null)
				{
					obj2=obj;
					//System.out.println("TO obj2  "+obj.getRectangle());
				}
				if(obj1!=null&&obj2!=null)
				{
					//System.out.println("TO 这里需要一个新的obj了   "+obj.getRectangle()+" obj1 "+obj1.getRectangle()+" obj2 "+obj2.getRectangle());
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
			//if(touch!=Action.UNTOUCH)
			//System.out.println("TO有一个物体即将碰撞 "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y);
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
				System.out.println("错误！无物体在mario下");
				return ;
			}
			//对于墙物体的处理
			GameObject obj_wall=null,obj_ground=null;
			if(ground==1)
			{
				obj_wall=obj2;
				obj_ground=obj1;
			}
			else if(ground==2)
			{
				obj_wall=obj1;
				obj_ground=obj2;
			}
			
			//两个物体碰撞时垂直方向分析
			if(x<=obj_ground.x+obj_ground.all_w||x+all_w>=obj_ground.x) //站在地面块中
			{
				act=Action.STAND;
				y=obj1.y-all_h;
			}
			else
			{
				act=Action.UNSTAND;
			}
			
			//两物体碰撞时水平方向分析
			if(xspe<0)
			{
				touch=Action.LTOUCH;
				x=obj_wall.x+obj_wall.all_w+XSPE;	
				xspe=XSPE;
			}
			else if(xspe>0)
			{
				touch=Action.RTOUCH;
				x=obj_wall.x-all_w-XSPE;
				xspe=-XSPE;
			}
			else
			{
				touch=Action.UNTOUCH;
			}
			
			//if(touch!=Action.UNTOUCH)
			//System.out.println("TO有两个个物体即将碰撞     "+act+" "+touch+" "+touchhero+" xspe "+xspe+" yspe "+yspe);
		 
		}
		else if(obj1==null&obj2==null)
		{
			act=Action.UNSTAND;
			touch=Action.UNTOUCH;
			//System.out.println("TO没有物体即将碰撞 "+act+" "+touch+" x "+x+" xspe "+xspe+" y "+y+" yspe "+yspe);
		}
	}
}
