package test.css.controls.%PACKAGE%;

import org.junit.Test;
import client.test.Keywords;
import client.test.Smoke;
import org.junit.BeforeClass;
import org.junit.Before;
import test.javaclient.shared.TestBase;
import static test.css.controls.ControlPage.%PAGE%;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class %CLASS_NAME% extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(%COMPARATOR_DISTANCE%);
    }

    @BeforeClass
    public static void runUI() {
        %APP_CLASS%.main(null);
    }

    @Before
    public void createPage () {
        ((%APP_CLASS%)getApplication()).open(%PAGE%);
    }

%TESTS%

    public String getName() {
        return "ControlCss";
    }
}
