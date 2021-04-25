package pwr.edu.percolation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

final class ParamHandler {

    private static final String PARAM_FILE_NAME = "perc_init.txt";
    private static final byte REQUIRED_PARAMETERS_COUNT = 5;
    private static final Logger logger = Logger.getLogger(ParamHandler.class.getName());

    public static PercolationParams readInputParams() {
        try (final var resource = MonteCarloSimulation.class.getClassLoader().getResourceAsStream(PARAM_FILE_NAME);
             final var reader = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));) {
            final String[] strings = reader.lines()
                    .filter(line -> !line.startsWith("//"))
                    .map(line -> line.split("//", 2)[0].trim())
                    .filter(line -> !line.isBlank())
                    .toArray(String[]::new);
            return validateParams(strings);
        } catch (IOException e) {
            showErrorAndExit("Unexpected error with reading file \"" + PARAM_FILE_NAME + "\"");
        } catch (NullPointerException npEx) {
            showErrorAndExit("Missing file " + PARAM_FILE_NAME + " in resources folder");
        }
        throw new IllegalStateException("Params file was not loaded");
    }


    private static PercolationParams validateParams(final String[] params) {
        if (params.length != REQUIRED_PARAMETERS_COUNT)
            showErrorAndExit("Incorrect arguments count");

        Map<Integer, Function<Double, Boolean>> extraValidators = Map.of(
                0, s -> s > 0,
                1, s -> s > 0,
                2, s -> s <= 1.0 && s >= 0.0,
                3, s -> s <= 1.0 && s > 0.0,
                4, s -> s <= 1.0 && s > 0.0);

        double[] parsedArgs = new double[REQUIRED_PARAMETERS_COUNT];
        for (int i = 0; i < REQUIRED_PARAMETERS_COUNT; i++) {
            try {
                final double value = Double.parseDouble(params[i]);
                if (!extraValidators.get(i).apply(value))
                    showErrorAndExit("Value out of range for " + (i + 1) + " argument");
                parsedArgs[i] = value;
            } catch (NumberFormatException ex) {
                showErrorAndExit("Incorrect value for " + (i + 1) + " argument");
            }
        }
        return new PercolationParams((int) parsedArgs[0], (int) parsedArgs[1], parsedArgs[2], parsedArgs[3], parsedArgs[4]);
    }


    private static void showErrorAndExit(final String message) {
        logger.severe(message);
        System.exit(1);
    }


}

