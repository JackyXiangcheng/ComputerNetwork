package POPClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class POPcmd{
    public String getReturn(BufferedReader in) {
        String line = "";
        try {
            line = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    public String getResult(String line) {
        StringTokenizer st = new StringTokenizer(line, " ");
        return st.nextToken();
    }

    public String sendServer(String str, BufferedReader in, BufferedWriter out) throws IOException {
        out.write(str);
        out.newLine();
        out.flush();
        return getReturn(in);
    }

    public String user(String user, BufferedReader in, BufferedWriter out) throws IOException {
        return sendServer("user " + user, in, out);
    }

    public String pass(String password, BufferedReader in, BufferedWriter out) throws IOException {
        return sendServer("pass " + password, in, out);
    }

    public String stat(BufferedReader in, BufferedWriter out) throws IOException {
        return sendServer("stat", in, out);
    }

    public String list(BufferedReader in, BufferedWriter out) throws IOException {
        return sendServer("list", in, out);
    }

    public String list(int mailNum, BufferedReader in, BufferedWriter out) throws IOException {
        return sendServer("list " + mailNum, in, out);
    }

    public void dele(int mailNum, BufferedReader in, BufferedWriter out) throws IOException {
        sendServer("dele " + mailNum, in, out);
    }

    String getMessageDetail(BufferedReader in) throws UnsupportedEncodingException {
        String message = "";
        String line = null;

        try {
            line = in.readLine().toString();
            while (!".".equalsIgnoreCase(line)) {
                message = message + line + "\n";
                line = in.readLine().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public void retr(int mailNum, BufferedReader in, BufferedWriter out) throws IOException {
        String msg = sendServer("retr " + mailNum, in, out);
        String result = getResult(msg);

        if (!"+OK".equals(result)) {
            System.out.println(msg);
        }else {
            System.out.println(getMessageDetail(in));
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void quit(BufferedReader in, BufferedWriter out) throws IOException {
        sendServer("QUIT", in, out);
    }
}