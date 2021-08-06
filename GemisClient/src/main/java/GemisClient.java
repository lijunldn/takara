import com.fasterxml.jackson.core.JsonProcessingException;
import me.takara.shared.Env;
import me.takara.shared.entities.Bond;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

public class GemisClient {

    public Bond get(long id) throws IOException {
        URL url = new URL(Env.GEMIS_BOND_URI.toString() + "/" + id);
        String msg = exeGet(url);
        return Bond.of(msg);
    }

    public String hello() throws IOException {
        return exeGet(Env.GEMIS_BOND_URI.toURL());
    }

    String exeGet(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.connect();
        InputStream inputStream = con.getInputStream();
        byte[] data = new byte[1024];
        StringBuffer sb = new StringBuffer();
        while (inputStream.read(data) != -1) {
            String s = new String(data, Charset.forName("utf-8"));
            sb.append(s);
        }
        String message = sb.toString();
        inputStream.close();
        con.disconnect();
        return message;
    }
}
