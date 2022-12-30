package magic.files;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MagicHelper {

  private MagicHelper() {
  }

  /**
   * Проверяет, есть ли циклы в графе.
   * @param hashMap граф
   * @return true, если есть циклы; false, если нет
   */
  public static boolean isCorrect(Map<String, ArrayList<String>> hashMap) {
    for (String x : hashMap.keySet()) {
      for (String y : hashMap.get(x)) {
        if (hashMap.containsKey(y) && hashMap.get(y).contains(x)) {
          System.out.println("Найден циклический путь: " + x + " -> " + y);
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Конвертирует список в HashMap.
   *
   * @param list список
   * @return HashMap
   */
  public static Map<String, ArrayList<String>> convertToHashMap(List<Node> list) {
    HashMap<String, ArrayList<String>> hashMap = new HashMap<>();

    for (Node node : list) {
      File file = node.getPath().toFile();
      ArrayList<String> dependencies = new ArrayList<>();

      for (Path path : node.getDependencies()) {
        dependencies.add(path.toFile().getPath());
      }

      hashMap.put(file.getPath(), dependencies);
    }

    return hashMap;
  }

  /**
   * Сортировка списка.
   *
   * @param hashMap список
   * @return отсортированный список
   */
  static ArrayList<String> getOrderedList(Map<String, ArrayList<String>> hashMap) {
    ArrayList<String> result = new ArrayList<>(hashMap.keySet());

    if (!isCorrect(hashMap)) {
      return new ArrayList<>();
    }

    result.sort((String a, String b) -> {
      if (hashMap.get(a).contains(b)) {
        return 1;
      }

      if (hashMap.get(b).contains(a)) {
        return -1;
      }

      return 0;
    });

    return result;
  }
}
