package fr.rakambda.overpoweredmending.common;

import fr.rakambda.overpoweredmending.common.config.IConfiguration;
import fr.rakambda.overpoweredmending.common.config.proxy.ProxyConfiguration;
import fr.rakambda.overpoweredmending.common.config.real.Configuration;
import fr.rakambda.overpoweredmending.common.inventory.IInventoryProvider;
import fr.rakambda.overpoweredmending.common.inventory.PlayerInventoryProvider;
import fr.rakambda.overpoweredmending.common.network.PacketUtils;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import fr.rakambda.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import java.util.Collection;
import java.util.LinkedList;

@Getter
public abstract class OverpoweredMendingCommon{
	private static final int DURABILITY_PER_XP = 2;
	
	private final Configuration ownConfiguration;
	private final ProxyConfiguration proxyConfiguration;
	private final PacketUtils packetUtils;
	private final Collection<IInventoryProvider> inventoryProviders = new LinkedList<>();
	
	public OverpoweredMendingCommon(){
		ownConfiguration = Configuration.read();
		proxyConfiguration = new ProxyConfiguration(ownConfiguration);
		packetUtils = new PacketUtils(this);
		
		inventoryProviders.add(new PlayerInventoryProvider());
	}
	
	public IConfiguration getConfiguration(){
		return getProxyConfiguration();
	}
	
	public void addInventoryProvider(@NonNull IInventoryProvider provider){
		inventoryProviders.add(provider);
	}
	
	public boolean onXpPickedUp(@NonNull IPlayer player, @NonNull IXpOrb xpOrb){
		var item = getDamagedEnchantedItem(player);
		
		player.setExperiencePickUpDelay(2);
		player.sendPickup(xpOrb, 1);
		
		var xpAmount = xpOrb.getExperienceAmount();
		
		while(!item.isEmpty() && xpAmount > 0){
			var realRepair = Math.min(xpAmount * DURABILITY_PER_XP, item.getDamageValue());
			xpAmount -= realRepair / DURABILITY_PER_XP;
			item.setDamageValue(item.getDamageValue() - realRepair);
			item = getDamagedEnchantedItem(player);
		}
		if(xpAmount > 0){
			player.addExperience(xpAmount);
		}
		
		xpOrb.setCount(xpOrb.getCount() - 1);
		if(xpOrb.getCount() <= 0){
			xpOrb.discard();
		}
		return true;
	}
	
	@NonNull
	private IItemStack getDamagedEnchantedItem(@NonNull IPlayer player){
		return inventoryProviders.stream()
				.flatMap(provider -> provider.getInventoryContent(player))
				.filter(is -> !is.isEmpty())
				.filter(IItemStack::isDamageableItem)
				.filter(IItemStack::isDamaged)
				.filter(IItemStack::hasMendingEnchant)
				.max(getConfiguration().getSelectionMode().getComparator())
				.orElse(getEmptyItemStack());
	}
	
	@NonNull
	protected abstract IItemStack getEmptyItemStack();
	
	@NonNull
	public abstract ServerPacketHandler getServerPacketHandler();
}
