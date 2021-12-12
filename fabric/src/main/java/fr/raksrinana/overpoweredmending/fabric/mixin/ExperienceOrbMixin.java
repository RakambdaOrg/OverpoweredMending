package fr.raksrinana.overpoweredmending.fabric.mixin;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Comparator;
import java.util.stream.IntStream;

@Slf4j
@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin{
	private static final int DURABILITY_PER_XP = 2;
	
	@Inject(method = "onPlayerCollision", at = @At(value = "HEAD"), cancellable = true)
	public void onPlayerCollision(PlayerEntity player, CallbackInfo callbackInfo){
		var orb = (ExperienceOrbEntity) (Object) this;
		
		if(!orb.getEntityWorld().isClient()){
			if(player.experiencePickUpDelay == 0){
				player.experiencePickUpDelay = 2;
				player.sendPickup(orb, 1);
				
				var item = getDamagedEnchantedItem(Enchantments.MENDING, player);
				var xpAmount = orb.getExperienceAmount();
				log.info("Selected for mending: {} (damageable: {}, damaged: {}, damage: {})",
						item.getItem(),
						item.isDamageable(),
						item.isDamaged(),
						item.getDamage());
				
				while(!item.isEmpty() && xpAmount > 0){
					var realRepair = Math.min(xpAmount * DURABILITY_PER_XP, item.getDamage());
					xpAmount -= realRepair / DURABILITY_PER_XP;
					item.setDamage(item.getDamage() - realRepair);
					item = getDamagedEnchantedItem(Enchantments.MENDING, player);
				}
				if(xpAmount > 0){
					player.addExperience(xpAmount);
				}
				orb.discard();
				callbackInfo.cancel();
			}
		}
	}
	
	private static ItemStack getDamagedEnchantedItem(Enchantment ench, PlayerEntity player){
		var playerInventory = player.getInventory();
		var potential = IntStream.range(0, playerInventory.size())
				.mapToObj(playerInventory::getStack)
				.filter(is -> !is.isEmpty())
				.filter(ItemStack::isDamageable)
				.filter(ItemStack::isDamaged)
				.filter(is -> EnchantmentHelper.getLevel(ench, is) > 0)
				.toList();
		potential.forEach(is -> log.info("Potential item found for mending: {} (damageable: {}, damaged: {}, damage: {})",
				is.getItem(),
				is.isDamageable(),
				is.isDamaged(),
				is.getDamage()));
		return potential.stream().max(Comparator.comparing(ItemStack::getDamage))
				.orElse(ItemStack.EMPTY);
	}
}
