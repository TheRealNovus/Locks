package me.novus.locks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.spout.api.util.config.yaml.YamlConfiguration;

public class ConfigManager 
{
	private File configFile;
	private YamlConfiguration config;
	private List<String> defaultBlockList;
	
	public static boolean ENABLED;
	public static List<String> BLOCKS;
	
	public ConfigManager(LocksPlugin lp) throws IOException
	{
		configFile = new File(lp.getDataFolder() + File.separator + "config.yml");
		
		if (!configFile.exists())
		{
			configFile.getParentFile().mkdirs();
			configFile.createNewFile();
		}
		
		config = new YamlConfiguration(configFile);
		
		copyDefaults();
		load();
	}
	
	protected void copyDefaults()
	{
		config.setWritesDefaults(true);
		
		String[] defaultBlocks = {"Chest", "Wooden Door", "Iron Door", "Fence Gate", 
				"Trapdoor", "Button", "Lever", "Dispenser", "Dropper", "Pressure Plate"};
		
		defaultBlockList = Arrays.asList(defaultBlocks);
	}
	
	protected void load()
	{
		ENABLED = config.getNode("enabled").getBoolean(true);
		BLOCKS = config.getNode("blocks").getStringList(defaultBlockList);
	}
}
