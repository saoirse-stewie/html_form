package testing;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;

/**
 * Created by saoirse on 05/12/2015.
 */

public class SurveyServlet extends HttpServlet {

    private Connection connection;
    private PreparedStatement updateVotes,totalVotes,results;

    public void init(ServletConfig config)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey?user=root&password=root");





           // First = connection.prepareStatement("Insert ")


            updateVotes = connection.prepareStatement("UPDATE surveyResult SET votes = votes + 1 " +
                   "WHERE id = ?");

            totalVotes = connection.prepareStatement("select sum( votes ) from surveyResult ");

            results = connection.prepareStatement("SELECT surveyoption, votes , id " + "from surveyResult ORDER by id");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out  = response.getWriter();
    DecimalFormat twoDigits = new DecimalFormat("0.00");

    out.println("<?xml version = \"1.0\"?>");

    out.println( "<!DOCTYPE html PUBLIC \"-//W3C//DTD "
   + "XHTML 1.0 Strict//EN\" \"http://www.w3.org" +
            "/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
    out.println("<html xmlns = \"http://www.w3.org/1999/xhtml1\">");

    out.println("<head>");

    int value = Integer.parseInt( request.getParameter("animal`"));

    try {
        updateVotes.setInt( 1 , value );

        updateVotes.executeUpdate();

        ResultSet result = totalVotes.executeQuery();

        result.next();
        int total = result.getInt( 1 );

        ResultSet result2 = results.executeQuery();

        out.println("<title>Thank you!</title>");
        out.println("</head>");

        out.println("<body>");

        out.println("<p> thank you for participating.");
        out.println("<br /> Results:</p><pre>");

        int votes;

        while( result2.next())
        {
            out.print(result2.getString(1));
            out.print(": ");
            votes = result2.getInt( 2 );
            out.print(twoDigits.format((double) votes/total*100));
            out.print("% responses: ");
            out.println( votes );
        }

        result2.close();

        out.print( "Total responses: " );
        out.print( total );

        out.println("</pre></body></html>");
        out.close();

    } catch (SQLException e) {
        e.printStackTrace();
        out.println( "<title>Error</title>" );
        out.println( "</head>" );
        out.println( "<body><p>Database error occurred. " );
        out.println( "Try again later.</p></body></html>" );
        out.close();
    }


}
    public void destroy() {
        try {
            updateVotes.close();
            totalVotes.close();
            results.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
