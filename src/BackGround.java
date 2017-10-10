import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

class BackGround 
{
	public boolean available = true;
	protected static Toolkit tk = Toolkit.getDefaultToolkit();
	protected static Image[] imgs =null;
	protected static Map<String,Image> back_imgs = new HashMap<String,Image>();
	public String img_name=null;
	public int x=0,y=0,xspe=1; 
	protected GameClient gc =null;
	protected Hero player =null;
	protected BackGround follow_bg = null;
	
	static 
	{
		imgs = new Image[]
				{
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/forest1.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/background_cloud.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/forest3.2.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/menu1.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/menu2.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/menu3.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/menu4.png")),
					tk.getImage(BackGround.class.getClassLoader().getResource("Img/menu5.png")),
				};
		back_imgs.put("FOREST",imgs[0]);
		back_imgs.put("CLOUD",imgs[1]);
		back_imgs.put("MENU",imgs[2]);
		back_imgs.put("MAIN1",imgs[3]);
		back_imgs.put("MAIN2",imgs[4]);
		back_imgs.put("MAIN3",imgs[5]);
		back_imgs.put("DIE1",imgs[6]);
		back_imgs.put("DIE2",imgs[7]);
		
	}
	BackGround(int x,int y,String img_name,GameClient gc)
	{
		this.x=x;
		this.y=y;
		this.img_name=img_name;
		this.gc=gc;
	}
	BackGround(int x,int y,String img_name,BackGround follow_bg,GameClient gc)
	{
		this.x=x;
		this.y=y;
		this.img_name=img_name;
		this.gc=gc;
		this.follow_bg=follow_bg;	
	}
	
	public void draw(Graphics g)
	{
		Image img = back_imgs.get(img_name);
		g.drawImage(img, x, y, null);
		move();
		refresh(follow_bg);
	}
	public void move()
	{
		player = gc.player1;
		if(player.x+player.xspe>Hero.LIM_X2&&player.finish==false)
		{
			x-=player.xspe/4;
		}
		if(img_name=="CLOUD")
		{
			x-=xspe;
		}
		else if (img_name=="FOREST")
		{
			
		}
		else
		{
			x-=xspe*2;
		}
	}
	
	public void setFollowBg(BackGround bg)
	{
		this.follow_bg=bg;
	}
	protected void refresh(BackGround bg)
	{
		if(this.x<=-GameClient.F_W)
		{
			this.x=GameClient.F_W;
		}
		if(bg!=null)
		{
			if(bg.x<=0&&bg.x>=-GameClient.F_W)
			{
				this.x=bg.x+GameClient.F_W;
			}
		}
	}
}
