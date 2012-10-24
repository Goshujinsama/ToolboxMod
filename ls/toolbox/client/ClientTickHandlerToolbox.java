package ls.toolbox.client;

import java.util.EnumSet;

import ls.toolbox.common.InventoryToolbox;
import ls.toolbox.common.ItemToolbox;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandlerToolbox implements ITickHandler {
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
	    {
        if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
        else if (type.equals(EnumSet.of(TickType.CLIENT)))
        {
            GuiScreen guiscreen = FMLClientHandler.instance().getClient().currentScreen;
            if (guiscreen != null)
            {
                onTickInGUI(guiscreen);
            } else {
                onTickInGame();
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
    }

    @Override
    public String getLabel() { return null; }


    public void onRenderTick()
    {
    	Minecraft minecraft = FMLClientHandler.instance().getClient();
    	
    	if (minecraft.inGameHasFocus && minecraft.isGuiEnabled()) {
      		new GuiToolboxOverlay(FMLClientHandler.instance().getClient()).drawOverlay();
    	}
    }

    public void onTickInGUI(GuiScreen guiscreen) {}

    public void onTickInGame() {
    	
    }
}