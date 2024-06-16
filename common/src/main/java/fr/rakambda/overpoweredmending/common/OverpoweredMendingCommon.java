package fr.rakambda.overpoweredmending.common;

import fr.rakambda.overpoweredmending.common.inventory.IInventoryProvider;
import fr.rakambda.overpoweredmending.common.inventory.PlayerInventoryProvider;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import fr.rakambda.overpoweredmending.common.wrapper.IXpOrb;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

public abstract class OverpoweredMendingCommon{
	private static final int DURABILITY_PER_XP = 2;
	private final Collection<IInventoryProvider> inventoryProviders = new LinkedList<>();
	
	public OverpoweredMendingCommon(){
		inventoryProviders.add(new PlayerInventoryProvider());
	}
	
	public void addInventoryProvider(@NotNull IInventoryProvider provider){
		inventoryProviders.add(provider);
	}
	
	public boolean onXpPickedUp(@NotNull IPlayer player, @NotNull IXpOrb xpOrb){
		var item = getDamagedEnchantedItem(player);
		
		player.setExperiencePickUpDelay(2);
		player.sendPickup(xpOrb, 1);
		
		var xpAmount = xpOrb.getExperienceAmount();
		
		while(!item.isEmpty() && xpAmount > 0){
			var realRepair = Math.min(xpAmount * DURABILITY_PER_XP, item.getDamageValue());
			xpAmount -= realRepair / DURABILITY_PER_XP;
			xpOrb.setExperienceAmount(xpAmount);
			item.setDamageValue(item.getDamageValue() - realRepair);
			item = getDamagedEnchantedItem(player);
		}
		if(xpAmount > 0){
			player.addExperience(xpAmount);
		}
		xpOrb.discard();
		return true;
	}
	
	@NotNull
	private IItemStack getDamagedEnchantedItem(@NotNull IPlayer player){
		return inventoryProviders.stream()
				.flatMap(provider -> provider.getInventoryContent(player))
				.filter(is -> !is.isEmpty())
				.filter(IItemStack::isDamageableItem)
				.filter(IItemStack::isDamaged)
				.filter(IItemStack::hasMendingEnchant)
				.max(Comparator.comparingInt(IItemStack::getDamageValue))
				.orElse(getEmptyItemStack());
	}
	
	@NotNull
	protected abstract IItemStack getEmptyItemStack();
}
