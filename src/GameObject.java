import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

class GameObject 
{
	public int x,y,in_x;
	public int obj_w=28, obj_h=28;
	public int hasrun_x=0;
	public int all_w=0;//物体的全部宽度，因为某些物体是由多个子块拼成（如地面，砖块）
	public int all_h=0;//物体的全部高度，因为某些物体是由多个子块拼成（如地面，砖块）
	public GameClient gc=null;
	public boolean draw = true;//是否绘制
	public boolean available=true;//是否检测碰撞
	protected static Toolkit tk =Toolkit.getDefaultToolkit();
	protected static Random random = new Random();
	protected int ran_num = random.nextInt(10);
	protected static Image [] imgs = null;
	protected Action act=Action.UNSTAND,touch=Action.UNTOUCH,touchhero = Action.UNTOUCH;
	protected Hero player=null;
	
	
	
	GameObject(int x,int y,GameClient gc)
	{
		this.x=x;
		this.y=y;
		this.gc=gc;
		all_w=obj_w;
		all_h=obj_h;
		in_x=x;
	}
	
	public void draw(Graphics g)
	{
		checkAvailable();
		getHasrun();
		touchWithHero(gc.player1);
		move();
	}
	
	protected void getHasrun() {
		this.hasrun_x = gc.player1.hasrun_x;
	}

	/**
	 * 每帧检验物体是否还有效，需要绘制
	 */
	protected void checkAvailable() 
	{
		if(available==false) return;
		//若物体不在窗口内且为已经走过的场景则不再有效（绘制)
		if(getRectangle().intersects(new Rectangle(0,0,GameClient.F_W,GameClient.F_H))==false)
		{
			if(x<=-GameClient.F_W/2)
			{
				draw=false;
				available=false;
			}
		}
	}

	public void move()
	{
		if(available==true)
		player = gc.player1;
		{
			if(player.x+player.xspe>Hero.LIM_X2&&player.finish==false)
			{
				x-=player.xspe;
			}
		}
		
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,all_w,all_h);
	}
	
	protected void touchWithHero(Hero hero)
	{
		if(this.draw==false) return ;
	}
	
	/**
	 * 检测当前物体与角色是否碰撞，采用能够检测穿透式碰撞的检测方式
	 * 两形状重叠的碰撞检测方式在物体移动过快，一帧内直接穿过物体的情况下无效。
	 * @param hero
	 * @return
	 */
	public boolean throughCheck(Hero hero)
	{
		int x,y,x1,y1;
		int r_x,r_y,r_w,r_h;
		x=hero.x;//当前帧X坐标
		y=hero.y;//当前帧Y坐标
		x1=hero.x+hero.xspe;//下一帧X坐标
		y1=hero.y+hero.yspe;//下一帧Y坐标
		if(x<=x1)//在往右移动，x为移动矩形的x坐标
		{
			r_x=x;
		}
		else r_x=x1;
		if(y<=y1)//在往上移动,
		{
			r_y=y1;
		}
		else r_y=y;
		if(hero.xspe<0) r_w=-hero.xspe;//矩阵宽度就是x轴一帧的移动距离
		else r_w=hero.xspe;
		if(hero.yspe<0) r_h=-hero.yspe;//矩阵高度就是Y轴一帧的移动距离
		else r_h=hero.yspe;
		//构造移动坐标构造成的矩形，用于检测碰撞
		Rectangle move_r=new Rectangle(r_x,r_y,r_w,r_h);
		if(move_r.intersects(this.getRectangle()))
		{
			return true;
		}
		else return false;
	}
	
	public boolean throughCheck(GameCreature gc)
	{
		int x,y,x1,y1,r_x,r_y,r_w,r_h;
		x=gc.x;
		y=gc.y;
		x1=gc.x+gc.xspe;
		y1=gc.y+gc.yspe;
		if(x<=x1)
		{
			r_x=x;
		}
		else r_x=x1;
		if(y<=y1)
		{
			r_y=y;
		}
		else r_y=y1;
		if(gc.xspe<0) r_w=-gc.xspe;
		else r_w=gc.xspe;
		if(gc.yspe<0) r_h=-gc.yspe;
		else r_h=gc.yspe;
		Rectangle move_r=new Rectangle(r_x,r_y,r_w,r_h);
		if(move_r.intersects(this.getRectangle()))
		{
			return true;
		}
		else return false;
	}
	
	protected void action()
	{
		
	}
}
