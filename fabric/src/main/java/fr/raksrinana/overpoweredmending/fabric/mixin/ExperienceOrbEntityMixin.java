package fr.raksrinana.overpoweredmending.fabric.mixin;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
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
		ExperienceOrb orb = (ExperienceOrb) (Object) this;
		
		if(!orb.getCommandSenderWorld().isClientSide()){
			if(orb.throwTime == 0 && player.takeXpDelay == 0){
				player.takeXpDelay = 2;
				player.take(orb, 1);
				
				ItemStack item = getDamagedEnchantedItem(Enchantments.MENDING, player);
				int xpAmount = orb.getValue();
				
				while(!item.isEmpty() && xpAmount > 0){
					int realRepair = Math.min(xpAmount * DURABILITY_PER_XP, item.getDamageValue());
					xpAmount -= realRepair / DURABILITY_PER_XP;
					item.setDamageValue(item.getDamageValue() - realRepair);
					item = getDamagedEnchantedItem(Enchantments.MENDING, player);
				}
				if(xpAmount > 0){
					player.giveExperiencePoints(xpAmount);
				}
				orb.remove();
				callbackInfo.cancel();
			}
		}
	}
	
	private static ItemStack getDamagedEnchantedItem(Enchantment ench, Player player){
		Inventory playerInventory = player.inventory;
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
