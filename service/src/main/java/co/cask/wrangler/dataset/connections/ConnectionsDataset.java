package co.cask.wrangler.dataset.connections;

import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.DatasetSpecification;
import co.cask.cdap.api.dataset.lib.AbstractDataset;
import co.cask.cdap.api.dataset.module.EmbeddedDataset;
import co.cask.cdap.api.dataset.table.Table;
import co.cask.wrangler.dataset.workspace.WorkspaceDataset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class description here.
 */
public final class ConnectionsDataset extends AbstractDataset {
  private static final Logger LOG = LoggerFactory.getLogger(WorkspaceDataset.class);
  private final Table table;
  private final Gson gson;
  public static final byte[] NAME_COL       = Bytes.toBytes("name");
  public static final byte[] DESC_COL       = Bytes.toBytes("description");
  public static final byte[] TYPE_COL       = Bytes.toBytes("type");
  public static final byte[] CREATED_COL    = Bytes.toBytes("created");
  public static final byte[] UPDATED_COL    = Bytes.toBytes("updated");
  public static final byte[] PROPERTIES_COL = Bytes.toBytes("properties");

  public ConnectionsDataset(DatasetSpecification specification,
                          @EmbeddedDataset("connections") Table table){
    super(specification.getName(), table);
    this.table = table;
    this.gson = new GsonBuilder().create();
  }


}
