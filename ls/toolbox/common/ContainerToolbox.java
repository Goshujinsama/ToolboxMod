package ls.toolbox.common;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerToolbox extends Container {
	
	private InventoryToolbox _inventoryToolbox;
	private int _toolboxSlots;
	
	public ContainerToolbox(InventoryPlayer inventoryPlayer, InventoryToolbox inventoryToolbox) {
		_inventoryToolbox = inventoryToolbox;
		_toolboxSlots = _inventoryToolbox.getSizeInventory();
		
		int xOffset = (9 - _toolboxSlots) * 9;
		
		for(int i = 0; i < _toolboxSlots; i++) {
			addSlotToContainer(new SlotToolbox(_inventoryToolbox, i, 8 + xOffset + i * 18, 18));
		}
		
		for (int j = 0; j < 3; j++)
	        {
	            for (int i = 0; i < 9; i++)
	            {
	                addSlotToContainer(new Slot(inventoryPlayer, i + j * 9 + 9, 8 + i * 18, 49 + j * 18));
	            }
	        }

	        for (int i = 0; i < 9; i++)
	        {
	            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 107));
	        }
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return _inventoryToolbox.isUseableByPlayer(player);
	}

	@Override
    public ItemStack transferStackInSlot(int slot)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot)inventorySlots.get(slot);
        Slot testSlot = (Slot)inventorySlots.get(0);

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            
            if (!testSlot.isItemValid(stack))
            	return null;
            
            if (slot < _toolboxSlots)
            {
                if (!mergeItemStack(stackInSlot, _toolboxSlots, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(stackInSlot, 0, _toolboxSlots, false))
            {
                return null;
            }

            if (stackInSlot.stackSize == 0)
            {
                slotObject.putStack((ItemStack)null);
            }
            else
            {
                slotObject.onSlotChanged();
            }
        }

        return stack;
    }
}