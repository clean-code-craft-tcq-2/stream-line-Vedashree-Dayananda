#define CATCH_CONFIG_MAIN  // This tells Catch to provide a main() - only do this in one cpp file

#include "test/catch.hpp"
#include "Sender.h"

TEST_CASE("verify Battery Parameters read and sent to console") {
	bool status = readAndSendBatteryData("BatteryParameterData.txt");
	REQUIRE(status == STATUS_OK);

	//wrong file 
	status = readAndSendBatteryData("BatteryData.txt");
	REQUIRE(status == STATUS_NOK);
}

TEST_CASE("verify Battery Parameters read") {
	float temperatureReadings[TOTAL_NUMBER_OF_READINGS], stateOfChargeReadings[TOTAL_NUMBER_OF_READINGS];
	bool status = readBatteryParameters(temperatureReadings, stateOfChargeReadings, "BatteryParameterData.txt");
	REQUIRE(status == STATUS_OK);

	REQUIRE(temperatureReadings[0] == 17);
	REQUIRE(stateOfChargeReadings[0] == 25);

	REQUIRE(temperatureReadings[24] == 44);
	REQUIRE(stateOfChargeReadings[24] == 32);

	REQUIRE(temperatureReadings[49] == 0);
	REQUIRE(stateOfChargeReadings[49] == 68);

	//wrong file 
	status = readBatteryParameters(temperatureReadings, stateOfChargeReadings, "BatteryData.txt");;
	REQUIRE(status == STATUS_NOK);
}
