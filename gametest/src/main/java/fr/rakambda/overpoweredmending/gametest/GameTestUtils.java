package fr.rakambda.overpoweredmending.gametest;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import java.lang.reflect.Field;

/**
 * Every test is fully synchronous: it builds a mock player and an orb, touches the orb (which goes through the loader specific hook) and asserts the outcome.
 */
public final class GameTestUtils{
	/**
	 * {@link ExperienceOrb#count} is only made public by Forge/NeoForge access transformers.
	 */
	private static final Field ORB_COUNT;

	static{
		try{
			ORB_COUNT = ExperienceOrb.class.getDeclaredField("count");
			ORB_COUNT.setAccessible(true);
		}
		catch(NoSuchFieldException e){
			throw new IllegalStateException(e);
		}
	}

	private GameTestUtils(){
	}

	@NonNull
	public static Player createPlayer(@NonNull GameTestHelper helper){
		return helper.makeMockServerPlayer(GameType.SURVIVAL);
	}

	@NonNull
	public static ExperienceOrb createOrb(@NonNull GameTestHelper helper, int value, int count){
		var pos = helper.absoluteVec(Vec3.ZERO);
		var orb = new ExperienceOrb(helper.getLevel(), pos.x(), pos.y(), pos.z(), value);
		setOrbCount(orb, count);
		return orb;
	}

	public static int getOrbCount(@NonNull ExperienceOrb orb){
		try{
			return ORB_COUNT.getInt(orb);
		}
		catch(IllegalAccessException e){
			throw new IllegalStateException(e);
		}
	}

	public static void setOrbCount(@NonNull ExperienceOrb orb, int count){
		try{
			ORB_COUNT.setInt(orb, count);
		}
		catch(IllegalAccessException e){
			throw new IllegalStateException(e);
		}
	}

	@NonNull
	public static ItemStack damaged(@NonNull Item item, int damage){
		var stack = new ItemStack(item);
		stack.setDamageValue(damage);
		return stack;
	}

	@NonNull
	public static ItemStack mending(@NonNull GameTestHelper helper, @NonNull Item item, int damage){
		var stack = damaged(item, damage);
		stack.enchant(helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING), 1);
		return stack;
	}

	/**
	 * Runs with the given selection mode, then restores the configuration (tests may share a batch).
	 */
	public static void withSelectionMode(@NonNull OverpoweredMendingCommon mod, @NonNull SelectionMode mode, @NonNull Runnable runnable){
		var proxy = mod.getProxyConfiguration();
		proxy.setSelectionMode(mode);
		try{
			runnable.run();
		}
		finally{
			proxy.reset();
		}
	}

	@NonNull
	public static SelectionMode otherSelectionMode(@NonNull SelectionMode mode){
		return mode == SelectionMode.ABSOLUTE ? SelectionMode.PERCENTAGE : SelectionMode.ABSOLUTE;
	}
}
