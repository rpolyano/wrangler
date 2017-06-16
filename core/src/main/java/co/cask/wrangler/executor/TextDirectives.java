/*
 * Copyright © 2016-2017 Cask Data, Inc.
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

package co.cask.wrangler.executor;

import co.cask.wrangler.api.AbstractStep;
import co.cask.wrangler.api.DirectiveParseException;
import co.cask.wrangler.api.Directives;
import co.cask.wrangler.api.Step;
import co.cask.wrangler.api.Usage;
import co.cask.wrangler.grammar.GeneralDirectiveParser;
import co.cask.wrangler.grammar.NodeValue;
import co.cask.wrangler.steps.IncrementTransientVariable;
import co.cask.wrangler.steps.SetTransientVariable;
import co.cask.wrangler.steps.column.ChangeColCaseNames;
import co.cask.wrangler.steps.column.CleanseColumnNames;
import co.cask.wrangler.steps.column.Columns;
import co.cask.wrangler.steps.column.ColumnsReplace;
import co.cask.wrangler.steps.column.Copy;
import co.cask.wrangler.steps.column.Drop;
import co.cask.wrangler.steps.column.Keep;
import co.cask.wrangler.steps.column.Merge;
import co.cask.wrangler.steps.column.Rename;
import co.cask.wrangler.steps.column.SetType;
import co.cask.wrangler.steps.column.SplitToColumns;
import co.cask.wrangler.steps.column.Swap;
import co.cask.wrangler.steps.date.DiffDate;
import co.cask.wrangler.steps.date.FormatDate;
import co.cask.wrangler.steps.language.SetCharset;
import co.cask.wrangler.steps.nlp.Stemming;
import co.cask.wrangler.steps.parser.CsvParser;
import co.cask.wrangler.steps.parser.FixedLengthParser;
import co.cask.wrangler.steps.parser.HL7Parser;
import co.cask.wrangler.steps.parser.JsParser;
import co.cask.wrangler.steps.parser.JsPath;
import co.cask.wrangler.steps.parser.ParseAvro;
import co.cask.wrangler.steps.parser.ParseAvroFile;
import co.cask.wrangler.steps.parser.ParseDate;
import co.cask.wrangler.steps.parser.ParseExcel;
import co.cask.wrangler.steps.parser.ParseLog;
import co.cask.wrangler.steps.parser.ParseProtobuf;
import co.cask.wrangler.steps.parser.ParseSimpleDate;
import co.cask.wrangler.steps.parser.XmlParser;
import co.cask.wrangler.steps.parser.XmlToJson;
import co.cask.wrangler.steps.row.Fail;
import co.cask.wrangler.steps.row.Flatten;
import co.cask.wrangler.steps.row.RecordConditionFilter;
import co.cask.wrangler.steps.row.RecordMissingOrNullFilter;
import co.cask.wrangler.steps.row.RecordRegexFilter;
import co.cask.wrangler.steps.row.SendToError;
import co.cask.wrangler.steps.row.SetRecordDelimiter;
import co.cask.wrangler.steps.row.SplitToRows;

import co.cask.wrangler.steps.transformation.CatalogLookup;
import co.cask.wrangler.steps.transformation.CharacterCut;
import co.cask.wrangler.steps.transformation.Decode;
import co.cask.wrangler.steps.transformation.Encode;
import co.cask.wrangler.steps.transformation.Expression;
import co.cask.wrangler.steps.transformation.ExtractRegexGroups;
import co.cask.wrangler.steps.transformation.FillNullOrEmpty;
import co.cask.wrangler.steps.transformation.FindAndReplace;
import co.cask.wrangler.steps.transformation.GenerateUUID;
import co.cask.wrangler.steps.transformation.IndexSplit;
import co.cask.wrangler.steps.transformation.InvokeHttp;
import co.cask.wrangler.steps.transformation.Lower;
import co.cask.wrangler.steps.transformation.MaskNumber;
import co.cask.wrangler.steps.transformation.MaskShuffle;
import co.cask.wrangler.steps.transformation.MessageHash;
import co.cask.wrangler.steps.transformation.Quantization;
import co.cask.wrangler.steps.transformation.SetColumn;
import co.cask.wrangler.steps.transformation.Split;
import co.cask.wrangler.steps.transformation.SplitEmail;
import co.cask.wrangler.steps.transformation.SplitURL;
import co.cask.wrangler.steps.transformation.TableLookup;
import co.cask.wrangler.steps.transformation.TextDistanceMeasure;
import co.cask.wrangler.steps.transformation.TextMetricMeasure;
import co.cask.wrangler.steps.transformation.TitleCase;
import co.cask.wrangler.steps.transformation.Upper;
import co.cask.wrangler.steps.transformation.UrlEncode;
import co.cask.wrangler.steps.transformation.XPathArrayElement;
import co.cask.wrangler.steps.transformation.XPathElement;
import co.cask.wrangler.steps.transformation.Trim;
import co.cask.wrangler.steps.transformation.LeftTrim;
import co.cask.wrangler.steps.transformation.RightTrim;


import co.cask.wrangler.steps.writer.WriteAsCSV;
import co.cask.wrangler.steps.writer.WriteAsJsonMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Parses the DSL into specification containing stepRegistry for wrangling.
 *
 * Following are some of the commands and format that {@link TextDirectives}
 * will handle.
 */