/*
<div class="row center-xs center-sm center-md center-lg  middle-xs middle-sm middle-md middle-lg ">
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="KatarzynaWeron" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 kolumna">
                                <h3 class="role">Head of Department</h3>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Katarzyna-Weron.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. Katarzyna <nobr>Sznajd-Weron</nobr></h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 218/A1</p>
                                    <p><a href="http://www.if.pwr.wroc.pl/~katarzynaweron/index_pl.html"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a> </p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:katarzyna.weron@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">katarzyna.weron@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-1851-8508" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Katarzyna_Sznajd-Weron" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=XStBoTwAAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6701680061"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=472730"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AndrzejJanutka" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 kolumna">
                                <h3 class="role">Vice-head of Department</h3>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Andrzej-Janutka.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr hab. inż. Andrzej Janutka, prof. PWr</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262a/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~janutka/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:andrzej.janutka@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">andrzej.janutka@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-0551-9122" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Andrzej_Janutka" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6506010316"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=353246"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                </div>
<div class="row center-xs center-sm center-md center-lg  middle-xs middle-sm middle-md middle-lg " style="
    background: antiquewhite;
">
                    <!-- Poczatek człowieka -->
                    <!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="ImieNazwisko" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Imie-Nazwisko.png" alt="Avatar" onContextMenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Stopnie Imie Nazwisko</h4>
                                    <p><i class="fas fa-home"></i> XXX/AA</p>
                                    <p><a href="stronadomowa" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p>
                                    <p>
                                        <div class="tooltip"><a href="mailto:email" class="fas fa-envelope"></a><span class="tooltiptext">email</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0000-0000-0000" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Imie_nazwisko" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    </p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId="><i class="fas fa-link"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid="><i class="fas fa-link"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div> -->
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MaciejBieniek" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Maciej-Bieniek.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Maciej Bieniek</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 504a/A1</p>
                                    <p><a href="https://mysite.science.uottawa.ca/phawrylak/member_pages/maciek/index-maciek.html"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:maciej.bieniek@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">maciej.bieniek@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-4505-1998 " class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=wDdSyzwAAAAJ&amp;view_op=list_works&amp;sortby=pubdate" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57191189949&amp;eid=2-s2.0-85083390863&amp;featureToggles=FEATURE_AUTHOR_DETAILS_BOTOX:1&amp;at_feature_toggle=1"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600362"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="LilianaBujkiewicz" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/women_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Liliana Bujkiewicz</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 218/D1</p>
                                    <p><a href="http://rainbow.if.pwr.wroc.pl/~lilianb/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:liliana.bujkiewicz@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">liliana.bujkiewicz@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="https://orcid.org/0000-0001-7400-2197" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="https://www.researchgate.net/profile/Krzysztof_Gawarecki" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="https://scholar.google.pl/citations?hl=pl&user=RzjA_cwAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6506394842"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=354292"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MartaBrzezinska" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Marta-Brzezinska.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Marta Brzezińska</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 504a/A1</p>
                                    <p><a href="https://martabrz.github.io/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:marta.a.brzezinska@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">marta.a.brzezinska@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-0657-4382" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=d2azi6kAAAAJ&amp;hl=en" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57194544425"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802374"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="KrzysztofGawarecki" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Krzysztof-Gawarecki.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Krzysztof Gawarecki</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 220/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~kgawar/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:krzysztof.gawarecki@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">krzysztof.gawarecki@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-7400-2197" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Krzysztof_Gawarecki" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?hl=pl&amp;user=RzjA_cwAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=36459801500"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=471870"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MichalGawelczyk" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Michal-Gawelczyk.png" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Michał Gawełczyk</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 222/A1</p>
                                    <p><a href="https://wppt.pwr.edu.pl/pracownicy/michal-gawelczyk"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:michal.gawelczyk@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">michal.gawelczyk@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-2299-140X" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Michal_Gawelczyk" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?hl=pl&amp;user=FdlSYjgAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=54882407900"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=496020"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AnnaHajdusianek" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Anna-Hajdusianek.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Anna Hajdusianek, prof. PWr</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 219/A1</p>
                                    <p><a href="https://wppt.pwr.edu.pl/pracownicy/anna-hajdusianek"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:anna.hajdusianek@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">anna.hajdusianek@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=340901"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="JacekHerbrych" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Jacek-Herbrych.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Jacek Herbrych</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262d/A1</p>
                                    <p><a href="https://herbrychjacek.bitbucket.io/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:jacek.herbrych@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">jacek.herbrych@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-9860-2146" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=en&amp;user=jeeDlpwAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=54083167700"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600308"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="ArkadiuszJedrzejewski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Arkadiusz-Jedrzejewski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Arkadiusz Jędrzejewski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 524/B4</p>
                                    <p><a href="http://if.pwr.edu.pl/~jedrzejewski/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:arkadiusz.jedrzejewski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">arkadiusz.jedrzejewski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-7965-2014" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Arkadiusz_Jedrzejewski" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=NgOWJ9EAAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56964185900"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600469"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="PawelKarwat" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Paweł Karwat</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 222/A1</p>
                                    <p><a href="http://www.pawelkar.cal24.pl/pwr/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:pawel.karwat@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">pawel.karwat@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-7874-9060" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Pawel_Karwat" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=54415838400"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=490040"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MarekKlonowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Marek-Klonowski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. inż. Marek Klonowski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 213/D1</p>
                                    <p><a href="http://cs.pwr.edu.pl/klonowski/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:marek.klonowski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">marek.klonowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-3141-8712" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="https://www.researchgate.net/profile/Pawel_Karwat" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=YtYgQmAAAAAJ&amp;hl=pl&amp;oi=ao" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=55914696700"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=421270"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="PiotrKowalski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Piotr Kowalski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 223/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:piotr.m.kowalski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">piotr.m.kowalski@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600470"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MalgorzataKuchta" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/women_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Małgorzata Kuchta</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 218/D1</p>
                                    <p><a href="https://cs.pwr.edu.pl/kuchta/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:malgorzata.kuchta@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">malgorzata.kuchta@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-6235-1808" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="https://www.researchgate.net/profile/Pawel_Karwat" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=14035689500"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=341292"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AdrianLewandowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Adrian-Lewandowski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Adrian Lewandowski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262a/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:adrian.lewandowski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">adrian.lewandowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-3120-5047" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=bMAaL28AAAAJ&amp;view_op=list_works" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600394"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="PatrycjaLydzba" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/women_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Patrycja Łydżba</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 222/A1</p>
                                    <p><a href="https://wppt.pwr.edu.pl/pracownicy/patrycja-lydzba"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:patrycja.lydzba@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">patrycja.lydzba@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-5804-4848" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Patrycja_Lydzba2" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56741361600"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600230"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="PawelMachnikowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <!-- <h4 class="role">Dean </h4> -->
                                <img class="img-fluid" src="images/profiles/Pawel-Machnikowski.jpeg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. inż. Paweł Machnikowski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 235/A1</p>
                                    <a href="http://www.if.pwr.wroc.pl/~machnik"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b>
                                    <p>
                                        </p></a><div class="tooltip"><a href="http://www.if.pwr.wroc.pl/~machnik"></a><a href="mailto:pawel.machnikowski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">pawel.machnikowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-0349-1725" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Pawel_Machnikowski" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=QgZqH80AAAAJ&amp;view_op=list_works&amp;sortby=pubdate" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6701755717"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=361962"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="JanMajor" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Jan Major</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 235/A1</p>
                                    <a href="https://sites.google.com/view/jan-major-physics/home?authuser=0"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b>
                                    <p>
                                        </p></a><div class="tooltip"><a href="https://sites.google.com/view/jan-major-physics/home?authuser=0"></a><a href="mailto:jan.j.major@gmail.com" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">jan.j.major@gmail.com</span></div>
                                        <!-- <div class="tooltip"><a href="https://orcid.org/0000-0003-0349-1725" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Jan_Major" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <!-- <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&user=QgZqH80AAAAJ&view_op=list_works&sortby=pubdate" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56147263700"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <!-- <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=361962"><i class="fas fa-link"></i> <b>DONA</b></a> -->
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MaciejMaska" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Maciej-Maska.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. Maciej Maśka</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262c/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:maciej.maska@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">maciej.maska@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-2214-3283" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Maciej_Maska" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=fWmT1GkAAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6602755782"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600476"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AdamMielnikPyszczorski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Adam_Mielnik-Pyszczorski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Adam <nobr>Mielnik-Pyszczorski</nobr></h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="http://if.pwr.edu.pl/~mielnik/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:adam.mielnik-pyszczorski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext"><nobr>adam.mielnik-pyszczorski@pwr.edu.pl</nobr></span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-4770-4078" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Adam_Mielnik-Pyszczorski" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=yR1QJoQAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56651019500"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600235"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MarcinMierzejewski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Marcin-Mierzejewski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. Marcin Mierzejewski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262b/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~mierzejewski/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:marcin.mierzejewski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">marcin.mierzejewski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-1945-1437" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=2R0W8S0AAAAJ&amp;hl=en" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=7003815262"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600027"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AntoniMitus" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. Antoni Mituś</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262e/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~amitus/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:antoni.mitus@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">antoni.mitus@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-2316-780X" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Antoni_Mitus" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=1znL0T0AAAAJ&amp;view_op=list_works&amp;sortby=pubdate" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=7003961190"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=146258"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MichalMorayne" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. Michał Morayne</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 217/D1</p>
                                    <p><a href="https://cs.pwr.edu.pl/morayne/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:michal.morayne@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">michal.morayne@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-9856-479X" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="https://www.researchgate.net/profile/Antoni_Mitus" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&user=1znL0T0AAAAJ&view_op=list_works&sortby=pubdate" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6603253976"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=360659"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MaciejMulak" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Maciej-Mulak.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Maciej Mulak</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 223/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~mmulak/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:maciej.mulak@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">maciej.mulak@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-4754-1088" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Maciej_Mulak" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=Yt2puFwAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6602823921"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=355895"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="WojciechMulak" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Wojciech Mulak</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 223/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~wojciechmulak/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:wojciech.mulak@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">wojciech.mulak@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=351299"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="WojciechMydlarczyk" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr hab. Wojciech Mydlarczyk, prof. PWr</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 217/D1</p>
                                    <p><a href="https://cs.pwr.edu.pl/mydlarczyk/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:wojciech.mydlarczyk@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">wojciech.mydlarczyk@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-2387-9487" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Wojciech_Mydlarczyk" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=6506202906"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=344949"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="GrzegorzPawlik" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr hab. inż. Grzegorz Pawlik, prof. PWr</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 219/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~grpawlik/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:grzegorz.pawlik@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">grzegorz.pawlik@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-7342-4013" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Grzegorz_Pawlik" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=11iTnH0AAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=7007035077"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=414860"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="JaroslawPawlowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Jarek-Pawlowski.png" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Jarosław Pawłowski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 219/A1</p>
                                    <p><a href="https://wppt.pwr.edu.pl/pracownicy/jaroslaw-pawlowski"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:jaroslaw.pawlowski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">jaroslaw.pawlowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-3638-3966" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Jaroslaw_Pawlowski" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=xe34UC4AAAAJ&amp;hl=pl&amp;oi=sra" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56363096800"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=500400"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="WojciechRadosz" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Wojciech-Radosz.JPG" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Wojciech Radosz</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="http://www.if.pwr.wroc.pl/~wradosz/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:wojciech.radosz@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">wojciech.radosz@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-5641-3344" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Wojciech_Radosz" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=26GuWgnrfhAC" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=55551276500"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600294"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="KatarzynaRoszak" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/kasia_roszak.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr hab. inż. Katarzyna Roszak, prof. PWr</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262a/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~katarzynaroszak/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:katarzyna.roszak@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">katarzyna.roszak@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-9955-4331" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Katarzyna_Roszak" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=X0i4xd0AAAAJ&amp;hl=pl&amp;oi=ao" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=12242263200"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=435580"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AdamSajna" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Adam Sajna</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 220/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:adam.sajna@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">adam.sajna@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Adam_Sajna" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&amp;user=tN7oAKQAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56102478300"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600483"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AnnaSitek" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Ania-Sitek.png" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr hab. inż. Anna Sitek, prof. PWr</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 262a/A1</p>
                                    <p><a href="http://www.if.pwr.edu.pl/~sitek/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:anna.sitek@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">anna.sitek@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-0602-1959" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Anna_Sitek5" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=39_wOl8AAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=22735444200"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=451000"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="PiotrSurowka" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Piotr-Surowka.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Piotr Surówka</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 220/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:piotr.surowka@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">piotr.surowka@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-2204-9422" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=iv2yY8EAAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=23478735700"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>DONA</b></a> -->
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="JustynaTrzmiel" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/women_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr inż. Justyna Trzmiel</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 219/A1</p>
                                    <p><a href="https://wppt.pwr.edu.pl/pracownicy/justyna-trzmiel"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:justyna.trzmiel@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">justyna.trzmiel@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0001-8756-8130" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=Sy7lwGIAAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=13106271700"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=430830"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="DanielWigger" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Daniel-Wigger.jpeg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Dr Daniel Wigger</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 220/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:daniel.wigger@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">daniel.wigger@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-4190-8803" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=ch43xaYAAAAJ&amp;hl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=52365464200"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>DONA</b></a> -->
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="ArkadiuszWojs" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <!-- <h4 class="role">Rector</h4> -->
                                <img class="img-fluid" src="images/profiles/Arkadiusz-Wojs.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Prof. dr hab. inż. Arkadiusz Wójs</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 128/A1</p>
                                    <p><a href="http://aw.wppt.pwr.edu.pl"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:arkadiusz.wojs@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">arkadiusz.wojs@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-3624-5275" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Arkadiusz_Wojs" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=2DPVFdoAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=7004416503"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=355828"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                </div>

                --<div class="row center-xs center-sm center-md center-lg  middle-xs middle-sm middle-md middle-lg " style="
    background: aqua;
">

                    Our PhD students


                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AngelikaAbramiukSzurlej" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Angelika_Abramiuk-Szurlej.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr inż. Angelika <nobr>Abramiuk-Szurlej</nobr></h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:angelika.abramiuk@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext"><nobr>angelika.abramiuk-szurlej@pwr.edu.pl</nobr></span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-1495-0117" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Angelika_Abramiuk" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57209143171"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=904547"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#KatarzynaWeron"> <b><nobr>Katarzyna Sznajd-Weron</nobr></b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="KacperBrzuszek" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Kacper-Brzuszek.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr Kacper Brzuszek</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:kacper.brzuszek@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">kacper.brzuszek@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0003-1254-6775" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Kacper_Brzuszek" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=NGM9LOwAAAAJ&amp;hl=pl&amp;oi=ao" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57202507902"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802898"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#AndrzejJanutka"> <b>Andrzej Janutka</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="PawelBugajny" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr Paweł Bugajny</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 504a/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:pawel.bugajny@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">pawel.bugajny@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=55933022200"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802076"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#ArkadiuszWojs"> <b>Arkadiusz Wójs</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="DanielGroll" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Daniel Groll</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">Mail</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>DONA</b></a> -->
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#PawelMachnikowski"> <b>Paweł Machnikowski</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="ThiloHahn" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Thilo Hahn</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">Mail</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>DONA</b></a> -->
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#PawelMachnikowski"> <b>Paweł Machnikowski</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="KarolKawa" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Karol_Kawa.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr Karol Kawa</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="https://wwr.wppt.pwr.edu.pl/pracownicy/karol-kawa"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:karol.kawa@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">karol.kawa@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-3846-7437" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Karol_Kawa" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=OFSka-4AAAAJ&amp;hl=en&amp;oi=ao" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57210199150"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802705"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#PawelMachnikowski"> <b>Paweł Machnikowski</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MateuszKrzykowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Mateusz-Krzykowski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr Mateusz Krzykowski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="https://wwr.wppt.pwr.edu.pl/pracownicy/mateusz-krzykowski"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:mateusz.krzykowski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">mateusz.krzykowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-7585-4073" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Mateusz_Krzykowski" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=E5YqJW4AAAAJ&amp;hl=en" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57192154118"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="http://dona.pwr.edu.pl/szukaj/(X(1)S(leipulgtvodd11cmjvyfy5mf))/default.aspx?nrewid=802497"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#PawelMachnikowski"> <b>Paweł Machnikowski</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MichalKupczynski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Michal-Kupczynski.JPG" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr inż. Michał Kupczyński</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 504a/A1</p>
                                    <p><a href="https://wppt.pwr.edu.pl/pracownicy/michal-kupczynski"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:michal.kupczynski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">michal.kupczynski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-5802-2881" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=0aTIsJMAAAAJ&amp;hl=en" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57202804300"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802574"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#ArkadiuszWojs"> <b>Arkadiusz Wójs</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="BartoszKusmierz" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/men_1.svg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr Bartosz Kuśmierz</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 504a/A1</p>
                                    <!-- <p><a href="" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p> -->
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:bartosz.kusmierz@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">bartosz.kusmierz@pwr.edu.pl</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <!-- <div class="tooltip"><a href="" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div> -->
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=56444404000"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802040"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#ArkadiuszWojs"> <b>Arkadiusz Wójs</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="BartlomiejNowak" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Bartlomiej-Nowak.png" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr inż. Bartłomiej Nowak</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="http://prac.im.pwr.wroc.pl/~bnowak/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:bartlomiej.nowak@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">bartlomiej.nowak@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-9208-2570" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Bartlomiej_Nowak6" class="fab fa-researchgate" aria-hidden="true"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.pl/citations?user=YAhsrssAAAAJ&amp;hl=pl&amp;oi=ao" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57208597387"><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802687"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#KatarzynaWeron"> <b><nobr>Katarzyna Sznajd-Weron</nobr></b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="BartoszRzepkowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Bartosz-Rzepkowski.jpg" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr inż. Bartosz Rzepkowski</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="https://brzepkowski.github.io/"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:bartosz.rzepkowski@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">bartosz.rzepkowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-8002-1033 " class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=6JvJDlQAAAAJ&amp;hl=pl" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>Scopus</b></a>   -->
                                        <!-- <a href=""><i class="fas fa-link"></i> <b>DONA</b></a> -->
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#ArkadiuszWojs"> <b>Arkadiusz Wójs</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="MaksymilianSroda" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <img class="img-fluid" src="images/profiles/Maksymilian-Sroda.png" alt="Avatar" oncontextmenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 kolumna">
                                <div class="bucket">
                                    <h4>Mgr inż. Maksymilian Środa</h4>
                                    <p><i class="fas fa-home" aria-hidden="true"></i> 503/A1</p>
                                    <p><a href="http://maksymiliansroda.pl"><i class="fas fa-laptop-house" aria-hidden="true"></i> <b>homepage</b></a></p>
                                    <p>
                                        </p><div class="tooltip"><a href="mailto:maksymilian.sroda@pwr.edu.pl" class="fas fa-envelope" aria-hidden="true"></a><span class="tooltiptext">maksymilian.sroda@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-6767-921X" class="fab fa-orcid" aria-hidden="true"></a><span class="tooltiptext">Orcid</span></div>
                                        <!-- <div class="tooltip"><a href="" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div> -->
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?user=qSIkQ7gAAAAJ&amp;hl=en&amp;oi=ao" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    <p></p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?origin=resultslist&amp;authorId=57207938555&amp;zone="><i class="fas fa-link" aria-hidden="true"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=600421"><i class="fas fa-link" aria-hidden="true"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#MarcinMierzejewski"> <b>Marcin Mierzejewski</b></a></h3>
                            </div>
                        </div>
                    </div>
                    <!-- Koniec czlowieka -->
                    <!-- Poczatek człowieka -->
                    <!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 ludzik">
                        <div id="AndrzejWieckowski" class="row center-xs center-sm center-md center-lg middle-xs middle-sm middle-md middle-lg wizytowki">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <img class="img-fluid" src="images/profiles/Andrzej-Wieckowski.jpg" alt="Avatar" onContextMenu="return false;">
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <div class="bucket">
                                    <h3>Andrzej Więckowski</h3>
                                    <p><i class="fas fa-home"></i> 503/A1</p>
                                    <p><a href="https://andywiecko.github.io/about/" ><i class="fas fa-laptop-house"></i> <b>homepage</b></a></p>
                                    <p>
                                        <div class="tooltip"><a href="mailto:andrzej.wieckowski@pwr.edu.pl" class="fas fa-envelope"></a><span class="tooltiptext">andrzej.wieckowski@pwr.edu.pl</span></div>
                                        <div class="tooltip"><a href="https://orcid.org/0000-0002-8113-4021" class="fab fa-orcid"></a><span class="tooltiptext">Orcid</span></div>
                                        <div class="tooltip"><a href="https://www.researchgate.net/profile/Andrzej_Wieckowski3" class="fab fa-researchgate"></a><span class="tooltiptext">Researchgate</span></div>
                                        <div class="tooltip"><a href="https://scholar.google.com/citations?hl=pl&user=X9qrSQoAAAAJ" class="ai ai-google-scholar ai-3x"></a><span class="tooltiptext">Google-Scholar</span></div>
                                    </p>
                                    <p>
                                        <a href="https://www.scopus.com/authid/detail.uri?authorId=57212557407"><i class="fas fa-link"></i> <b>Scopus</b></a>
                                        <a href="https://dona.pwr.edu.pl/szukaj/default.aspx?nrewid=802603"><i class="fas fa-link"></i> <b>DONA</b></a>
                                    </p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3 class="supervisor">Supervisor:<a href="people.html#AndrzejJanutka" > <b>Andrzej Janutka</b></a></h3>
                            </div>
                        </div>
                    </div> -->
                    <!-- Koniec czlowieka -->
                </div>
*/