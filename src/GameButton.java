import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


class GameButton extends JButton
{
	protected static Toolkit tk =Toolkit.getDefaultToolkit();
	protected GameClient gc = null;
	protected static Map<String,Image> obj_imgs =  new HashMap<String,Image>();
	protected static Image [] imgs = null;  
	protected String name = null;
	protected ImageIcon imgicon = null,rollicon=null,pressedicon=null;
	
	static 
	{
		imgs = new Image []
				{
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_exit1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_exit2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_exit3.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_restart1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_restart2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_restart3.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_start1.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_start2.png")),
				tk.getImage(BackGround.class.getClassLoader().getResource("Img/b_start3.png")),
				};
		obj_imgs.put("BE1", imgs[0]);
		obj_imgs.put("BE2", imgs[1]);
		obj_imgs.put("BE3", imgs[2]);
		obj_imgs.put("BR1", imgs[3]);
		obj_imgs.put("BR2", imgs[4]);
		obj_imgs.put("BR3", imgs[5]);
		obj_imgs.put("BS1", imgs[6]);
		obj_imgs.put("BS2", imgs[7]);
		obj_imgs.put("BS3", imgs[8]);
	}

	GameButton(String name)
	{
		//属性初始化
		super();
		this.name=name;
		this.gc=gc;
		
		//设置个状态显示的图片
		if(name=="EIXT")
		{
			imgicon = new ImageIcon (obj_imgs.get("BE1"));
			rollicon = new ImageIcon(obj_imgs.get("BE2"));
			pressedicon = new ImageIcon(obj_imgs.get("BE3"));
		}
		else if(name=="RESTART")
		{
			imgicon = new ImageIcon (obj_imgs.get("BR1"));
			rollicon = new ImageIcon(obj_imgs.get("BR2"));
			pressedicon = new ImageIcon(obj_imgs.get("BR3"));
		}
		else if(name=="START")
		{
			imgicon = new ImageIcon (obj_imgs.get("BS1"));
			rollicon = new ImageIcon(obj_imgs.get("BS2"));
			pressedicon = new ImageIcon(obj_imgs.get("BS3"));
		}
		super.setIcon(imgicon);
		super.setPressedIcon(pressedicon);
		super.setRolloverIcon(rollicon);
		
	}
	
	
}
