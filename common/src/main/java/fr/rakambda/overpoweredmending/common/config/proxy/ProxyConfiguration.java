package fr.rakambda.overpoweredmending.common.config.proxy;

import fr.rakambda.overpoweredmending.common.config.IConfiguration;
import fr.rakambda.overpoweredmending.common.config.IResettable;
import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import java.util.Optional;

public class ProxyConfiguration implements IConfiguration, IResettable{
	private final IConfiguration delegate;
	
	@Setter
	private SelectionMode selectionMode;
	
	public ProxyConfiguration(IConfiguration delegate){
		this.delegate = delegate;
	}
	
	@Override
	public void reset(){
		selectionMode = null;
	}
	
	@Override
	@NonNull
	public SelectionMode getSelectionMode(){
		return Optional.ofNullable(selectionMode).orElseGet(delegate::getSelectionMode);
	}
}
