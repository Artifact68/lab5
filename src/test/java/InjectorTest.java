package somepackage;

import Injector;
import annotations.AutoInjectable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    private Injector injector;

    @BeforeEach
    void setUp() {

        injector = new Injector("src/main/resources/config.properties");
    }

    @Test
    void testInjectDependency() {
        SomeBean someBean = new SomeBean();


        injector.inject(someBean);


        assertNotNull(someBean.field1, "Field1 should not be null");
        assertNotNull(someBean.field2, "Field2 should not be null");


        someBean.foo();
    }

    @Test
    void testInjectWithDifferentConfig() {

        Injector newInjector = new Injector("src/main/resources/config.properties");

        SomeBean someBean = new SomeBean();
        newInjector.inject(someBean);


        assertNotNull(someBean.field1, "Field1 should not be null");
        assertNotNull(someBean.field2, "Field2 should not be null");


        someBean.foo();
    }

    @Test
    void testInjectWithMissingDependency() {

        Injector faultyInjector = new Injector("src/main/resources/faulty_config.properties");

        SomeBean someBean = new SomeBean();
        assertThrows(NullPointerException.class, () -> faultyInjector.inject(someBean));
    }
}
