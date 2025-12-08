package fr.rakambda.overpoweredmending.fabric;

import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

public class OverpoweredMendingUtils {
	@NonNull
	public static ResourceLocation id(@NonNull String name){
		return ResourceLocation.fromNamespaceAndPath(OverpoweredMending.MOD_ID, name);
	}
	
	@NonNull
	public static ResourceLocation idExternal(@NonNull String fullName){
		return ResourceLocation.parse(fullName);
	}
}
