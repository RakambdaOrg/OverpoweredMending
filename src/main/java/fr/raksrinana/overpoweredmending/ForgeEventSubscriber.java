package fr.raksrinana.overpoweredmending;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Comparator;
import java.util.stream.IntStream;

@Mod.EventBusSubscriber(modid = OverpoweredMending.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {
	private static final int DURABILITY_PER_XP = 2;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerPickupXpEvent(PlayerPickupXpEvent e){
		System.out.println("TEST");
		
		e.setCanceled(true);
		
		PlayerEntity player = e.getPlayer();
		ExperienceOrbEntity xp = e.getOrb();
		ItemStack item = getDamagedEnchantedItem(Enchantments.MENDING, player);
		
		// See EntityXPOrb#onCollideWithPlayer for details.
		// All requirements for picking up XP are met at this point.
		
		// -> EntityPlayer#xpCooldown is set to 2.
		player.xpCooldown = 2;
		
		// -> EntityPlayer#onItemPickup is called with the xp orb and 1 (quantity).
		player.onItemPickup(xp, 1);
		
		// -> The mending effect is applied and the xp value is recalculated.
		if (!item.isEmpty()) {
			int realRepair = Math.min(xp.xpValue * DURABILITY_PER_XP, item.getDamage());
			xp.xpValue -= realRepair / DURABILITY_PER_XP;
			item.setDamage(item.getDamage() - realRepair);
		}
		
		// -> The XP are added to the player's experience.
		if (xp.xpValue > 0) {
			player.giveExperiencePoints(xp.xpValue);
		}
		
		// -> The XP orb is killed.
		xp.remove();
	}
	
	private static ItemStack getDamagedEnchantedItem(Enchantment ench, PlayerEntity player) {
		IInventory playerInventory = player.inventory;
		return IntStream.range(0, playerInventory.getSizeInventory())
				.mapToObj(playerInventory::getStackInSlot)
				.filter(is -> !is.isEmpty())
				.filter(ItemStack::isDamageable)
				.filter(ItemStack::isDamaged)
				.filter(is -> EnchantmentHelper.getEnchantmentLevel(ench, is) > 0)
				.max(Comparator.comparing(ItemStack::getDamage))
				.orElse(ItemStack.EMPTY);
	}
}
