package ls.toolbox.common;

import ls.toolbox.client.GuiToolbox;
import ls.toolbox.client.GuiToolboxOverlay;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerToolbox implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0: // Toolbox Inventory
			InventoryToolbox inventoryToolbox = new InventoryToolbox(player.inventory.getStackInSlot(x));
			return new ContainerToolbox(player.inventory, inventoryToolbox);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0: // Toolbox Inventory
			InventoryToolbox inventoryToolbox = new InventoryToolbox(player.inventory.getStackInSlot(x));
			return new GuiToolbox(player.inventory, inventoryToolbox);
		default:
			return null;
		}
	}
}
