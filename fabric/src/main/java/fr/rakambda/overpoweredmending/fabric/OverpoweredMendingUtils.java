package fr.rakambda.overpoweredmending.fabric;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class OverpoweredMendingUtils {
	@NonNull
	public static Identifier id(@NonNull String name){
		return Identifier.fromNamespaceAndPath(OverpoweredMending.MOD_ID, name);
	}
	
	@NonNull
	public static Identifier idExternal(@NonNull String fullName){
		return Identifier.parse(fullName);
	}
}
