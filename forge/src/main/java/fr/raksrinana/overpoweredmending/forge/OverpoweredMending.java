package fr.raksrinana.overpoweredmending.forge;

import fr.raksrinana.overpoweredmending.common.inventory.IInventoryProvider;
import fr.raksrinana.overpoweredmending.forge.common.OverpoweredMendingImpl;
import fr.raksrinana.overpoweredmending.forge.inventory.CuriosInventoryProvider;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Mod(OverpoweredMending.MOD_ID)
@Log4j2
public class OverpoweredMending{
	public static final String MOD_ID = "overpoweredmending";
	@Getter
	private static final OverpoweredMendingImpl mod = new OverpoweredMendingImpl();
	
	public OverpoweredMending(){
		initCurios().ifPresent(mod::addInventoryProvider);
		
		mod.registerForge(MinecraftForge.EVENT_BUS);
	}
	
	@NotNull
	private Optional<IInventoryProvider> initCurios(){
		if(ModList.get().isLoaded("curios")){
			try{
				return Optional.of(Class.forName("fr.raksrinana.overpoweredmending.forge.hooks.CuriosHook")
						.asSubclass(CuriosInventoryProvider.class)
						.getConstructor()
						.newInstance());
			}
			catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
				log.error("Failed to hook into Curios", e);
			}
		}
		return Optional.empty();
	}
}
