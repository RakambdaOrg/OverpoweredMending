package fr.raksrinana.overpoweredmending.common.inventory;

import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import fr.raksrinana.overpoweredmending.common.wrapper.IPlayer;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Stream;

public interface IInventoryProvider{
	@NotNull
	Stream<IItemStack> getInventoryContent(@NotNull IPlayer player);
}
