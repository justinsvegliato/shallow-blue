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
import shallowblue.ChessBoard.Player;

public class ShallowBlue {
  private static ChessBoard board = new ChessBoard();

  public static void main(String[] args) throws IOException, JSONException, InterruptedException {
    final String id = "21";
    final int player = 1;
    final String password = "32c68cae";
    final int color = Player.BLACK.value;
    
    HttpClient client = new DefaultHttpClient();
    String baseUrl = "http://www.bencarle.com/chess/poll/" + id + "/" + player + "/" + password + "/";
    HttpGet pollRequest = new HttpGet(baseUrl);
    HttpGet pushRequest;
    JSONObject json;
    HttpResponse response;
    BufferedReader reader;
    
    Agent agent = new Agent(board, color, 5);
    byte[] currentMove;
    while (true) {
      do {
        response = client.execute(pollRequest);
        reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        json = new JSONObject(reader.readLine());
        Thread.sleep(5000);
      } while (!json.getBoolean("ready"));
      
      currentMove = getInternalMove(json.getString("lastmove"));
      board.move(currentMove[0], currentMove[1]);
      
      currentMove = agent.getBestMove();
      board.move(currentMove[0], currentMove[1]);
      
      pushRequest = new HttpGet(baseUrl + getCanonicalMove(currentMove) + "/");
      client.execute(pollRequest);
    }
  }

  private static byte[] getInternalMove(String move) {
    String from = move.substring(1, 3);
    String to = move.substring(3, 5);
    return new byte[]{toByte(from), toByte(to)};
  }

  private static byte toByte(String location) {
    char[] components = location.toCharArray();
    byte start = (byte) (((10 - ((int) (components[1]) - 48)) * 10) + 1);
    byte offset = (byte) ((int) components[0] - 97);
    return (byte) (start + offset);
  }
  
  private static String getCanonicalMove(byte[] move) {
    byte from = move[0];
    byte to = move[1];
    return board.getPieceSymbol(board.getPiece(from)) + toString(from) + toString(to);
  }
  
  private static String toString(byte location) {
    char file = (char) ((location % 10) + 96);
    int rank =  10 - (location / 10);
    return file + "" + rank;
  }
}