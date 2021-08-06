import me.takara.shared.Env;
import me.takara.shared.entities.Bond;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class GemisClient {

    public Bond get(long id) throws IOException {

        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(Env.GEMIS_BOND_URI).path("get").path("" + id);
        var resp = target.request().accept(MediaType.APPLICATION_JSON).get();
        return resp.readEntity(Bond.class);
//        URL url = new URL(Env.GEMIS_BOND_URI.toString() + "/get/" + id);
//        String msg = exeGet(url);
//        return Bond.of(msg);
    }

    public String hello() throws IOException {

        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(Env.GEMIS_BOND_URI);
        return target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

//        return exeGet(Env.GEMIS_BOND_URI.toURL());

    }

    public void set(Bond bond) throws IOException {

//        Client client = ClientBuilder.newClient( new ClientConfig(MultiPartFeature.class) );
//        WebTarget webTarget = client.target(Env.GEMIS_BOND_URI).path("/add");
//
//        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
//        Response response = invocationBuilder.post(Entity.entity(bond, MediaType.APPLICATION_JSON));
//
//        System.out.println(response.getStatus());
//        System.out.println(response.readEntity(String.class));

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
