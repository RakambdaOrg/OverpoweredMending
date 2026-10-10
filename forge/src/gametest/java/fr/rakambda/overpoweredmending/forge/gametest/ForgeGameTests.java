package fr.rakambda.overpoweredmending.forge.gametest;

import fr.rakambda.overpoweredmending.forge.OverpoweredMending;
import fr.rakambda.overpoweredmending.gametest.OverpoweredMendingGameTests;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(OverpoweredMendingGameTests.NAMESPACE)
public class ForgeGameTests{
	public ForgeGameTests(FMLJavaModLoadingContext context){
		var testFunctions = DeferredRegister.create(Registries.TEST_FUNCTION, OverpoweredMendingGameTests.NAMESPACE);
		OverpoweredMendingGameTests.testFunctions(OverpoweredMending::getMod).forEach((name, function) -> testFunctions.register(name, () -> function));
		testFunctions.register(context.getModBusGroup());
	}
}
