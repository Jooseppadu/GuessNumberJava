/**
 * On Java põhiklass, mis sisaldab main meetodit.
 */
public class App {
    /**
     *  konstruktor mis loob uue uue model objekti
     */
    public App() {
        new Model().showMenu();

    }

    /**
     * app main method
     * @param args arguments from command line
     */

    public static void main(String[] args) {
        new App();
    }
}
