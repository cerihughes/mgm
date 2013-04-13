package uk.co.cerihughes.denon.core.dao.impl.spotify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.co.cerihughes.denon.core.dao.EDaoType;
import uk.co.cerihughes.denon.core.dao.rest.ConverterException;
import uk.co.cerihughes.denon.core.model.Album;

public class TestSpotifyAlbumsForArtistJsonConverter
{
	private static final String SINGLE_RESULT_JSON = "{\"info\": {\"type\": \"artist\"}, \"artist\": {\"albums\": [{\"album\": {\"artist-id\": \"spotify:artist:1GTOtEnNA0hJBwfCuoo4dg\", \"name\": \"Sleep Education\", \"artist\": \"Jewellers\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"5055489207315\"}], \"released\": \"2011\", \"href\": \"spotify:album:71ilXXlwf8zuSMn9mpNkgx\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}], \"href\": \"spotify:artist:1GTOtEnNA0hJBwfCuoo4dg\", \"name\": \"Jewellers\"}}";
	private static final String MULTIPLE_RESULTS_JSON = "{\"info\": {\"type\": \"artist\"}, \"artist\": {\"albums\": [{\"album\": {\"artist-id\": \"spotify:artist:4h2QaToPkcxneMGGNFbGSU\", \"name\": \"After the Blackout\", \"artist\": \"Sugarman\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"602143473125\"}], \"released\": \"2013\", \"href\": \"spotify:album:67Pjc7GqUX6ZDFYNLxy93l\", \"availability\": {\"territories\": \"worldwide\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"artist-id\": \"spotify:artist:4h2QaToPkcxneMGGNFbGSU\", \"name\": \"Rio De Janeiro Party Club Hits (The Best Copacabana Limbo Top)\", \"artist\": \"Sugarman\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"887845277640\"}], \"released\": \"2013\", \"href\": \"spotify:album:2Mv6Rv7Zu2AdXiPc0vPQcf\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IM IN IO IQ IR IS IT JE JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"artist-id\": \"spotify:artist:4h2QaToPkcxneMGGNFbGSU\", \"name\": \"Rio De Janeiro Party Club Hits (The Best Copacabana Limbo Top 40)\", \"artist\": \"Sugarman\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"887845330116\"}], \"released\": \"2013\", \"href\": \"spotify:album:0ZH4FmFMMe8QzWyzjPvfmV\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IM IN IO IQ IR IS IT JE JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"artist-id\": \"spotify:artist:4h2QaToPkcxneMGGNFbGSU\", \"name\": \"Black Magic\", \"artist\": \"Sugarman\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3610150167187\"}], \"released\": \"2005\", \"href\": \"spotify:album:4XFgLSyHu1np4lbpnL4E87\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"New York Icy Lounge\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3661585920441\"}], \"released\": \"2011\", \"href\": \"spotify:album:35tSMiYGx7vt7yun8CtzwK\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Chill for Lovers\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3661585920533\"}], \"released\": \"2011\", \"href\": \"spotify:album:6WWeAnZsyGWaNXcsxIhLqi\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Celebrate Lounge Music, Vol. 2\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3661585768173\"}], \"released\": \"2011\", \"href\": \"spotify:album:2c3ECZfYKRsJPzP8RxdqXd\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Breezing Summer Chill y Club No.2\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3661585593041\"}], \"released\": \"2010\", \"href\": \"spotify:album:2KoNN995mK8jqdvLXiYRg2\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Chill Around the X-mas Tree\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3661585590866\"}], \"released\": \"2010\", \"href\": \"spotify:album:0XPXUcTai2RyQfWjOgIjWT\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Aspen Chill Lounge\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"3661585579533\"}], \"released\": \"2010\", \"href\": \"spotify:album:2i0URSRKqfrAdFEnNYgtLi\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Ibiza Chill Sensation\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"4029596000153\"}], \"released\": \"2009\", \"href\": \"spotify:album:0iuaFolSjGRSxcyo2TcYMm\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"New York Lounge Sensation - Special Edition\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"4029596000146\"}], \"released\": \"2009\", \"href\": \"spotify:album:4uzxEpmxKbQljHuNzz15e0\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Offshore Grooves\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"4029596000115\"}], \"released\": \"2009\", \"href\": \"spotify:album:29136iL5zbD3bBL6LnAWGv\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Lounge Area - Fit Your Day\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"4029596000061\"}], \"released\": \"2008\", \"href\": \"spotify:album:7jNjkZkJlWoblVJLbjBs24\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Lounge Area - All Night Long\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"4029596000085\"}], \"released\": \"2008\", \"href\": \"spotify:album:2JsXNUymTk4WkEb0RmWtvu\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Sugar Hut 'Thai Chill' Volume 1\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"844185010054\"}], \"released\": \"2008\", \"href\": \"spotify:album:6KeNBRFn2NfYVVNtS2tQt9\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}, {\"album\": {\"name\": \"Ibiza Chill Sensation 2007 Vol. 2\", \"artist\": \"Various Artists\", \"external-ids\": [{\"type\": \"upc\", \"id\": \"881969189421\"}], \"released\": \"2007\", \"href\": \"spotify:album:6y8zK1Ody1rV72O3hGnNlL\", \"availability\": {\"territories\": \"AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW ZZ\"}}, \"info\": {\"type\": \"album\"}}], \"href\": \"spotify:artist:4h2QaToPkcxneMGGNFbGSU\", \"name\": \"Sugarman\"}}";

