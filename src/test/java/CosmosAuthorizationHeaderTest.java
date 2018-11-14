
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class CosmosAuthorizationHeaderTest {

    private static final Logger log = LoggerFactory.getLogger(CosmosAuthorizationHeaderTest.class);

    @Test
    public void testHeader() {

        CosmosAuthorizationHeader cah = new CosmosAuthorizationHeader();

        String verb = "GET";
        String resourceType = "dbs";
        String resourceId = "dbs/ToDoList";
        String date = "Thu, 27 Apr 2017 00:51:12 GMT";
        String masterKey = "dsZQi3KtZmCv1ljt3VNWNm7sQUF1y5rJfC6kv5JiwvW0EndXdDku/dkKBp8/ufDToSxLzR4y+O/0H/t4bQtVNw==";
        String header = "";

        try{
            header = cah.getHeader(verb, resourceType, resourceId, masterKey, date);
        }
        catch(UnsupportedEncodingException uee){
            log.error(uee.getMessage());
            fail();
        }

        assertEquals("type%3Dmaster%26ver%3D1.0%26sig%3Dc09PEVJrgp2uQRkr934kFbTqhByc7TVr3OHyqlu%2Bc%2Bc%3D", header);
    }
}

