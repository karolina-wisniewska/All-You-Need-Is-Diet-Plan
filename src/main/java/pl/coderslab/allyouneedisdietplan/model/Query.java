package pl.coderslab.allyouneedisdietplan.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;

@RequiredArgsConstructor
@Data
public class Query implements UrlElement {
  private final String query;
  @Override
  public String getUrlPart() {
    return "&q=" + query;
  }
}
