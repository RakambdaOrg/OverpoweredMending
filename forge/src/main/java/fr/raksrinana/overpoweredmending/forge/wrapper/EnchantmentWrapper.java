package fr.raksrinana.overpoweredmending.forge.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.IEnchantment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class EnchantmentWrapper implements IEnchantment{
	@NotNull
	@Getter
	private final Enchantment raw;
}
