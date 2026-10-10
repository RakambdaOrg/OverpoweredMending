package fr.rakambda.overpoweredmending.gametest.scenario;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import fr.rakambda.overpoweredmending.gametest.GameTestCase;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createOrb;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createPlayer;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.mending;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.withSelectionMode;

/**
 * Which item is repaired first. Diamond pickaxe 200/1561 (~13%) has more absolute damage, wooden pickaxe 30/59 (~51%) has a higher ratio.
 */
public final class SelectionScenarios{
	private SelectionScenarios(){
	}

	@GameTestCase
	public static void percentageModeRepairsHighestRatio(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		selection(helper, mod, SelectionMode.PERCENTAGE, 200, 28);
	}

	@GameTestCase
	public static void absoluteModeRepairsHighestDamage(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		selection(helper, mod, SelectionMode.ABSOLUTE, 198, 30);
	}

	private static void selection(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod, @NonNull SelectionMode mode, int expectedDiamondDamage, int expectedWoodenDamage){
		withSelectionMode(mod, mode, () -> {
			var player = createPlayer(helper);
			player.getInventory().setItem(5, mending(helper, Items.DIAMOND_PICKAXE, 200));
			player.getInventory().setItem(6, mending(helper, Items.WOODEN_PICKAXE, 30));

			createOrb(helper, 1, 1).playerTouch(player);

			helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), expectedDiamondDamage, "diamond pickaxe damage");
			helper.assertValueEqual(player.getInventory().getItem(6).getDamageValue(), expectedWoodenDamage, "wooden pickaxe damage");
		});
		helper.succeed();
	}
}
