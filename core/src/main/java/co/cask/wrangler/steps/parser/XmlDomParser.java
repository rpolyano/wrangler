/*
 * Copyright © 2016 Cask Data, Inc.
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

package co.cask.wrangler.steps.parser;

import co.cask.wrangler.api.AbstractStep;
import co.cask.wrangler.api.PipelineContext;
import co.cask.wrangler.api.Record;
import co.cask.wrangler.api.StepException;
import co.cask.wrangler.api.Usage;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * A XML Parser.
 */
@Usage(
  directive = "parse-as-xml-dom",
  usage = "parse-as-xml-dom <column>",
  description = "Parses a column as XML Dom."
)
public class XmlDomParser extends AbstractStep {
  // Column within the input row that needs to be parsed as CSV
  private String col;
  private DocumentBuilderFactory factory;


  public XmlDomParser(int lineno, String detail, String col) {
    super(lineno, detail);
    this.col = col;
    factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
  }

  /**
   * Parses a give column in a {@link Record} as a XML.
   *
   * @param records Input {@link Record} to be wrangled by this step.
   * @param context Specifies the context of the pipeline.
   * @return New Record containing multiple columns based on CSV parsing.
   */
  @Override
  public List<Record> execute(List<Record> records, PipelineContext context)
    throws StepException {

    for (Record record : records) {
      int idx = record.find(col);
      if (idx == -1) {
        continue; // didn't find the column.
      }

      Object object = record.getValue(idx);
      if (object == null) {
        continue; // If it's null keep it as null.
      }

      if (object instanceof String) {
        String xml = (String) object;
        DocumentBuilder builder = null;
        try {
          builder = factory.newDocumentBuilder();
          Document doc = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
          record.setValue(idx, doc);
        } catch (ParserConfigurationException | SAXException e) {
          throw new StepException(
            String.format("Unable to parse XML Dom. %s", e.getMessage())
          );
        } catch (UnsupportedEncodingException e) {
          throw new StepException(
            String.format("Failed parsing XML as encoding is not valid. %s", e.getMessage())
          );
        } catch (IOException e) {
          throw new StepException(
            String.format("Failed to read XML. %s", e.getMessage())
          );
        }
      }
    }
    return records;
  }
}
