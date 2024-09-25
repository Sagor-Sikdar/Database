import java.sql.*;
import java.io.*;
import java.lang.*;
import java.util.*;

class ComputePearsonCoeff
{
    static
    {
        try
        {
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String argv[])
    {
        try
        {
            Connection con = null;
            String dbname = argv[0];
            String url = "jdbc:db2://127.0.0.1:25000/" + dbname;
            if (argv.length == 5)
            {
                String userid = argv[1];
                String passwd = argv[2];
                con = DriverManager.getConnection(url, userid, passwd);
            }
            else
            {
                throw new Exception("\n Encountered Exception!!! Please follow: java PearsonCC dbname login password stock1 stock2 \n");
            }
            String stock1 = argv[3];
            String stock2 = argv[4];
            List<Double> list1 = new ArrayList<Double>();
            Statement statement1 = con.createStatement();
            ResultSet result1 = statement1.executeQuery("SELECT CLOSE FROM CSE532.STOCK WHERE STOCKNAME = '" + stock1 + "' ORDER BY DATE");
            while (result1.next())
            {
                list1.add(result1.getDouble(1));
            }
            result1.close();
            statement1.close();

            List<Double> list2 = new ArrayList<Double>();
            Statement statement2 = con.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT CLOSE FROM CSE532.STOCK WHERE STOCKNAME = '" + stock2 + "' ORDER BY DATE");
            while (result2.next())
            {
                list2.add(result2.getDouble(1));
            }
            result2.close();
            statement2.close();
            con.close();

            double total_x = 0.0;
            double total_y = 0.0;
            double total_xy = 0.0;
            double total_x2 = 0.0;
            double total_y2 = 0.0;
            double pos_x;
            double pos_y;
            double cc;
            int n = list1.size();
            for(int i = 0; i < n; i++)
            {
                pos_x = list1.get(i);
                pos_y = list2.get(i);
                total_x = total_x + pos_x;
                total_y = total_y + pos_y;
                total_xy = total_xy + pos_x * pos_y;
                total_x2 = total_x2 + pos_x * pos_x;
                total_y2 = total_y2 + pos_y * pos_y;
            }
            cc = ((n * total_xy) - (total_x * total_y))/(Math.sqrt(((n * total_x2) - (total_x * total_x)) * ((n * total_y2) - (total_y * total_y))));
            System.out.println("Pearson Correlation Coefficient = " + cc);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}