public class TextDirectives implements Directives {
  private static final Logger LOG = LoggerFactory.getLogger(TextDirectives.class);

  // directives for wrangling.
  private String[] directives;

  // Usage Registry
  private final UsageRegistry usageRegistry = new UsageRegistry();

  public TextDirectives(String[] directives) {
    this.directives = directives;
  }

  public TextDirectives(String directives) {
    this(directives.split("\n"));
  }

  public TextDirectives(List<String> directives) {
    this(directives.toArray(new String[directives.size()]));
  }

  /**
   * Parses the DSL to generate a sequence of stepRegistry to be executed by {@link co.cask.wrangler.api.Pipeline}.
   *
   * The transformation parsing here needs a better solution. It has many limitations and having different way would
   * allow us to provide much more advanced semantics for directives.
   *
   * @return List of stepRegistry to be executed.
   * @throws ParseException
   */
  private List<Step> parse() throws DirectiveParseException {
    List<Step> steps = new ArrayList<>();

    // Split directive by EOL
    int lineno = 1;

    //get all step classes
    //TODO: might be super slow to do this every time type in a directive. Need to do this in pre-process.
    Reflections reflections = new Reflections("co.cask.wrangler.steps");
    Set<Class<? extends AbstractStep>> stepClasses = reflections.getSubTypesOf(AbstractStep.class);

    // Iterate through each directive and create necessary stepRegistry.
    for (String directive : directives) {
      directive = directive.trim();
      if (directive.isEmpty() || directive.startsWith("//") || directive.startsWith("#")) {
        continue;
      }
      StringTokenizer tokenizer = new StringTokenizer(directive, " ");
      String command = tokenizer.nextToken();

      //TODO: use switch for these for now
      switch (command) {
        // parse-as-csv <column> <delimiter> [<header=true/false>]
        case "parse-as-csv": {
          String column = getNextToken(tokenizer, command, "column", lineno);
          String delimStr = getNextToken(tokenizer, command, "delimiter", lineno);
          char delimiter = delimStr.charAt(0);
          if (delimStr.startsWith("\\")) {
            String unescapedStr = StringEscapeUtils.unescapeJava(delimStr);
            if (unescapedStr == null) {
              throw new DirectiveParseException("Invalid delimiter for CSV Parser: " + delimStr);
            }
            delimiter = unescapedStr.charAt(0);
          }

          boolean hasHeader;
          String hasHeaderLinesOpt = getNextToken(tokenizer, "\n", command, "true|false", lineno, true);
          if (hasHeaderLinesOpt == null || hasHeaderLinesOpt.equalsIgnoreCase("false")) {
            hasHeader = false;
          } else {
            hasHeader = true;
          }
          CsvParser.Options opt = new CsvParser.Options(delimiter, true);
          steps.add(new CsvParser(lineno, directive, opt, column, hasHeader));
        }
        break;

        // parse-as-json <column> [depth]
        case "parse-as-json": {
          String column = getNextToken(tokenizer, command, "column", lineno);
          String depthOpt = getNextToken(tokenizer, "\n", command, "depth", lineno, true);
          int depth = Integer.MAX_VALUE;
          if (depthOpt != null && !depthOpt.isEmpty()) {
            try {
              depth = Integer.parseInt(depthOpt);
            } catch (NumberFormatException e) {
              throw new DirectiveParseException(
                String.format("Depth '%s' specified is not a valid number.", depthOpt)
              );
            }
          }
          steps.add(new JsParser(lineno, directive, column, depth));
        }
        break;

        // parse-as-avro <column> <schema-id> <json|binary> [version]
        case "parse-as-avro": {
          String column = getNextToken(tokenizer, command, "column", lineno);
          String schemaId = getNextToken(tokenizer, command, "schema-id", lineno);
          String type = getNextToken(tokenizer, command, "type", lineno);
          if (!"json".equalsIgnoreCase(type) && !"binary".equalsIgnoreCase(type)) {
            throw new DirectiveParseException(
              String.format("Parsing AVRO can be either of type 'json' or 'binary'")
            );
          }
          String versionOpt = getNextToken(tokenizer, "\n", command, "depth", lineno, true);
          int version = -1;
          if (versionOpt != null && !versionOpt.isEmpty()) {
            try {
              version = Integer.parseInt(versionOpt);
            } catch (NumberFormatException e) {
              throw new DirectiveParseException(
                String.format("Version '%s' specified is not a valid number.", versionOpt)
              );
            }
          }
          steps.add(new ParseAvro(lineno, directive, column, schemaId, type, version));
        }
        break;

        // parse-as-protobuf <column> <schema-id> <record-name> [version]
        case "parse-as-protobuf": {
          String column = getNextToken(tokenizer, command, "column", lineno);
          String schemaId = getNextToken(tokenizer, command, "schema-id", lineno);
          String recordName = getNextToken(tokenizer, command, "record-name", lineno);
          String versionOpt = getNextToken(tokenizer, "\n", command, "depth", lineno, true);
          int version = -1;
          if (versionOpt != null && !versionOpt.isEmpty()) {
            try {
              version = Integer.parseInt(versionOpt);
            } catch (NumberFormatException e) {
              throw new DirectiveParseException(
                String.format("Version '%s' specified is not a valid number.", versionOpt)
              );
            }
          }
          steps.add(new ParseProtobuf(lineno, directive, column, schemaId, recordName, version));
        }
        break;

        default: {
          //find class from directive statement
          Class<? extends AbstractStep> directiveClass = null;
          for (Class<? extends AbstractStep> stepClass : stepClasses) {
            //if usage matches, that's the class we want to use
            Usage usage = stepClass.getAnnotation(Usage.class);
            String directiveName = usage.directive();
            if (command.equals(directiveName)) {
              directiveClass = stepClass;
            }
          }
          if (directiveClass != null) {
            //make a instance and add to step list
            String directiveSyntax = directiveClass.getAnnotation(Usage.class).usage();
            //starts with command name
            String[] parameterList = directiveSyntax.split(" ");
            int parameterNumber = parameterList.length - 1;
            //TODO: assume all parameters are strings
            //TODO: some parameters are optional
            Object[] argumentList = new Object[parameterNumber + 2];
            argumentList[0] = lineno;
            argumentList[1] = directive;
            for (int i = 0; i < parameterNumber; i++) {
              String parameterName = parameterList[i + 1];
              String argument = getNextToken(tokenizer, command, parameterName, lineno);
              argumentList[i + 2] = argument;
            }
            try {
              Class[] types = new Class[parameterNumber + 2];
              types[0] = int.class;
              types[1] = String.class;
              for (int i = 2; i < types.length; i++) {
                types[i] = String.class;
              }
              Constructor constructor = directiveClass.getConstructor(types);
              Step step = (Step) constructor.newInstance(argumentList);
              steps.add(step);
            } catch (Exception e) {
              e.printStackTrace();
              throw new DirectiveParseException(
                String.format("Unknown syntax '%s' at line %d", directive, lineno)
              );
            }
          }
          //no matched class found
          else {
            throw new DirectiveParseException(
              String.format("Unknown directive '%s' found in the directive at line %d", command, lineno)
            );
          }
        }
      }
      lineno++;
    }
    return steps;
  }


