package POPClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class POPClient{
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        POPcmd commands = new POPcmd();

        BufferedReader in;
        BufferedWriter out;

        //连接服务器，POP服务器是pop.163.com。
        while (true) {
            System.out.println("Welcome to the mailpop service");
            System.out.println("Please input information in the form of '[POP Server] [Port]'");
            System.out.println("eg: pop.163.com 110");
            System.out.printf("> ");
            String[] conn = scanner.nextLine().split(" ");
            if (conn.length != 2) {
                System.out.println("USAGE: [POP Server] [Port]");
                System.out.println("eg: pop.163.com 110");
                continue;
            }

            String popServer = conn[0];
            int port = Integer.valueOf(conn[1]);

            Socket socket = null;
            try {
                socket = new Socket(popServer, port);
            } catch (Exception e) {
                System.out.println("Fail to create the socket");
                continue;
            }

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String msg = commands.getReturn(in);

            System.out.println(msg);
            String result = commands.getResult(msg);
            if (!"+OK".equals(result)) {
                System.out.println("Fail to connect to the mail server");
                continue;
            }
            break;
        }

        //邮箱用户的身份认证。
        while (true) {
            System.out.printf("> ");
            String[] userParam = scanner.nextLine().split(" ");

            if (userParam.length != 2 || !userParam[0].equals("user")) {
                System.out.println("Please enter correct username and password first.");
                continue;
            }
            System.out.println(commands.user(userParam[1], in, out));

            System.out.printf("> ");
            String[] passParam = scanner.nextLine().split(" ");
            if (passParam.length != 2 || !passParam[0].equals("pass")) {
                System.out.println("Please enter correct username and password first.");
                continue;
            }
            System.out.println(commands.pass(passParam[1], in, out));
            break;
        }

        //不同命令的处理。
        while (true) {
            System.out.printf("> ");
            String[] cmd = scanner.nextLine().split(" ");

            if (cmd[0].equals("stat")) {
                System.out.println(commands.stat(in, out));
                continue;
            } else if (cmd[0].equals("list")) {
                if (cmd.length == 1) {
                    System.out.println(commands.list(in, out));
                } else {
                    System.out.println(commands.list(Integer.valueOf(cmd[1]), in, out));
                }
                continue;
            } else if (cmd[0].equals("retr")) {
                if (cmd.length == 2) {
                    commands.retr(Integer.valueOf(cmd[1]), in, out);
                } else {
                    System.out.println("USAGE: retr [Mail Num]");
                }
                continue;
            } else if (cmd[0].equals("dele")) {
                if (cmd.length == 2) {
                    commands.dele(Integer.valueOf(cmd[1]), in, out);
                } else {
                    System.out.println("USAGE: dele [Mail Num]");
                }
                continue;
            }

            else if (cmd[0].equals("quit")) {
                commands.quit(in, out);
                return;
            }

            System.out.println("Please use cmd like stat, list, retr, dele or quit, not others.");
        }

    }
}