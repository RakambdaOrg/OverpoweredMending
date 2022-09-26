package fr.raksrinana.overpoweredmending.fabric.inventory;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import fr.raksrinana.overpoweredmending.common.inventory.IInventoryProvider;
import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import fr.raksrinana.overpoweredmending.common.wrapper.IPlayer;
import fr.raksrinana.overpoweredmending.fabric.wrapper.ItemStackWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.stream.Stream;

public class TrinketsInventoryProvider implements IInventoryProvider{
	@Override
	@NotNull
	public Stream<IItemStack> getInventoryContent(@NotNull IPlayer player){
		return TrinketsApi.getTrinketComponent((PlayerEntity) player.getRaw())
				.stream()
				.map(TrinketComponent::getAllEquipped)
				.flatMap(Collection::stream)
				.map(Pair::getRight)
				.map(ItemStackWrapper::new);
	}
}
