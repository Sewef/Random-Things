package lumien.randomthings.container;

import lumien.randomthings.tileentity.TileEntityAdvancedRedstoneRepeater;
import lumien.randomthings.tileentity.TileEntityBlockDestabilizer;
import lumien.randomthings.tileentity.TileEntityIronDropper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAdvancedRedstoneRepeater extends ContainerTE<TileEntityAdvancedRedstoneRepeater>
{
	public ContainerAdvancedRedstoneRepeater(EntityPlayer player, World world, int x, int y, int z)
	{
		super(player, world, x, y, z);
	}

	@Override
	public void signal(int signal)
	{
		switch (signal)
		{
			case 0:
				te.decreaseTurnOffDelay(1);
				break;
			case 1:
				te.increaseTurnOffDelay(1);
				break;
			case 2:
				te.decreaseTurnOnDelay(1);
				break;
			case 3:
				te.increaseTurnOnDelay(1);
				break;
			case 4:
				te.decreaseTurnOffDelay(10);
				break;
			case 5:
				te.increaseTurnOffDelay(10);
				break;
			case 6:
				te.decreaseTurnOnDelay(10);
				break;
			case 7:
				te.increaseTurnOnDelay(10);
				break;
		}
	}
}
