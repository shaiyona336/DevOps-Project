import java.util.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.time.Duration;

public class LoadTestSimulation extends Simulation {

  HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:9090")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,…")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en,he;q=0.9,…")
    .contentTypeHeader("application/x-www-form-urlencoded")
    .doNotTrackHeader("1")
    .originHeader("http://localhost:9090")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (…)");

  private Map<CharSequence,String> headers = Map.ofEntries(
    Map.entry("Cache-Control","max-age=0"),
    Map.entry("Sec-Fetch-Dest","document"),
    Map.entry("Sec-Fetch-Mode","navigate"),
    Map.entry("Sec-Fetch-Site","same-origin"),
    Map.entry("Sec-Fetch-User","?1"),
    Map.entry("sec-ch-ua","Chromium\";v=\"…\""),
    Map.entry("sec-ch-ua-mobile","?0"),
    Map.entry("sec-ch-ua-platform","Windows")
  );

  ScenarioBuilder scn = scenario("FullCycleLoad")
    .exec(
      http("Homepage")
        .get("/")
        .headers(headers)
        .check(status().is(200))
    );

  {
    setUp(
      scn.injectOpen(
        rampUsersPerSec(1).to(100).during(Duration.ofMinutes(2)),
        constantUsersPerSec(100).during(Duration.ofMinutes(5)),
        rampUsersPerSec(100).to(0).during(Duration.ofMinutes(2))
      )
    ).protocols(httpProtocol);
  }
}