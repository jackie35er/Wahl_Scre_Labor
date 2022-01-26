import java.io.*;
import java.text.DecimalFormat;
import java.util.Objects;

class Kandidat {

    private String name;
    private int punkte;
    private int platz1;

    public Kandidat(String name) {
        this.name = name;
    }


    public void addPoints(int p) {
        this.punkte += p;
        if (p == 2)
            this.platz1++;
    }

    public String toString() {
        DecimalFormat dc = new DecimalFormat("###0");
        return dc.format(punkte) + " / " + dc.format(platz1) + "   " + this.name;
    }

    public char firstChar() {
        return this.name.toLowerCase().charAt(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kandidat kandidat = (Kandidat) o;
        return punkte == kandidat.punkte && platz1 == kandidat.platz1 && Objects.equals(name, kandidat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, punkte, platz1);
    }
}

public class WahlShittyVersion {
    static Kandidat[] k = new Kandidat[5];

    static {
        k[0] = new Kandidat("Dominik Hofmann");
        k[1] = new Kandidat("Kilian Prager");
        k[2] = new Kandidat("Niklas Hochstöger");
        k[3] = new Kandidat("Paul Pfiel");
        k[4] = new Kandidat("Raid Alarkhanov");
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Keine Logdatei");
            System.out.println("Usage: java Wahl pathToLog");
            System.exit(1);
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int i = 1;
        DecimalFormat dc = new DecimalFormat("000");
        try {
            PrintWriter out = new PrintWriter(new FileWriter(args[0]));
            System.out.print(dc.format(i) + " >");
            out.print(dc.format(i) + " >");
            String ein;

            while ((ein = in.readLine()) != null && !ein.equals("quit")) {
                int ok = 0;
                int ul = 0;
                out.println(ein);
                if (isValid(ein)) {

                    if (ein.charAt(0) == '-') {
                        ul++;
                        ok++;
                    }
                    if (ein.charAt(1) == '-') {
                        ul++;
                        ok++;
                    }
                    if (ul > 1) ok = 0;

                    for (int a = 0; a < k.length; a++) {
                        if (ein.charAt(0) == k[a].firstChar()) {
                            k[a].addPoints(2);
                            ok += 1;
                        }
                        if (ein.charAt(1) == k[a].firstChar()) {
                            k[a].addPoints(1);
                            ok += 1;
                        }
                    }

                }

                if (ok != 2) {
                    System.out.println("     Falsche Eingabe!");
                    out.println("     Falsche Eingabe!");
                } else {
                    i++;
                    for (int a = 0; a < k.length; a++) {
                        System.out.println("    " + k[a]);
                        out.println("     " + k[a]);
                    }
                }
                System.out.println("-----------------------------------------------------------");
                out.println("-----------------------------------------------------------");
                out.flush();
                System.out.print(dc.format(i) + " >");
                out.print(dc.format(i) + " >");
            }
            out.close();
        } catch (IOException ioe) {
            System.err.println("Error: " + ioe.getMessage());
        }

    }

    static boolean isValid(String s) {
        if (s.length() < 2) return false;
        char ch1 = s.charAt(0);
        char ch2 = s.charAt(1);
        int test1 = 0, test2 = 0;
        if (s.length() != 2) return false;
        if (ch1 == ch2) return false;
        for (int i = 0; i < k.length; i++) {
            if (ch1 == k[i].firstChar()) test1++;
            if (ch2 == k[i].firstChar()) test2++;
        }
        if (ch1 == '-') test1++;
        if (ch2 == '-') test2++;
        if (test1 != 1 || test2 != 1) return false;

        return true;

    }


}
