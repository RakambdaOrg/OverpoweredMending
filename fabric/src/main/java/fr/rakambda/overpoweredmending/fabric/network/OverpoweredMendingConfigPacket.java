package fr.rakambda.overpoweredmending.fabric.network;

import fr.rakambda.overpoweredmending.common.network.ConfigurationPacket;
import fr.rakambda.overpoweredmending.fabric.common.FriendlyByteBufWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;
import static fr.rakambda.overpoweredmending.fabric.OverpoweredMendingUtils.id;

@RequiredArgsConstructor
public class OverpoweredMendingConfigPacket implements CustomPacketPayload{
	public static final Type<OverpoweredMendingConfigPacket> TYPE = new Type<>(id("configuration-packet"));
	public static final StreamCodec<FriendlyByteBuf, OverpoweredMendingConfigPacket> CODEC = CustomPacketPayload.codec(
			OverpoweredMendingConfigPacket::write,
			packet -> new OverpoweredMendingConfigPacket(ConfigurationPacket.read(new FriendlyByteBufWrapper(packet))));
	
	@Getter
	private final ConfigurationPacket packet;
	
	public void write(FriendlyByteBuf buf){
		packet.write(new FriendlyByteBufWrapper(buf));
	}
	
	@Override
	@NonNull
	public Type<? extends CustomPacketPayload> type(){
		return TYPE;
	}
}
