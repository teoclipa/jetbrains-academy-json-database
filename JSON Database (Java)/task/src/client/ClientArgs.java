package client;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ClientArgs {

    @Parameter(names = "-t", description = "Type")
    private String type;

    @Parameter(names = "-k", description = "Key", converter = JsonElementConverter.class)
    private JsonElement key;

    @Parameter(names = "-v", description = "Value")
    private String value;

    @Parameter(names = "-in", description = "Input file")
    private String inFile;

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getInFile() {
        return inFile;
    }

    static class JsonElementConverter implements IStringConverter<JsonElement> {
        @Override
        public JsonElement convert(String value) {
            return JsonParser.parseString(value);
        }
    }
}
