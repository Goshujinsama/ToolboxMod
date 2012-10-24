package ls.toolbox.client;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import ls.toolbox.common.CommonProxyToolbox;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxyToolbox extends CommonProxyToolbox {
	@Override
	public void registerRenderThings() {
		MinecraftForgeClient.preloadTexture("/gui/toolboxItems.png");
	}
}
