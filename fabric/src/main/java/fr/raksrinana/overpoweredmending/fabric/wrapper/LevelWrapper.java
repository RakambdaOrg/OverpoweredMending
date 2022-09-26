package fr.raksrinana.overpoweredmending.fabric.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.ILevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class LevelWrapper implements ILevel{
	@NotNull
	@Getter
	private final World raw;
	
	@Override
	public boolean isServer(){
		return !raw.isClient();
	}
}
