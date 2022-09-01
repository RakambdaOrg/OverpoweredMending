package fr.raksrinana.overpoweredmending.fabric;

import fr.raksrinana.overpoweredmending.fabric.common.OverpoweredMendingImpl;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

public class OverpoweredMending implements ModInitializer{
	@Getter
	private static final OverpoweredMendingImpl mod = new OverpoweredMendingImpl();
	
	@Override
	public void onInitialize(){
	}
}
