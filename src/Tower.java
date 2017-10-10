import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

class Tower extends GameObject 
{
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();//这个不能放到父类中
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/tower1.png")),
				};
		obj_imgs.put("T1", imgs[0]);
	}

	Tower(int x, int y, GameClient gc) {
		super(x, y, gc);
		obj_w=150;
		obj_h=150;
		all_w=150;
		all_h=150;
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.drawImage(obj_imgs.get("T1"),x,y,null);
		touchWithHero(gc.player1);
	}

	protected void action(Hero hero) 
	{
		if(touchhero==Action.BUNT)
		{
			hero.finish();
		}
	}

	protected void touchWithHero(Hero hero) {
		super.touchWithHero(hero);
		if(getRectangle().intersects(hero.getRectangle())==true)
		{
			hero.finish();
			touchhero=Action.BUNT;
			System.out.println("Tower touch hero!");
		}
		action();
	}
}
