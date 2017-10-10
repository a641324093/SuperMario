import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;


class BackGroundMap 
{
	protected int num = 0;
	protected GameClient gc = null;
	public List<BackGround> objs = new LinkedList<BackGround>();
	protected boolean initialize = false;
	
	BackGroundMap(int num,GameClient gc)
	{
		this.num=num;
		this.gc=gc;
	}
	
	public void draw(Graphics g)
	{
		if(initialize==false) makeBackGround();
		BackGround bg = null;
		for(int i=0;i<objs.size();i++)
		{
			bg=objs.get(i);
		    bg.draw(g);
		}
	}

	private void makeBackGround() 
	{
		BackGround bg1,bg2,bg3;
		if(num==1)
		{
			//ÔÆ±³¾°
			bg1 = new BackGround(0,0,"CLOUD",gc);
			bg2 = new BackGround(800,0,"CLOUD",bg1,gc);
			bg1.setFollowBg(bg2);;
			objs.add(bg1);
			objs.add(bg2);
			//É­ÁÖ±³¾°
			bg1 = new BackGround(0,0,"FOREST",gc);
			bg2 = new BackGround(800,0,"FOREST",bg1,gc);
			bg1.setFollowBg(bg2);
			objs.add(bg1);
			objs.add(bg2);
		}
		else if(num==2)
		{
			//Ö÷²Ëµ¥±³¾°
			bg1 = new BackGround(0,0,"MAIN2",gc);
			bg2 = new BackGround(800,0,"MAIN3",bg1,gc);
			bg2.setFollowBg(bg1);
			objs.add(bg1);
			objs.add(bg2);
			
		}
		else if(num==3)
		{
			//ËÀÍö²Ëµ¥±³¾°
			bg1 = new BackGround(0,0,"DIE1",gc);
			bg2 = new BackGround(800,0,"DIE2",bg1,gc);
			bg1.setFollowBg(bg2);;
			objs.add(bg1);
			objs.add(bg2);
		}
		else 
		{
			System.out.println("´íÎóµÄ±³¾°ÊýÖµ£¡");
		}
		initialize=true;
		num=0;
	}
}
