package fr.rakambda.overpoweredmending.fabric.mixin;

import fr.rakambda.overpoweredmending.fabric.OverpoweredMending;
import fr.rakambda.overpoweredmending.fabric.wrapper.PlayerWrapper;
import fr.rakambda.overpoweredmending.fabric.wrapper.XpOrbWrapper;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin{
	@Inject(method = "playerTouch", at = @At(value = "HEAD"), cancellable = true)
	public void onPlayerCollision(Player player, CallbackInfo callbackInfo){
		var orb = (ExperienceOrb) (Object) this;
		
		if(callbackInfo.isCancelled()){
			return;
		}
		
		if(OverpoweredMending.getMod().onXpPickedUp(new PlayerWrapper(player), new XpOrbWrapper(orb))){
			callbackInfo.cancel();
		}
	}
}