  // If there are more tokens, then it proceeds with parsing, else throws exception.
  private String getNextToken(StringTokenizer tokenizer, String directive,
                          String field, int lineno) throws DirectiveParseException {
    return getNextToken(tokenizer, null, directive, field, lineno, false);
  }

  private String getNextToken(StringTokenizer tokenizer, String delimiter,
                              String directive, String field, int lineno) throws DirectiveParseException {
    return getNextToken(tokenizer, delimiter, directive, field, lineno, false);
  }

  private String getNextToken(StringTokenizer tokenizer, String delimiter,
                          String directive, String field, int lineno, boolean optional)
    throws DirectiveParseException {
    String value = null;
    if (tokenizer.hasMoreTokens()) {
      if (delimiter == null) {
        value = tokenizer.nextToken().trim();
      } else {
        value = tokenizer.nextToken(delimiter).trim();
      }
    } else {
      if (!optional) {
        String usage = usageRegistry.getUsage(directive);
        throw new DirectiveParseException(
          String.format("Missing field '%s' at line number %d for directive <%s> (usage: %s)",
                        field, lineno, directive, usage)
        );
      }
    }
    return value;
  }

  /**
   * @return List of steps to executed in the order they are specified.
   * @throws ParseException throw in case of parsing exception of specification.
   */
  @Override
  public List<Step> getSteps() throws DirectiveParseException {
    return parse();
  }
}
