import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;


class Pipe extends GameObject 
{
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();
	protected int p_h=0,obj_w=28,obj_h=28;
	protected Chomper f = null;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/pipe2.1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/pipe2.2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/pipe2.3.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/pipe2.4.png")),
				};
		obj_imgs.put("P1", imgs[0]);
		obj_imgs.put("P2", imgs[1]);
		obj_imgs.put("P3", imgs[2]);
		obj_imgs.put("P4", imgs[3]);

	}
	
	Pipe(int x, int y,int p_h, GameClient gc) {
		super(x, y, gc);
		this.p_h=p_h;
		all_w=obj_w*2;
		all_h=obj_h*p_h;
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		Image img = null;
		if(f!=null) f.draw(g);
		for(int j=1;j<=p_h;j++)
		{
			for(int i=1;i<=2;i++)
			{
				if(j==1)
				{
					if(i==1)
					img=obj_imgs.get("P1");
					else if(i==2)
					img=obj_imgs.get("P2");
				}
				else if(j>1)
				{
					if(i==1)
					img=obj_imgs.get("P3");
					else if(i==2)
					img=obj_imgs.get("P4");
				}
				g.drawImage(img,x+(i-1)*obj_w,y+(j-1)*obj_h, null);
			}
		}
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+all_w,y,5,5);
		g.fillOval(x,y+all_h,5,5);
		g.setColor(c);
	}
	
	protected void touchWithHero(Hero hero) {
		super.touchWithHero(hero);
		if(hero.getNextRectangle().intersects(this.getRectangle()))
		{
			if(hero.y<=y+all_h)
			{
				touch=Action.BUNT;
			}
		}
		else
		{
			touch=Action.UNTOUCH;
		}
	}
	
}
