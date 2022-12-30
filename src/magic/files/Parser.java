package magic.files;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Parser {

  private Parser() {
  }

  /**
   * Парсит файл и возвращает список зависимостей.
   * @param path путь к файлу
   * @param root корневая директория
   * @return список зависимостей
   */
  public static List<Path> parseDependencies(Path path, String root) {
    List<String> content;
    List<Path> dependencies = new ArrayList<>();

    try {
      content = Files.readAllLines(path, Charset.defaultCharset());
    } catch (IOException e) {
      System.out.printf(e.getMessage());
      return dependencies;
    }

    for (String line : content) {
      parse(line, dependencies, root);
    }

    return dependencies;
  }

  /**
   * Парсит строку.
   * @param line строка
   * @param dependencies список зависимостей
   * @param directory директория
   */
  private static void parse(String line, List<Path> dependencies, String directory) {
    if ("".equals(line) || line == null) {
      return;
    }

    if (!line.startsWith("require")) {
      return;
    }

    String path = line.replaceFirst("require ", "").replaceAll("[\"'“”‘’«»]", "");
    dependencies.add(FileHelper.combinePath(directory, path));
  }
}
