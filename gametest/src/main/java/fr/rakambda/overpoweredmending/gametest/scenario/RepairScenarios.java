package fr.rakambda.overpoweredmending.gametest.scenario;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.gametest.GameTestCase;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;
import java.util.function.Consumer;
import java.util.function.Function;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createOrb;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.createPlayer;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.damaged;
import static fr.rakambda.overpoweredmending.gametest.GameTestUtils.mending;

/**
 * Where items are repaired from and how the xp is split. One xp repairs 2 durability.
 */
public final class RepairScenarios{
	private RepairScenarios(){
	}

	/**
	 * The point of the mod: the item does not need to be held or worn.
	 */
	@GameTestCase
	public static void repairsItemInMainInventory(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		repairs(helper, p -> p.getInventory().setItem(20, mending(helper, Items.DIAMOND_PICKAXE, 100)), p -> p.getInventory().getItem(20));
	}

	@GameTestCase
	public static void repairsSelectedHotbarItem(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		repairs(helper, p -> p.getInventory().setItem(0, mending(helper, Items.DIAMOND_PICKAXE, 100)), Player::getMainHandItem);
	}

	@GameTestCase
	public static void repairsArmorItem(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		repairs(helper, p -> p.setItemSlot(EquipmentSlot.CHEST, mending(helper, Items.DIAMOND_CHESTPLATE, 100)), p -> p.getItemBySlot(EquipmentSlot.CHEST));
	}

	@GameTestCase
	public static void repairsOffhandItem(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		repairs(helper, p -> p.setItemSlot(EquipmentSlot.OFFHAND, mending(helper, Items.SHIELD, 100)), Player::getOffhandItem);
	}

	@GameTestCase
	public static void leftoverXpGoesToPlayer(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var player = createPlayer(helper);
		player.getInventory().setItem(5, mending(helper, Items.DIAMOND_PICKAXE, 6));

		createOrb(helper, 10, 1).playerTouch(player);

		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), 0, "item damage");
		helper.assertValueEqual(player.totalExperience, 7, "player xp");
		helper.succeed();
	}

	/**
	 * Documents current rounding: 1 durability costs 0 xp (integer division).
	 */
	@GameTestCase
	public static void oddDamageRepairsFullyAndKeepsXp(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var player = createPlayer(helper);
		player.getInventory().setItem(5, mending(helper, Items.DIAMOND_PICKAXE, 1));

		createOrb(helper, 1, 1).playerTouch(player);

		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), 0, "item damage");
		helper.assertValueEqual(player.totalExperience, 1, "player xp");
		helper.succeed();
	}

	@GameTestCase
	public static void xpSpreadsOverMultipleItems(@NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		var player = createPlayer(helper);
		player.getInventory().setItem(5, mending(helper, Items.DIAMOND_PICKAXE, 4));
		player.getInventory().setItem(6, mending(helper, Items.DIAMOND_PICKAXE, 4));
		player.getInventory().setItem(7, damaged(Items.DIAMOND_PICKAXE, 4));

		createOrb(helper, 10, 1).playerTouch(player);

		helper.assertValueEqual(player.getInventory().getItem(5).getDamageValue(), 0, "first item damage");
		helper.assertValueEqual(player.getInventory().getItem(6).getDamageValue(), 0, "second item damage");
		helper.assertValueEqual(player.getInventory().getItem(7).getDamageValue(), 4, "non mending item damage");
		helper.assertValueEqual(player.totalExperience, 6, "player xp");
		helper.succeed();
	}

	private static void repairs(@NonNull GameTestHelper helper, @NonNull Consumer<Player> setup, @NonNull Function<Player, ItemStack> getter){
		var player = createPlayer(helper);
		setup.accept(player);
		var orb = createOrb(helper, 10, 1);

		orb.playerTouch(player);

		helper.assertValueEqual(getter.apply(player).getDamageValue(), 80, "item damage");
		helper.assertValueEqual(player.totalExperience, 0, "player xp");
		helper.assertTrue(orb.isRemoved(), "orb should be removed");
		helper.succeed();
	}
}
