package shallowblue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import shallowblue.Chessboard.Player;

public class ShallowBlue {

  private static Chessboard board = new Chessboard();

  public static void main(String[] args) throws IOException, JSONException, InterruptedException {
    // Must update these for each game
    final String gameId = "240";
    final int color = Player.BLACK.value;
    final int depth = 5;

    final String password = "a923cc0";
    final int team = 15;
    final String baseUrl = "http://www.bencarle.com/chess/";
    final String credentials = gameId + "/" + team + "/" + password + "/";

    HttpClient client = new DefaultHttpClient();
    HttpGet pollRequest = new HttpGet(baseUrl + "poll/" + credentials + "/");
    HttpGet pushRequest;
    JSONObject json = null;
    HttpResponse response;
    BufferedReader reader;

    Agent agent = new Agent(board, color, depth);
    byte[] currentMove;
    int removedPiece = 0;

    System.out.println(board);
    while (true) {
      do {
        response = client.execute(pollRequest);
        reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        json = new JSONObject(reader.readLine());
        reader.close();
        EntityUtils.consume(response.getEntity());
        Thread.sleep(5000);
        System.out.println("Waiting for the opponents move...");
      } while (!json.getBoolean("ready"));


      String lastmove = json.getString("lastmove");
      if (!lastmove.isEmpty()) {
        currentMove = getInternalMove(lastmove);

        System.out.println(currentMove[0] + " " + currentMove[1]);

        System.out.println("The opponent's move is " + lastmove + ".");
        if (lastmove.charAt(currentMove.length - 1) == 'Q') {
          System.out.println("Queen promotion");
          removedPiece = board.promote(currentMove[0], currentMove[1]);
        } else if (lastmove.charAt(0) == 'K') {
          System.out.println("Moved king");
          if (Math.abs(currentMove[0] - currentMove[1]) == 2) {
            System.out.println("King castled");
            board.castle(currentMove[0], currentMove[1]);
          } else {
            removedPiece = board.move(currentMove[0], currentMove[1]);
          }
        } else if (lastmove.charAt(0) == 'P') {
          int delta = Math.abs(currentMove[0] - currentMove[1]);
          if (delta == 9 || delta == 11) {
            System.out.println("Pawn attacked");
            if (board.getPiece(currentMove[1]) == 0) {
              board.croissant(currentMove[0], currentMove[1]);
            } else {
              removedPiece = board.move(currentMove[0], currentMove[1]);
            }
          } else {
            removedPiece = board.move(currentMove[0], currentMove[1]);
          }
        } else {
          removedPiece = board.move(currentMove[0], currentMove[1]);
        }

        if (Math.abs(removedPiece) == Chessboard.king) {
          break;
        }
      } else {
        System.out.println("Oh wait, we're actually going first!");
      }

      agent.selectBestMove();
      currentMove = agent.getBestMove();
      System.out.println("Our move is " + getCanonicalMove(currentMove) + ".");
      pushRequest = new HttpGet(baseUrl + "move/" + credentials + getCanonicalMove(currentMove) + "/");
      response = client.execute(pushRequest);
      EntityUtils.consume(response.getEntity());
      removedPiece = board.move(currentMove[0], currentMove[1]);
      System.out.println(board);

      if (Math.abs(removedPiece) == Chessboard.king) {
        break;
      }
    }
    System.out.println("Game over!");
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
    int rank = 10 - (location / 10);
    return file + "" + rank;
  }
}