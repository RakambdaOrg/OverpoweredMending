package fr.rakambda.overpoweredmending.common.inventory;

import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Stream;

public interface IInventoryProvider{
	@NotNull
	Stream<IItemStack> getInventoryContent(@NotNull IPlayer player);
}
