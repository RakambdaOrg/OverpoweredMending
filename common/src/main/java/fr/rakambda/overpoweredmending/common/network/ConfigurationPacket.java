package fr.rakambda.overpoweredmending.common.network;

import fr.rakambda.overpoweredmending.common.config.IConfiguration;
import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import fr.rakambda.overpoweredmending.common.wrapper.IFriendlyByteBuf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationPacket {
	private boolean dedicated;
	private SelectionMode selectionMode;
	
	public static ConfigurationPacket get(boolean dedicated, @NonNull IConfiguration configuration){
		return builder()
				.dedicated(dedicated)
				.selectionMode(configuration.getSelectionMode())
				.build();
	}
	
	public void write(IFriendlyByteBuf buf){
		buf.writeBoolean(isDedicated());
		buf.writeInteger(getSelectionMode().ordinal());
	}
	
	public static ConfigurationPacket read(IFriendlyByteBuf buf){
		return builder()
				.dedicated(buf.readBoolean())
				.selectionMode(SelectionMode.getValues()[buf.readInteger()])
				.build();
	}
}
