package fr.rakambda.overpoweredmending.forge;

import fr.rakambda.overpoweredmending.forge.common.OverpoweredMendingCommonsImpl;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.fml.common.Mod;

@Mod(OverpoweredMending.MOD_ID)
@Log4j2
public class OverpoweredMending{
	public static final String MOD_ID = "overpoweredmending";
	@Getter
	private static final OverpoweredMendingCommonsImpl mod = new OverpoweredMendingCommonsImpl();
	
	public OverpoweredMending(){
		mod.registerForge();
	}
}
