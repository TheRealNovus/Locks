package me.novus.locks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map.Entry;

import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

public class DataManager 
{
	private LocksPlugin plugin;
	private File dataFile;
	private HashMap<Point, String> locks;
	
	public DataManager(LocksPlugin lp)
	{
		plugin = lp;
		dataFile = new File(plugin.getDataFolder() + File.separator + "locks.dat");
		locks = new HashMap<Point, String>();
		
		if (!dataFile.exists())
		{
			dataFile.getParentFile().mkdirs();
			try 
			{
				dataFile.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void addLock(String player, Point position)
	{
		locks.put(position, player);
	}
	
	public void removeLock(Point position)
	{
		locks.remove(position);
	}
	
	public boolean isLocked(Point position)
	{
		return locks.containsKey(position);
	}
	
	public boolean hasAccess(Player player, Point position)
	{
		return locks.get(position).equals(player.getName())
				|| player.hasPermission("locks.admin");
	}
	
	public void load() throws IOException
	{
		FileInputStream in = new FileInputStream(dataFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		int line = 0;
		
		String s;
		while ((s = reader.readLine()) != null)
		{
			line++;
			String[] split = s.split(":");
			String player = split[0];
			World world = plugin.getEngine().getWorld(split[1]);
			int x = Integer.valueOf(split[2]);
			int y = Integer.valueOf(split[3]);
			int z = Integer.valueOf(split[4]);
			
			if (world == null)
			{
				plugin.getLogger().warning("Unknown world on line " + line + ": " + split[1]);
			}
			
			addLock(player, new Point(world, x, y, z));
		}
		
		reader.close();
	}
	
	public void save() throws IOException
	{
		FileOutputStream out = new FileOutputStream(dataFile);
		PrintStream printer = new PrintStream(out);
		
		for (Entry<Point, String> entry : locks.entrySet())
		{
			Point pos = entry.getKey();
			String player = entry.getValue();
			String world = pos.getWorld().getName();
			int x = pos.getBlockX();
			int y = pos.getBlockY();
			int z = pos.getBlockZ();
			
			printer.println(player + ":" + world + ":" + x + ":" + y + ":" + z);
		}
		
		printer.close();
	}
}
