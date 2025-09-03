package fr.rakambda.overpoweredmending.common.wrapper;

import org.jspecify.annotations.NonNull;
import java.util.stream.Stream;

public interface IPlayer extends IWrapper{
	@NonNull
	Stream<IItemStack> streamInventory();
	
	void addExperience(int amount);
	
	void sendPickup(@NonNull IXpOrb xp, int count);
	
	void setExperiencePickUpDelay(int delay);
}
