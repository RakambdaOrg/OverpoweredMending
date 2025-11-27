package fr.rakambda.overpoweredmending.common.config;

import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import org.jspecify.annotations.NonNull;

public interface IConfiguration {
	@NonNull
	SelectionMode getSelectionMode();
}
