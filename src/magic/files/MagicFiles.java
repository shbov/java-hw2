package magic.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Класс для работы с файлами.
 */
public final class MagicFiles {

  private final Path currentDirectory;
  private final List<Node> files;

  public MagicFiles(String currentDirectory) {
    this.currentDirectory = FileHelper.combinePath(currentDirectory, "data");
    this.files = new ArrayList<>();
  }

  /**
   * Текущая директория.
   *
   * @return текущая директория
   */
  public Path getCurrentDirectory() {
    return currentDirectory;
  }

  /**
   * Запуск программы.
   */
  public void run() {
    Stream<Path> all;

    try {
      all = FileHelper.findAllFiles(getCurrentDirectory());
    } catch (IOException e) {
      System.out.printf(e.getMessage());
      return;
    }

    all.forEach(this::addFile);
    Map<String, ArrayList<String>> hashMap = MagicHelper.convertToHashMap(files);
    FileHelper.writeAllFiles(getCurrentDirectory().getParent(),
        MagicHelper.getOrderedList(hashMap));
  }

  /**
   * Добавляет файл в список.
   *
   * @param file файл
   */
  private void addFile(Path file) {
    files.add(new Node(file, getCurrentDirectory()));
  }
}
