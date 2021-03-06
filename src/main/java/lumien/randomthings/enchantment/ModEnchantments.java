package lumien.randomthings.enchantment;

import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModEnchantments
{
	public static EnchantmentMagnetic magnetic;

	public static void preInit(FMLPreInitializationEvent event)
	{
		magnetic = new EnchantmentMagnetic(Rarity.VERY_RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
		ForgeRegistries.ENCHANTMENTS.register(magnetic);
	}
}
