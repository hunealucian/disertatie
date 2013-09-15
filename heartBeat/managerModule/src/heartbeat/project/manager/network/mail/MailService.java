package project.manager.network.mail;

/**
 * User: Hunea Paul Lucian
 * Date: 9/14/13
 */
public class MailService {
    private static final String strMailfrom		= "hunealucian@gmail.com";
    private static final String strMailto		= "hunealucian@gmail.com";
    private static final String subject		= "Distributed cluster alert";

    public static String NODE_FAILED = "The storage node %s with ip %s has failed do to unknouwn sources. Please verify that this node is up and running!";

    public static void sendMail( String message ){
        SendMail.sendMail(strMailfrom, subject, message, strMailto);
    }

}
