package ls.toolbox.common;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumAction;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeHooks;
import railcraft.common.api.core.items.ICrowbar;
import buildcraft.api.liquids.LiquidStack;
import buildcraft.api.tools.IToolPipette;
import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ItemToolbox extends Item implements IToolWrench, IToolPipette {
	
	public ItemToolbox(int i)
	{
		super(i);
		maxStackSize = 1;
		setHasSubtypes(true);
		
	}
	
	public String getTextureFile()
	{
		return "/gui/toolboxItems.png";
	}
	
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int i) {
		return i;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(i,1,0));
		list.add(new ItemStack(i,1,1));
		list.add(new ItemStack(i,1,2));
		list.add(new ItemStack(i,1,3));
		list.add(new ItemStack(i,1,4));
	}
	
    @Override
    public boolean getShareTag() {
    	return true;
    }
    
    public String getItemNameIS(ItemStack i) {
    	switch(i.getItemDamage()) {
    	case 1: return "Stone Toolbox";
    	case 2: return "Iron Toolbox";
    	case 3: return "Golden Toolbox";
    	case 4: return "Diamond Toolbox";
    	default: return "Wooden Toolbox";
    	}
    }
    
	@Override
	public boolean onItemUseFirst(ItemStack toolboxStack, EntityPlayer player, World world, int X, int Y, int Z, int side, float hitX, float hitY, float hitZ) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		boolean result = currentItem.getItem().onItemUseFirst(currentItem, player, world, X, Y, Z, side, hitX, hitY, hitZ);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    		return result;
    	}
    	return super.onItemUseFirst(toolboxStack, player, world, X, Y, Z, side, hitX, hitY, hitZ);
	}
	
	@Override
    public boolean onItemUse(ItemStack toolboxStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		boolean result = currentItem.tryPlaceItemIntoWorld(player, world, par4, par5, par6, par7, par8, par9, par10);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    		return result;
    	}
        return super.onItemUse(toolboxStack, player, world, par4, par5, par6, par7, par8, par9, par10);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack toolboxStack, World world, EntityPlayer player) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		//System.out.println(player.getCurrentEquippedItem());
    		ItemStack result = currentItem.getItem().onItemRightClick(currentItem, world, player);
    		//System.out.println(result);
    		setCurrentItem(toolboxStack, result);
    		wieldItem(player, toolboxStack);
    		return toolboxStack;
    	}
   		int slot = getSlot(toolboxStack, (EntityPlayer)player);
   		if (slot >= 0)
   			player.openGui(ModToolbox.instance(), 0, world, slot, 0, 0);
    	return toolboxStack;
    }
    
    @Override 
    public boolean onLeftClickEntity(ItemStack toolboxStack, EntityPlayer player, Entity target) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		boolean result = currentItem.getItem().onLeftClickEntity(currentItem, player, target);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    		return result;
    	}
    	return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack toolboxStack, EntityLiving target) {
    	EntityPlayer player = getOwner(toolboxStack);
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		boolean result = currentItem.interactWith(target);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    		return result;
    	}
    	return super.itemInteractionForEntity(toolboxStack, target);
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack itemStack) {
    	ItemStack currentItem = getCurrentItem(itemStack);
    	if (currentItem != null) {
    		return currentItem.getItemUseAction();
    	}
        return EnumAction.none;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack itemStack) {
    	ItemStack currentItem = getCurrentItem(itemStack);
    	if (currentItem != null) {
    		return currentItem.getMaxItemUseDuration();
    	}
    	return super.getMaxItemUseDuration(itemStack);
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack toolboxStack, int x, int y, int z, EntityPlayer player) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		boolean result = currentItem.getItem().onBlockStartBreak(currentItem, x, y, z, player);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    		return result;
    	}
    	return super.onBlockStartBreak(toolboxStack, x, y, z, player);
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack toolboxStack, World world, EntityPlayer player, int par4) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		currentItem.onPlayerStoppedUsing(world, player, par4);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    	}
    	super.onPlayerStoppedUsing(toolboxStack, world, player, par4);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
    	return false;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack toolboxStack, World world, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving) {
    	EntityPlayer player = getOwner(toolboxStack);
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		wieldItem(player, currentItem);
    		boolean result = currentItem.getItem().onBlockDestroyed(currentItem, world, par3, par4, par5, par6, par7EntityLiving);
    		setCurrentItem(toolboxStack, currentItem);
    		wieldItem(player, toolboxStack);
    		return result;
    	}
    	return super.onBlockDestroyed(toolboxStack, world, par3, par4, par5, par6, par7EntityLiving);    	
    }
    
    @Override
    public float getStrVsBlock(ItemStack toolboxStack, Block block) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		return currentItem.getStrVsBlock(block);
    	}
    	return 0.0f;
    }
    
    @Override
    public float getStrVsBlock(ItemStack toolboxStack, Block block, int metadata) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null) {
    		return currentItem.getItem().getStrVsBlock(currentItem, block, metadata);
    	}
    	return 0.0f;
    }
    
    @Override
    public int getDamageVsEntity(Entity entity) {
    	return 0;
    }
    
    @Override
    public void onUsingItemTick(ItemStack itemStack, EntityPlayer player, int count) {
    	ItemStack currentItem = getCurrentItem(itemStack);
    	if (currentItem != null) {
    		currentItem.getItem().onUsingItemTick(currentItem, player, count);
    		setCurrentItem(itemStack, currentItem);
    	}
    }
    
    public int getToolboxSize(ItemStack i) {
    	switch (i.getItemDamage()) {
    	case 1:
    		return 4;
    	case 2:
    		return 5;
    	case 3:
    		return 7;
    	case 4:
    		return 9;
  		default:
   			return 3;
    	}
    }
    
    private EntityPlayer getOwner(ItemStack toolboxStack) {
    	if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
    		return FMLClientHandler.instance().getClient().thePlayer;
    	}
    	else if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
    		Iterator players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList.iterator();
    		while (players.hasNext()) {
    			EntityPlayerMP playerMP = (EntityPlayerMP)players.next();
    			if (playerMP.getCurrentEquippedItem() == toolboxStack)
    				return playerMP;
    		}
    	}
    	return null;
    }
    
    public int getSlot(ItemStack itemStack, EntityPlayer player) {
    	for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
    		if(player.inventory.getStackInSlot(i) == itemStack) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    private void setMetadata(ItemStack toolboxStack) {
    	ItemStack stack = getCurrentItem(toolboxStack);
        if (stack == null)
        {
            toolboxStack.itemID = ModToolbox.toolbox.shiftedIndex;
        }
        else {
        	EntityPlayer player = getOwner(toolboxStack);
        	wieldItem(player, stack);

        	if (stack.getItem() instanceof ICrowbar) {
        		toolboxStack.itemID = ModToolbox.toolbox_Crowbar.shiftedIndex;
        	}
        	else if (ForgeHooks.canHarvestBlock(Block.obsidian, player, 0)) {
                toolboxStack.itemID = ModToolbox.toolbox_PickaxeLevel3.shiftedIndex;
        	}
        	else if (ForgeHooks.canHarvestBlock(Block.oreDiamond, player, 0)) {
                toolboxStack.itemID = ModToolbox.toolbox_PickaxeLevel2.shiftedIndex;
        	}
        	else if (ForgeHooks.canHarvestBlock(Block.oreIron, player, 0)) {
                toolboxStack.itemID = ModToolbox.toolbox_PickaxeLevel1.shiftedIndex;
        	}
        	else if (ForgeHooks.canHarvestBlock(Block.stone, player, 0)) {
                toolboxStack.itemID = ModToolbox.toolbox_PickaxeLevel0.shiftedIndex;
        	}
        	else if (ForgeHooks.canHarvestBlock(Block.snow, player, 0)) {
                toolboxStack.itemID = ModToolbox.toolbox_Shovel.shiftedIndex;
        	}
        	else if (ForgeHooks.canHarvestBlock(Block.wood, player, 0)) {
                toolboxStack.itemID = ModToolbox.toolbox_Axe.shiftedIndex;
        	}
        	
        	wieldItem(player, toolboxStack);
        }
    }
    
    private int getCurrentIndex(ItemStack toolboxStack) {
    	if(!toolboxStack.hasTagCompound() || !toolboxStack.stackTagCompound.hasKey("CurrentItem"))
    		return -1;
    	int index = toolboxStack.stackTagCompound.getInteger("CurrentItem");
    	if (index >= 0 && index < getToolboxSize(toolboxStack))
    		return index;
    	return -1;
    }
    
    private void setCurrentIndex(ItemStack toolboxStack, int index) {
    	if (!toolboxStack.hasTagCompound())
    		toolboxStack.setTagCompound(new NBTTagCompound());
    	toolboxStack.stackTagCompound.setInteger("CurrentItem", index);
    	setMetadata(toolboxStack);
    }
    
    public void switchLeft(ItemStack toolboxStack) {
    	int index = getCurrentIndex(toolboxStack) - 1;
    	
    	if (index < -1)
    		index = getToolboxSize(toolboxStack) - 1;
    	
    	setCurrentIndex(toolboxStack, index);
    }

    public void switchRight(ItemStack toolboxStack) {
    	int index = getCurrentIndex(toolboxStack) + 1;
    	
    	if (index >= getToolboxSize(toolboxStack))
    		index = -1;
    	
    	setCurrentIndex(toolboxStack, index);
    }
    
    public void wieldItem(EntityPlayer player, ItemStack itemStack) {
		if (player != null)
			player.inventory.mainInventory[player.inventory.currentItem] = itemStack;
    }
    
    public ItemStack getCurrentItem(ItemStack toolboxStack) {
    	InventoryToolbox inventory = new InventoryToolbox(toolboxStack);
    	int currentIndex = getCurrentIndex(toolboxStack);
    	if( currentIndex >= 0) {
    		return inventory.getStackInSlot(currentIndex);
    	}
    	return null;
    }
    
    public void setCurrentItem(ItemStack toolboxStack, ItemStack itemStack) {
    	InventoryToolbox inventory = new InventoryToolbox(toolboxStack);
    	int currentIndex = getCurrentIndex(toolboxStack);
    	if( currentIndex >= 0) {
    		if (itemStack.stackSize > 0)
    			inventory.setInventorySlotContents(currentIndex, itemStack);
    		else
    			inventory.setInventorySlotContents(currentIndex, null);
    		
    	}
    }

	@Override
	public int getCapacity(ItemStack toolboxStack) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null && currentItem.getItem() instanceof IToolPipette) {
    		return ((IToolPipette)currentItem.getItem()).getCapacity(currentItem);
    	}
		return 0;
	}

	@Override
	public boolean canPipette(ItemStack toolboxStack) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null && currentItem.getItem() instanceof IToolPipette) {
    		return ((IToolPipette)currentItem.getItem()).canPipette(currentItem);
    	}
		return false;
	}

	@Override
	public int fill(ItemStack toolboxStack, LiquidStack liquid, boolean doFill) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null && currentItem.getItem() instanceof IToolPipette) {
    		return ((IToolPipette)currentItem.getItem()).fill(currentItem, liquid, doFill);
    	}
		return 0;
	}

	@Override
	public LiquidStack drain(ItemStack toolboxStack, int maxDrain, boolean doDrain) {
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null && currentItem.getItem() instanceof IToolPipette) {
    		return ((IToolPipette)currentItem.getItem()).drain(currentItem, maxDrain, doDrain);
    	}
		return null;
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		ItemStack toolboxStack = player.getCurrentEquippedItem();
		if (!(toolboxStack.getItem() instanceof ItemToolbox))
			return false;
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null && currentItem.getItem() instanceof IToolWrench) {
    		return ((IToolWrench)currentItem.getItem()).canWrench(player, x, y, z);
    	}
		return false;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		ItemStack toolboxStack = player.getCurrentEquippedItem();
		if (!(toolboxStack.getItem() instanceof ItemToolbox))
			return;
    	ItemStack currentItem = getCurrentItem(toolboxStack);
    	if (currentItem != null && currentItem.getItem() instanceof IToolWrench) {
    		((IToolWrench)currentItem.getItem()).wrenchUsed(player, x, y, z);
    	}		
	}
}
