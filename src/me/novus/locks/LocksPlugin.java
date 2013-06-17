package me.novus.locks;

import java.io.IOException;
import java.util.List;

import org.spout.api.Spout;
import org.spout.api.command.annotated.AnnotatedCommandExecutorFactory;
import org.spout.api.entity.Player;
import org.spout.api.plugin.Plugin;
import org.spout.api.plugin.PluginDescriptionFile;

public class LocksPlugin extends Plugin
{
	private DataManager data;
	private List<Player> selecting;
	
	@Override
	public void onEnable()
	{
		data = new DataManager(this);
		
		AnnotatedCommandExecutorFactory.create(new CommandManager(this));
		getEngine().getEventManager().registerEvents(new EventManager(this), this);
		
		try 
		{
			new ConfigManager(this);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Spout.getLogger().info("Locks v" + description.getVersion() + " enabled.");
	}
	
	@Override
	public void onDisable()
	{
		Spout.getLogger().info("Locks v" + description.getVersion() + " disabled.");
	}
	
	public PluginDescriptionFile getPDF()
	{
		return description;
	}
	
	public DataManager getDataManager()
	{
		return data;
	}
	
	public void select(Player player, boolean select)
	{
		if (select)
		{
			if (!selecting.contains(player))
			{
				selecting.add(player);
			}
		}
		else
		{
			while (selecting.contains(player))
			{
				selecting.remove(player);
			}
		}
	}
	
	public boolean isSelecting(Player player)
	{
		return selecting.contains(player);
	}
}
