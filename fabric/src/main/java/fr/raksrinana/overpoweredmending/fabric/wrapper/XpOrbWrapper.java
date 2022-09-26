package fr.raksrinana.overpoweredmending.fabric.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.ILevel;
import fr.raksrinana.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.entity.ExperienceOrbEntity;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class XpOrbWrapper implements IXpOrb{
	@NotNull
	@Getter
	private final ExperienceOrbEntity raw;
	
	@Override
	public int getExperienceAmount(){
		return raw.getExperienceAmount();
	}
	
	@Override
	public void setExperienceAmount(int amount){
	}
	
	@Override
	public void discard(){
		raw.discard();
	}
	
	@Override
	@NotNull
	public ILevel getLevel(){
		return new LevelWrapper(raw.getWorld());
	}
}
