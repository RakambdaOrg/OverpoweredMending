package fr.rakambda.overpoweredmending.fabric.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.IXpOrb;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.entity.ExperienceOrb;
import org.jspecify.annotations.NonNull;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
@ToString
public class XpOrbWrapper implements IXpOrb{
	@NonNull
	@Getter
	private final ExperienceOrb raw;
	@NonNull
	private final Supplier<Integer> countGetter;
	@NonNull
	private final Consumer<Integer> countSetter;
	
	@Override
	public int getExperienceAmount(){
		return raw.getValue();
	}
	
	@Override
	public int getCount(){
		return countGetter.get();
	}
	
	@Override
	public void setCount(int count){
		countSetter.accept(count);
	}
	
	@Override
	public void discard(){
		raw.discard();
	}
}
