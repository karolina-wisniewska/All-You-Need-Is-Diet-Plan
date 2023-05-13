package pl.coderslab.allyouneedisdietplan.service.external.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserParamsService;
import pl.coderslab.allyouneedisdietplan.service.external.HighchartService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HighchartServiceImpl implements HighchartService {

  private final LatestWeightService latestWeightService;
  private final UserParamsService userParamsService;

  @Override
  public JSONObject getWeightDataJson(User user) {
    List<LatestWeight> userWeights = latestWeightService.findByUserOrderByWeightingDateAsc(user);
    Double dreamWeight = userParamsService.findByUser(user)
            .getDreamWeight();
    JSONArray jsonWeightingDate = new JSONArray();
    JSONArray jsonWeight = new JSONArray();
    JSONObject json = new JSONObject();
    userWeights.forEach(weight -> {
      jsonWeightingDate.put(weight.getWeightingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
      jsonWeight.put(weight.getWeight());
    });
    json.put("weightingDate", jsonWeightingDate);
    json.put("weight", jsonWeight);
    json.put("dreamWeight", dreamWeight);
    return json;
  }
}
