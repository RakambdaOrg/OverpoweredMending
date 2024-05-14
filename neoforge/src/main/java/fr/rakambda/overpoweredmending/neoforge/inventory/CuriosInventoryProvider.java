package fr.rakambda.overpoweredmending.neoforge.inventory;

import fr.rakambda.overpoweredmending.common.inventory.IInventoryProvider;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import fr.rakambda.overpoweredmending.neoforge.wrapper.ItemStackWrapper;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CuriosInventoryProvider implements IInventoryProvider{
	@Override
	@NotNull
	public Stream<IItemStack> getInventoryContent(@NotNull IPlayer player){
		return Optional.ofNullable(CuriosApi.getCuriosHelper())
				.map(helper -> helper.getEquippedCurios((Player) player.getRaw()))
				.stream()
				.flatMap(Optional::stream)
				.flatMap(inventory -> IntStream.range(0, inventory.getSlots()).mapToObj(inventory::getStackInSlot))
				.map(ItemStackWrapper::new);
	}
}
