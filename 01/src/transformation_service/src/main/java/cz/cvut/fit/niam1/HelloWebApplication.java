package cz.cvut.fit.niam1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@EnableAutoConfiguration
public class HelloWebApplication {

    public String Parser(String message) {
        Pattern dataPattern = Pattern.compile("===\\s*([^=]*)\\s*===", Pattern.MULTILINE);
        Matcher matcher = dataPattern.matcher(message);

        if (matcher.find()) {
            String detailData = matcher.group();
            String result = "{" + "\n";

            // parse id
            Pattern idPattern = Pattern.compile("id:\\s*\"([^\"]*)\"", Pattern.MULTILINE);
            Matcher idMatcher = idPattern.matcher(detailData);
            if (!idMatcher.find()) {
                return "Error: id not found.\n";
            }
            result += String.format("  \"id\": \"%s\"", idMatcher.group(1)) + ",\n";

            // parse location
            Pattern locationPattern = Pattern.compile("Location:\\s*\"([^\"]*)\"", Pattern.MULTILINE);
            Matcher locationMatcher = locationPattern.matcher(detailData);
            if (!locationMatcher.find()) {
                return "Error: Location not found.\n";
            }
            result += String.format("  \"location\": \"%s\"", locationMatcher.group(1)) + ",\n";

            // parse name
            Pattern personPattern = Pattern.compile("Person:\\s*\"(\\w*)\\s*(\\w*)\"", Pattern.MULTILINE);
            Matcher personMatcher = personPattern.matcher(detailData);
            if (!personMatcher.find() || personMatcher.groupCount() != 2) {
                return "Error: Person not found.\n";
            }
            result += "  \"person\": {" + "\n" +
                      String.format("    \"name\": \"%s\"", personMatcher.group(1)) + ",\n" +
                      String.format("    \"surname\": \"%s\"", personMatcher.group(2)) + "\n" +
                      "  }" + "\n" +
                      "}" + "\n";

            return result;
        }
        return "Error: wrong data.\n";
    }

    @PostMapping(value = "/transformation", consumes = "text/plain")
    String transformation(@RequestBody String message) {
        return this.Parser(message);
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloWebApplication.class, args);
    }
}