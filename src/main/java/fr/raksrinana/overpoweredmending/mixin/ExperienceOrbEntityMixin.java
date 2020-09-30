package fr.raksrinana.overpoweredmending.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Comparator;
import java.util.stream.IntStream;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin{
	private static final int DURABILITY_PER_XP = 2;
	
	@Inject(method = "onPlayerCollision", at = @At(value = "TAIL"), cancellable = true)
	public void onPlayerCollision(PlayerEntity player, CallbackInfo callbackInfo){
		callbackInfo.cancel();
		
		ExperienceOrbEntity orb = (ExperienceOrbEntity) (Object) this;
		
		ItemStack item = getDamagedEnchantedItem(Enchantments.MENDING, player);
		player.experiencePickUpDelay = 2;
		player.sendPickup(orb, 1);
		
		int xpAmount = orb.getExperienceAmount();
		
		while(!item.isEmpty() && xpAmount > 0){
			int realRepair = Math.min(xpAmount * DURABILITY_PER_XP, item.getDamage());
			xpAmount -= realRepair / DURABILITY_PER_XP;
			item.setDamage(item.getDamage() - realRepair);
			item = getDamagedEnchantedItem(Enchantments.MENDING, player);
		}
		if(xpAmount > 0){
			player.addExperience(xpAmount);
		}
		orb.remove();
	}
	
	private static ItemStack getDamagedEnchantedItem(Enchantment ench, PlayerEntity player){
		PlayerInventory playerInventory = player.inventory;
		return IntStream.range(0, playerInventory.size())
				.mapToObj(playerInventory::getStack)
				.filter(is -> !is.isEmpty())
				.filter(ItemStack::isDamageable)
				.filter(ItemStack::isDamaged)
				.filter(is -> EnchantmentHelper.getLevel(ench, is) > 0)
				.max(Comparator.comparing(ItemStack::getDamage))
				.orElse(ItemStack.EMPTY);
	}
}
