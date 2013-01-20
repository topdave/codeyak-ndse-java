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
