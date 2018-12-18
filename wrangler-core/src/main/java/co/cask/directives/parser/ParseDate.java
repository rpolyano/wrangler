/*
 *  Copyright © 2017-2018 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package co.cask.directives.parser;

import co.cask.cdap.api.annotation.Description;
import co.cask.cdap.api.annotation.Name;
import co.cask.cdap.api.annotation.Plugin;
import co.cask.wrangler.api.Arguments;
import co.cask.wrangler.api.Directive;
import co.cask.wrangler.api.DirectiveExecutionException;
import co.cask.wrangler.api.DirectiveParseException;
import co.cask.wrangler.api.ErrorRowException;
import co.cask.wrangler.api.ExecutorContext;
import co.cask.wrangler.api.Optional;
import co.cask.wrangler.api.Row;
import co.cask.wrangler.api.annotations.Categories;
import co.cask.wrangler.api.parser.ColumnName;
import co.cask.wrangler.api.parser.Text;
import co.cask.wrangler.api.parser.TokenType;
import co.cask.wrangler.api.parser.UsageDefinition;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A Executor to parse date.
 */
@Plugin(type = Directive.TYPE)
@Name("parse-as-date")
@Categories(categories = { "parser", "date"})
@Description("Parses column values as dates using natural language processing and " +
  "automatically identifying the format (expensive in terms of time consumed).")
public class ParseDate implements Directive {
  public static final String NAME = "parse-as-date";
  private String column;
  private TimeZone timezone;

  @Override
  public UsageDefinition define() {
    UsageDefinition.Builder builder = UsageDefinition.builder(NAME);
    builder.define("column", TokenType.COLUMN_NAME);
    builder.define("timezone", TokenType.TEXT, Optional.TRUE);
    return builder.build();
  }

  @Override
  public void initialize(Arguments args) throws DirectiveParseException {
    this.column = ((ColumnName) args.value("column")).value();
    if (args.contains("timezone")) {
      this.timezone = TimeZone.getTimeZone(((Text) args.value("timezone")).value());
    } else {
      this.timezone = TimeZone.getTimeZone("UTC");
    }
  }

  @Override
  public void destroy() {
    // no-op
  }

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext context)
    throws DirectiveExecutionException, ErrorRowException {
    for (Row row : rows) {
      int idx = row.find(column);
      if (idx != -1) {
        Object object = row.getValue(idx);
        // If the data in the cell is null or is already of date format, then
        // continue to next row.
        if (object == null || object instanceof ZonedDateTime) {
          continue;
        }
        if (object instanceof String) {
          Parser parser = new Parser(timezone);
          List<DateGroup> groups = parser.parse((String) object);
          int i = 1;
          for (DateGroup group : groups) {
            List<Date> dates = group.getDates();
            for (Date date : dates) {
              row.add(String.format("%s_%d", column, i), date.toInstant().atZone(timezone.toZoneId()));
            }
            i++;
          }
        } else {
          throw new ErrorRowException(
            String.format("%s : Invalid type '%s' of column '%s'. Should be of type String.", toString(),
                          object.getClass().getName(), column), 1);
        }
      }
    }
    return rows;
  }
}
