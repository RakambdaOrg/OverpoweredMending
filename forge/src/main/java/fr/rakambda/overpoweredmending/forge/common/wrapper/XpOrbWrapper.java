package fr.rakambda.overpoweredmending.forge.common.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.entity.ExperienceOrb;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
@ToString
public class XpOrbWrapper implements IXpOrb{
	@NonNull
	@Getter
	private final ExperienceOrb raw;
	
	@Override
	public int getExperienceAmount(){
		return raw.getValue();
	}
	
	@Override
	public int getCount(){
		return raw.count;
	}
	
	@Override
	public void setCount(int count){
		raw.count = count;
	}
	
	@Override
	public void discard(){
		raw.discard();
	}
}
