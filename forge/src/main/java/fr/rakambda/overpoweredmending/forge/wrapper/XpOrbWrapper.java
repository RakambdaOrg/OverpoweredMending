package fr.rakambda.overpoweredmending.forge.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.ILevel;
import fr.rakambda.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.entity.ExperienceOrb;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class XpOrbWrapper implements IXpOrb{
	@NotNull
	@Getter
	private final ExperienceOrb raw;
	
	@Override
	public int getExperienceAmount(){
		return raw.getValue();
	}
	
	@Override
	public void setExperienceAmount(int amount){
		raw.value = amount;
	}
	
	@Override
	public void discard(){
		raw.discard();
	}
	
	@Override
	@NotNull
	public ILevel getLevel(){
		return new LevelWrapper(raw.getCommandSenderWorld());
	}
}
