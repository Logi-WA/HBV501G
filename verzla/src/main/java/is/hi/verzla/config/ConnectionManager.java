package is.hi.verzla.config;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class ConnectionManager {

  private final DataSource dataSource;

  public ConnectionManager(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @PreDestroy
  public void closeConnection() {
    try {
      if (dataSource != null) {
        dataSource.getConnection().close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
