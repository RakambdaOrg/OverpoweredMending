package fr.rakambda.overpoweredmending.fabric.common;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.fabric.network.FabricServerPacketHandler;
import fr.rakambda.overpoweredmending.fabric.wrapper.ItemStackWrapper;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class OverpoweredMendingCommonsImpl extends OverpoweredMendingCommon{
	@Getter
	private final ServerPacketHandler serverPacketHandler;
	
	public OverpoweredMendingCommonsImpl(){
		serverPacketHandler = new FabricServerPacketHandler(this);
	}
	
	@Override
	@NonNull
	protected IItemStack getEmptyItemStack(){
		return new ItemStackWrapper(ItemStack.EMPTY);
	}
	
	public void register(){
		getServerPacketHandler().registerServer();
	}
}
