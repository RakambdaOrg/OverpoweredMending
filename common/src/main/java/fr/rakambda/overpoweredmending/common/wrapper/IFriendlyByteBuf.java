package fr.rakambda.overpoweredmending.common.wrapper;

public interface IFriendlyByteBuf extends IWrapper{
	void writeInteger(int value);
	
	void writeBoolean(boolean value);
	
	int readInteger();
	
	boolean readBoolean();
}
