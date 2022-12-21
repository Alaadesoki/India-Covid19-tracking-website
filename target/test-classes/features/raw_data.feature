@Regression
Feature: India Covid19 tracking report


    #Test Scenario 1: Compare the downloaded CSV file to a similar file then to a different one -second test should fail-
  Scenario Outline: Compare newly generated CSV report to a similar file and different one
    Given India COVID API website is open
    When I download raw_data1 file
    Then I find the csv "<generated file>" report is the same as the newly generated report
    #Then I find the xsl of the report previously generated is similar to the newly generated xsl report
    Examples:
      | generated file         |
      | Positive scenario file |
      | Negative scenario file |

      #Test Scenario 2: Compare the downloaded Excel file to a similar file then to a different one -second test should fail-
#  Scenario Outline: Compare newly generated Excel report to a similar file and different one
#    Given India COVID API website is open
#    When I download raw_data1 file
#    Then I find the xsl "<generated file>" report previously generated is similar to the newly generated xsl report
#    Examples:
#      | generated file         |
#      | Positive scenario file |
#      | Negative scenario file |







