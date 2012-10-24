package ls.toolbox.common;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotToolbox extends Slot {

	public SlotToolbox(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
    public boolean isItemValid(ItemStack itemStack)
    {
		if(itemStack != null) {
			if (itemStack.getItem() instanceof ItemToolbox || itemStack.getItem() instanceof ItemBlock)
				return false;
		}
        return true;
    }
}
