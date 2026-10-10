package fr.rakambda.overpoweredmending.fabric.gametest;

import fr.rakambda.overpoweredmending.fabric.OverpoweredMending;
import fr.rakambda.overpoweredmending.gametest.OverpoweredMendingGameTests;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class FabricGameTests implements ModInitializer{
	@Override
	public void onInitialize(){
		OverpoweredMendingGameTests.testFunctions(OverpoweredMending::getMod).forEach((name, function) ->
				Registry.register(BuiltInRegistries.TEST_FUNCTION, Identifier.fromNamespaceAndPath(OverpoweredMendingGameTests.NAMESPACE, name), function));
	}
}
