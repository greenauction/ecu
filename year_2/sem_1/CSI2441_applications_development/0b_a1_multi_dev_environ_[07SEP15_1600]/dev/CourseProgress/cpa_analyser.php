<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>CPA 2015</title>
</head>

<body>

<?php

require_once 'includes/php/Student.php';
use includes\Student;
require_once 'includes/php/Units.php';
use includes\Units;
require_once 'includes/php/BusinessRules.php';
use includes\BusinessRules;
require_once 'includes/php/Validator.php';
use includes\Validator;
require_once 'includes/php/View.php';
use includes\View;

/**
 * This php script accepts student and unit input data and presents
 * a course progression summary to the user, if input passes validation.
 *
 * It is designed and written in an object oriented style, attempting to implement MVC.
 * Student, Units and BusinessRules classes represent the Model,
 * Validator class (and $this::cpa_analyser.php) as the controller,
 * and the View class as the View.
 *
 * Basic and advanced validation/criteria/business rules,
 * per the assignment brief have been implemented.
 *
 * @author Martin Ponce, 10371381
 * @version 20150904
 */

// create instance of each object
$theStudent = new Student();
$theUnits = new Units();
$theRules = new BusinessRules();

// create theValidator, pass theStudent, theUnits, and theRules so it can use them to validate
$theValidator = new Validator($theStudent, $theUnits, $theRules);

// import theValidator to theStudent so it can use Validator->missingInputError() as it retrieves data
$theStudent->setValidator($theValidator);

// start theStudent and theUnits, these calls initiate the retrieval and storage of user input into arrays
$theStudent->startStudent();
$theUnits->startUnits();

// import the input arrays to theValidator
// this function also kicks off the validation, @see Validator->setInputArrays()
$theValidator->setInputArrays($theStudent->getStudentDetails(), $theUnits->getUnitDetails());

// if no validation errors,
if($theValidator->getStudentErrorTally() == 0 && $theValidator->getUnitErrorTally() == 0 && $theValidator->getLogicErrorTally() == 0) {
    // import input arrays to theRules,
    // this function also kicks off summary calculation, @see BusinessRules->setInputArrays()
    $theRules->setInputArrays($theStudent->getStudentDetails(), $theUnits->getUnitDetails());
    // then show me the summary view
    //$theSummaryView = new ViewSummary();
} else {
    // else there are errors, go to the error view immediately
    //$theErrorView = new ViewError();
}

/**
 * BEGIN TEST
 */
//echo(var_dump($theUnits->getUnitDetails()[0][Units::UM]));
/**
 * END TEST
 */

?>

<p>&nbsp;</p>
</body>
</html>
