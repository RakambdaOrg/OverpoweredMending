package fr.rakambda.overpoweredmending.fabric.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.ILevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import net.minecraft.world.level.Level;

@RequiredArgsConstructor
@ToString
public class LevelWrapper implements ILevel{
	@NonNull
	@Getter
	private final Level raw;
	
	@Override
	public boolean isServer(){
		return !raw.isClientSide();
	}
}
