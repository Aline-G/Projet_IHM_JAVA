package data;
import sample.Reseau;
import sample.Annee;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileReader {
    public static void getDataFromCSVFile(String csvFilePath, ArrayList<ArrayList<String>> collec)
    {
        //Document data
        String a1880,a1881,a1882,a1883,a1884,a1885,a1886,a1887,a1888,a1889;
        String a1890,a1891,a1892,a1893,a1894,a1895,a1896,a1897,a1898,a1899;
        String a1900,a1901,a1902,a1903,a1904,a1905,a1906,a1907,a1908,a1909;
        String a1910,a1911,a1912,a1913,a1914,a1915,a1916,a1917,a1918,a1919;
        String a1920,a1921,a1922,a1923,a1924,a1925,a1926,a1927,a1928,a1929;
        String a1930,a1931,a1932,a1933,a1934,a1935,a1936,a1937,a1938,a1939;
        String a1940,a1941,a1942,a1943,a1944,a1945,a1946,a1947,a1948,a1949;
        String a1950,a1951,a1952,a1953,a1954,a1955,a1956,a1957,a1958,a1959;
        String a1960,a1961,a1962,a1963,a1964,a1965,a1966,a1967,a1968,a1969;
        String a1970,a1971,a1972,a1973,a1974,a1975,a1976,a1977,a1978,a1979;
        String a1980,a1981,a1982,a1983,a1984,a1985,a1986,a1987,a1988,a1989;
        String a1990,a1991,a1992,a1993,a1994,a1995,a1996,a1997,a1998,a1999;
        String a2000,a2001,a2002,a2003,a2004,a2005,a2006,a2007,a2008,a2009;
        String a2010,a2011,a2012,a2013,a2014,a2015,a2016,a2017,a2018,a2019,a2020;
        String lat;
        String lon;

        try (BufferedReader bufRead = Files.newBufferedReader(Paths.get(csvFilePath), StandardCharsets.ISO_8859_1))
        {
            //Read the first line
            String line = bufRead.readLine();

            //Get data from the line
            //String[] data = line.split(",");

/*
            if(data.length != 143)
            {
                System.out.println("[FileReader] The file at " + csvFilePath + " does not contain the right number of columns.");
                return;
            } */


            //Read the file line by line
            while (line != null)
            {
                //Get data from the line
                String[] data = line.split(",");

                //Sort data
                lat = data[0];          a1919 = data[41];       a1960 = data[82];       a2001 = data[123];
                lon = data[1];          a1920 = data[42];       a1961 = data[83];       a2002 = data[124];
                a1880 = data[2];        a1921 = data[43];       a1962 = data[84];       a2003 = data[125];
                a1881 = data[3];        a1922 = data[44];       a1963 = data[85];       a2004 = data[126];
                a1882 = data[4];        a1923 = data[45];       a1964 = data[86];       a2005 = data[127];
                a1883 = data[5];        a1924 = data[46];       a1965 = data[87];       a2006 = data[128];
                a1884 = data[6];        a1925 = data[47];       a1966 = data[88];       a2007 = data[129];
                a1885 = data[7];        a1926 = data[48];       a1967 = data[89];       a2008 = data[130];
                a1886 = data[8];        a1927 = data[49];       a1968 = data[90];       a2009 = data[131];
                a1887 = data[9];        a1928 = data[50];       a1969 = data[91];       a2010 = data[132];
                a1888 = data[10];       a1929 = data[51];       a1970 = data[92];       a2011 = data[133];
                a1889 = data[11];       a1930 = data[52];       a1971 = data[93];       a2012 = data[134];
                a1890 = data[12];       a1931 = data[53];       a1972 = data[94];       a2013 = data[135];
                a1891 = data[13];       a1932 = data[54];       a1973 = data[95];       a2014 = data[136];
                a1892 = data[14];       a1933 = data[55];       a1974 = data[96];       a2015 = data[137];
                a1893 = data[15];       a1934 = data[56];       a1975 = data[97];       a2016 = data[138];
                a1894 = data[16];       a1935 = data[57];       a1976 = data[98];       a2017 = data[139];
                a1895 = data[17];       a1936 = data[58];       a1977 = data[99];       a2018 = data[140];
                a1896 = data[18];       a1937 = data[59];       a1978 = data[100];      a2019 = data[141];
                a1897 = data[19];       a1938 = data[60];       a1979 = data[101];      a2020 = data[142];
                a1898 = data[20];       a1939 = data[61];       a1980 = data[102];
                a1899 = data[21];       a1940 = data[62];       a1981 = data[103];
                a1900 = data[22];       a1941 = data[63];       a1982 = data[104];
                a1901 = data[23];       a1942 = data[64];       a1983 = data[105];
                a1902 = data[24];       a1943 = data[65];       a1984 = data[106];
                a1903 = data[25];       a1944 = data[66];       a1985 = data[107];
                a1904 = data[26];       a1945 = data[67];       a1986 = data[108];
                a1905 = data[27];       a1946 = data[68];       a1987 = data[109];
                a1906 = data[28];       a1947 = data[69];       a1988 = data[110];
                a1907 = data[29];       a1948 = data[70];       a1989 = data[111];
                a1908 = data[30];       a1949 = data[71];       a1990 = data[112];
                a1909 = data[31];       a1950 = data[72];       a1991 = data[113];
                a1910 = data[32];       a1951 = data[73];       a1992 = data[114];
                a1911 = data[33];       a1952 = data[74];       a1993 = data[115];
                a1912 = data[34];       a1953 = data[75];       a1994 = data[116];
                a1913 = data[35];       a1954 = data[76];       a1995 = data[117];
                a1914 = data[36];       a1955 = data[77];       a1996 = data[118];
                a1915 = data[37];       a1956 = data[78];       a1997 = data[119];
                a1916 = data[38];       a1957 = data[79];       a1998 = data[120];
                a1917 = data[39];       a1958 = data[80];       a1999 = data[121];
                a1918 = data[40];       a1959 = data[81];       a2000 = data[122];

                //Ajout des data au tableau final

                ArrayList ajoutColonne = new ArrayList();

                ajoutColonne.add(lat);
                ajoutColonne.add(""+lon);
                ajoutColonne.add(""+a1880);ajoutColonne.add(""+a1881);ajoutColonne.add(""+a1882);ajoutColonne.add(""+a1883);ajoutColonne.add(""+a1884);ajoutColonne.add(""+a1885);ajoutColonne.add(""+a1886);ajoutColonne.add(""+a1887);ajoutColonne.add(""+a1888);ajoutColonne.add(""+a1889);
                ajoutColonne.add(""+a1890);ajoutColonne.add(""+a1891);ajoutColonne.add(""+a1892);ajoutColonne.add(""+a1893);ajoutColonne.add(""+a1894);ajoutColonne.add(""+a1895);ajoutColonne.add(""+a1896);ajoutColonne.add(""+a1897);ajoutColonne.add(""+a1898);ajoutColonne.add(""+a1899);
                ajoutColonne.add(""+a1900);ajoutColonne.add(""+a1901);ajoutColonne.add(""+a1902);ajoutColonne.add(""+a1903);ajoutColonne.add(""+a1904);ajoutColonne.add(""+a1905);ajoutColonne.add(""+a1906);ajoutColonne.add(""+a1907);ajoutColonne.add(""+a1908);ajoutColonne.add(""+a1909);
                ajoutColonne.add(""+a1910);ajoutColonne.add(""+a1911);ajoutColonne.add(""+a1912);ajoutColonne.add(""+a1913);ajoutColonne.add(""+a1914);ajoutColonne.add(""+a1915);ajoutColonne.add(""+a1916);ajoutColonne.add(""+a1917);ajoutColonne.add(""+a1918);ajoutColonne.add(""+a1919);
                ajoutColonne.add(""+a1920);ajoutColonne.add(""+a1921);ajoutColonne.add(""+a1922);ajoutColonne.add(""+a1923);ajoutColonne.add(""+a1924);ajoutColonne.add(""+a1925);ajoutColonne.add(""+a1926);ajoutColonne.add(""+a1927);ajoutColonne.add(""+a1928);ajoutColonne.add(""+a1929);
                ajoutColonne.add(""+a1930);ajoutColonne.add(""+a1931);ajoutColonne.add(""+a1932);ajoutColonne.add(""+a1933);ajoutColonne.add(""+a1934);ajoutColonne.add(""+a1935);ajoutColonne.add(""+a1936);ajoutColonne.add(""+a1937);ajoutColonne.add(""+a1938);ajoutColonne.add(""+a1939);
                ajoutColonne.add(""+a1940);ajoutColonne.add(""+a1941);ajoutColonne.add(""+a1942);ajoutColonne.add(""+a1943);ajoutColonne.add(""+a1944);ajoutColonne.add(""+a1945);ajoutColonne.add(""+a1946);ajoutColonne.add(""+a1947);ajoutColonne.add(""+a1948);ajoutColonne.add(""+a1949);
                ajoutColonne.add(""+a1950);ajoutColonne.add(""+a1951);ajoutColonne.add(""+a1952);ajoutColonne.add(""+a1953);ajoutColonne.add(""+a1954);ajoutColonne.add(""+a1955);ajoutColonne.add(""+a1956);ajoutColonne.add(""+a1957);ajoutColonne.add(""+a1958);ajoutColonne.add(""+a1959);
                ajoutColonne.add(""+a1960);ajoutColonne.add(""+a1961);ajoutColonne.add(""+a1962);ajoutColonne.add(""+a1963);ajoutColonne.add(""+a1964);ajoutColonne.add(""+a1965);ajoutColonne.add(""+a1966);ajoutColonne.add(""+a1967);ajoutColonne.add(""+a1968);ajoutColonne.add(""+a1969);
                ajoutColonne.add(""+a1970);ajoutColonne.add(""+a1971);ajoutColonne.add(""+a1972);ajoutColonne.add(""+a1973);ajoutColonne.add(""+a1974);ajoutColonne.add(""+a1975);ajoutColonne.add(""+a1976);ajoutColonne.add(""+a1977);ajoutColonne.add(""+a1978);ajoutColonne.add(""+a1979);
                ajoutColonne.add(""+a1980);ajoutColonne.add(""+a1981);ajoutColonne.add(""+a1982);ajoutColonne.add(""+a1983);ajoutColonne.add(""+a1984);ajoutColonne.add(""+a1985);ajoutColonne.add(""+a1986);ajoutColonne.add(""+a1987);ajoutColonne.add(""+a1988);ajoutColonne.add(""+a1989);
                ajoutColonne.add(""+a1990);ajoutColonne.add(""+a1991);ajoutColonne.add(""+a1992);ajoutColonne.add(""+a1993);ajoutColonne.add(""+a1994);ajoutColonne.add(""+a1995);ajoutColonne.add(""+a1996);ajoutColonne.add(""+a1997);ajoutColonne.add(""+a1998);ajoutColonne.add(""+a1999);
                ajoutColonne.add(""+a2000);ajoutColonne.add(""+a2001);ajoutColonne.add(""+a2002);ajoutColonne.add(""+a2003);ajoutColonne.add(""+a2004);ajoutColonne.add(""+a2005);ajoutColonne.add(""+a2006);ajoutColonne.add(""+a2007);ajoutColonne.add(""+a2008);ajoutColonne.add(""+a2009);
                ajoutColonne.add(""+a2010);ajoutColonne.add(""+a2011);ajoutColonne.add(""+a2012);ajoutColonne.add(""+a2013);ajoutColonne.add(""+a2014);ajoutColonne.add(""+a2015);ajoutColonne.add(""+a2016);ajoutColonne.add(""+a2017);ajoutColonne.add(""+a2018);ajoutColonne.add(""+a2019);
                ajoutColonne.add(""+a2020);

                collec.add(ajoutColonne);

                line = bufRead.readLine();
            }
            bufRead.close();
        }

        catch (IOException exception)
        {
            System.err.println(exception);
        }
    }
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> donnees = new ArrayList<ArrayList<String>>();

        if (args.length > 0) {
            File tempFile = new File(args[0]);

            if (tempFile.exists()) {
                System.out.println("[Main] Reading the file " + args[0] + " ...");

                //We start by reading the CSV file
                FileReader.getDataFromCSVFile(args[0], donnees);

                System.out.println("[Main] End of the file " + args[0] + ".");
            }else{
                System.out.println("[Main] No file " + args[0]);
            }
            Reseau terre = new Reseau(donnees);
            //terre.afficherListeZone();
            //terre.afficherListeAnnee();
            System.out.println(terre.getMaximum());
            System.out.println(terre.getMinimum());
            terre.rechercheMinMax();
            System.out.println("La valeur maximale est "+terre.getMaximum());
            System.out.println("La valeur minimale est "+terre.getMinimum());
            float anomalie = terre.rechercheValAnomalie(2019, -68, -62);
            System.out.println(anomalie);

        }else{
            System.out.println("[Main] You should enter the CSV file path as a parameters.");
        }
    }
}
