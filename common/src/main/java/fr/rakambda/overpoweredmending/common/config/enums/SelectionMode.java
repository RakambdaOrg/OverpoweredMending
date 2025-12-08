package fr.rakambda.overpoweredmending.common.config.enums;

import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Comparator;

@Getter
@RequiredArgsConstructor
public enum SelectionMode{
	ABSOLUTE(Comparator.comparingInt(IItemStack::getDamageValue)),
	PERCENTAGE(Comparator.comparingDouble(IItemStack::getDamagePercentage));
	
	@Getter
	private final static SelectionMode[] values = values();
	
	private final Comparator<IItemStack> comparator;
}
