package fr.rakambda.overpoweredmending.forge.network;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ConfigurationPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jspecify.annotations.NonNull;
import javax.annotation.Nonnull;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
public class PlayerJoinListener{
	@NonNull
	private final OverpoweredMendingCommon mod;
	
	public void onPlayerLoggedInEvent(@Nonnull PlayerEvent.PlayerLoggedInEvent event){
		if(event.getEntity() instanceof ServerPlayer serverPlayer){
			var server = serverPlayer.server;
			if(Objects.nonNull(server) && server.isDedicatedServer()){
				var packet = ConfigurationPacket.get(server.isDedicatedServer(), mod.getConfiguration());
				ForgePacketHandler.INSTANCE.send(new OverpoweredMendingConfigPacket(packet), PacketDistributor.PLAYER.with(serverPlayer));
			}
			else{
				log.info("Player connected to a local world, not setting up proxy config");
			}
		}
	}
}
