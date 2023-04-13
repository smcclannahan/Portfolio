<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Contains configuration data specific to this function
//**********************************************************************
?>

<?php


//----------------------------------------------------
// Customization Settings
//----------------------------------------------------

// These variables must be set correctly for the form to work

$BASE         = "http://nfl.mcclannahan.com";  // location of the nfl.php file
$IMAGES       = "$BASE/nfl_logos";             // location of the nfl team logo images
$my_domain    = "mcclannahan.com";             // only domain permitted to originate email



$hostname     = "localhost";
$database     = "gpmcom_nfl";
$user         = "gpmcom_player";
$password     = "fumble";

$SF  = 0;
$CHI = 1;
$CIN = 2;
$DEN = 3;
$CLE = 4;
$TB  = 5;
$BUF = 6;
$ARZ = 7;
$SDG = 8;
$KC  = 9;
$IND = 10;
$DAL = 11;
$MIA = 12;
$PHI = 13;
$ATL = 14;
$NYG = 15;
$JAX = 16;
$NYJ = 17;
$DET = 18;
$GB  = 19;
$CAR = 20;
$NE  = 21;
$LV  = 22;
$LAR = 23;
$BLT = 24;
$WAS = 25;
$NO  = 26;
$SEA = 27;
$PIT = 28;
$HOU = 29;
$TEN = 30;
$MN  = 31;

$wkseason = 18;
$ww       = 19;
$dp       = 20;
$cc       = 21;
$sb       = 22;
$inv      = 23;

$name_offset         = 0;
$pick_offset         = 3;
$pvalid_offset       = 69;
$tbrk_weekly_offset  = 25;
$tbrk_playoff_offset = 44;
$right_offset        = 45;
$tbrk_season_offset  = 45;
$weeks_won_offset    = 68;

$teamurl   = "https://espn.com/nfl/team/_/name/";
$teamlist  = array("sf/san-francisco-49ers","chi/chicago-bears","cin/cincinnati-bengals","den/denver-broncos","cle/cleveland-browns","tb/tampa-bay-buccaneers","buf/buffalo-bills","ari/arizona-cardinals","lac/los-angeles-chargers","kc/kansas-city-chiefs","ind/indianapolis-colts","dal/dallas-cowboys","mia/miami-dolpins","phi/philadelphia-eages","atl/atlanta-falcons","nyg/new-york-giants","jac/jacksonville-jaguars","nyj/new-york-jets","det-detroit-lions","gb/green-bay-packers","car/carolina-panthers","ne/new-england-patriots","lv/las-vegas-raiders","lar/los-angeles-rams","bal/baltimore-raves","wsh/washington","no/new-orleans-saints","sea/seattle-seahawks","pit/pittsburge-steelers","hou/houston-texans","ten/tennessee-titans","min/minnesota-vikings","tbd");

$divisions = array('AFC East', 'AFC North', 'AFC South', 'AFC West', 'NFC East', 'NFC North', 'NFC South', 'NFC West');
$team_div  = array(7,5,1,3,1,6,0,7,3,3,2,4,0,4,6,4,2,0,5,5,6,0,3,7,1,4,6,7,1,2,2,5);
$abbrev = array('SF','CHI','CIN','DEN','CLE','TB','BUF','ARZ','LAC','KC','IND','DAL','MIA','PHI','ATL','NYG','JAX','NYJ','DET','GB','CAR','NE','LV','LAR','BLT','WAS','NO','SEA','PIT','HOU','TEN','MN','No Pick');
$teams  = array('San Francisco 49ers','Chicago Bears','Cincinnati Bengals','Denver Broncos','Cleveland Browns','Tampa Bay Buccaneers','Buffalo Bills','Arizona Cardinals','Los Angeles Chargers','Kansas City Chiefs','Indianapolis Colts','Dallas Cowboys','Miami Dolphins','Philadelphia Eagles','Atlanta Falcons','New York Giants','Jacksonville Jaguars','New York Jets','Detroit Lions','Green Bay Packers','Carolina Panthers','New England Patriots','Las Vegas Raiders','Los Angeles Rams','Baltimore Ravens','Washington Team','New Orleans Saints','Seattle Seahawks','Pittsburgh Steelers','Houston Texans','Tennessee Titans','Minnesota Vikings','TBD');
$logos  = array("$IMAGES/49ers.gif","$IMAGES/bears.gif","$IMAGES/bengals.gif","$IMAGES/broncos.gif","$IMAGES/browns.gif","$IMAGES/buccaneers.gif","$IMAGES/buffalo.gif","$IMAGES/cardinals.gif","$IMAGES/chargers.gif","$IMAGES/chiefs.gif","$IMAGES/colts.gif","$IMAGES/cowboys.gif","$IMAGES/dolphins.gif","$IMAGES/eagles.gif","$IMAGES/falcons.gif","$IMAGES/giants.gif","$IMAGES/jaguars.gif","$IMAGES/jets.gif","$IMAGES/lions.gif","$IMAGES/packers.gif","$IMAGES/panthers.gif","$IMAGES/patriots.gif","$IMAGES/raiders.gif","$IMAGES/rams.gif","$IMAGES/ravens.gif","$IMAGES/redskins.gif","$IMAGES/saints.gif","$IMAGES/seahawks.gif","$IMAGES/steelers.gif","$IMAGES/texans.gif","$IMAGES/titans.gif","$IMAGES/vikings.gif","$IMAGES/nfl.gif");










//----------------------------------------------------
// Program settings - you shouldn't have to mess with
// anything below this point...
//----------------------------------------------------


?>

