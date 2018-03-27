String hostName = "127.0.0.1";
    int portNumber = 8081;
    try {
        System.out.println("Connecting to " + hostName + " on port " + portNumber);
        Socket client = new Socket(hostName, portNumber);
        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        //OutputStream outToServer = client.getOutputStream();
        //DataOutputStream out = new DataOutputStream(outToServer);
        PrintWriter pw = new PrintWriter(client.getOutputStream());
        pw.println("GET / HTTP/1.1");
        pw.println("Host: 127.0.0.1");
        pw.println()
        pw.println("<html><body><h1>Hello world<\\h1><\\body><\\html>")
        pw.println()
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String t;
        while((t = br.readLine()) != null) System.out.println(t);
        br.close();
        
    }catch(IOException e) {
       e.printStackTrace();
    }