package fr.rakambda.overpoweredmending.gametest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@code public static void name(GameTestHelper helper, OverpoweredMendingCommon mod)} method of a class listed in
 * {@link OverpoweredMendingGameTests#SCENARIO_CLASSES} as a GameTest.
 * <p>
 * The test is named after the method in snake_case. Its {@code test_instance} data is generated from this annotation by {@code gametest/gametest.gradle}, which
 * reads the sources: keep the annotation directly above the method declaration, and only use literal values.
 * <p>
 * All tests share the same environment and may run in the same batch: a test changing the (global) mod configuration must restore it before returning.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GameTestCase{
	/**
	 * Keep in sync with the default in {@code gametest/gametest.gradle}.
	 */
	int DEFAULT_MAX_TICKS = 20;

	int maxTicks() default DEFAULT_MAX_TICKS;
}
