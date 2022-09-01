package fr.raksrinana.overpoweredmending.fabric.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.ILevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.level.Level;

@RequiredArgsConstructor
@ToString
public class LevelWrapper implements ILevel{
	@NotNull
	@Getter
	private final Level raw;
	
	@Override
	public boolean isServer(){
		return !raw.isClientSide();
	}
}
