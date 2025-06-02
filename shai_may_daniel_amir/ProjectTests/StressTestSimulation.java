import java.util.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StressTestSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:9090")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,/;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en,he;q=0.9,en-GB;q=0.8,en-US;q=0.7")
    .contentTypeHeader("application/x-www-form-urlencoded")
    .doNotTrackHeader("1")
    .originHeader("http://localhost:9090")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");

  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Cache-Control", "max-age=0"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "none"),
    Map.entry("Sec-Fetch-User", "?1")
  );

  private ScenarioBuilder scn = scenario("StressTestScenario")
    .exec(
      http("Homepage Request")
        .get("/")
        .headers(headers_0)
        .check(status().is(200))
    );

  {
    setUp(
      scn.injectOpen(
        rampUsersPerSec(1).to(250).during(300) // Ramps from 1 to 250 req/sec in 5 minutes
      )
    ).protocols(httpProtocol);
  }
}