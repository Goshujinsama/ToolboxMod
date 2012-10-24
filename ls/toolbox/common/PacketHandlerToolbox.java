package ls.toolbox.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandlerToolbox implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("ToolboxKeyEvents")) {
			handleKeyEvent(packet, player);
		}
	}

	private void handleKeyEvent(Packet250CustomPayload packet, Player player) {
		String msg = new String(packet.data);
		ItemStack itemStack = ((EntityPlayer)player).getCurrentEquippedItem();
		if (itemStack != null && itemStack.getItem() instanceof ItemToolbox) {
			if (msg.equals("L"))
				((ItemToolbox)itemStack.getItem()).switchLeft(itemStack);
			if (msg.equals("R"))
				((ItemToolbox)itemStack.getItem()).switchRight(itemStack);
		}
	}
	
}
