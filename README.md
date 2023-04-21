Below you can find the test scenarios requested there are 6 of them The first 5 have been created

#Scenario 1: App behaves as expected on startup
##GIVEN
* The Calculator App has been started and no values have been entered
##WHEN
* I do nothing
##THEN
* The App context is correct
* AND The firstNumber/secondNumber have empty text values, are displayed, and have active textBoxes
* AND The hint values for the cta, firstNumber, secondNumber are correct
* AND The result, and error all have empty text values and are not displayed
* AND The loading icon is not displayed

#Scenario 2: App handles non int Correctly
##GIVEN
* The Calculator App has been started and no values have been entered
##WHEN
* I enter Special Chars & String values into the first/second number in the following combinations:
[firstNumber: Special Chars, secondNumber: String]
[firstNumber: String, secondNumber: Special Chars]
AND I click the Add button
##THEN
* The Load icon displays as it resolves the request
* AND I receive the InvalidInput error in the error field
* AND The results field remains empty
* AND The App reverts to Initial state aside from the error message
* AND The next first/second number combination can be requested

#Scenario 3: App handles Empty values Correctly
##GIVEN
* The Calculator App has been started and no values have been entered
##WHEN
* I enter Empty Values into the first/second number in the following combinations:
[firstNumber: EmptyValue, secondNumber: EmptyValue]
[firstNumber: ValidNumber, secondNumber: EmptyValue]
* And I click the Add button
##THEN
* The Load icon displays as it resolves the request
* AND I receive the EmptyInput error in the error field
* AND The results field remains empty
* AND The App reverts to Initial state aside from the error message
* AND The next first/second number combination can be requested

#Scenario 4: App handles high and low values correctly
##GIVEN
* The Calculator App has been started and no values have been entered
##WHEN
* I enter values outside the Min/Max allowed boundaries in the following combinations:
[firstNumber: Min - 1, secondNumber: ValidNumber]
[firstNumber: ValidNumber, secondNumber: Max + 1]
[firstNumber: Max + 1, secondNumber: Min - 1]
* And I click the Add button
##THEN
* The Load icon displays as it resolves the request
* AND I receive the Overflow error in the error field
* AND The App reverts to Initial state aside from the error message
* AND The next first/second number combination can be requested

##Scenario 5: App correctly adds valid values together
##GIVEN
* The Calculator App has been started and no values have been entered
##WHEN
* I enter valid Positive/Negative values in the following combinations:
[firstNumber: ValidNumber1, secondNumber: ValidNumber2]
* And I click the Add button
##THEN
* The Load icon displays as it resolves the request
* And The Error has a empty text value and is not displayed
* AND The result displays the firstNumber + secondNumber value
* AND The App reverts to Initial state aside from the result value
* AND The next first/second number combination can be requested

##Scenario 6: Added values remain editable after initial value entered
##GIVEN
* The Calculator App has been started and no values have been entered
##WHEN
* I enter any value into the firstNumber/secondNumber
* AND enter new values into the firstNumber/secondNumber
* AND I click the Add button
##THEN
* The result displays the second values entered
