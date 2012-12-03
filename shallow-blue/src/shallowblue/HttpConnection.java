package shallowblue;

import java.io.*;
import java.net.Socket;

public class HttpConnection {

  private final String ipAddress;
  private final int port;
  private BufferedWriter out;
  private BufferedReader in;

  
  public static void main(String[] args) throws IOException {
    HttpConnection conn = new HttpConnection("174.129.254.32", 80);
    conn.open();
    conn.poll();
    conn.close();
  }
  
  public HttpConnection(String ipAddress, int port) {
     this.ipAddress = ipAddress;
     this.port = port;
  } 
 
  public void open() throws IOException {
    Socket socket = new Socket(ipAddress, port);
    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }
  
  public void close() throws IOException {
    out.close();
    in.close();
  }

  public void poll() throws IOException {
    out.write("GET /lastpage.html HTTP/1.1\r\nHost: www.google.com\r\n\r\n\r\n");
    out.flush();
    
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null) {
      System.out.println(line);
    }
  }
}