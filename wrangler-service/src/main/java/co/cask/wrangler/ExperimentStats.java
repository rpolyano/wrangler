package co.cask.wrangler;

import java.io.Serializable;

/**
 *
 */
public class ExperimentStats implements Serializable {
  private static final long serialVersionUID = -2102253929159434695L;
  private String val;
  private Double total;
  private Double count;
  private Double min;
  private Double max;

  public ExperimentStats(String val) {
    this(val, null, null, null, null);
  }

  public ExperimentStats(Double total) {
    this.total = total;
  }

  public ExperimentStats(String val, Double total, Double count, Double min, Double max) {
    this.val = val;
    this.total = total;
    this.count = count;
    this.min = min;
    this.max = max;
  }

  public String getVal() {
    return val;
  }

  public Double getTotal() {
    return total;
  }

  public Double getCount() {
    return count;
  }

  public Double getMin() {
    return min;
  }

  public Double getMax() {
    return max;
  }
}
