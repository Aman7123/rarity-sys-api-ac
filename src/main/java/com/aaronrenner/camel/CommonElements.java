package com.aaronrenner.camel;

import org.springframework.stereotype.Component;
import com.ms3_inc.tavros.extensions.rest.*;
import static com.ms3_inc.tavros.extensions.rest.OperationResult.Level;
import com.ms3_inc.tavros.extensions.rest.OperationResult.Message;
import com.ms3_inc.tavros.extensions.rest.exception.NotFoundException;

@Component
public class CommonElements extends BaseRestRouteBuilder {

	/** Common error builders */
    public static Message throwBadRequest(Object message, Object diagnostics) {
    	if(message instanceof String && diagnostics instanceof String) {
        	return new Message(Level.ERROR, "BAD_REQUEST", "400", 
	        				message.toString(), 
	        				diagnostics.toString()
        				);
    	}
    	if(message instanceof String) {
        	return new Message(Level.ERROR, "BAD_REQUEST", "400", 
		    				message.toString(), 
		    				null
						);
    	}
    	return new Message(Level.ERROR, "BAD_REQUEST", "400", 
						"The server could not process the data!", 
						diagnostics.toString()
					);
    }
    
    public static OperationResult.Message throwNotFound(String message) {
    	if(message != "null") {
        	return OperationResult.MessageBuilder
    		        .error("NOT_FOUND", message)
    		        .withCode("404")
    		        .build();
    	}
    	return OperationResult.MessageBuilder
		        .error("NOT_FOUND", "The server could not find the requested resource!")
		        .withCode("404")
		        .build();
    }
    
    public static OperationResult.Message throwServerError(Object message, Object diagnostics) {
    	if(message instanceof String && diagnostics instanceof String) {
        	return new Message(Level.ERROR, "INTERNAL_SERVER_ERROR", "500", 
		    				message.toString(), 
		    				diagnostics.toString()
						);
    	}
    	if(message instanceof String) {
        	return new Message(Level.ERROR, "INTERNAL_SERVER_ERROR", "500", 
		    				message.toString(), 
		    				null
						); 		
    	}
    	return new Message(Level.ERROR, "INTERNAL_SERVER_ERROR", "500", 
						"The server could not process the request!", 
						null
					);
    }
    
    /** Common routes for utilities */
    @Override
    public void configure() throws  Exception {
        super.configure();

        // Validate SQL response of a GET by UUID operation
        // No special values are returned to validate a row is returned so we check if returned body exists
        from(direct("validate-sql-get-operation"))
                .routeId("direct:validate-sql-get-operation")

                // Validate DB returned at least one record or throw 404 - Not Found
                .choice()
                    .when(simple("${body['#result-set-1'].size} == 0"))
                    	.throwException(new NotFoundException(throwNotFound("null")))
                .end()
        ;
        
        // Validate SQL response of a DELETE by UUID operation
        // We will check if the update count was 1 "Success" or 0 "Error"
        from(direct("validate-sql-update-operation"))
                .routeId("direct:validate-sql-update-operation")

                // Validate DB returned at least one record or throw 404 - Not Found
                .choice()
                    .when(simple("${body['#update-count-1']} == 0"))
                    	.throwException(new NotFoundException(throwNotFound("null")))
                .end()
        ;
        
    }
}