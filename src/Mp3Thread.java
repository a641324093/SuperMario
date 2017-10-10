import javazoom.jl.decoder.JavaLayerException;


class Mp3Thread implements Runnable
{
	protected String name;
	protected GameMp3 gm = null;
	
	Mp3Thread(String musicname)
	{
		this.name=musicname;
	}
	
	public void run()
	{
		while(true)
		{
			if(gm==null)
			{
				gm = new GameMp3(name);
			}
			gm.play();
		}
	}
	
	public void changeMusic(String name)
	{
		gm.setName(name);
	}
	
/*	public void stopMp3()
	{
		
	}*/
}
