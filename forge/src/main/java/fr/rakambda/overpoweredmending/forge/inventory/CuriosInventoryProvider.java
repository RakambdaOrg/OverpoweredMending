package fr.rakambda.overpoweredmending.forge.inventory;

import fr.rakambda.overpoweredmending.common.inventory.IInventoryProvider;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.common.wrapper.IPlayer;
import fr.rakambda.overpoweredmending.forge.wrapper.ItemStackWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CuriosInventoryProvider implements IInventoryProvider{
	@Override
	@NotNull
	public Stream<IItemStack> getInventoryContent(@NotNull IPlayer player){
		return Optional.ofNullable(CuriosApi.getCuriosInventory((Player) player.getRaw()))
				.flatMap(LazyOptional::resolve)
				.stream()
				.flatMap(inventory -> inventory.getCurios().values().stream())
				.map(ICurioStacksHandler::getStacks)
				.flatMap(stackHandler -> IntStream.range(0, stackHandler.getSlots()).mapToObj(stackHandler::getStackInSlot))
				.map(ItemStackWrapper::new);
	}
}
