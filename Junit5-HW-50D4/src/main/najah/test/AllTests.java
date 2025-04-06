package main.najah.test;

import org.junit.platform.suite.api.*;

@Suite
@SelectClasses({
    CalculatorTest.class,
    ProductTest.class,
    UserServiceSimpleTest.class,
    RecipeBookTest.class
})
public class AllTests {
}
