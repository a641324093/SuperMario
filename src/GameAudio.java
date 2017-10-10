import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.*; 


class GameAudio 
{
	protected String name;
	protected InputStream in ;
	protected AudioStream as ;
	protected ContinuousAudioDataStream cas ;
	protected AudioData data=null;
	
	GameAudio(String name)
	{
		this.name=name;
		try {
			switch(name)
			
			{
			
			case "点击" : in = new FileInputStream(new File("src\\Aduio\\点击.wav"));break;
			case "嚼东西" : in = new FileInputStream(new File("src\\Aduio\\嚼东西.wav"));break;
			case "水泡" : in = new FileInputStream(new File("src\\Aduio\\水泡.wav"));break;
			case "失败" : in = new FileInputStream(new File("src\\Aduio\\失败.wav"));break;
			case "打击" : in = new FileInputStream(new File("src\\Aduio\\踩踏.wav"));break;
			case "撞箱子" : in = new FileInputStream(new File("src\\Aduio\\撞箱子.wav"));break;
			case "庆祝" : in = new FileInputStream(new File("src\\Aduio\\庆祝.wav"));break;
			case "鼠标进入" : in = new FileInputStream(new File("src\\Aduio\\鼠标1.wav"));break;
			}
			as = new AudioStream(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		AudioPlayer.player.start(as);
	}
	
	public void stop()
	{
		AudioPlayer.player.stop(as);
	}
	
	public void continuousPlay()
	{
		try {
			data = as.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cas = new ContinuousAudioDataStream(data);
		AudioPlayer.player.start(cas);
	}
	
	public void continuousStop()
	{
		AudioPlayer.player.stop(cas);
	}
}