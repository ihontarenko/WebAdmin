package io.startform.common.pebble.function;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssetFunction implements Function {

    public static final String FUNCTION_NAME = "asset";
    public static final String ARGUMENT_NAME = "path";

    private ResourceUrlProvider provider;
    private HttpServletRequest request;

    public AssetFunction(HttpServletRequest request, ResourceUrlProvider provider) {
        this.provider = provider;
        this.request = request;
    }

    @Override
    public Object execute(Map<String, Object> arguments, PebbleTemplate self,
                          EvaluationContext context, int lineNumber) {
        String path = (String) arguments.get(ARGUMENT_NAME);

        if (path != null) {
            path = provider.getForLookupPath(path.startsWith("/") ? path : "/" + path);
        } else {
            path = "/UNDEFINED";
        }

        return request.getContextPath() + path;
    }

    @Override
    public List<String> getArgumentNames() {
        return new ArrayList<>() {{
            add(ARGUMENT_NAME);
        }};
    }

}
