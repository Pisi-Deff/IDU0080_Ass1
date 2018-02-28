package ee.eerikmagi.ttu.idu0080.ass1.app.context;

import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	private ObjectMapper mapper;
	
	public ObjectMapperProvider() {
		this.mapper = createMapper();
	}
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}
	
	private static ObjectMapper createMapper() {
		ObjectMapper mapper = new ObjectMapper()
		   .registerModule(new ParameterNamesModule())
		   .registerModule(new Jdk8Module())
		   .registerModule(new JavaTimeModule())
		   .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper;
	}

}
