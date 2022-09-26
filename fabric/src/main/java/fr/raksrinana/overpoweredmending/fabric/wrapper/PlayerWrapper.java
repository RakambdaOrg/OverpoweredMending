package fr.raksrinana.overpoweredmending.fabric.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import fr.raksrinana.overpoweredmending.common.wrapper.IPlayer;
import fr.raksrinana.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
public class PlayerWrapper implements IPlayer{
	@NotNull
	@Getter
	private final PlayerEntity raw;
	
	@Override
	@NotNull
	public Stream<IItemStack> streamInventory(){
		var playerInventory = raw.getInventory();
		return IntStream.range(0, playerInventory.size())
				.mapToObj(playerInventory::getStack)
				.map(ItemStackWrapper::new);
	}
	
	@Override
	public void addExperience(int amount){
		raw.addExperience(amount);
	}
	
	@Override
	public void sendPickup(@NotNull IXpOrb xp, int count){
		raw.sendPickup((Entity) xp.getRaw(), count);
	}
	
	@Override
	public void setExperiencePickUpDelay(int delay){
		raw.experiencePickUpDelay = delay;
	}
}
