package fr.raksrinana.overpoweredmending.fabric.mixin;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Comparator;
import java.util.stream.IntStream;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbEntityMixin{
	private static final int DURABILITY_PER_XP = 2;
	
	@Inject(method = "playerTouch", at = @At(value = "HEAD"), cancellable = true)
	public void onPlayerCollision(Player player, CallbackInfo callbackInfo){
		var orb = (ExperienceOrb) (Object) this;
		
		if(!orb.getCommandSenderWorld().isClientSide()){
			if(player.takeXpDelay == 0){
				player.takeXpDelay = 2;
				player.take(orb, 1);
				
				var item = getDamagedEnchantedItem(Enchantments.MENDING, player);
				var xpAmount = orb.getValue();
				
				while(!item.isEmpty() && xpAmount > 0){
					var realRepair = Math.min(xpAmount * DURABILITY_PER_XP, item.getDamageValue());
					xpAmount -= realRepair / DURABILITY_PER_XP;
					item.setDamageValue(item.getDamageValue() - realRepair);
					item = getDamagedEnchantedItem(Enchantments.MENDING, player);
				}
				if(xpAmount > 0){
					player.giveExperiencePoints(xpAmount);
				}
				orb.discard();
				callbackInfo.cancel();
			}
		}
	}
	
	private static ItemStack getDamagedEnchantedItem(Enchantment ench, Player player){
		var playerInventory = player.getInventory();
		return IntStream.range(0, playerInventory.getContainerSize())
				.mapToObj(playerInventory::getItem)
				.filter(is -> !is.isEmpty())
				.filter(ItemStack::isDamageableItem)
				.filter(ItemStack::isDamaged)
				.filter(is -> EnchantmentHelper.getItemEnchantmentLevel(ench, is) > 0)
				.max(Comparator.comparing(ItemStack::getDamageValue))
				.orElse(ItemStack.EMPTY);
	}
}
