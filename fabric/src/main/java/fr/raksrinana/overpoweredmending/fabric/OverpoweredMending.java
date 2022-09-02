package fr.raksrinana.overpoweredmending.fabric;

import fr.raksrinana.overpoweredmending.common.inventory.IInventoryProvider;
import fr.raksrinana.overpoweredmending.fabric.common.OverpoweredMendingImpl;
import fr.raksrinana.overpoweredmending.fabric.inventory.TrinketsInventoryProvider;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Log4j2
public class OverpoweredMending implements ModInitializer{
	@Getter
	private static final OverpoweredMendingImpl mod = new OverpoweredMendingImpl();
	
	@Override
	public void onInitialize(){
		initTrinkets().ifPresent(mod::addInventoryProvider);
	}
	
	@NotNull
	private Optional<IInventoryProvider> initTrinkets(){
		if(FabricLoader.getInstance().isModLoaded("trinkets")){
			try{
				return Optional.of(Class.forName("fr.raksrinana.overpoweredmending.fabric.inventory.TrinketsInventoryProvider")
						.asSubclass(TrinketsInventoryProvider.class)
						.getConstructor()
						.newInstance());
			}
			catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
				log.error("Failed to hook into ClothConfig", e);
			}
		}
		return Optional.empty();
	}
}
