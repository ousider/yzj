package xn.core.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	
	private static ObjectMapper jacksonMapper = new ObjectMapper();

    static {

	}
	
	public static String objectToJackson(Object obj) throws JsonProcessingException {
		return jacksonMapper.writeValueAsString(obj);
	}

	public static <T> T jacksonToCollection(String src,Class<?> collectionClass, Class<?>... valueType) throws JsonParseException, JsonMappingException, IOException  {
        JavaType javaType = jacksonMapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, valueType);
		jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return jacksonMapper.readValue(src, javaType);
	}

}
