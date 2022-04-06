#include "Sender.h"
#include <iostream>
using namespace std;

void sendToConsole(float temperature, float stateOfCharge)
{
	cout << temperature << "," << stateOfCharge << endl;
}

bool readBatteryParameters(float temperatureReadings[TOTAL_NUMBER_OF_READINGS], float stateOfChargeReadings[TOTAL_NUMBER_OF_READINGS], const char* filename)
{
	FILE *fp;
	fp = fopen(filename, "r");
	if (fp)
	{
		float tempValue = 0.0, stateOfChargeValue = 0.0;
		for (int i = 0; fscanf(fp, "%f,%f\n", &tempValue, &stateOfChargeValue) != EOF; i++)
		{
			temperatureReadings[i] = tempValue;
			stateOfChargeReadings[i] = stateOfChargeValue;
		}
		fclose(fp);
		return STATUS_OK;
	}
	return STATUS_NOK;
}

bool readAndSendBatteryData(const char* filename)
{
	float temperatureReadings[TOTAL_NUMBER_OF_READINGS], stateOfChargeReadings[TOTAL_NUMBER_OF_READINGS];
	bool readStatus = readBatteryParameters(temperatureReadings, stateOfChargeReadings,filename);
	if (readStatus)
	{
		for (int i = 0; i < TOTAL_NUMBER_OF_READINGS; i++)
		{
			sendToConsole(temperatureReadings[i], stateOfChargeReadings[i]);
		}
	}
	return readStatus;
}
