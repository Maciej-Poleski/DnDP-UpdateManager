package org.dndp.UpdateManager;

public class Launcher
{

    /**
     * Inicjalizuje i inicjuje aktualizację
     * @param args Mają by dokładnie 2 Ścieżka do Working Copy i platforma
     */
    public static void main(String[] args)
    {
        if(args.length!=2)
        {
            System.err.println("Moduł UpdateManager został nieprawidłowo uruchomiony.\nNie można kontynuować");
            for(String arg:args)
                System.err.println(arg);
            System.exit(1);
        }
        SVNUtils.initialize();
        UpdateUtils.initialize(args[1], args[0]);
        UpdateUtils.executeUpdate();
    }

}
