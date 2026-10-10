package fr.rakambda.overpoweredmending.fabric.mixin;

import fr.rakambda.overpoweredmending.fabric.OverpoweredMending;
import fr.rakambda.overpoweredmending.fabric.wrapper.PlayerWrapper;
import fr.rakambda.overpoweredmending.fabric.wrapper.XpOrbWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin{
	@Shadow
	private int count;
	
	@Inject(method = "playerTouch", at = @At(value = "HEAD"), cancellable = true)
	public void onPlayerCollision(Player player, CallbackInfo callbackInfo){
		var orb = (ExperienceOrb) (Object) this;
		
		// Same conditions as vanilla before it picks up the orb (NeoForge/Forge events are only fired after these)
		if(callbackInfo.isCancelled() || !(player instanceof ServerPlayer) || player.takeXpDelay != 0){
			return;
		}
		
		if(OverpoweredMending.getMod().onXpPickedUp(new PlayerWrapper(player), new XpOrbWrapper(orb, () -> this.count, val -> this.count = val))){
			callbackInfo.cancel();
		}
	}
}
