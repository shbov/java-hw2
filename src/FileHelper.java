import java.io.File;
import java.nio.file.Path;

/**
 * Класс для работы с файлами.
 */
public final class FileHelper {

  private FileHelper() {
  }

  /**
   * Объединяет два пути в один.
   *
   * @param currentDirectory путь 1
   * @param s                путь 2
   * @return объединенный путь
   */
  public static Path combinePath(String currentDirectory, String s) {
    String[] args = {currentDirectory, s};
    return Path.of(String.join(File.separator, args));
  }
}
