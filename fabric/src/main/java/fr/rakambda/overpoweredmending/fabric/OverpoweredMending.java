package fr.rakambda.overpoweredmending.fabric;

import fr.rakambda.overpoweredmending.fabric.common.OverpoweredMendingCommonsImpl;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ModInitializer;

@Log4j2
public class OverpoweredMending implements ModInitializer{
	public static final String MOD_ID = "overpoweredmending";
	
	@Getter
	private static final OverpoweredMendingCommonsImpl mod = new OverpoweredMendingCommonsImpl();
	
	@Override
	public void onInitialize(){
		mod.register();
	}
}
