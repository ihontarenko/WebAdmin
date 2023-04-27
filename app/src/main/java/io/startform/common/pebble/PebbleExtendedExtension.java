package io.startform.common.pebble;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import io.startform.common.pebble.function.AssetFunction;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PebbleExtendedExtension extends AbstractExtension {

    private ResourceUrlProvider provider;
    private HttpServletRequest request;

    public PebbleExtendedExtension(HttpServletRequest request, ResourceUrlProvider provider) {
        this.provider = provider;
        this.request = request;
    }

    @Override
    public Map<String, Function> getFunctions() {
        return new HashMap<>() {{
           put(AssetFunction.FUNCTION_NAME, new AssetFunction(request, provider));
        }};
    }

}
