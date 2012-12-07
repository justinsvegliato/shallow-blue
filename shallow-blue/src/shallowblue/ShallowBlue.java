package shallowblue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ShallowBlue {

  public static void main(String[] args) throws IOException, JSONException, InterruptedException {
    Agent agent = new Agent();
    String id = "21";
    int player = 1;
    String password = "32c68cae";
    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet("http://www.bencarle.com/chess/poll/" + id + "/" + player + "/" + password + "/");

    JSONObject json;
    HttpResponse response;
    BufferedReader reader;
    while (true) {
      do {
        response = client.execute(request);
        reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        json = new JSONObject(reader.readLine());
        byte[] move = parseMove(json.getString("lastmove"));
        System.out.println("From: " + move[0] + " To: " + move[1]);
        Thread.sleep(5000);
      } while (!json.getBoolean("ready"));
    }
  }

  private static byte[] parseMove(String move) {
    String from = move.substring(1, 3);
    String to = move.substring(3, 5);
    return new byte[]{convertToByte(from), convertToByte(to)};
  }

  private static byte convertToByte(String location) {
    char[] components = location.toCharArray();
    byte start = (byte) (((10 - ((int) (components[1]) - 48)) * 10) + 1);
    byte offset = (byte) ((int) components[0] - 97);
    return (byte) (start + offset);
  }
}