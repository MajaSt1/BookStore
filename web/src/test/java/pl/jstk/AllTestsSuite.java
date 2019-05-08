package pl.jstk;

import pl.jstk.controller.HomeControllerTest;
import pl.jstk.rest.BookControllerMockTest;
import pl.jstk.rest.BookControllerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomeControllerTest.class,
        BookControllerTest.class,
        BookControllerMockTest.class
})
public class AllTestsSuite {

}
