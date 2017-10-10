import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

class Grass extends GameObject 
{

	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();
	protected int g_w=0,g_h=0;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_L1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_L2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_R1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_R2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass3.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_soil.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_soil1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_soil2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/grass_soil3.png")),
				};
		obj_imgs.put("GL1", imgs[0]);
		obj_imgs.put("GL2", imgs[1]);
		obj_imgs.put("GR1", imgs[2]);
		obj_imgs.put("GR2", imgs[3]);
		obj_imgs.put("G1", imgs[4]);
		obj_imgs.put("G2", imgs[5]);
		obj_imgs.put("G3", imgs[6]);
		obj_imgs.put("GS", imgs[7]);
		obj_imgs.put("GS1", imgs[8]);
		obj_imgs.put("GS2", imgs[9]);
		obj_imgs.put("GS3", imgs[10]);
	}
	
	Grass(int x, int y,int g_w,int g_h , GameClient gc) 
	{
		super(x,y,gc);
		this.g_w=g_w;
		this.g_h=g_h;
		all_w=obj_w*g_w;
		all_h=obj_h*g_h;
	}

	public void draw(Graphics g) {
		super.draw(g);
		int step=1;
		Image img = null;
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+obj_w*g_w,y,5,5);
		g.setColor(c);
		
		for(int w = 1;w<=g_w;w++)
		{
			for(int h = 1; h<=g_h;h++)
			{
				if(w==1&&h==1) img=obj_imgs.get("GL1");
				else if(w==1&&h>1) img=obj_imgs.get("GL2");
				else if(w==g_w&&h==1) img=obj_imgs.get("GR1");
				else if(w==g_w&&h>1) img=obj_imgs.get("GR2");
				else if(h==1&&(w!=1&&w!=g_w))
				{
					if(step==1) 
						{
							img=obj_imgs.get("G1");
							step++;
						}
					else if(step==2) 
						{
							img=obj_imgs.get("G2");
							step++;
						}
					else if(step==3) 
						{
							img=obj_imgs.get("G3");
							step=1;
						}
				}
				else 
				{
						img=obj_imgs.get("GS");
				}
				g.drawImage(img,x+(w-1)*obj_w,y+(h-1)*obj_h,null);
			}
		}
	}

	public void move() {
		super.move();
	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,obj_w*g_w,obj_h*g_h);
	}
	protected void touchingHero(Hero hero) {
		
	}
	
	

}
