package ls.toolbox.client;

import ls.toolbox.common.InventoryToolbox;
import ls.toolbox.common.ItemToolbox;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiToolboxOverlay extends GuiScreen {
	private Minecraft mc;
    private static RenderItem renderItem = new RenderItem();
	
	public GuiToolboxOverlay(Minecraft minecraft) {
		mc = minecraft;
	}

	public void drawOverlay() {
		if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemToolbox) {
			ItemStack toolboxStack = mc.thePlayer.getCurrentEquippedItem();
			int currentItem = -1;
			if (toolboxStack.hasTagCompound() && toolboxStack.stackTagCompound.hasKey("CurrentItem"))
				currentItem = toolboxStack.stackTagCompound.getInteger("CurrentItem");
			InventoryToolbox inventory = new InventoryToolbox(toolboxStack);
	        ScaledResolution resolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
	        int width = resolution.getScaledWidth();
	        int height = resolution.getScaledHeight();
	        
	        mc.entityRenderer.setupOverlayRendering();
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        RenderHelper.disableStandardItemLighting();
	        
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
	        zLevel = -90.0F;
	        
	        int xOffset = width / 2 - 10 * inventory.getSizeInventory();
	        int yOffset = height - 60;
	        
	        drawTexturedModalRect(xOffset, yOffset, 1, 1, 20 * inventory.getSizeInventory(), 20);
	        if (currentItem >= 0 && currentItem < inventory.getSizeInventory()) {
	        	drawTexturedModalRect(xOffset - 2 + currentItem * 20, yOffset - 2, 0, 22, 24, 24);
	        }
	        for (int i = 0; i < inventory.getSizeInventory(); i++) {
	        	ItemStack itemStack = inventory.getStackInSlot(i);
	        	if (itemStack != null) {
	                renderItem.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemStack, xOffset + 2 + i * 20, yOffset + 2);
	                renderItem.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemStack, xOffset + 2 + i * 20, yOffset + 2);
	        	}
	        }
		}
	}
}
