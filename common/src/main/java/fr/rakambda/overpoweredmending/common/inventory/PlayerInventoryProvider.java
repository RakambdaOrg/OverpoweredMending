package fr.rakambda.overpoweredmending.common.inventory;

import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Stream;

public class PlayerInventoryProvider implements IInventoryProvider{
	@Override
	@NotNull
	public Stream<IItemStack> getInventoryContent(@NotNull IPlayer player){
		return player.streamInventory();
	}
}
