package sample;
import java.sql.*;
public class Connect //Η Connect χρησιμοποιείται για την επικοινωννία με την βάση δεδομένων
{
    private static Connection conn = null;
    private Statement st = null;
    private ResultSet rst =null;


    public static boolean connectDB() throws SQLException //Η connectDB() χρησιμοποιείται για να δημιουργηθεί connection με την mySQL χρησιμοποιώντας τον jdbc connector και αν δεν πετάξει error επιστρέφει true
    {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/xmlestore","root","");
        return true;
    }

    public ResultSet getSelect(String statement) throws SQLException //Η getSelect() δέχεται ένα select statement το τρέχει μέσω του connection και επιστρέφει το result
    {
        st = conn.createStatement();
        rst = st.executeQuery(statement);
        return rst;
    }
    public int insertInto(String statement) throws SQLException //Η insertInto() δέχεται ένα insert statement τα εκτελεί μέσω του connection και επιστρέφει το autoIncrement id ώστε να μπορεί να χρησιμοποιηθεί. Αν αποτύχει επιστρέφει -1
    {
        st = conn.createStatement();
       int affectedRows = st.executeUpdate(statement,Statement.RETURN_GENERATED_KEYS);
       if(affectedRows == 0){
           return -1;
       }
        ResultSet generatedkeys = st.getGeneratedKeys();
       if(generatedkeys.next()){
           return (int) generatedkeys.getLong(1);
       }
        return -1;
    }
    public int Update(String statement) throws SQLException //Η Update() δέχεται ένα statement για update και το εκτελεί μέσω του connection. Επιστρέφει τον αριθμό των affected rows
    {
        st = conn.createStatement();
        int affectedRows = st.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
        return affectedRows;
    }
    public static void connectClose() throws SQLException //Η connectClose() κλείνει την σύνδεση με την βάση δεδομένων
    {
        conn.close();
    }
}
