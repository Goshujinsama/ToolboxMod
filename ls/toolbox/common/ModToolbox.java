////////////////////////////////////////
// Copyright (C) 2012 Michael Liebenow
////////////////////////////////////////

package ls.toolbox.common;

import ls.toolbox.client.ClientTickHandlerToolbox;
import ls.toolbox.client.KeyHandlerToolbox;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

@Mod(modid = "mod_toolbox", name = "Toolbox", version = "1.0.0a")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, serverPacketHandlerSpec = @SidedPacketHandler(channels={"ToolboxKeyEvents"}, packetHandler = PacketHandlerToolbox.class))

public class ModToolbox {
	
	@Instance
	private static ModToolbox INSTANCE = new ModToolbox();
	
	// IDs
	public static int toolboxID;
	public static int toolboxID_PickaxeLevel0;
	public static int toolboxID_PickaxeLevel1;
	public static int toolboxID_PickaxeLevel2;
	public static int toolboxID_PickaxeLevel3;
	public static int toolboxID_Axe;
	public static int toolboxID_Shovel;
	public static int toolboxID_Crowbar;
	
	// Items
	public static Item toolbox;
	public static Item toolbox_PickaxeLevel0;
	public static Item toolbox_PickaxeLevel1;
	public static Item toolbox_PickaxeLevel2;
	public static Item toolbox_PickaxeLevel3;
	public static Item toolbox_Axe;
	public static Item toolbox_Shovel;
	public static Item toolbox_Crowbar;
	
	@SidedProxy(clientSide = "ls.toolbox.client.ClientProxyToolbox", serverSide = "ls.toolbox.common.CommonProxyToolbox")
	public static CommonProxyToolbox proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		toolboxID = config.getItem("ToolboxID", 550).getInt();
		toolboxID_PickaxeLevel0 = config.getItem("ToolboxID_PickaxeLevel0", 551).getInt();
		toolboxID_PickaxeLevel1 = config.getItem("ToolboxID_PickaxeLevel1", 552).getInt();
		toolboxID_PickaxeLevel2 = config.getItem("ToolboxID_PickaxeLevel2", 553).getInt();
		toolboxID_PickaxeLevel3 = config.getItem("ToolboxID_PickaxeLevel3", 554).getInt();
		toolboxID_Axe = config.getItem("ToolboxID_Axe", 555).getInt();
		toolboxID_Shovel = config.getItem("ToolboxID_Shovel", 556).getInt();
		toolboxID_Crowbar = config.getItem("ToolboxID_Crowbar", 557).getInt();
		config.save();
	}
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		TickRegistry.registerTickHandler(new ClientTickHandlerToolbox(), Side.CLIENT);
		KeyBindingRegistry.registerKeyBinding(new KeyHandlerToolbox());
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandlerToolbox());

		toolbox = new ItemToolbox(toolboxID).setItemName("toolbox");
		toolbox_PickaxeLevel0 = new ItemToolbox(toolboxID_PickaxeLevel0).setItemName("toolbox_PickaxeLevel0").setCreativeTab(CreativeTabs.tabTools);
		toolbox_PickaxeLevel1 = new ItemToolbox(toolboxID_PickaxeLevel1).setItemName("toolbox_PickaxeLevel1").setCreativeTab(CreativeTabs.tabTools);
		toolbox_PickaxeLevel2 = new ItemToolbox(toolboxID_PickaxeLevel2).setItemName("toolbox_PickaxeLevel2").setCreativeTab(CreativeTabs.tabTools);
		toolbox_PickaxeLevel3 = new ItemToolbox(toolboxID_PickaxeLevel3).setItemName("toolbox_PickaxeLevel3").setCreativeTab(CreativeTabs.tabTools);
		toolbox_Axe = new ItemToolbox(toolboxID_Axe).setItemName("toolbox_Axe");
		toolbox_Shovel = new ItemToolbox(toolboxID_Shovel).setItemName("toolbox_Shovel");
		toolbox_Crowbar = new ItemCrowbarToolbox(toolboxID_Crowbar).setItemName("toolbox_Crowbar");

		Item[] items = {toolbox, toolbox_PickaxeLevel0, toolbox_PickaxeLevel1, toolbox_PickaxeLevel2, toolbox_PickaxeLevel3, toolbox_Axe, toolbox_Shovel, toolbox_Crowbar};
		for( Item i : items)
		{
			LanguageRegistry.addName(new ItemStack(i, 1, 0), "Wooden Toolbox");
			LanguageRegistry.addName(new ItemStack(i, 1, 1), "Stone Toolbox");
			LanguageRegistry.addName(new ItemStack(i, 1, 2), "Iron Toolbox");
			LanguageRegistry.addName(new ItemStack(i, 1, 3), "Golden Toolbox");
			LanguageRegistry.addName(new ItemStack(i, 1, 4), "Diamond Toolbox");
		}
		
		MinecraftForge.setToolClass(toolbox_PickaxeLevel0, "pickaxe", 0);
		MinecraftForge.setToolClass(toolbox_PickaxeLevel1, "pickaxe", 1);
		MinecraftForge.setToolClass(toolbox_PickaxeLevel2, "pickaxe", 2);
		MinecraftForge.setToolClass(toolbox_PickaxeLevel2, "pickaxe", 3);
		MinecraftForge.setToolClass(toolbox_Axe, "axe", 0);
		MinecraftForge.setToolClass(toolbox_Shovel, "shovel", 0);
		MinecraftForge.setToolClass(toolbox_Crowbar, "crowbar", 0);
		
		GameRegistry.addRecipe(new ItemStack(toolbox, 1, 0), new Object[]
		{
			" X ", "YYY", 'X', Item.stick, 'Y', Block.planks
		});
		GameRegistry.addRecipe(new ItemStack(toolbox, 1, 1), new Object[]
		{
			" X ", "YYY", 'X', Item.stick, 'Y', Block.stone
		});
		GameRegistry.addRecipe(new ItemStack(toolbox, 1, 2), new Object[]
		{
			" X ", "YYY", 'X', Item.stick, 'Y', Item.ingotIron
		});
		GameRegistry.addRecipe(new ItemStack(toolbox, 1, 3), new Object[]
		{
			" X ", "YYY", 'X', Item.stick, 'Y', Item.ingotGold
		});
		GameRegistry.addRecipe(new ItemStack(toolbox, 1, 4), new Object[]
		{
			" X ", "YYY", 'X', Item.stick, 'Y', Item.diamond
		});

		proxy.registerRenderThings();
	}
	
	public static ModToolbox instance() {
		return INSTANCE;
	}
}
