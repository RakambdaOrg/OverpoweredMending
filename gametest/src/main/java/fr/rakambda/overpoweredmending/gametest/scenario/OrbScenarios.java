package fr.rakambda.overpoweredmending.gametest.scenario;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.gametest.GameTestCase;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createOrb;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createPlayer;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.getOrbCount;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.mending;

/**
 * Orb consumption and pickup delay.
 */
public final class OrbScenarios{
	private OrbScenarios(){
	}

	@GameTestCase
	public static void mergedOrbIsConsumedOneByOne(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var player = createPlayer(helper);
		player.getInventory().setItem(5, mending(helper, Items.DIAMOND_PICKAXE, 100));
		var orb = createOrb(helper, 10, 3);

		orb.playerTouch(player);
		helper.assertValueEqual(getOrbCount(orb), 2, "orb count");
		helper.assertFalse(orb.isRemoved(), "orb should not be removed");
		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), 80, "item damage after first pickup");

		for(var i = 0; i < 2; i++){
			player.takeXpDelay = 0;
			orb.playerTouch(player);
		}
		helper.assertTrue(orb.isRemoved(), "orb should be removed");
		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), 40, "item damage after all pickups");
		helper.succeed();
	}

	@GameTestCase
	public static void pickupDelayIsSet(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var player = createPlayer(helper);

		createOrb(helper, 10, 1).playerTouch(player);

		helper.assertValueEqual(player.takeXpDelay, 2, "pickup delay");
		helper.succeed();
	}

	@GameTestCase
	public static void pickupDelayBlocksPickup(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var player = createPlayer(helper);
		player.getInventory().setItem(5, mending(helper, Items.DIAMOND_PICKAXE, 100));
		player.takeXpDelay = 2;
		var orb = createOrb(helper, 10, 1);

		orb.playerTouch(player);

		helper.assertFalse(orb.isRemoved(), "orb should not be removed");
		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), 100, "item damage");
		helper.assertValueEqual(player.totalExperience, 0, "player xp");
		helper.succeed();
	}
}
