package fr.rakambda.overpoweredmending.neoforge.common.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.ILevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

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
