import java.util.*;
import java.io.*;
public class Callregister {

	public static void main(String[] args) throws IOException {
		
		File textfile = new File("CSV.txt");
        Scanner sc = new Scanner(new FileReader(textfile));
        Scanner sc1 = new Scanner(new FileReader(textfile));
        String str = "";
        int inbound = 0, inbound_duration = 0;
        int outbound = 0, outbound_duration = 0;
        int inprogress = 0, inprogress_duration = 0;
        int queued = 0, queued_duration = 0;
        int completed = 0, completed_duration = 0;
        int canceled = 0, canceled_duration = 0;
        int failed = 0, failed_duration = 0;
        int numberOfEntries = 0;
        while(sc1.hasNextLine() && sc1.nextLine() != null)
        {
            numberOfEntries++;
        }
        while(sc1.hasNextLine() && sc1.nextLine() != null)
        {
            numberOfEntries++;
        }
        //System.out.println(numberOfEntries);
        for (int i = 0; i < numberOfEntries; i++)
        {
            str = sc.nextLine();
            StringTokenizer st = new StringTokenizer(str,",");
            int x = st.countTokens();
            //String entry = "";
            int b = 0;
            for(int j = 0; j < x; j++)
            {
                String t = st.nextToken();
                t = t.trim();
                //System.out.print(t +  "  ");
                int a = 0;                
                if(t.equals("inbound"))
                {
                    inbound++;
                    a = Integer.parseInt(st.nextToken());
                    inbound_duration = inbound_duration + a;
                    b = a;
                    j++;
                }
                if(t.equals("outbound-sip"))
                {
                    outbound++;
                    a = Integer.parseInt(st.nextToken());
                    outbound_duration = outbound_duration + a;
                    b = a;
                    j++;
                }
                if(t.equals("queued"))
                {
                    queued++;
                    queued_duration = queued_duration + b;
                }
                if(t.equals("canceled"))
                {
                    canceled++;
                    canceled_duration = canceled_duration + b;
                }
                if(t.equals("completed"))
                {
                    completed++;
                    completed_duration = completed_duration + b;
                }
                if(t.equals("in-progress"))
                {
                    inprogress++;
                    inprogress_duration = inprogress_duration + b;
                }
                if(t.equals("failed"))
                {
                    failed++;
                    failed_duration = failed_duration + b;
                }
            }
        }
            //System.out.println();
            System.out.println("No. of inbound calls: " + inbound + "\n"
                    + "Duration of inbound calls: " + (inbound_duration) + " min\n\n"
                    + "No. of outbound calls: " + outbound + "\n"
                    +"Duration of outbound calls: " + (outbound_duration) + " min\n\n"
                    +"No. of completed calls: " + completed + "\n"
                    +"Duration of completed calls: " + (completed_duration) + " min\n\n"
                    +"No. of queued calls: " + queued + "\n"
                    +"Duration of queued calls: " + (queued_duration) + " min\n\n"
                    +"No. of canceled calls: " + canceled + "\n"
                    +"Duration of canceled calls: " + (canceled_duration) + " min\n\n"
                    +"No. of in-progress calls: " + inprogress + "\n"
                    +"Duration of in-progress calls: " + (inprogress_duration) + " min\n\n"
                    +"No. of failed calls: " + failed + "\n"
                    +"Duration of failed calls: " + (failed_duration) + " min");
        }

	}


