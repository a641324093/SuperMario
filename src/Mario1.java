import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class Mario1 extends Hero 
{

	protected int shero_w=25,shero_h=25,w1=0,h1=0; 
	protected static Image [] imgs = null;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	protected int r_step=1,j_step=1;
	protected int t_step=1;
	protected boolean initialize=false;
	
	static
	{
		imgs = new Image[]
		{
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_L_jump1.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_L_jump2.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_L_run1.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_L_run2.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_L_run0.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_R_jump1.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_R_jump2.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_R_run1.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_R_run2.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_R_run0.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_stop_R.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/mario_stop_L.png")),
			//小马里奥图片加载
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_jump1_L.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_jump2_L.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_run1_L.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_run2_L.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_stand_L.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_stop_L.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_jump1_R.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_jump2_R.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_run1_R.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_run2_R.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_stand_R.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_stop_R.png")),
			//死亡图片加载
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/b_mario_die.png")),
			tk.getImage(Mario1.class.getClassLoader().getResource("Img/s_mario_die.png")),
		};
		hero_img.put("LJ1", imgs[0]);
		hero_img.put("LJ2", imgs[1]);
		hero_img.put("LR1", imgs[2]);
		hero_img.put("LR2", imgs[3]);
		hero_img.put("LR0", imgs[4]);
		hero_img.put("RJ1", imgs[5]);
		hero_img.put("RJ2", imgs[6]);
		hero_img.put("RR1", imgs[7]);
		hero_img.put("RR2", imgs[8]);
		hero_img.put("RR0", imgs[9]);
		hero_img.put("SR", imgs[10]);
		hero_img.put("SL", imgs[11]);
		//小马里奥
		hero_img.put("SLJ1", imgs[12]);
		hero_img.put("SLJ2", imgs[13]);
		hero_img.put("SLR1", imgs[14]);
		hero_img.put("SLR2", imgs[15]);
		hero_img.put("SLS", imgs[16]);
		hero_img.put("SLSP", imgs[17]);
		hero_img.put("SRJ1", imgs[18]);
		hero_img.put("SRJ2", imgs[19]);
		hero_img.put("SRR1", imgs[20]);
		hero_img.put("SRR2", imgs[21]);
		hero_img.put("SRS", imgs[22]);
		hero_img.put("SRSP", imgs[23]);
		//死亡
		hero_img.put("BD", imgs[24]);
		hero_img.put("SD", imgs[25]);
	}
	public Mario1(int x, int y,GameClient gc) {
		super(x, y,gc);
		w1=hero_w;
		h1=hero_h;
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
		if(initialize==false)
		{
			for(int i=0;i<imgs.length;i++)
			{
				g.drawImage(imgs[i],-200,-200,null);
			}
			initialize=true;
		}
		drawMario(g);
		if(live==false) return;
		transformate(g);
		
	}
	
	public void reset()
	{
		super.reset();
		r_step=1;
		j_step=1;
		t_step=1;
	}
	public void transformate(Graphics g)
	{
		if(big==false)
		{
			hero_w=shero_w;
			hero_h=shero_h;
			jumpMaxLimit=1;
		}
		else
		{
			hero_w=w1;
			hero_h=h1;
			jumpMaxLimit=2;
		}
	}
	
	
	protected void drawMario(Graphics g)
	{
		if(live==false)
		{
			Image img = null;
			if(big==false)
			{
				img=hero_img.get("SD");
			}
			else if(big==true)
			{
				img=hero_img.get("BD");
			}
			g.drawImage(img, x, y, null);
			return;
		}
		if(big==true)
		{
			if(act==Action.STAND)
			{
				if(move_dir==Dirction.STOP)
				{
					if(face_dir==Dirction.R)
					g.drawImage(hero_img.get("RR0"),x,y,null);
					else if(face_dir==Dirction.L)
					g.drawImage(hero_img.get("LR0"),x,y,null);
				}
				else if(move_dir==Dirction.L)
				{
					if(march==true) 
						{
							if(r_step<=2)
							{
								g.drawImage(hero_img.get("LR1"),x,y,null);
								r_step++;
							}
							else if(r_step>2&&r_step<=4)
							{
								g.drawImage(hero_img.get("LR2"),x,y,null);
								r_step++;
							}
							else if(r_step>4&&r_step<=6)
							{
								g.drawImage(hero_img.get("LR0"),x,y,null);
								r_step=1;
							}
						}
					else if(march==false)
					{
						if(xspe>=-5) g.drawImage(hero_img.get("LR0"),x,y,null);
						else 
						g.drawImage(hero_img.get("SR"),x,y,null);
					}
				}
				else if(move_dir==Dirction.R)
				{
					if(march==true) 
					{
						if(r_step<=2)
						{
							g.drawImage(hero_img.get("RR1"),x,y,null);
							r_step++;
						}
						else if(r_step>2&&r_step<=4)
						{
							g.drawImage(hero_img.get("RR2"),x,y,null);
							r_step++;
						}
						else if(r_step>4&&r_step<=6)
						{
							g.drawImage(hero_img.get("RR0"),x,y,null);
							r_step=1;
						}
					}
					else if(march==false)
					{
						if(xspe<=5) g.drawImage(hero_img.get("RR0"),x,y,null);
						else 
						g.drawImage(hero_img.get("SL"),x,y,null);
					}
				}
				
			}
			else if(act==Action.UNSTAND)
			{
				if(move_dir==Dirction.L)
				{
					if(yspe<0)
					{
						g.drawImage(hero_img.get("LJ1"),x,y,null);
					}
					else if(yspe>=0)
					{
						g.drawImage(hero_img.get("LJ2"),x,y,null);
					}
					
				}
				else if(move_dir==Dirction.R)
				{
					if(yspe<0)
					{
						g.drawImage(hero_img.get("RJ1"),x,y,null);
					}
					else if(yspe>=0)
					{
						g.drawImage(hero_img.get("RJ2"),x,y,null);
					}
				}
				else if(move_dir==Dirction.STOP)
				{
					if(face_dir==Dirction.R)
					g.drawImage(hero_img.get("RJ1"),x,y,null);
					else if(face_dir==Dirction.L)
					g.drawImage(hero_img.get("LJ1"),x,y,null);
				}
			}
		}
		else if (big==false)
		{
			if(act==Action.STAND)
			{
				if(move_dir==Dirction.STOP)
				{
					if(face_dir==Dirction.R)
					g.drawImage(hero_img.get("SRS"),x,y,null);
					else if(face_dir==Dirction.L)
					g.drawImage(hero_img.get("SLS"),x,y,null);
				}
				else if(move_dir==Dirction.L)
				{
					if(march==true) 
						{
							if(r_step==1)
							{
								g.drawImage(hero_img.get("SLR1"),x,y,null);
								r_step++;
							}
							else if(r_step==2)
							{
								g.drawImage(hero_img.get("SLR2"),x,y,null);
								r_step++;
							}
							else if(r_step==3)
							{
								g.drawImage(hero_img.get("SLS"),x,y,null);
								r_step=1;
							}
						}
					else if(march==false)
					{
						if(xspe>=-5) g.drawImage(hero_img.get("SLS"),x,y,null);
						else 
						g.drawImage(hero_img.get("SLSP"),x,y,null);
					}
				}
				else if(move_dir==Dirction.R)
				{
					if(march==true) 
					{
						if(r_step==1)
						{
							g.drawImage(hero_img.get("SRR1"),x,y,null);
							r_step++;
						}
						else if(r_step==2)
						{
							g.drawImage(hero_img.get("SRR2"),x,y,null);
							r_step++;
						}
						else if(r_step==3)
						{
							g.drawImage(hero_img.get("SRS"),x,y,null);
							r_step=1;
						}
					}
					else if(march==false)
					{
						if(xspe<=5) g.drawImage(hero_img.get("SRS"),x,y,null);
						else 
						g.drawImage(hero_img.get("SRSP"),x,y,null);
					}
				}
				
			}
			else if(act==Action.UNSTAND)
			{
				if(move_dir==Dirction.L)
				{
					if(yspe<0)
					{
						g.drawImage(hero_img.get("SLJ1"),x,y,null);
					}
					else if(yspe>=0)
					{
						g.drawImage(hero_img.get("SLJ2"),x,y,null);
					}
					
				}
				else if(move_dir==Dirction.R)
				{
					if(yspe<0)
					{
						g.drawImage(hero_img.get("SRJ1"),x,y,null);
					}
					else if(yspe>=0)
					{
						g.drawImage(hero_img.get("SRJ2"),x,y,null);
					}
				}
				else if(move_dir==Dirction.STOP)
				{
					if(face_dir==Dirction.R)
					g.drawImage(hero_img.get("SRJ1"),x,y,null);
					else if(face_dir==Dirction.L)
					g.drawImage(hero_img.get("SLJ1"),x,y,null);
				}
			}
		}
	}

}
