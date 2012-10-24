package ls.toolbox.client;

import ls.toolbox.common.ContainerToolbox;
import ls.toolbox.common.InventoryToolbox;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiToolbox extends GuiContainer
{
    private InventoryPlayer _inventoryPlayer;
    private InventoryToolbox _inventoryToolbox;

    public GuiToolbox(InventoryPlayer inventoryPlayer, InventoryToolbox inventoryToolbox)
    {
        super(new ContainerToolbox(inventoryPlayer, inventoryToolbox));
        _inventoryPlayer = inventoryPlayer;
        _inventoryToolbox = inventoryToolbox;
        allowUserInput = false;
        ySize = 132;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(this._inventoryToolbox.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this._inventoryPlayer.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int windowTexture = mc.renderEngine.getTexture("/gui/toolboxGUI.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(windowTexture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        int toolboxSlots = _inventoryToolbox.getSizeInventory();
		int xOffset = (9 - toolboxSlots) * 9;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 7 + xOffset, y + 17, 7, 106, 18 * toolboxSlots, 18);
    }
}