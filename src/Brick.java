import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

class Brick extends GameObject 
{

	protected int b_w,b_h;
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();
	protected String name=null;
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/brick1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/brick2.png")),
				};
		obj_imgs.put("B1", imgs[0]);
		obj_imgs.put("B2", imgs[1]);

	}
	Brick(int x, int y, int b_w, int b_h,String name,GameClient gc) 
	{
		super(x, y, gc);
		this.b_w=b_w;
		this.b_h=b_h;
		this.name=name;
		all_w=obj_w*b_w;
		all_h=obj_h*b_h;
		obj_w=30;
		obj_h=30;
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
		int step=1;
		Image img = null;
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x,y,5,5);
		g.fillOval(x+obj_w*b_w,y,5,5);
		g.setColor(c);
		
		for(int w = 1;w<=b_w;w++)
		{
			for(int h = 1; h<=b_h;h++)
			{
				img=obj_imgs.get(name);
				g.drawImage(img,x+(w-1)*obj_w,y+(h-1)*obj_h,null);
			}
		}
	}
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,obj_w*b_w,obj_h*b_h);
	}

}
