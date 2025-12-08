package fr.rakambda.overpoweredmending.neoforge;

import fr.rakambda.overpoweredmending.neoforge.common.OverpoweredMendingCommonsImpl;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.jspecify.annotations.NonNull;

@Mod(OverpoweredMending.MOD_ID)
@Log4j2
public class OverpoweredMending{
	public static final String MOD_ID = "overpoweredmending";
	@Getter
	private static OverpoweredMendingCommonsImpl mod;
	
	public OverpoweredMending(@NonNull IEventBus modEventBus, @NonNull ModContainer modContainer){
		mod = new OverpoweredMendingCommonsImpl(modEventBus);
		mod.registerNeoForge(NeoForge.EVENT_BUS);
	}
}
