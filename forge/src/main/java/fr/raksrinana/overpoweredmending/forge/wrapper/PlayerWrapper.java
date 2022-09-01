package fr.raksrinana.overpoweredmending.forge.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import fr.raksrinana.overpoweredmending.common.wrapper.IPlayer;
import fr.raksrinana.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
public class PlayerWrapper implements IPlayer{
	@NotNull
	@Getter
	private final Player raw;
	
	@Override
	@NotNull
	public Stream<IItemStack> streamInventory(){
		var playerInventory = raw.getInventory();
		return IntStream.range(0, playerInventory.getContainerSize())
				.mapToObj(playerInventory::getItem)
				.map(ItemStackWrapper::new);
	}
	
	@Override
	public void addExperience(int amount){
		raw.giveExperiencePoints(amount);
	}
	
	@Override
	public void sendPickup(@NotNull IXpOrb xp, int count){
		raw.take((Entity) xp, count);
	}
	
	@Override
	public void setExperiencePickUpDelay(int delay){
		raw.takeXpDelay = delay;
	}
}
