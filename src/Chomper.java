import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;


class Chomper extends GameCreature 
{

	protected int d_time=0;
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();//这个不能放到父类中
	protected int XSPE=2,YSPE=1,xspe=0,ysep=0,in_y=0;
	protected String ground=null;
	protected Pipe p = null;
	protected boolean ymove=false;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/flower1.1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/flower1.2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/flower1.3.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/flower1.4.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/flower1.5.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/flower1.6.png")),
				};
		obj_imgs.put("F1", imgs[0]);
		obj_imgs.put("F2", imgs[1]);
		obj_imgs.put("F3", imgs[2]);
		obj_imgs.put("F4", imgs[3]);
		obj_imgs.put("F5", imgs[4]);
		obj_imgs.put("F6", imgs[5]);
	}
	
	Chomper(int x,int y,String ground ,GameClient gc)
	{
		super(x,y,gc);
		this.ground=ground;
		obj_h=58;
		obj_w=30;
		all_h=58;
		all_w=30;
		in_y=y;
	}
	
	Chomper(int x,int y,String ground,Pipe p,GameClient gc)
	{
		super(x,y,gc);
		this.p=p;
		this.ground=ground;
		obj_h=58;
		obj_w=30;
		all_h=58;
		all_w=30;
		in_y=y;
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		Image img = null;
		if(ground=="Grass")
		{
			if(d_time<=2)
			{
				all_h=58;
				img=obj_imgs.get("F1");
				d_time++;
			}
			else if(d_time>2&&d_time<=4)
			{
				img=obj_imgs.get("F2");
				if(d_time==4) d_time=1;
				else 
				d_time++;
			}
			
		}
		else if(ground=="Pipe")
		{
			if(d_time<=2)
			{
				all_h=obj_h;
				img=obj_imgs.get("F3");
				d_time++;
			}
			else if(d_time>2&&d_time<=4)
			{
				img=obj_imgs.get("F4");
				if(d_time==4) d_time=1;
				else 
				d_time++;
			}
		}
		else
		{
			System.out.println("Wrong Ground in Flower");
		}
		g.drawImage(img, x, y, null);
		
		Color c = g.getColor();
		g.drawImage(img, x, y, null);
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+all_w,y,5,5);
		g.fillOval(x,y+all_h,5,5);
		g.setColor(c);
		
//		touchWithHero(gc.player1);
//		move();
		
/*		if(ground=="Pipe")
		System.out.println("Chomper"+x+" "+y);*/
	}
	
	public void move()
	{
		super.move();
		yMove();
	}
	
	protected void yMove() 
	{
		if(ground=="Grass")
		{
			if(y>=in_y)
			{
				yspe=-YSPE;
			}
			else if(y<=in_y-all_h*0.8)
			{
				yspe=YSPE;
			}
		}
		else if(ground=="Pipe")
		{
			if(y>in_y)
			{
				yspe=0;
				y=in_y;
			}
			else if(y<=in_y-all_h*0.8)
			{
				yspe=YSPE;
			}
		}
		
		y+=yspe;
	}
	
	public void touchWithHero(Hero hero) {
		super.touchWithHero(hero);
		if(draw==false||hero.live==false) return;
		//System.out.println(hero.getNextRectangle().intersects(getNextRectangle()));
		if((hero.x>=x&&hero.x<=x+all_w)&&hero.y<=y&&ground=="Pipe")
		{
			yspe=-YSPE;
		}
		if(hero.getNextRectangle().intersects(getRectangle())
			||hero.getARectangle(hero.x+1, hero.y,hero.hero_w,hero.hero_h).intersects(getRectangle())
			||hero.getARectangle(hero.x-1,hero.y,hero.hero_w,hero.hero_h).intersects(getRectangle())
				)
		{
			if(hero.y+hero.hero_h==y||((hero.x>=x&&hero.x<=x+all_w)&&hero.y+hero.hero_h>=y&&ground=="Pipe"))
			{
				touch=Action.BUNT;
			}
			else if(hero.x<=x)
			{
				touch=Action.LTOUCH;
			}
			else if(hero.x+hero.hero_w>=x+all_w)
			{
				touch=Action.RTOUCH;
			}
		}
		else
		{
			touch=Action.UNTOUCH;
		}
		action(gc.player1);
	}
	
	protected void action(Hero hero) {
		super.action();
		if(touch==Action.BUNT)
		{
			hero.die();
		}
	}
	
}
