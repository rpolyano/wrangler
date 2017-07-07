package co.cask.wrangler.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class RangeMap<K, V> {
  private final TreeMap<K, V> rangeMap;

  public RangeMap() {
    this.rangeMap = new TreeMap<>();
  }

  public void put(K lowerbound, K upperbound, V value) {
    rangeMap.put(lowerbound, value);
    rangeMap.put(upperbound, null);
  }

  public V get(K key) {
    Map.Entry<K, V> e = rangeMap.floorEntry(key);
    if (e != null && e.getValue() == null) {
      e = rangeMap.lowerEntry(key);
    }
    return e == null ? null : e.getValue();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");

    Iterator<Map.Entry<K, V>> iterator = rangeMap.entrySet().iterator();

    while (iterator.hasNext()) {
      Map.Entry<K, V> lower = iterator.next();
      sb.append("[").append(lower.getKey()).append("..");
      Map.Entry<K, V> upper = iterator.next();
      sb.append(upper.getKey()).append("]=").append(lower.getValue());
      if (iterator.hasNext()) {
        sb.append(", ");
      }
    }

    sb.append("]");

    return sb.toString();
  }
}
