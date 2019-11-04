package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public abstract class BasePage {

    protected WebDriver driver;
    WebDriverWait wait;

    BasePage(WebDriver driver) {
        /*Se le asigna el driver a la pagina que se utilice, y se crea el "wait" que van a tener todas las paginas
        que tiene un driver, y un tiempo de "time out"
         */
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 4);
    }

    public boolean isElementVisible(By locator){
        //Este metodo retorna true si un elemento esta visible, false si no lo está
        try{
            wait.until(visibilityOfElementLocated(locator));
            return true;
        }catch (TimeoutException e){
            return false;
        }
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        //Se crea con este nombre para que no haya imagenes con nombre duplicado
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //Luego de tener el screenshot, se guarda en la siguiente ruta con el nombre y la fecha
        String destination = "C:\\Users\\lautaro.laghezza\\Desktop\\reportes\\Screens\\"+screenshotName+dateName+".png";
        File finalDestination = new File(destination);
        FileHandler.copy(source, finalDestination);
        //Retorna la ruta del screenshot
        return destination;
    }
	
	protected void seleccionarElementoDeSelect(Select select, String nombreElementoABuscar) {
		List<WebElement> listaWE = select.getOptions();
		for (int i = 0; i < listaWE.size(); i++) {
			if (listaWE.get(i).getText().equalsIgnoreCase(nombreElementoABuscar)) {
				listaWE.get(i).click();
				}
		}
	}	

}