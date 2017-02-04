/*
 * Copyright © 2017 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.wrangler;

import co.cask.wrangler.api.Directives;
import co.cask.wrangler.api.Record;
import co.cask.wrangler.api.Step;
import co.cask.wrangler.internal.TextDirectives;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Main Application for performance testing the wrangler.
 */
public class WranglerPerfMain {
  private static final Logger LOG = LoggerFactory.getLogger(WranglerPerfMain.class);
  static final MetricRegistry registry = new MetricRegistry();

  public void run(String[] args) throws Exception {
    final CsvReporter reporter = CsvReporter.forRegistry(registry)
      .formatFor(Locale.US)
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .build(new File(args[0]));
    reporter.start(10, TimeUnit.SECONDS);

    // Read the directives in
    List<String> d = Files.readAllLines(Paths.get(args[1]), StandardCharsets.UTF_8);

    String[] directives = new String[d.size()];
    Directives specification = new TextDirectives(d.toArray(directives));
    List<Step> steps = specification.getSteps();

    List<Record> records = new ArrayList<>();
    try(BufferedReader br = new BufferedReader(new FileReader(args[2]))) {
      String line;
      int i = 1;
      Meter recordMeasure = registry.meter("record-measure");
      while ((line = br.readLine()) != null) {
        try {
          records.clear();
          records.add(new Record("demo", line));
          for (Step step : steps) {
            records = step.execute(records, null);
          }
          recordMeasure.mark();
        } catch (Exception e) {
          System.out.println("Error line no " + (i+1));
        }
        i++;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    new WranglerPerfMain().run(args);
  }

}
