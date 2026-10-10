package fr.rakambda.overpoweredmending.gametest;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.gametest.scenario.ConfigurationScenarios;
import fr.rakambda.overpoweredmending.gametest.scenario.IgnoredItemScenarios;
import fr.rakambda.overpoweredmending.gametest.scenario.OrbScenarios;
import fr.rakambda.overpoweredmending.gametest.scenario.RepairScenarios;
import fr.rakambda.overpoweredmending.gametest.scenario.SelectionScenarios;
import net.minecraft.gametest.framework.GameTestHelper;
import org.jspecify.annotations.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Test functions shared by every loader.
 * <p>
 * Each loader only registers these functions in {@link net.minecraft.core.registries.Registries#TEST_FUNCTION} under {@link #NAMESPACE}. The tests themselves (structure,
 * environment, timeout) are data generated from the {@link GameTestCase} annotations, see {@code gametest/gametest.gradle}.
 */
public final class OverpoweredMendingGameTests{
	public static final String NAMESPACE = "overpoweredmending_gametest";

	/**
	 * Classes holding {@link GameTestCase} methods.
	 */
	public static final List<Class<?>> SCENARIO_CLASSES = List.of(
			RepairScenarios.class,
			IgnoredItemScenarios.class,
			SelectionScenarios.class,
			OrbScenarios.class,
			ConfigurationScenarios.class
	);

	private OverpoweredMendingGameTests(){
	}

	@NonNull
	public static Map<String, Consumer<GameTestHelper>> testFunctions(@NonNull Supplier<? extends OverpoweredMendingCommon> mod){
		var functions = new TreeMap<String, Consumer<GameTestHelper>>();
		for(var scenarioClass : SCENARIO_CLASSES){
			Arrays.stream(scenarioClass.getDeclaredMethods())
					.filter(method -> method.isAnnotationPresent(GameTestCase.class))
					.forEach(method -> {
						var name = testName(method);
						if(functions.put(name, helper -> invoke(method, helper, mod.get())) != null){
							throw new IllegalStateException("Duplicate game test name " + name);
						}
					});
		}
		return functions;
	}

	/**
	 * Must give the same name as {@code gametest/gametest.gradle}.
	 */
	@NonNull
	public static String testName(@NonNull Method method){
		return method.getName().replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
	}

	private static void invoke(@NonNull Method method, @NonNull GameTestHelper helper, @NonNull OverpoweredMendingCommon mod){
		if(!Modifier.isStatic(method.getModifiers())){
			throw new IllegalStateException("Game test " + method + " must be static");
		}
		try{
			method.invoke(null, helper, mod);
		}
		catch(InvocationTargetException e){
			if(e.getCause() instanceof RuntimeException runtimeException){
				throw runtimeException;
			}
			throw new IllegalStateException(e.getCause());
		}
		catch(IllegalAccessException e){
			throw new IllegalStateException("Game test " + method + " must be public", e);
		}
	}
}
