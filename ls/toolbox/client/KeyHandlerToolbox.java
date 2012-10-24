package ls.toolbox.client;

import java.util.EnumSet;

import ls.toolbox.common.ItemToolbox;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.Packet250CustomPayload;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class KeyHandlerToolbox extends KeyHandler {
    
    static KeyBinding switchToolLeftBinding = new KeyBinding("Toolbox Switch Left", Keyboard.KEY_Z);
    static KeyBinding switchToolRightBinding = new KeyBinding("Toolbox Switch Right", Keyboard.KEY_X);

    public KeyHandlerToolbox() {
            //the first value is an array of KeyBindings, the second is whether or not the call 
            //keyDown should repeat as long as the key is down
            super(new KeyBinding[]{switchToolLeftBinding, switchToolRightBinding}, new boolean[]{false, false});
    }

    @Override
    public String getLabel() {
            return "toolboxkeybindings";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb,
                    boolean tickEnd, boolean isRepeat) {
    	if (tickEnd && FMLClientHandler.instance().getClient().thePlayer != null && FMLClientHandler.instance().getClient().currentScreen == null) {
	    	EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
	    	ItemStack currentItem = player.getCurrentEquippedItem();
	    	if (currentItem != null && currentItem.getItem() instanceof ItemToolbox) {
	    		String msg = "";
	    		if (kb == switchToolLeftBinding)
	    			msg = "L";
	    		if (kb == switchToolRightBinding)
	    			msg = "R";
	    		Packet250CustomPayload packet = new Packet250CustomPayload();
	    		packet.channel = "ToolboxKeyEvents";
				packet.data = msg.getBytes();
				packet.length = msg.getBytes().length;
				PacketDispatcher.sendPacketToServer(packet);
	        }
    	}
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
            //do whatever
    }

    @Override
    public EnumSet<TickType> ticks() {
            return EnumSet.of(TickType.CLIENT);
    }
}