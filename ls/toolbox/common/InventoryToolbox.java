package ls.toolbox.common;

import java.util.UUID;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class InventoryToolbox implements IInventory {
	private ItemStack[] _inv;
	private int _toolboxID;

	public InventoryToolbox(ItemStack itemStack) {
		int size = ((ItemToolbox)itemStack.getItem()).getToolboxSize(itemStack);
		_inv = new ItemStack[size];
		if (!itemStack.hasTagCompound())
			itemStack.setTagCompound(new NBTTagCompound());
		NBTTagCompound stackTag = itemStack.getTagCompound();
		if (!stackTag.hasKey("ToolboxID"))
			stackTag.setInteger("ToolboxID", ControllerToolbox.getNextToolboxID());
		_toolboxID = stackTag.getInteger("ToolboxID");

		readFromNBT(ControllerToolbox.loadToolboxTag(_toolboxID));
	}

	@Override
	public int getSizeInventory() {
		return _inv.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return _inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
				onInventoryChanged();
			}
			else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
					onInventoryChanged();
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		_inv[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "Toolbox";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void onInventoryChanged() {
		NBTTagCompound tagCompound = ControllerToolbox.loadToolboxTag(_toolboxID);
		writeToNBT(tagCompound);
		ControllerToolbox.saveToolboxTag(_toolboxID, tagCompound);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
	public void readFromNBT(NBTTagCompound tagCompound) {
		if (tagCompound == null)
			return;
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < _inv.length) {
				_inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound) {
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < _inv.length; i++) {
			ItemStack stack = _inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}
}
