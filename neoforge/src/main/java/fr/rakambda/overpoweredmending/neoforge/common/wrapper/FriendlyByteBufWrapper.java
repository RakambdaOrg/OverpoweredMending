package fr.rakambda.overpoweredmending.neoforge.common.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.IFriendlyByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.network.FriendlyByteBuf;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
@ToString
public class FriendlyByteBufWrapper implements IFriendlyByteBuf{
	@NonNull
	@Getter
	private final FriendlyByteBuf raw;
	
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
