package fr.raksrinana.overpoweredmending;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Comparator;
import java.util.stream.IntStream;

@Mod.EventBusSubscriber(modid = OverpoweredMending.MOD_ID)
public final class ForgeEventSubscriber {
	private static final int DURABILITY_PER_XP = 2;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerPickupXpEvent(PlayerPickupXpEvent e){
		e.setCanceled(true);
		
		EntityPlayer player = e.getEntityPlayer();
		EntityXPOrb xp = e.getOrb();
		ItemStack item = getDamagedEnchantedItem(Enchantments.MENDING, player);
		
		// See EntityXPOrb#onCollideWithPlayer for details.
		// All requirements for picking up XP are met at this point.
		
		// -> EntityPlayer#xpCooldown is set to 2.
		player.xpCooldown = 2;
		
		// -> EntityPlayer#onItemPickup is called with the xp orb and 1 (quantity).
		player.onItemPickup(xp, 1);
		
		// -> The mending effect is applied and the xp value is recalculated.
		while (!item.isEmpty() && xp.xpValue > 0) {
			int realRepair = Math.min(xp.xpValue * DURABILITY_PER_XP, item.getItemDamage());
			xp.xpValue -= realRepair / DURABILITY_PER_XP;
			item.setItemDamage(item.getItemDamage() - realRepair);
			item = getDamagedEnchantedItem(Enchantments.MENDING, player);
		}
		
		// -> The XP are added to the player's experience.
		if (xp.xpValue > 0) {
			player.addExperience(xp.xpValue);
		}
		
		// -> The XP orb is killed.
		xp.setDead();
	}
	
	private static ItemStack getDamagedEnchantedItem(Enchantment ench, EntityPlayer player) {
		IInventory playerInventory = player.inventory;
		return IntStream.range(0, playerInventory.getSizeInventory())
				.mapToObj(playerInventory::getStackInSlot)
				.filter(is -> !is.isEmpty())
				.filter(ItemStack::isItemStackDamageable)
				.filter(ItemStack::isItemDamaged)
				.filter(is -> EnchantmentHelper.getEnchantmentLevel(ench, is) > 0)
				.max(Comparator.comparing(ItemStack::getItemDamage))
				.orElse(ItemStack.EMPTY);
	}
}
