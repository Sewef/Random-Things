package lumien.randomthings.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import lumien.randomthings.worldgen.SingleRandomChestContent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class ItemLavaCharm extends ItemBase implements IBauble
{
	public ItemLavaCharm()
	{
		super("lavaCharm");

		this.setMaxStackSize(1);

		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new SingleRandomChestContent(new ItemStack(this), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new SingleRandomChestContent(new ItemStack(this), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.NETHER_FORTRESS, new SingleRandomChestContent(new ItemStack(this), 1, 1, 5));
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.RING;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (entityIn != null && entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			if (!player.worldObj.isRemote)
			{
				if (itemstack.getTagCompound() == null)
				{
					itemstack.setTagCompound(new NBTTagCompound());
				}

				NBTTagCompound compound = itemstack.getTagCompound();

				int chargeCooldown = compound.getInteger("chargeCooldown");
				if (chargeCooldown > 0)
				{
					compound.setInteger("chargeCooldown", chargeCooldown - 1);
				}
				else
				{
					int charge = compound.getInteger("charge");
					if (charge < 200)
					{
						compound.setInteger("charge", charge + 1);
					}
				}
			}
		}
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if (!player.worldObj.isRemote)
		{
			if (itemstack.getTagCompound() == null)
			{
				itemstack.setTagCompound(new NBTTagCompound());
			}

			NBTTagCompound compound = itemstack.getTagCompound();

			int chargeCooldown = compound.getInteger("chargeCooldown");
			if (chargeCooldown > 0)
			{
				compound.setInteger("chargeCooldown", chargeCooldown - 1);
			}
			else
			{
				int charge = compound.getInteger("charge");
				if (charge < 200)
				{
					compound.setInteger("charge", charge + 1);
				}
			}
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
}
