/**
 * Входная точка программы
 */
public final class Main {

  /**
   * Входная точка программы
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) {
    String path = System.getProperty("user.dir");
    MagicFiles magicFiles = new MagicFiles(path);
    magicFiles.run();
  }
}