	private SpotifyAlbumsForArtistJsonConverter cut;

	@Before
	public void setup()
	{
		cut = new SpotifyAlbumsForArtistJsonConverter();
	}

	private String getYear(Date date)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}

	@Test
	public void testSingleResult() throws ConverterException
	{
		final JSONObject json = new JSONObject(SINGLE_RESULT_JSON);
		final List<Album> result = cut.convert(json);
		Assert.assertEquals(1, result.size());
		final Album album = result.get(0);
		Assert.assertEquals(EDaoType.SPOTIFY_DIRECT, album.getSource());
		Assert.assertEquals("Sleep Education", album.getName());
		Assert.assertNull(album.getPopularity());
		Assert.assertEquals("spotify:album:71ilXXlwf8zuSMn9mpNkgx", album.getLocation());
		Assert.assertEquals("71ilXXlwf8zuSMn9mpNkgx", album.getId());
		Assert.assertEquals("Jewellers", album.getArtistName());
		Assert.assertEquals("2011", getYear(album.getReleaseDate()));
		Assert.assertEquals("1GTOtEnNA0hJBwfCuoo4dg", album.getAttribute(SpotifyJsonConverter.SPOTIFY_ARTIST_ID_ATTRIBUTE));
		Assert.assertEquals("71ilXXlwf8zuSMn9mpNkgx", album.getAttribute(SpotifyJsonConverter.SPOTIFY_ALBUM_ID_ATTRIBUTE));
		Assert.assertNull(album.getAttribute(SpotifyJsonConverter.SPOTIFY_TRACK_ID_ATTRIBUTE));
	}

	@Test
	public void testMultipleResults() throws ConverterException
	{
		final JSONObject json = new JSONObject(MULTIPLE_RESULTS_JSON);
		final List<Album> result = cut.convert(json);
		Assert.assertEquals(17, result.size());
		final Album album = result.get(0);
		Assert.assertEquals(EDaoType.SPOTIFY_DIRECT, album.getSource());
		Assert.assertEquals("After the Blackout", album.getName());
		Assert.assertNull(album.getPopularity());
		Assert.assertEquals("spotify:album:67Pjc7GqUX6ZDFYNLxy93l", album.getLocation());
		Assert.assertEquals("67Pjc7GqUX6ZDFYNLxy93l", album.getId());
		Assert.assertEquals("Sugarman", album.getArtistName());
		Assert.assertEquals("2013", getYear(album.getReleaseDate()));
		Assert.assertEquals("4h2QaToPkcxneMGGNFbGSU", album.getAttribute(SpotifyJsonConverter.SPOTIFY_ARTIST_ID_ATTRIBUTE));
		Assert.assertEquals("67Pjc7GqUX6ZDFYNLxy93l", album.getAttribute(SpotifyJsonConverter.SPOTIFY_ALBUM_ID_ATTRIBUTE));
		Assert.assertNull(album.getAttribute(SpotifyJsonConverter.SPOTIFY_TRACK_ID_ATTRIBUTE));
	}
}
