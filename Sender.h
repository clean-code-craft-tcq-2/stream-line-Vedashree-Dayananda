#define STATUS_OK true
#define STATUS_NOK false
#define TOTAL_NUMBER_OF_READINGS 50

void sendToConsole(float temperature, float stateOfCharge);
bool readBatteryParameters(float temperatureReadings[TOTAL_NUMBER_OF_READINGS], float stateOfChargeReadings[TOTAL_NUMBER_OF_READINGS], const char* filename);
bool readAndSendBatteryData(const char* filename);
