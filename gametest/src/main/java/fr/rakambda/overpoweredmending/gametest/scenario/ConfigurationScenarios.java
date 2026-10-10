package fr.rakambda.overpoweredmending.gametest.scenario;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import fr.rakambda.overpoweredmending.common.network.ConfigurationPacket;
import fr.rakambda.overpoweredmending.common.wrapper.IFriendlyByteBuf;
import fr.rakambda.overpoweredmending.gametest.GameTestCase;
import io.netty.buffer.Unpooled;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.FriendlyByteBuf;
import org.jspecify.annotations.NonNull;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.otherSelectionMode;

/**
 * Server to client configuration sync.
 */
public final class ConfigurationScenarios{
	private ConfigurationScenarios(){
	}

	@GameTestCase
	public static void dedicatedServerConfigOverridesLocal(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var local = mod.getOwnConfiguration().getSelectionMode();
		var remote = otherSelectionMode(local);
		try{
			mod.getPacketUtils().onClientConfigurationPacket(new ConfigurationPacket(true, remote));
			helper.assertValueEqual(mod.getConfiguration().getSelectionMode(), remote, "selection mode after packet");

			mod.getPacketUtils().onClientDisconnect();
			helper.assertValueEqual(mod.getConfiguration().getSelectionMode(), local, "selection mode after disconnect");
		}
		finally{
			mod.getProxyConfiguration().reset();
		}
		helper.succeed();
	}

	@GameTestCase
	public static void integratedServerConfigIsIgnored(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var local = mod.getOwnConfiguration().getSelectionMode();
		try{
			mod.getPacketUtils().onClientConfigurationPacket(new ConfigurationPacket(false, otherSelectionMode(local)));
			helper.assertValueEqual(mod.getConfiguration().getSelectionMode(), local, "selection mode");
		}
		finally{
			mod.getProxyConfiguration().reset();
		}
		helper.succeed();
	}

	@GameTestCase
	public static void configurationPacketRoundTrip(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		for(var dedicated : new boolean[]{true, false}){
			for(var mode : SelectionMode.getValues()){
				var packet = new ConfigurationPacket(dedicated, mode);
				var buf = new ByteBuf(new FriendlyByteBuf(Unpooled.buffer()));
				packet.write(buf);
				helper.assertValueEqual(ConfigurationPacket.read(buf), packet, "packet");
			}
		}
		helper.succeed();
	}

	/**
	 * Same as the loaders' FriendlyByteBufWrapper, which are not visible from shared code.
	 */
	private record ByteBuf(@NonNull FriendlyByteBuf raw) implements IFriendlyByteBuf{
		@Override
		@NonNull
		public FriendlyByteBuf getRaw(){
			return raw;
		}
		
		@Override
		public void writeInteger(int value){
			raw.writeInt(value);
		}

		@Override
		public void writeBoolean(boolean value){
			raw.writeBoolean(value);
		}

		@Override
		public int readInteger(){
			return raw.readInt();
		}

		@Override
		public boolean readBoolean(){
			return raw.readBoolean();
		}
	}
}
