import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import com.microsoft.azure.documentdb.internal.BaseAuthorizationTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Locale;
import java.net.URLEncoder;

public class CosmosAuthorizationHeader {

    private static final Logger log = LoggerFactory.getLogger(CosmosAuthorizationHeader.class);

    public CosmosAuthorizationHeader(){}

    private static String ENCODING_TYPE = "UTF-8";

    public String getHeader(String verb, String resourceType, String resourceId, String masterKey, String date) throws UnsupportedEncodingException {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-ms-date", date);

        log.info("Creating header for verb {}, resourceType {}, resourceId {}, date {}", verb, resourceType, resourceId, date);

        BaseAuthorizationTokenProvider tokenFactory = new BaseAuthorizationTokenProvider(masterKey);

        String header = tokenFactory.generateKeyAuthorizationSignature(verb, resourceId, resourceType, headers);

        return URLEncoder.encode(header, ENCODING_TYPE);
    }

    public String getHeader(String verb, String resourceType, String resourceId, String masterKey) throws UnsupportedEncodingException{
        String date = getServerTime();
        return getHeader(verb, resourceType, resourceId, masterKey,date);
    }

    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
}
