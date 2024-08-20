package fr.rakambda.overpoweredmending.fabric.inventory;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import fr.rakambda.overpoweredmending.common.inventory.IInventoryProvider;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import fr.rakambda.overpoweredmending.fabric.wrapper.ItemStackWrapper;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.stream.Stream;

public class TrinketsInventoryProvider implements IInventoryProvider{
	@Override
	@NotNull
	public Stream<IItemStack> getInventoryContent(@NotNull IPlayer player){
		return TrinketsApi.getTrinketComponent((Player) player.getRaw())
				.stream()
				.map(TrinketComponent::getAllEquipped)
				.flatMap(Collection::stream)
				.map(Tuple::getB)
				.map(ItemStackWrapper::new);
	}
}
