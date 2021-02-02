package suite.regression.UI;


import logic.pages.MasterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AspireTestCase extends BaseTest {

    @Test(enabled = true, description = "Aspire testing")
    public void register() {

        test.get().info("Step 1: Register the phone ");
        MasterPage.getInstance().registerPhone("123456789");

        test.get().info("Step 2: Fill in the OTP ");
        MasterPage.getInstance().fillInOTP();
    }
}
