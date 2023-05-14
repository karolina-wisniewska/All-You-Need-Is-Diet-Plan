package pl.coderslab.allyouneedisdietplan.model;

import lombok.RequiredArgsConstructor;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;

@RequiredArgsConstructor
public class Query implements UrlElement {
  private final String query;
  @Override
  public String getUrlPart() {
    return "&q=" + query;
  }
}
