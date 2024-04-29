package pages;

public class PaginaMenu extends BasePage{

    private final String xpathItem = "//a[normalize-space()='%s']";
    private final String xpathLogout = "//*[@id=\"root\"]/div/div[2]/div/div/a[3]";

    public PaginaMenu() {
        super(driver);
    }

    public boolean isPresentButton(String bt){
        String xpathBt = String.format(xpathItem, bt);
        return isPresentText(xpathBt, bt);
    }
    public void clickButton(String bt){
        clickElement(xpathLogout);
    }

    public void cerrarSesion() {

    }
}
