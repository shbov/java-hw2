package magic.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс-помощник для работы с файлами.
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

  /**
   * Находит все файлы в директории.
   *
   * @return поток путей к файлам
   * @throws IOException если что-то пошло не так
   */
  public static Stream<Path> findAllFiles(Path path) throws IOException {
    return Files.find(
        path,
        Integer.MAX_VALUE,
        (filePath, fileAttr) -> fileAttr.isRegularFile()
    );
  }

  /**
   * Конкатинирует все файлы в один.
   *
   * @param currentDirectory текущая директория
   * @param files            список файлов
   */
  public static void writeAllFiles(Path currentDirectory, List<String> files) {
    String path = FileHelper.combinePath(currentDirectory.toString(), "all.txt").toString();

    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
      for (String file : files) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
          while (bufferedReader.ready()) {
            bufferedWriter.write(bufferedReader.readLine());
            bufferedWriter.newLine();
          }
        }
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}
