package fr.rakambda.overpoweredmending.common.wrapper;

import org.jetbrains.annotations.NotNull;
import java.util.stream.Stream;

public interface IPlayer extends IWrapper{
	@NotNull
	Stream<IItemStack> streamInventory();
	
	void addExperience(int amount);
	
	void sendPickup(@NotNull IXpOrb xp, int count);
	
	void setExperiencePickUpDelay(int delay);
}
