package fr.rakambda.overpoweredmending.forge.network;

import fr.rakambda.overpoweredmending.common.network.ClientPacketHandler;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import fr.rakambda.overpoweredmending.forge.OverpoweredMending;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import static fr.rakambda.overpoweredmending.forge.OverpoweredMendingUtils.id;

@RequiredArgsConstructor
public class ForgePacketHandler implements ClientPacketHandler, ServerPacketHandler{
	public static final SimpleChannel INSTANCE = ChannelBuilder
			.named(id("main"))
			.optional()
			.networkProtocolVersion(0)
			.simpleChannel()
			.play()
			.clientbound()
			.add(OverpoweredMendingConfigPacket.class, OverpoweredMendingConfigPacket.CODEC, ForgePacketHandler::handleConfigurationPacket)
			.build();
	
	@Override
	public void registerServer(){
	}
	
	@Override
	public void registerClient(){
	}
	
	public static void handleConfigurationPacket(OverpoweredMendingConfigPacket configurationPacket, CustomPayloadEvent.Context context){
		context.enqueueWork(() -> {
			if(FMLEnvironment.dist.isClient()){
				OverpoweredMending.getMod().getPacketUtils().onClientConfigurationPacket(configurationPacket.getPacket());
			}
		});
		context.setPacketHandled(true);
	}
}
