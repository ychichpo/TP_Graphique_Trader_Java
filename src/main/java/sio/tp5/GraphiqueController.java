package sio.tp5;

import sio.tp5.Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> GetAllActions()
    {
        ArrayList<String> lesNomsDesActions = new ArrayList<>();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("select nomAction from action");
            rs = ps.executeQuery();
            while(rs.next())
            {
                lesNomsDesActions.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return lesNomsDesActions;
    }
    public HashMap<String,Double> GetDatasGraphique1(String nomAction)
    {
        HashMap<String, Double> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("select trader.nomTrader, acheter.montantAchat\n" +
                    "from trader\n" +
                    "inner join acheter on trader.idTrader = acheter.numTrader\n" +
                    "inner join action on acheter.numAction = action.idAction\n" +
                    "where action.nomAction = ?");
            ps.setString(1, nomAction);
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getDouble(2));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return datas;
    }
    public HashMap<String,Integer> GetDatasGraphique2()
    {
        HashMap<String, Integer> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("select trader.nomTrader, COUNT(*) as nb\n" +
                    "from trader\n" +
                    "inner join acheter on trader.idTrader = acheter.numTrader\n" +
                    "GROUP by trader.nomTrader");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return datas;
    }
    public HashMap<String,ArrayList<String>> GetDatasGraphique3()
    {
        HashMap<String,ArrayList<String>> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("select trader.nomTrader, acheter.montantAchat,action.nomAction\n" +
                    "from trader\n" +
                    "inner join acheter on trader.idTrader = acheter.numTrader\n" +
                    "inner join action on acheter.numAction = action.idAction");
            rs = ps.executeQuery();

            while(rs.next())
            {
                if(!datas.containsKey(rs.getString("nomAction")))
                {
                    ArrayList<String> lesActions = new ArrayList<>();
                    lesActions.add(rs.getString("nomTrader"));
                    lesActions.add(rs.getString("montantAchat"));
                    datas.put(rs.getString("nomAction"),lesActions);
                }
                else
                {
                    datas.get(rs.getString("nomAction")).add(rs.getString("nomTrader"));
                    datas.get(rs.getString("nomAction")).add(rs.getString("montantAchat"));
                }

            }
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return datas;
    }
}
