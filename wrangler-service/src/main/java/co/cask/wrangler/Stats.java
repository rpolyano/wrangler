package co.cask.wrangler;

/**
 *
 */
public class Stats {
  private int zipcode;
  private long population;
  private double medianAge;
  private long males;
  private long females;
  private long houseHolds;
  private double avgHouseHolds;

  public int getZipcode() {
    return zipcode;
  }

  public void setZipcode(int zipcode) {
    this.zipcode = zipcode;
  }

  public long getPopulation() {
    return population;
  }

  public void setPopulation(long population) {
    this.population = population;
  }

  public double getMedianAge() {
    return medianAge;
  }

  public void setMedianAge(double medianAge) {
    this.medianAge = medianAge;
  }

  public long getMales() {
    return males;
  }

  public void setMales(long males) {
    this.males = males;
  }

  public long getFemales() {
    return females;
  }

  public void setFemales(long females) {
    this.females = females;
  }

  public long getHouseHolds() {
    return houseHolds;
  }

  public void setHouseHolds(long houseHolds) {
    this.houseHolds = houseHolds;
  }

  public double getAvgHouseHolds() {
    return avgHouseHolds;
  }

  public void setAvgHouseHolds(double avgHouseHolds) {
    this.avgHouseHolds = avgHouseHolds;
  }
}
