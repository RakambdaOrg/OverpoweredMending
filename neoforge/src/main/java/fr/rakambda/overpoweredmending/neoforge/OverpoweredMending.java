package fr.rakambda.overpoweredmending.neoforge;

import fr.rakambda.overpoweredmending.common.inventory.IInventoryProvider;
import fr.rakambda.overpoweredmending.neoforge.common.OverpoweredMendingImpl;
import fr.rakambda.overpoweredmending.neoforge.inventory.CuriosInventoryProvider;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
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
		
		mod.registerForge(NeoForge.EVENT_BUS);
	}
	
	@NotNull
	private Optional<IInventoryProvider> initCurios(){
		if(ModList.get().isLoaded("curios")){
			try{
				return Optional.of(Class.forName("fr.rakambda.overpoweredmending.neoforge.inventory.CuriosInventoryProvider")
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
