import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Класс для работы с файлами.
 */
public final class MagicFiles {

  private final String currentDirectory;
  private final ArrayList<File> files = new ArrayList<>();

  public MagicFiles(String currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public String getCurrentDirectory() {
    return currentDirectory;
  }

  /**
   * Запуск программы.
   */
  public void run() {
    Stream<Path> all;
    try {
      all = findAllFiles();
    } catch (IOException e) {
      System.out.printf(e.getMessage());
      return;
    }

    all.forEach(this::addFile);
    sortFiles();
    writeAllFiles();
  }

  /**
   * Сортирует файлы
   */
  private void sortFiles() {
    files.sort(Comparator.comparing(File::getName));
  }

  /**
   * Объединяет все файлы в один all.txt.
   */
  private void writeAllFiles() {
    String path = FileHelper.combinePath(currentDirectory, "all.txt").toString();

    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
      for (File file : files) {
        try (BufferedReader bufferedReader = new BufferedReader(
            new FileReader(file.toString()))) {
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

  /**
   * Добавляет файл в список.
   *
   * @param file файл
   */
  private void addFile(Path file) {
    files.add(file.toFile());
  }

  /**
   * Находит все файлы в директории.
   *
   * @return поток путей к файлам
   * @throws IOException если что-то пошло не так
   */
  private Stream<Path> findAllFiles() throws IOException {
    return Files.find(
        Paths.get(FileHelper.combinePath(getCurrentDirectory(), "data").toString()),
        Integer.MAX_VALUE,
        (filePath, fileAttr) -> fileAttr.isRegularFile()
    );
  }
}
