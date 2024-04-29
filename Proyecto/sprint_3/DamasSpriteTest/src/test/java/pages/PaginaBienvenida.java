package pages;

import org.openqa.selenium.WebElement;

public class PaginaBienvenida extends BasePage{

    String message = "Te damos la bienvenida a DamasSprite.";
    String xpathMessage = "//div[@class='text-center container mb-3']";

    public PaginaBienvenida() {
        super(driver);
    }

    public boolean isMessageWelcomePresent(){
       return isPresentText(xpathMessage,message);
    }

}
