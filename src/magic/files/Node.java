package magic.files;

import java.nio.file.Path;
import java.util.List;

public final class Node {

  private final Path path;
  private final List<Path> dependencies;

  public Node(Path path, Path root) {
    this.path = path;
    this.dependencies = Parser.parseDependencies(path, root.toString());
  }

  /**
   * Возвращает путь к файлу.
   * @return путь к файлу
   */
  public Path getPath() {
    return path;
  }

  /**
   * Возвращает список зависимостей.
   * @return список зависимостей
   */
  public List<Path> getDependencies() {
    return dependencies;
  }

  /**
   * Переопределение toString.
   * @return строковое представление
   */
  @Override
  public String toString() {
    return String.format("%s. Depends on %s", path.getFileName(), getDependencies().toString());
  }
}

