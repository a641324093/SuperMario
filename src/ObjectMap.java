import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

class ObjectMap 
{
	protected int num = 0;
	protected GameClient gc = null;
	public List<GameObject> objs = new LinkedList<GameObject>();
	protected Hero player;
	
	ObjectMap(int num,GameClient gc)
	{
		this.gc=gc;
		this.num=num;
	}
	
	public void draw(Graphics g)
	{
		makeObj();
		for(int i=0;i<objs.size();i++)
		{
			GameObject obj = objs.get(i);
			if(obj.available==true)
			{
				obj.draw(g);
			}
			else if(obj.available==false)
			{
				objs.remove(i);
			}
		}
	}
	
	protected void makeObj() 
	{
		if(num==1)
		{
			//一号蘑菇怪
			objs.add(new Fungus(150,300,Dirction.R,gc));
			//一号食人花
			objs.add(new Chomper(250,420,"Grass",gc));
			//一号地面
			objs.add(new Grass(300,380,3,2,gc));
			objs.add(new Grass(-100,430,20,6,gc));
			//二号地面
			objs.add(new Grass(600,430,20,6,gc));
			//一号砖
			objs.add(new Brick(450,300,2,2,"B2",gc));
			//二号砖
			objs.add(new Brick(600,340,2,3,"B2",gc));
			//一号箱子
			//objs.add(new Brick(721,300,2,1,"B1",gc));
			objs.add(new Box(780,300,gc));
			//objs.add(new Brick(809,300,2,1,"B1",gc));
			//一号乌龟
			objs.add(new Tortoise(780,400,Dirction.L,gc));
			//一号黄色阶梯
			objs.add(new Brick(870,400,1,1,"B1",gc));
			objs.add(new Brick(900,400-30,1,2,"B1",gc));
			objs.add(new Brick(930,400-30*2,1,3,"B1",gc));
			objs.add(new Brick(960,400-30*3,1,4,"B1",gc));
			objs.add(new Brick(990,400-30*4,1,5,"B1",gc));
			//两阶梯间的地板
			objs.add(new Brick(990,400-60,32,2,"B1",gc));
			//一号蘑菇怪群
			objs.add(new Fungus(1120,300,Dirction.L,gc));
			objs.add(new Fungus(1320,300,Dirction.L,gc));
			objs.add(new Fungus(1520,300,Dirction.L,gc));
			//三号地面
			objs.add(new Grass(1800,430,20,6,gc));
			//二号蓝色阶梯
			objs.add(new Brick(1900,280,1,5,"B1",gc));
			objs.add(new Brick(1900+30,280+30,1,4,"B1",gc));
			objs.add(new Brick(1900+30*2,280+30*2,1,3,"B1",gc));
			objs.add(new Brick(1900+30*3,280+30*3,1,2,"B1",gc));
			objs.add(new Brick(1900+30*4,280+30*4,1,1,"B1",gc));
			//一号水管与二号食人花
			objs.add(new Chomper(2135,380,"Pipe",gc));
			objs.add(new Pipe(2125,372,2,gc));
			//二号水管
			objs.add(new Pipe(2225,344,3,gc));
			//三号水管与食人花
			objs.add(new Chomper(2335,324,"Pipe",gc));
			objs.add(new Pipe(2325,316,4,gc));
			//四号水管
			objs.add(new Pipe(2525,316,14,gc));
			//五号水管和食人花
			//objs.add(new Chomper(2735,324,"Pipe",gc));
			objs.add(new Pipe(2725,316,14,gc));
			//四号地面
			objs.add(new Grass(2900,430,20,6,gc));
			
			System.out.println("Num 1 地图已经建立（重置）！ ");
		}
		num=0;
	}
	
	public void reset(int num)
	{
		objs.removeAll(objs);
		this.num=num;
		makeObj();
	}
}
