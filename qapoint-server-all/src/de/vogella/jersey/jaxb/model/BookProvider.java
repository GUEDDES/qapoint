package de.vogella.jersey.jaxb.model;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

@javax.ws.rs.ext.Provider
public class BookProvider implements MessageBodyWriter<de.vogella.jersey.jaxb.model.Book> {

	@Override
	public long getSize(Book arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
			MediaType arg4) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeTo(Book arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
			MediaType arg4, MultivaluedMap<String, Object> arg5,
			OutputStream arg6) throws IOException, WebApplicationException {
		// TODO Auto-generated method stub
		
	}




}

