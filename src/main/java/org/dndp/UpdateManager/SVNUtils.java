/**
 * 
 */
package org.dndp.UpdateManager;


import java.io.File;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * Podstawowe narzędzia do zarządzanie WOrking Copy. Pamiętaj o wywołaniu metody initialize() przed użyciem dowolnej innej metody tej klasy.
 * @author evil
 */
class SVNUtils
{
    private static boolean         initialized;
    private static SVNUpdateClient updateClient;

    /**
     * Klasa statyczna
     */
    private SVNUtils()
    {}

    /**
     * Wykonuje checkout (oraz nadaje się do aktualizacji) zadanego repozytorium
     * (tylko WebDAV jest wspierany) do danego katalogu
     * 
     * @param repository Repozytorium WebDAV które będzie check-outowane
     * @param destination Katalog przeznaczony na Working Copy
     * @throws SVNException
     */
    public static void doCheckout(String repository, File destination)
        throws SVNException
    {
        if(!initialized) throw new IllegalStateException(
            "Klasa org.dndp.UpdateManager.SVNUtils jest niezainicjalizowana. Zapomniałeś o SVNUtils.initialize()");
        SVNURL svnURL = SVNURL.parseURIEncoded(repository);
        updateClient.doCheckout(svnURL, destination, SVNRevision.HEAD,
            SVNRevision.HEAD, SVNDepth.INFINITY, false);
    }

    /**
     * Inicjalizuje bibliotekę SVN. Należy wykonać conajwyżej raz oraz zawsze
     * przed użyciem dowolnej metody tej klasy.
     */
    public static void initialize()
    {
        if(initialized) throw new IllegalStateException(
            "Klasa została już zainicjalizowana");
        initialized = true;
        DAVRepositoryFactory.setup();
        updateClient =
            new SVNUpdateClient(SVNWCUtil.createDefaultAuthenticationManager(),
                SVNWCUtil.createDefaultOptions(true));
    }

}
