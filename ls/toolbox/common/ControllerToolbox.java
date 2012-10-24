package ls.toolbox.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ControllerToolbox {
	
	private static NBTTagCompound _masterTag = null;
	
	public static NBTTagCompound loadToolboxTag(int toolboxID) {
		NBTTagCompound masterTag = loadMasterTag();
		String tagName = "Toolbox_" + toolboxID;
		if (!masterTag.hasKey(tagName))
			masterTag.setTag(tagName, new NBTTagCompound());
		return (NBTTagCompound)masterTag.getTag(tagName);
	}
	
	public static boolean saveToolboxTag(int toolboxID, NBTTagCompound tag) {
		NBTTagCompound masterTag = loadMasterTag();
		String tagName = "Toolbox_" + toolboxID;
		masterTag.setTag(tagName, tag);
		return saveMasterTag(masterTag);
	}
	
	public static int getNextToolboxID() {
		NBTTagCompound masterTag = loadMasterTag();
		int nextToolboxID = masterTag.getInteger("NextToolboxID");
		masterTag.setInteger("NextToolboxID", nextToolboxID + 1);
		saveMasterTag(masterTag);
		return nextToolboxID;
	}
	
	protected static NBTTagCompound createMasterTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("NextToolboxID", 0);
		saveMasterTag(tag);
		return tag;
	}
	
	protected static NBTTagCompound loadMasterTag() {
		if (_masterTag != null)
			return _masterTag;
		FileInputStream fis;
		DataInputStream dis; 
		try {
			fis = new FileInputStream(fileName());
			dis = new DataInputStream(fis);
		} catch (FileNotFoundException e) {
			System.out.println("Toolbox data not found... creating new store.");
			_masterTag = createMasterTag();
			return _masterTag;
		}
		
		try {
			_masterTag = (NBTTagCompound)NBTTagCompound.readNamedTag(dis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			dis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (_masterTag == null)
			_masterTag = createMasterTag();
		return _masterTag;
	}
	
	protected static boolean saveMasterTag(NBTTagCompound tag) {
		if (tag == null)
			return false;
		FileOutputStream fos;
		DataOutputStream dos;
		try {
			fos = new FileOutputStream(fileName());
			dos = new DataOutputStream(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		boolean result = true;
		try {
			NBTTagCompound.writeNamedTag(tag, dos);
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		try {
			dos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	protected static String fileName() {
		return "./toolboxes.dat";
	}
}
