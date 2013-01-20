codeyak-ndse-java
=================

n-dimensional scrabble engine in Java


Ever wondered what would happen if you got 15 Scrabble sets and played a game of 3d Scrabble?

Something like this (you'll need to view in raw txt format, no wrap)

***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** *****************  
***************** * G    M     O N* *         COXAL * * C    R     R  * *D           F  * *     HOARD  I T* * Y  NONGUILTS E* *RETE WN     HEW* * HARDENED     H* *          R  BI* * KYAR D   E   T* *   BIKIE  A  C * *    FIER  R    * *     T    O    * *CLUTCHED  S    * *  HOHED   EL   * *****************  
***************** * L  AWA  E  W A* *    DOVISH UH  * * A  ZOO L  RE  * *AD  ER QUODDED * * V K A  SI  N  * *RIZA R UH      * * S  JAY I  LIRE* * O  O   E      * * REALIZES    L * * YEN  O        * *   IDIOTIC   A * * PILE M O   T  * * E   OE        * *    AID      V * * RIPE      E   * *****************  
***************** *ROOFY ZA NEVELS* *   A  AN    I  * * V R  GI    D G* *R  C      ZOEAE* *   IF    POWN O* * L NE  N  ANTI * *   GIP       MY* *    JAFA     PA* *QI  ODOR     OP* *    A P      LO* *KYNDS        IN* * A   BE R  PUTS* * DOF I     U E * *  FERN OSSATURE* *  T        Z   * *****************  
***************** * WHYEVER T    T* *NAME  R        * * I  L E   C EE * *T        MONTH * *   V  C   I   P* *ILIAD  L  N   I* *  FIZ V   A QIS* *   ROTE B B I  * * LEYS R OWLY W * *     ZA WOE    * * E   E J W   QI* *EX   A  AE  R  * *  DA L BRENTEST* *  AYES       O * *   U    T      * *****************  
***************** *CWM   D  R    I* *      E        * * T TA R  Q     * *L  W ZAMOUSE   * *   A  H  A     * *   N   OPT     * *   G   DIS VAN * *  E     A L T G* * IN   I N  U BO* *  V    KEF    W* *JAY V  ATOC  UN* *A  JEFF TE TA M* *G DERE HEH    A* * VAES    N   UN* *I WRONGERS   G * *****************  
***************** * O       E A OE* *       FIREBOAT* * I  IT     L T * *E          E   * *N     I   K  JO* *EL     CROAKIER* * A  C  E  VIDE * * V ROSTELLAR L * * E  OH  O SKRY * * EE MU UT  S   * *OR   N TIX  PEE* * SNORT  O  RC E* *     SHUN     R* * I L          Y* *N  I    U    R * *****************  
***************** * R     W E L  R* *       A     B * * ED KOFF D   U * *S    REFLEW  G * *U  D  Z  NACH  * *QI U J KI BIO  * * C BHAJI   GID * *VAT  C    G  EN* *OVA  AR    O S * *WAXIER T   WOK * *EL   E O    L  * *DIOTAS  R  OI  * * E     T    V  * * R I        E  * *D ET    N   RAJ* *****************  
***************** * M       SLAG  * *           AHA * * SI  QI  J HIM * *     I ROE   IO* *         U RANI* *UM  WAN EXO    * * O  ABAFT      * * N  WAGE  G  AA* * OEDEMATA      * *  E  P A  ZIG  * *SI     S    O  * *        I RUN  * *  L      V     * * O A     A     * *U YE   ND Q  I * *****************  
***************** * S        OE   * *V    HAJ   WIT * *I C RURALITE   * *R    G YONI   B* *G      SUS OY O* *IN   B  D   AHA* *NE S ABYE   QIS* *ET EOLIAN I OPT* *D  S  GEE   N  * *  DA    D  ZAGS* * E M W     ISO * *  JOG   O UP O * *  OIL    O SUGH* * G DI    B     * *N R D  GLOUTING* *****************  
***************** *     V    U   C* *     AWAVE EMEU* *  T  E   NIXIES* *     S    FOLKS* *U  Q       N   * *P BI   PION G  * * L        AGIO * *   NEPER  N  TI* * B OXIDE     T * * U DOC  URE MOE* * SPINAE   F I  * * TE   LA  TIC  * * LA   O  D  R  * *JETSONP PAP O  * *ARY   EWE O NA * *****************  
***************** *   F      V    * *   I EAR   T D * * GAROTE    ACH * *   R     U K O * *N FIRETRAP E L * *E UN    EHING  * *FORGOING E     * *   S    LAG   V* *     REMOVED   * *      NIXER   C* *V ICED G DE V  * *I FIRE G   A  D* *VIFDA  SNOKED  * *AN      EMO I  * *S      ED P  G * *****************  
***************** *      BUAZE    * *     NYS  FOB  * *N T Z      DE  * *E   AUCTORIAL  * *W           FIB* *S C    QUOTERS * *YU    AINEE YO * *        I     I* *     E     U M * *  TUAN       IT* * REH E L  N EXO* *  E   JAROOL YO* *    BLAT U    N* *WEM    HEPT    * *       E   G E * *****************  
***************** * QUIETEN AR    * *         GOS   * * XEROTIC ABED  * *  PUB      Y   * *E IMITATED    U* *  C   NIX   OI * * S      O    R * *       ANNOYERS* *E         BA I * *L   BUBBAS R S * *E  TORII UM SI * *G SEXPOT N  MOW* *I  FY  O N  ON * *S      UN T W  * *T        JILT  * *****************  
***************** *QI  MIZ  G   S * *AR  AFAR UH  O * *TID N    AIS V * * DO YEA  V   R * *T T   BORAX ZAG* *  ALAPAS     N * *  L          S * *   Q CONI FUD T* *R  A      ER NU* *O  T    W MEL M* *R  S   FY E   U* *I    ALEE     L* *D TAEL T      O* *  DETECTED    U* *       STATIONS* *****************  
***************** *ISO ST   SIK   * *    TAXED N    * * S  E     B    * *TOLUENE   U    * * P  KYAT DIVA  * *  L  EN   L   U* *       QUITE  N* *  WITHWIND  S S* * MEDIAE B G PIT* *   EXO ILLUMINE* *       FAAN N P* *   GANT MUGGARS* *        ER  L  * *        DA  L  * *           MY  * *****************  
***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** ***************** *****************  

game took 18670ms, plays 374, score 12079

Awesome.


But why stop at 3 dimensions - here's an 8x8x8x8 grid

********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *       T* *   D    * *   O    * *ADZE    * *        * *        * *    F G * *       O* **********  
********** * V     U* *    G   * *  HWAN  * *N A WE  * * CHACKS * *   XI   * *U   E   * *    R F * **********  
********** *RIGORISM* *        * *      U * *N XI Y  * *  I     * *        * *    N   * *        * **********  
********** * V     O* *     M  * *      V * *O    E  * *  A     * *TRIATICS* *    I  A* *       L* **********  
********** * E D   R* *GAVEL   * *   R  A * *T  A    * *  NY    * *        * *GRITTEST* *        * **********  
********** *V      O* *AL M W  * *N JEHUS * *AVOW N  * *D T  N  * *O    E  * *U   AR  * *S      V* **********  
********** * J     U* *TAJES   * *  AHI   * *T W     * *        * *        * *    R   * *     M  * **********  
********** *HALTERES* *        * *  K     * *E N     * *  O     * *  W     * *  N S   * *     U  * **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *  O     * *  R   V * *  D     * * EA   I * *SALVOING* *  I     * *  A  QI * *  N    V* **********  
********** *    P   * *    U   * *    B   * *  DUE N * *  I     * *   I V  * *N    I  * *   C ERE* **********  
********** *      L * *     E  * *        * *   T  T * *  N     * *       N* * W    G * *WECHT  N* **********  
********** *  L     * *   CLOUD* *        * *     FE * *  G    L* *    R  A* *Q      Z* *   E   O* **********  
********** *   R    * *     C  * *OBLIQUES* * A TIRR * *ATUAS  U* *W      T* *       E* *   ZAG S* **********  
********** *  C     * * A IDE  * *  ANIMI * *    FEN * *  I    B* *        * * C     B* *K   ZA I* **********  
********** * I      * *     N  * *        * *  O  TEF* *  N    E* *        * *       U* *  M OU T* **********  
********** *O       * *R  IDE  * *O EM    * *N  P  D * *A ER C S* *S AE    * *A  G   B* *L  N N Y* **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *    G Q * *     QI * *  OBA   * *URP  V  * *     OU * *     D G* *   BLUB * *     NYE* **********  
********** *   QI U * *CLOUTING* *  XIS G * *   P  E * *   OO N * *   SKITE* *S   A L * *      E * **********  
********** *    N EA* *        * *        * *  RE    * *      V * *    AG A* *TAILZIES* *    O X * **********  
********** * POZZIES* *   H T  * *   O    * * QIS    * * U    E * *JO TIG R* *AI  O BO* * N  N  D* **********  
********** *   OO N * *F   PA  * *EA MI   * *TIG O   * *L   I I * *O   DE I* *C     B * *KORAI   * **********  
********** * AE  AYE* * N MIX  * * AYE    * * S  I   * *      L * *     R N* *KO  FEY * *I  TE  V* **********  
********** * Z      * * EMULOUS* *PLANING * *  G    A* *      E * *P   N  G* *E       * *T AT D  * **********  
********** *MOKE    * * PYNING * *  E     * *    C  B* *     IDE* *  L    S* *T      E* *E    I E* **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *  O    L* *  R   NO* *  BEGO W* *GOS    E* *    Q  R* *  TUI  I* *     O E* * MEALIER* **********  
********** *REBITES * *  R     * *  I    E* *R T     * *  T     * *     V  * *I       * *      E * **********  
********** *     MEW* * ZAMOUSE* *P DIP  D* *A  M    * *    V   * *   QI   * * Q  N N * *   JO   * **********  
********** *A Q A R * *   OW I * *    E AG* *V     N * *      T * *A GIN L * *N O U Y * *  AA   E* **********  
********** *L  OY   * *    EA  * * A US  Y* *ID     U* *        * *W       * *        * * REX   N* **********  
********** *F N  TEX* * CUED   * * R    P * *TO  N   * * C      * * KNIFED * * S  AXEL* *F  IDOLA* **********  
********** *AZOIC E * *      R * *        * *O      B* *      L * *O   E I * *N  BLUR * *E TE HIC* **********  
********** *SOOT  L * *    N   * *  N     * *N  AR   * *   GUV A* *U LID   * *   OE   * *D  S O T* **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *BRECCIA * *      AD* *RAIL B E* *       T* *   GABLE* *   R HIN* * GHI A T* * IMMIT S* **********  
********** *    A  L* *      WO* *     U T* *    T  E* * J  I   * * A OPEN * *LI  IN  * *  A   B * **********  
********** *    LOPE* *        * *L    R  * * J      * * O   HA * *  PURIN * * FEH ET * *  A N   * **********  
********** *  U P  N* *   G    * *     Q  * *        * *  B  EH * *PRATER  * *A YAR   * *DJEBEL  * **********  
********** *   K   V* * AVOWS  * * L S A  * *COW  I N* * WAGERS * * EXITS  * *G ED    * * AD W   * **********  
********** * ETIC  O* * H D    * *      L * * PELF   * *EOLIAN  * * A NA E * *OS D OH * *F   S  T* **********  
********** * E THEWY* *      E * *        * *  B    R* *DEED    * *OM EXINE* *GO I    * *E U  O  * **********  
********** *       S* *    G   * *ONSTEAD * *    O   * *     E R* *FEE     * *ENTERIC * *U    N  * **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *        * *        * * LETDOWN* *        * * HUITAIN* *        * *WOO  D  * * MOI E T* **********  
********** * S    QI* * E KAKIS* * A      * * MM     * * A      * *PIT  R  * *ED R  L * *     WOO* **********  
********** *REZ  XIS* * HAIRIF * *I     F * *TAURIC V* *        * *I AA O Z* * SAE FY * *     T R* **********  
********** * LAPSUS * *        * *   DRUID* *  M    O* * GORI   * *E BISE  * *T ET EM * *AA     S* **********  
********** *   I    * *       G* *      C * *KI  T  L* *IO      * *D UT   F* *   I RE * * LI    E* **********  
********** *  R    N* * I    JA* *  M   O * *    O  T* * BIODATA* * EN A AR* *   E N  * *ET     S* **********  
********** * S  IDEA* *      AG* *        * *   GOV I* *   I I  * *JO RUN A* *OX DRYLY* *LOR  L  * **********  
********** *       H* *  DEEPIE* *        * *    T   * *     T A* *ONY E  G* *HUI     * *M       * **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** *  R     * *L       * *        * *   A UFO* *VERBS   * *   B    * *  WE    * *   D  BE* **********  
********** * OY  Y  * *AHOY  Z * * I     S* *JARRED H* *   A   E* *   R   A* *N YE   F* * V E  OS* **********  
********** * DO  AL * *NI      * *EF   J  * *I P DOJO* *FRO  TOD* *   R SEA* *  N     * * I    W * **********  
********** *  T  R  * *G      Q* *  P  A I* *V A   A * * UH   R * * H    E * *      A * *UN    R * **********  
********** *   T KET* *R     TI* *  O  FAN* *Y R   PI* * G    EL* * I     Y* * DAVY   * *  A     * **********  
********** *  U     * *EN    E * *  O JAY * *  D ORE * *        * * S    V * *  DI    * *D       * **********  
********** *    Z   * *L     L * *  F AS  * * FAIX  C* *CONFITS * *AN  S   * *RE E    * *  E  E  * **********  
********** *    A   * *   AY   * *   V    * *  HOON  * *   Y   T* *  EE    * *   R    * *   S    * **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** * I     D* * N P    * * T I  J * * I L MAW* * M E UPO* * A A    * * C T  O * * Y E   P* **********  
********** *       A* *  OE    * *   TROUT* *  T  ORE* *  U  REX* *  Q     * *TRUEBLUE* *  E   T * **********  
********** *  O   YU* *  UR    * *    U N * *TROG POX* *  PANINI* *   E   S* * O    V * * F   R  * **********  
********** *  S  D R* *  T WO  * *    ATT * *  WOK HE* *GLOBATED* * AWES   * * ME   R * *TED  U  * **********  
********** *       I* *  L I   * *    N A * *   V  AD* *   A E I* * TOLAR  * *BOWIE I * * MED K  * **********  
********** *MAMZER N* * GI T U * *  ZEALS * *    T   * *       C* *    PIE * * REFACED* * E EDH  * **********  
********** *       G* *  ECH   * *   L    * *  BOONGS* *   F    * *HOUF D  * *EN S  R * *  R  S  * **********  
********** *        * *  R S   * *        * * LAWNY  * *HUGE   S* *ENSEWS  * * A    E * *        * **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
                                                                                                               
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  
********** ********** ********** ********** ********** ********** ********** ********** ********** **********  

game took 33677ms, plays 365, score 12309


Higher dimensions are supported, although they may be harder to fathom
