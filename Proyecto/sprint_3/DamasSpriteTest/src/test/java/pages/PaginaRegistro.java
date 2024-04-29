package pages;

public class PaginaRegistro extends BasePage{

    private String registrarseButton = "//button[@type='submit']";
    private String iniciarSesionButton = "//a[@class='btn btn-dark w-100 mb-3']";
    private String terminosYCondiciones = "//input[@id='terminosYCondiciones']";
    private String itemCampo= "//input[@id='%s']";

    public PaginaRegistro() {
        super(driver);
    }
    public void navegarARegistro(){
        navigateTo("http://localhost:3000/registrar");
    }
    public void clickRegistrarseButton(){
        clickElement(registrarseButton);
    }
    public void clickIniciarSesionButton(){
        clickElement(iniciarSesionButton);
    }
    public void clickTerminosYCondiciones(){
        clickElement(terminosYCondiciones);
    }
    // Este metodo sirve para llenar los campos de: nombre, email, password, confirmPassword
    public void llenarCampo(String campo,String keysToSend){
        String xpathCampo = String.format(itemCampo, campo);
        write(xpathCampo,keysToSend);
    }

}
