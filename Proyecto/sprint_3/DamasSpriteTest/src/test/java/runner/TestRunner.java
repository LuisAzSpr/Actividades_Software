package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import pages.BasePage;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources", // Ruta a las carpetas de tus archivos .feature
    glue = "steps", // Ruta al paquete de tus steps (desde el paquete base)
    plugin = {"pretty", "html:target/cucumber-reports"}//, tags = "@Navigation"
)
public class TestRunner {
    @AfterClass
    public static void cleanDriver(){
        BasePage.closeBrowser();
    }
}
