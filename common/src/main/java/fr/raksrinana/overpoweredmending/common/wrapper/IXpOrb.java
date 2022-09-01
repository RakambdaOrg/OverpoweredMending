package fr.raksrinana.overpoweredmending.common.wrapper;

import org.jetbrains.annotations.NotNull;

public interface IXpOrb{
	int getExperienceAmount();
	
	void setExperienceAmount(int amount);
	
	void discard();
	
	@NotNull
	ILevel getLevel();
}
