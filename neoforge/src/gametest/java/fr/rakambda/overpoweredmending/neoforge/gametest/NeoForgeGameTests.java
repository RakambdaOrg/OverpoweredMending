package fr.rakambda.overpoweredmending.neoforge.gametest;

import fr.rakambda.overpoweredmending.gametest.OverpoweredMendingGameTests;
import fr.rakambda.overpoweredmending.neoforge.OverpoweredMending;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jspecify.annotations.NonNull;

@Mod(OverpoweredMendingGameTests.NAMESPACE)
public class NeoForgeGameTests{
	public NeoForgeGameTests(@NonNull IEventBus modEventBus){
		var testFunctions = DeferredRegister.create(Registries.TEST_FUNCTION, OverpoweredMendingGameTests.NAMESPACE);
		OverpoweredMendingGameTests.testFunctions(OverpoweredMending::getMod).forEach((name, function) -> testFunctions.register(name, () -> function));
		testFunctions.register(modEventBus);
	}
}
