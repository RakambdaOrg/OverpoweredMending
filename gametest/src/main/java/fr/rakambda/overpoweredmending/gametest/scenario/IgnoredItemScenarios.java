package fr.rakambda.overpoweredmending.gametest.scenario;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.gametest.GameTestCase;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createOrb;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createPlayer;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.damaged;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.mending;

/**
 * Items that must not be repaired: all the xp goes to the player.
 */
public final class IgnoredItemScenarios{
	private IgnoredItemScenarios(){
	}

	@GameTestCase
	public static void itemWithoutMendingIsIgnored(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		ignored(helper, damaged(Items.DIAMOND_PICKAXE, 50));
	}

	@GameTestCase
	public static void undamagedMendingItemIsIgnored(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		ignored(helper, mending(helper, Items.DIAMOND_PICKAXE, 0));
	}

	@GameTestCase
	public static void unbreakableMendingItemIsIgnored(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var stack = mending(helper, Items.DIAMOND_PICKAXE, 50);
		stack.set(DataComponents.UNBREAKABLE, Unit.INSTANCE);
		ignored(helper, stack);
	}

	@GameTestCase
	public static void emptyInventoryGivesAllXp(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		ignored(helper, ItemStack.EMPTY);
	}

	private static void ignored(@NonNull GameTestHelper helper, @NonNull ItemStack stack){
		var player = createPlayer(helper);
		var damage = stack.getDamageValue();
		player.getInventory().setItem(5, stack);
		var orb = createOrb(helper, 10, 1);

		orb.playerTouch(player);

		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), damage, "item damage");
		helper.assertValueEqual(player.totalExperience, 10, "player xp");
		helper.assertTrue(orb.isRemoved(), "orb should be removed");
		helper.succeed();
	}
}
