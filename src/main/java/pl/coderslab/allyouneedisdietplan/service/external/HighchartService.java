package pl.coderslab.allyouneedisdietplan.service.external;

import org.json.JSONObject;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface HighchartService {

  JSONObject getWeightDataJson(User user);
}
