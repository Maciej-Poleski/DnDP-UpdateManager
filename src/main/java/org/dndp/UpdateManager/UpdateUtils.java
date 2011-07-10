package org.dndp.UpdateManager;


import java.io.File;
import java.util.regex.Pattern;

/**
 * Narzędzia służące do sprawdzania potrzeby i przeprowadzania aktualizacji.
 * 
 * @author evil
 * 
 */
class UpdateUtils
{
    private static final String repository =
                                               "http://subversion.assembla.com/svn/DnDP.2/";
    private static String       osVersion;
    private static File         workingCopyDirectory;
    private static boolean      initialized;

    public static boolean executeUpdate()
    {
        if(!initialized) throw new IllegalStateException(
            "Klasa org.dndp.UpdateManager.UpdateUtils jest niezainicjalizowana. Zapomniałeś o UpdateUtils.initialize()");
        try
        {
            SVNUtils.doCheckout(repository + osVersion, workingCopyDirectory);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Inicjalizuje moduł aktualizacyjny. Należy wykonać conajwyżej raz oraz
     * zawsze
     * przed użyciem dowolnej metody tej klasy.
     */
    public static void initialize(String os, String directory)
    {
        if(initialized) throw new IllegalStateException(
            "Klasa została już zainicjalizowana");
        initialized = true;
        if(os.compareTo("x86_64") == 0) osVersion = "linux-x86_64";
        else if(Pattern.matches("i.86", os)) osVersion = "linux-x86";
        else if(os.toLowerCase().compareTo("amd64") == 0) osVersion =
            "win-x86_64";
        else if(os.toLowerCase().compareTo("x86") == 0) osVersion = "win-x86";
        else throw new UnsupportedOperationException("System " + os
            + " jest niewspierany. Powiadom dewelopera.");
        workingCopyDirectory = new File(directory);
    }
}
