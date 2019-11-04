import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pageobjects.BasePage;
import java.io.File;
import java.time.LocalDateTime;

public abstract class BaseTest {

    static WebDriver driver;
    private static ExtentTest extentTest;
    private static ExtentReports report ;
    private static ExtentTest test;
    private String nombreDePrueba = "TestName";
    private String autorTest = "TestAutor";

    static void generarLog(Boolean condicion, String logEvent) throws Exception {
        /* Este metodo recibe una "condicion" la cual:
         Si es verdadera, en el reporte se genera un Log PASS con el mensaje "logEvent - Exitoso" con su respectiva captura
         Si es falsa, en el reporte se genera un Log FAIL con el mensaje "logEvent - Fallido" con su respectiva captura
        */
        if (condicion){
            test.log(LogStatus.PASS,extentTest.addScreenCapture(BasePage.getScreenshot(driver,logEvent))+ logEvent +" - Exitoso");
        }else{
            test.log(LogStatus.FAIL,extentTest.addScreenCapture(BasePage.getScreenshot(driver,logEvent))+ logEvent+ " - Fallido");
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        /* Se pone la ruta del chromedriver que se encuentra en el repo, se inicializa un driver, se maximiza la pantalla
        se inicia el test con el nombre del test que se ejecuta. Se le asigna ese nombre en el reporte.
        Se le asigna un autor,la descripcion y se crea un "LOG" avisando que empieza el test, con su respectivo nombre
         */
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lautaro.laghezza\\Desktop\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        test = report.startTest(getClass().getName());
        test.assignAuthor(autorTest);
        test.setDescription(getClass().getName());
        extentTest.log(LogStatus.INFO, getClass().getName() + " --Inicia");
    }

    @BeforeTest
    public void startReport(){
        String dateNow = LocalDateTime.now().toString().substring(0, 19).replace('.', '-').replace('T', '(').replace(':', '-')+ ")";
        //Nombre del archivo del reporte con la direccion del test
        report = new ExtentReports("C:\\Users\\lautaro.laghezza\\Desktop\\reportes\\reporte.html");
        //Configuracion del reporte
        report.loadConfig(new File(System.getProperty("user.dir")+ "\\extent-config.xml"));
        //Nombre del test
        extentTest = report.startTest(nombreDePrueba);
        //Autor del test
        extentTest.assignAuthor(autorTest);
        //Descripcion del test
        extentTest.setDescription("Horarios de inicio y finalizacion de los test");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanAndRefresh(){
       driver.manage().deleteAllCookies();
       driver.navigate().refresh();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        /* Manda al reporte un LOG avisando que termino el test, el flush lo agrega al reporte, y cierra el navegador
         */
        extentTest.log(LogStatus.INFO, getClass().getName() + " -- Finaliza");
        report.flush();
        driver.quit();
    }

    @AfterTest(alwaysRun = true)
    public void reportTest(){
        //Al finalizar todos los tests, finaliza el reporte
      report.endTest(extentTest);
    }
}
