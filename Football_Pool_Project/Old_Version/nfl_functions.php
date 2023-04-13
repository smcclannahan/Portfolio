<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Contains useful functions
//**********************************************************************
?>

<?php


//==========================================================================
// Function: us_time($sqltime)
// Args: SQL Format time YYYY-MM-DD
// PreConditions:
// Returns: US Format time MM/DD/YYYY
// Description: converts time for SQL format to US format
//==========================================================================
function f_us_date($sqldate){

  $date_chunks = explode('-',$sqldate);
  $usdate      = $date_chunks[1]."/".$date_chunks[2]."/".$date_chunks[0];

  return $usdate;

}

//==========================================================================
// Function: us_time($sqltime)
// Args: SQL Format Time HH:MM:SS (24 hour format)
// PreConditions:
// Returns: US Format Time HH:MM AM/PM
// Description: converts time for SQL format to US format
//==========================================================================
function f_us_time($sqltime){

  $timechunks  = explode(':',$sqltime);
  $ampm = " PM";
  if ($timechunks[0] < 12) {
    $ampm = " AM";
  }
  else if ($timechunks[0] > 12) {
    $timechunks[0] -= 12;
  }
  $ustime   = $timechunks[0].":".$timechunks[1].$ampm;

  return $ustime;

}

?>

<?php
