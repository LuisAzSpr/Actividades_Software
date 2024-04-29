package pages;

public class PaginaPrincipal extends BasePage{

    //Los xpath asociados a la pagina principal
    private String registrarseButton = "//a[@class='btn btn-dark w-100 mb-3']";
    private String ingresarBotton = "//button[@type='submit']";
    private String itemBarra = "//a[normalize-space()='%s']";
    private String itemCampo = "//input[@id='%s']";

    public PaginaPrincipal() {
        super(driver);
    }

    // Metodo para navegar hasta la pagina de inicio
    public void navegarADamasSprite(){
        navigateTo("http://localhost:3000/");
    }

    public void clickOnSectionNavigateBar(String sectionName){
        //remplazar el marcador de posicion en sectionlink con el nombre
        String xpathSection = String.format(itemBarra, sectionName);
        clickElement(xpathSection);
    }
    public void clickOnIngresarBotton(){
        clickElement(ingresarBotton);
    }
    public void clickOnRegistrarseButton(){
        clickElement(registrarseButton);
    }
    public String getUrlActual(){
        return driver.getCurrentUrl();
    }
    public void completarCampos(String user, String pass){
        write(String.format(itemCampo,"username"), user);
        write(String.format(itemCampo,"password"), pass);
    }
}
