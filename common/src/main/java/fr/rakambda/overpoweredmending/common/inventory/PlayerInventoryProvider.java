package fr.rakambda.overpoweredmending.common.inventory;

import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import org.jspecify.annotations.NonNull;
import java.util.stream.Stream;

public class PlayerInventoryProvider implements IInventoryProvider{
	@Override
	@NonNull
	public Stream<IItemStack> getInventoryContent(@NonNull IPlayer player){
		return player.streamInventory();
	}
}
