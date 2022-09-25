package fr.raksrinana.overpoweredmending.fabric.mixin;

import fr.raksrinana.overpoweredmending.fabric.OverpoweredMending;
import fr.raksrinana.overpoweredmending.fabric.wrapper.PlayerWrapper;
import fr.raksrinana.overpoweredmending.fabric.wrapper.XpOrbWrapper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin{
	private static final int DURABILITY_PER_XP = 2;
	
	@Inject(method = "onPlayerCollision", at = @At(value = "HEAD"), cancellable = true)
	public void onPlayerCollision(PlayerEntity player, CallbackInfo callbackInfo){
		var orb = (ExperienceOrbEntity) (Object) this;
		
		if(callbackInfo.isCancelled()){
			return;
		}
		
		if(OverpoweredMending.getMod().onXpPickedUp(new PlayerWrapper(player), new XpOrbWrapper(orb))){
			callbackInfo.cancel();
		}
	}
}
